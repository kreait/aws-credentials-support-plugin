import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.2.71"
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.10.0"
}

group = "com.kreait.gradle"
version = "0.0.1-SNAPSHOT"
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
            displayName = "AWS Credentials Support"
            description = "Helps to develop locally and managing different aws credential sets"
            implementationClass = "com.kreait.gradle.aws.AwsCredentialsProviderPlugin"
        }
    }
}

pluginBundle {
    website = "http://kreait.com"
    vcsUrl = "https://github.com/kreait/aws-credentials-support-plugin.git"
    tags = listOf("maven", "aws", "s3", "repository", "credentials")
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
