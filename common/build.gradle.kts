dependencies {
  compileOnly(project(":api"))
  implementation(project(":captcha"))
  compileOnly(rootProject.libs.adventure.nbt)
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17
