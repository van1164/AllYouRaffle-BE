import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("plugin.jpa") version "1.9.24"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    kotlin("plugin.allopen") version "2.0.0"
    kotlin("kapt") version "1.9.24"
    idea
}

group = "com.van1164"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val queryDslVersion: String by extra

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    // ## Spring Boot 기본 스타터 의존성
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // JPA 사용
    implementation("org.springframework.boot:spring-boot-starter-mail") // 이메일 서비스

    // ## Spring Security 관련 의존성
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client") // OAuth2 클라이언트 지원
    implementation("org.springframework.boot:spring-boot-starter-security") // 보안 관련 기능
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf") // 템플릿 엔진 Thymeleaf
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6") // Thymeleaf와 Spring Security 통합
    testImplementation("org.springframework.security:spring-security-test") // Spring Security 테스트 지원

    // ## JWT 관련 의존성
    implementation("io.jsonwebtoken:jjwt:0.9.1") // JWT 토큰 사용

    // ## JAXB 관련 의존성 (XML 바인딩)
    implementation("com.sun.xml.bind:jaxb-impl:4.0.1") // JAXB 구현체
    implementation("com.sun.xml.bind:jaxb-core:4.0.1") // JAXB 코어
    // javax.xml.bind
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359") // JAXB API

    // ## Spring Web 및 Jackson 관련 의존성
    implementation("org.springframework.boot:spring-boot-starter-web") // 웹 애플리케이션 개발
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // Kotlin과 Jackson 통합
    implementation("org.jetbrains.kotlin:kotlin-reflect") // Kotlin 리플렉션
    implementation("org.springframework.boot:spring-boot-starter-validation") // 데이터 유효성 검사

    // Kotlin 표준 라이브러리
    implementation(kotlin("stdlib-jdk8"))

    // ## Lombok
    compileOnly("org.projectlombok:lombok") // 코드 자동 생성을 위한 Lombok
    annotationProcessor("org.projectlombok:lombok") // Lombok 애노테이션 처리기

    // ## 테스트 관련 의존성
    testImplementation("org.springframework.boot:spring-boot-starter-test") // 통합 테스트 지원
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5") // JUnit5와 Kotlin 테스트 통합
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc") // Spring REST Docs 사용
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") // JUnit 플랫폼 런처

    // ## 테스트 데이터를 위한 Fixture Monkey
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter-kotlin:1.0.20") // Fixture Monkey 스타터
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-jakarta-validation:1.0.20") // Jakarta 유효성 검증

    // ## 성능 테스트용 k6 Executor
    // https://mvnrepository.com/artifact/io.github.van1164/k6-executor
    implementation("io.github.van1164:k6-executor:0.6.0") // k6 Executor

    // ## P6Spy (SQL 로그 감시)
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0") // P6Spy 통합

    // ## 데이터베이스 및 MySQL 커넥터
    implementation("com.mysql:mysql-connector-j") // MySQL 데이터베이스 커넥터

    // ## Redis 관련 의존성
    implementation("org.springframework.boot:spring-boot-starter-data-redis") // Redis 데이터베이스 사용
    implementation("org.redisson:redisson-spring-boot-starter:3.31.0") // Redis 클라이언트 Redisson 사용

    // ## QueryDSL 사용
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta") // QueryDSL JPA 지원
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta") // QueryDSL APT
    kapt("jakarta.annotation:jakarta.annotation-api") // Jakarta 애노테이션 API
    kapt("jakarta.persistence:jakarta.persistence-api") // Jakarta Persistence API

    // ## Kotlin Coroutines 관련 의존성
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1") // 코어 코루틴
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.1") // Reactor와 코루틴 통합

    // ## AWS 통합
    // https://mvnrepository.com/artifact/io.awspring.cloud/spring-cloud-starter-aws
    implementation("io.awspring.cloud:spring-cloud-starter-aws:2.4.4") // AWS 서비스 통합

    // ## OpenAPI 지원
    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0") // OpenAPI UI 지원

    // ## Google API 클라이언트
    // https://mvnrepository.com/artifact/com.google.api-client/google-api-client
    implementation("com.google.api-client:google-api-client:2.6.0") // Google API 클라이언트
    // https://mvnrepository.com/artifact/com.google.oauth-client/google-oauth-client
    implementation("com.google.oauth-client:google-oauth-client:1.36.0") // Google OAuth 클라이언트
    // https://mvnrepository.com/artifact/com.google.http-client/google-http-client-gson
    implementation("com.google.http-client:google-http-client-gson:1.44.2") // Google HTTP 클라이언트와 Gson 통합

    // ## 메시지 API 통합
    implementation("net.nurigo:sdk:4.3.0") // Nurigo 메시지 SDK

    // ## Firebase 관리
    // https://mvnrepository.com/artifact/com.google.firebase/firebase-admin
    implementation("com.google.firebase:firebase-admin:9.3.0") // Firebase Admin SDK
}

val generated = file("src/main/generated")
tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(generated)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

sourceSets {
    main {
        kotlin.srcDirs += generated
    }
}

tasks.named("clean") {
    doLast {
        generated.deleteRecursively()
    }
}

kapt {
    generateStubs = true
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
    inputs.dir(project.extra["snippetsDir"]!!)
    dependsOn(tasks.test)
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}
