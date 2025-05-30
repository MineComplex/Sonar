/*
 * Copyright (C) 2025 Sonar Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.jonesdev.sonar.common.fallback.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.DecoderException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import xyz.jonesdev.sonar.api.fallback.protocol.ProtocolVersion;
import xyz.jonesdev.sonar.common.util.ProtocolUtil;
import xyz.jonesdev.sonar.common.util.exception.QuietDecoderException;

import static xyz.jonesdev.sonar.common.fallback.protocol.FallbackPreparer.maxTotalPacketsSent;

@RequiredArgsConstructor
public final class FallbackPacketDecoder extends ChannelInboundHandlerAdapter {
  private final ProtocolVersion protocolVersion;
  private FallbackPacketRegistry.ProtocolRegistry registry;
  @Setter
  private FallbackPacketListener listener;
  private int totalPacketsSent;

  public void updateRegistry(final @NotNull FallbackPacketRegistry registry) {
    this.registry = registry.getProtocolRegistry(FallbackPacketRegistry.Direction.SERVERBOUND, protocolVersion);
  }

  @Override
  public void channelRead(final @NotNull ChannelHandlerContext ctx, final Object msg) throws Exception {
    if (msg instanceof ByteBuf) {
      final ByteBuf byteBuf = (ByteBuf) msg;

      try {
        // Release the ByteBuf if the connection is not active
        // or the ByteBuf doesn't contain any data to avoid
        // memory leaks or other potential exploits.
        if (!ctx.channel().isActive() || !byteBuf.isReadable()) {
          return;
        }

        // Don't allow the player to spam packets to overload netty
        if (++totalPacketsSent > maxTotalPacketsSent) {
          throw ProtocolUtil.DEBUG ? new DecoderException("Sent too many packets") : QuietDecoderException.INSTANCE;
        }

        // Read the packet ID and then create the packet from it
        final int packetId = ProtocolUtil.readVarInt(byteBuf);
        final FallbackPacket packet = registry.createPacket(packetId);

        // Skip the packet if it's not registered within Sonar's packet registry
        if (packet == null) {
          return;
        }

        // Ensure that the packet isn't too large or too small
        checkPacketSize(byteBuf.readableBytes(), packet);

        try {
          // Try to decode the packet for the given protocol version
          packet.decode(byteBuf, protocolVersion);
        } catch (Throwable throwable) {
          throw ProtocolUtil.DEBUG ? new DecoderException(throwable) : QuietDecoderException.INSTANCE;
        }

        // Check if the packet still has bytes left after we decoded it
        if (byteBuf.isReadable()) {
          throw ProtocolUtil.DEBUG ? new DecoderException("Could not read packet to end ("
            + byteBuf.readableBytes() + " bytes left)") : QuietDecoderException.INSTANCE;
        }

        // Let our verification handler process the packet
        if (listener != null) {
          listener.handle(packet);
          // Make sure to let the timeout handler know about this packet
          ctx.fireChannelRead(packet);
        }
      } finally {
        // Release the ByteBuf to avoid memory leaks
        byteBuf.release();
      }
    }
  }

  private void checkPacketSize(final int packetSize, final @NotNull FallbackPacket packet) throws Exception {
    final int expectedMaxLen = packet.expectedMaxLength(protocolVersion);
    if (expectedMaxLen != -1 && packetSize > expectedMaxLen) {
      throw ProtocolUtil.DEBUG ? new DecoderException("Packet too large: " + packetSize + " max: " + expectedMaxLen)
        : QuietDecoderException.INSTANCE;
    }
    final int expectedMinLen = packet.expectedMinLength(protocolVersion);
    if (expectedMinLen != -1 && packetSize < expectedMinLen) {
      throw ProtocolUtil.DEBUG ? new DecoderException("Packet too small: " + packetSize + " min: " + expectedMinLen)
        : QuietDecoderException.INSTANCE;
    }
  }
}
