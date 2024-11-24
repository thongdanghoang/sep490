FROM eclipse-temurin:21.0.2_13-jdk-alpine

# Add a non-root user for better security
RUN addgroup -S idpuser && adduser -S idpuser -G idpuser

# Set the working directory and switch to non-root user
WORKDIR /app
USER idpuser:idpuser

COPY build/libs/*.jar app.jar

HEALTHCHECK --interval=30s --timeout=3s \
    CMD ["wget", "--no-verbose", "--tries=1", "--spider", "http://localhost:8080/actuator/health"]

ENTRYPOINT ["java", \
  "-XX:UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-jar", \
  "app.jar" \
]
