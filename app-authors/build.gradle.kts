plugins {
    id("java")
    id("io.quarkus") version "3.40.1.1"
    id("io.freefair.lombok") version "9.8.3"
}

group = "com.programcion.distribuida"
version = "unspecified"

repositories {
    mavenCentral()
}

val quarkusversion = "3.40.1.1"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

dependencies {
    implementation(enforcedPlatform("io.quarkus:quarkus-bom:${quarkusversion}"))
    //CDI
    implementation("io.quarkus:quarkus-arc")


    //REST
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jsonb")

    //JPA
    implementation("io.quarkus:quarkus-hibernate-orm-rest-data-panache")
    implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-jdbc-postgresql")

    //control de cambios DB
    implementation("io.quarkus:quarkus-flyway")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:13.12.2")


    //Service Discovery
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-consul-client")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}