FROM quay.io/keycloak/keycloak:24.0.5

USER 0
COPY ./greenbuildings.json /tmp
COPY ./target/greenbuildings-testcontainer-plugin.jar /opt/keycloak/providers
RUN /opt/keycloak/bin/kc.sh build --health-enabled=true
RUN /opt/keycloak/bin/kc.sh import --file /tmp/greenbuildings.json
