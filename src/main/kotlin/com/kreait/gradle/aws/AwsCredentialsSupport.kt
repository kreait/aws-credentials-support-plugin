package com.kreait.gradle.aws

import com.amazonaws.auth.AWSCredentialsProviderChain
import com.amazonaws.auth.BasicSessionCredentials
import com.amazonaws.auth.EC2ContainerCredentialsProviderWrapper
import com.amazonaws.auth.SystemPropertiesCredentialsProvider
import com.amazonaws.auth.profile.ProfileCredentialsProvider

/**
 * Configurable CredentialsProvider for AWS Credentials
 */
open class AwsCredentialsSupport {

    companion object {

        @JvmStatic
        fun credentials(): Credentials {
            return credentials(null)
        }

        /**
         * Fetches Credentials with an AWS Credentials Provider Chain
         * @return [Credentials]
         */
        @JvmStatic
        fun credentials(profile: String?): Credentials {

            try {
                val credentials = AWSCredentialsProviderChain(
                        EC2ContainerCredentialsProviderWrapper(),
                        SystemPropertiesCredentialsProvider(),
                        ProfileCredentialsProvider(profile)
                ).credentials

                return Credentials(credentials.awsAccessKeyId, credentials.awsSecretKey, (credentials as? BasicSessionCredentials)?.sessionToken)

            } catch (e: Exception) {
                throw IllegalStateException(e)
            }

        }
    }
}
