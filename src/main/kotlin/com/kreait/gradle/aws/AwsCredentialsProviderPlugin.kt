package com.kreait.gradle.aws

import org.gradle.api.Plugin
import org.gradle.api.Project

class AwsCredentialsProviderPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.extensions.create("AwsCredentialsSupport", AwsCredentialsSupport::class.java)
    }

}
