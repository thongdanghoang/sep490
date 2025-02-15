podman manifest rm thongdh3401/keycloak:24.0.5

# First, initialise the manifest
podman manifest create thongdh3401/keycloak:24.0.5

# Build the image attaching them to the manifest
podman build --platform linux/amd64,linux/arm64  --manifest thongdh3401/keycloak:24.0.5  .

# Finally publish the manifest
podman manifest push thongdh3401/keycloak:24.0.5