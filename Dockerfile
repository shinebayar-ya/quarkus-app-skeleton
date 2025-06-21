# Stage 1: Build with Mandrel (native build support)
FROM quay.io/quarkus/ubi-quarkus-mandrel-builder-image:jdk-21 AS build

ENV MAVEN_CLI_OPTS='--batch-mode -Dmaven.repo.local=/opt/app/.m2/repository'

# Copy Maven wrapper and configuration (if exists)
COPY --chown=1001 mvnw /opt/app/mvnw
COPY --chown=1001 .mvn /opt/app/.mvn
# Optional: settings.xml (ignore if missing)
# Only copy if you actually have it, or use default Maven settings
# COPY --chown=1001 .m2/settings.xml /opt/app/.m2/

COPY pom.xml /opt/app/
WORKDIR /opt/app

# Download dependencies
RUN ./mvnw $MAVEN_CLI_OPTS dependency:go-offline

# Copy source code & build
COPY src /opt/app/src
RUN ./mvnw $MAVEN_CLI_OPTS clean package -DskipTests

# Stage 2: Final container (JVM mode)
FROM registry.access.redhat.com/ubi8/openjdk-21:1.20

ENV LANGUAGE='en_US:en'

# Copy built application layers
COPY --from=build --chown=185 /opt/app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build --chown=185 /opt/app/target/quarkus-app/*.jar /deployments/
COPY --from=build --chown=185 /opt/app/target/quarkus-app/app/ /deployments/app/
COPY --from=build --chown=185 /opt/app/target/quarkus-app/quarkus/ /deployments/quarkus/

# Create temp directories for runtime use
RUN mkdir -p /tmp/uploads /tmp/downloads && chown -R 185:0 /tmp/uploads /tmp/downloads

EXPOSE 8080

USER 185

ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]
