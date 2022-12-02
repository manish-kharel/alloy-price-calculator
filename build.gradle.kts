import com.google.protobuf.gradle.id
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
  java
  id("org.springframework.boot") version "2.7.5"
  id("io.spring.dependency-management") version "1.0.15.RELEASE"
  id ("org.jetbrains.kotlin.plugin.jpa") version "1.6.0"
  id("com.google.protobuf") version "0.9.1"
  kotlin("jvm") version "1.6.21"
  kotlin("plugin.spring") version "1.6.21"
}

noArg { annotation("com.aoe.alloypricecalculator") }
allOpen {
  annotation("com.aoe.alloypricecalculator")
}

group = "com.aoe"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17


repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//  implementation("org.liquibase:liquibase-core")
  implementation("mysql:mysql-connector-java:8.0.30")
  testImplementation("org.springframework.boot:spring-boot-starter-test")

  //misc
  implementation("org.json:json:20211205")

  //database
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")

// Resilience4J
//  implementation("io.github.resilience4j:resilience4j-spring-boot2")

  // Caching
  implementation("org.ehcache:ehcache")
  implementation("javax.cache:cache-api")

  //grpc
  implementation("io.grpc:grpc-protobuf:1.51.0")
  implementation ("io.grpc:grpc-netty:1.51.0")
  implementation("io.grpc:grpc-kotlin-stub:1.3.0")

  //not needed .. cleanup
  //  implementation("com.google.protobuf:protobuf-kotlin:3.21.10")
  //  implementation("io.grpc:grpc-stub:1.51.0")
  //  implementation("com.google.protobuf:protobuf-java:3.21.9")
}

protobuf {
  protoc {
    artifact = "com.google.protobuf:protoc:3.14.0"
  }
  plugins {
    id("grpc") {
      artifact = "io.grpc:protoc-gen-grpc-java:1.51.0"
    }
  }
  generateProtoTasks {
    ofSourceSet("main").forEach {
      it.plugins {
        id("grpc")
      }
    }
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
