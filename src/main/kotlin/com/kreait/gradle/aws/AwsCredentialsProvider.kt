package com.kreait.gradle.aws

import com.amazonaws.auth.AWSCredentialsProviderChain
import com.amazonaws.auth.BasicSessionCredentials
import com.amazonaws.auth.EC2ContainerCredentialsProviderWrapper
import com.amazonaws.auth.SystemPropertiesCredentialsProvider
import com.amazonaws.auth.profile.ProfileCredentialsProvider

object AwsCredentialsProvider {

    fun credentials(): AwsCredentials {
        return credentials(null)
    }

    /**
     * Fetch credentials based on given profile
     * @return [Credentials]
     */
    fun credentials(profile: String?): AwsCredentials {

        try {
            val credentials = AWSCredentialsProviderChain(
                    EC2ContainerCredentialsProviderWrapper(),
                    SystemPropertiesCredentialsProvider(),
                    ProfileCredentialsProvider(profile)
            ).credentials

            return AwsCredentials(credentials.awsAccessKeyId, credentials.awsSecretKey, (credentials as? BasicSessionCredentials)?.sessionToken)

        } catch (e: Exception) {
            throw IllegalStateException(e)
        }

    }
}
