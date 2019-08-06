import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    kotlin("jvm") version "1.2.71"
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.10.1"
}

group = "com.kreait.gradle"
version = "0.0.3-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation(kotlin("gradle-plugin"))
    implementation(kotlin("gradle-plugin-api"))
    implementation(group = "com.amazonaws", name = "aws-java-sdk-core", version = "1.11.603")
}

gradlePlugin {
    plugins {
        register("awsCredentialsPlugin") {
            id = "com.kreait.gradle.aws"
            implementationClass = "com.kreait.gradle.aws.AwsCredentialsProviderPlugin"
        }
    }
}

pluginBundle {
    website = "http://kreait.com"
    vcsUrl = "https://github.com/kreait/aws-credentials-support-plugin.git"
    description = "Helps to develop locally and managing different aws credential sets"
    tags = listOf("maven", "aws", "s3", "repository", "credentials")

    (plugins) {
        "awsCredentialsPlugin" {
            id = "com.kreait.gradle.aws"
            displayName = "AWS Credentials Support"
        }
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}


val sourcesJar by tasks.creating(Jar::class) {
    dependsOn("classes")
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}


publishing {
    repositories {
        gradlePluginPortal()
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourcesJar)

            groupId = rootProject.group as? String
            artifactId = rootProject.name
            version = rootProject.version as? String

            pom {
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                scm {
                    url.set("https://github.com/kreait/aws-credentials-support-plugin")
                }
            }
        }
    }
}
