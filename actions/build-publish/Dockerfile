FROM openjdk:11-jdk-slim
CMD ./gradlew $JAVA_OPTS clean check build publishPlugins -Dgradle.publish.key=$GRADLE_PLUGIN_PORTAL_KEY -Dgradle.publish.secret=$GRADLE_PLUGIN_PORTAL_SECRET --info
