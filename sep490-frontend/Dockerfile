FROM docker.io/bitnami/nginx:1.25

COPY dist/frontend/browser /app
COPY nginx.conf /opt/bitnami/nginx/conf/nginx.conf

USER root
RUN chown 1001 /opt/bitnami/nginx/conf/nginx.conf

USER 1001
