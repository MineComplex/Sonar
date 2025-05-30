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

package xyz.jonesdev.sonar.api.config;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum Language {
  SYSTEM("system"),
  AB("Abkhazian"),
  AA("Afar"),
  AF("Afrikaans"),
  SQ("Albanian"),
  AM("Amharic"),
  AR("Arabic"),
  AN("Aragonese"),
  HY("Armenian"),
  AS("Assamese"),
  AE("Avestan"),
  AY("Aymara"),
  AZ("Azerbaijani"),
  BA("Bashkir"),
  EU("Basque"),
  BE("Belarusian"),
  BN("Bengali"),
  BH("Bihari"),
  BI("Bislama"),
  BS("Bosnian"),
  BR("Breton"),
  BG("Bulgarian"),
  MY("Burmese"),
  CA("Catalan"),
  CH("Chamorro"),
  CE("Chechen"),
  ZH("Chinese"),
  CU("Slavic"),
  CV("Chuvash"),
  KW("Cornish"),
  CO("Corsican"),
  HR("Croatian"),
  CS("Czech"),
  DA("Danish"),
  DV("Maldivian"),
  NL("Dutch"),
  DZ("Dzongkha"),
  EN("English"),
  EO("Esperanto"),
  ET("Estonian"),
  FO("Faroese"),
  FJ("Fijian"),
  FI("Finnish"),
  FR("French"),
  GD("Gaelic"),
  GL("Galician"),
  KA("Georgian"),
  DE("German"),
  EL("Greek"),
  GN("Guarani"),
  GU("Gujarati"),
  HT("Haitian Creole"),
  HA("Hausa"),
  HE("Hebrew"),
  HZ("Herero"),
  HI("Hindi"),
  HO("Hiri Motu"),
  HU("Hungarian"),
  IS("Icelandic"),
  IO("Ido"),
  ID("Indonesian"),
  IE("Interlingue"),
  IU("Inuktitut"),
  IK("Inupiaq"),
  GA("Irish"),
  IT("Italian"),
  JA("Japanese"),
  JV("Javanese"),
  KL("Kalaallisut"),
  KN("Kannada"),
  KS("Kashmiri"),
  KK("Kazakh"),
  KM("Khmer"),
  KI("Kikuyu"),
  RW("Kinyarwanda"),
  KY("Kirghiz"),
  KV("Komi"),
  KO("Korean"),
  KJ("Kwanyama"),
  KU("Kurdish"),
  LO("Lao"),
  LA("Latin"),
  LV("Latvian"),
  LI("Limburgish"),
  LN("Lingala"),
  LT("Lithuanian"),
  LB("Luxembourgish"),
  MK("Macedonian"),
  MG("Malagasy"),
  MS("Malay"),
  ML("Malayalam"),
  MT("Maltese"),
  GV("Manx"),
  MI("Maori"),
  MR("Marathi"),
  MH("Marshallese"),
  MO("Moldavian"),
  MN("Mongolian"),
  NA("Nauru"),
  NV("Navajo"),
  ND("Ndebele, North"),
  NR("Ndebele, South"),
  NG("Ndonga"),
  NE("Nepali"),
  SE("Northern Sami"),
  NO("Norwegian"),
  NB("Norwegian Bokmal"),
  NN("Norwegian Nynorsk"),
  NY("Chewa"),
  OC("Occitan"),
  OR("Oriya"),
  OM("Oromo"),
  OS("Ossetian"),
  PI("Pali"),
  PA("Panjabi"),
  FA("Persian"),
  PL("Polish"),
  PT("Portuguese"),
  PT_BR("Brazilian Portuguese", "pt-br"),
  PS("Pushto"),
  QU("Quechua"),
  RM("Raeto-Romance"),
  RO("Romanian"),
  RN("Rundi"),
  RU("Russian"),
  SM("Samoan"),
  SG("Sango"),
  SA("Sanskrit"),
  SC("Sardinian"),
  SR("Serbian"),
  SN("Shona"),
  II("Sichuan Yi"),
  SD("Sindhi"),
  SI("Sinhala"),
  SK("Slovak"),
  SL("Slovenian"),
  SO("Somali"),
  ST("Sotho, Southern"),
  ES("Spanish"),
  SU("Sundanese"),
  SW("Swahili"),
  SS("Swati"),
  SV("Swedish"),
  TL("Tagalog"),
  TY("Tahitian"),
  TG("Tajik"),
  TA("Tamil"),
  TT("Tatar"),
  TE("Telugu"),
  TH("Thai"),
  BO("Tibetan"),
  TI("Tigrinya"),
  TO("Tonga"),
  TS("Tsonga"),
  TN("Tswana"),
  TR("Turkish"),
  TK("Turkmen"),
  TW("Twi"),
  UG("Uighur"),
  UK("Ukrainian"),
  UR("Urdu"),
  UZ("Uzbek"),
  VI("Vietnamese"),
  VO("Volapuk"),
  WA("Walloon"),
  CY("Welsh"),
  FY("Western Frisian"),
  WO("Wolof"),
  XH("Xhosa"),
  YI("Yiddish"),
  YO("Yoruba"),
  ZA("Zhuang"),
  ZU("Zulu");

  private final String name;
  private final String code;

  Language(final String name) {
    this.name = name;
    this.code = name().toLowerCase();
  }

  Language(final String name, final String code) {
    this.name = name;
    this.code = code;
  }

  public static Language fromCode(final @NotNull String code) {
    for (final Language language : values()) {
      if (language.code.equalsIgnoreCase(code)) {
        return language;
      }
    }
    // Fallback to English
    return EN;
  }
}
