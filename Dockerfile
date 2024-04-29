FROM eclipse-temurin:21-jre-ubi9-minimal
LABEL maintainer="Sublinks Core Developers <hello@sublinks.org>"
LABEL description="Backend API service for Sublinks"

WORKDIR /app
COPY build/libs/sublinks-api-*.jar /app/sublinks-api.jar

EXPOSE 8080
CMD ["java", "-jar", "sublinks-api.jar"]