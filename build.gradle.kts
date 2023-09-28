import java.net.URI

plugins {
	java
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	id("com.jfrog.artifactory") version "5.1.0"
}

val springBootGrpc = "2.14.0.RELEASE"
val grpcVersion = "1.55.1"
val protobufVersion = "3.23.2"

group = "com.shire42"
version = "0.0.1-SNAPSHOT"


java {
	sourceCompatibility = JavaVersion.VERSION_17
}

allprojects {
	apply(plugin = "com.jfrog.artifactory")


	repositories {
		apply(plugin = "com.jfrog.artifactory")
		group = findProperty("group").toString()
		version = findProperty("version").toString()
		status = "Integration"
		maven {
			url = URI("http://127.0.0.1:8081/artifactory/gradle-dev-local")
			credentials {
				username = findProperty("artifactory_user").toString()
				password = findProperty("artifactory_password").toString()
			}
		}
		maven {
			url = URI("http://127.0.0.1:8081/artifactory/libs-release")
			credentials {
				username = findProperty("artifactory_user").toString()
				password = findProperty("artifactory_password").toString()
			}
		}
	}
}

buildscript {
	repositories {
		mavenCentral()

	}
	dependencies {
		classpath("org.jfrog.buildinfo:build-info-extractor-gradle:4+")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("software.amazon.awssdk:dynamodb-enhanced:2.20.153")
	implementation("br.com.shire42:proto-service:1.0.2")

	implementation("io.grpc:grpc-stub:$grpcVersion")
	implementation("com.google.protobuf:protobuf-java-util:$protobufVersion")
	implementation("net.devh:grpc-server-spring-boot-starter:$springBootGrpc")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
