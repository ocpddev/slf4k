plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
}

group = "dev.ocpd.slf4k"

java {
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    api(libs.slf4j)
    // required to check if a class is a companion object
    // see: dev.ocpd.slf4k.Slf4JKt#unwrapCompanion
    implementation(kotlin("reflect"))
    testImplementation(libs.bundles.logback)
    testImplementation(kotlin("test-junit5"))
}

tasks.named<Jar>("javadocJar") {
    from(tasks.named("dokkaJavadoc"))
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name = "slf4k"
                description = "Kotlin extensions for SLF4J"
                url = "https://github.com/ocpddev/slf4k"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                scm {
                    url = "https://github.com/ocpddev/slf4k"
                }
                developers {
                    developer {
                        id = "sola"
                        name = "Sola"
                        email = "sola@ocpd.dev"
                    }
                }
            }
        }
    }
    repositories {
        maven {
            val releaseUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")

            url = if (version.toString().endsWith("-SNAPSHOT")) snapshotUrl else releaseUrl

            credentials {
                username = project.findSecret("ossrh.username", "OSSRH_USERNAME")
                password = project.findSecret("ossrh.password", "OSSRH_PASSWORD")
            }
        }
    }
}

signing {
    val key = findSecret("ocpd.sign.key", "OCPD_SIGN_KEY")
    if (key != null) {
        val keyId = findSecret("ocpd.sign.key.id", "OCPD_SIGN_KEY_ID")
        val passphrase = findSecret("ocpd.sign.passphrase", "OCPD_SIGN_PASSPHRASE") ?: ""
        useInMemoryPgpKeys(keyId, key, passphrase)
    }
    sign(publishing.publications["maven"])
}

fun Project.findSecret(key: String, env: String): String? =
    project.findProperty(key) as? String ?: System.getenv(env)
