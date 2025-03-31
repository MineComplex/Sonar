rootProject.name = "Sonar"

sequenceOf(
  "api",
  "captcha",
  "common",
  "velocity"
).forEach {
  include(":$it")
}
