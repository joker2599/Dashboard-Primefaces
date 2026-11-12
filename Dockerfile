# syntax=docker/dockerfile:1
#
# Multi-stage: Maven JDK 21 construye el EAR → Payara 6 (Jakarta EE 10) lo despliega.
# Context root del WAR dentro del EAR: /primefaces-charts-dashboard
#

# ---------------------------------------------------------------------------
# Stage 1 — build
# ---------------------------------------------------------------------------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build

# Copiar POMs primero (mejor cache de dependencias)
COPY pom.xml .
COPY primefaces-charts-dashboard-ejb/pom.xml primefaces-charts-dashboard-ejb/
COPY primefaces-charts-dashboard-web/pom.xml primefaces-charts-dashboard-web/
COPY primefaces-charts-dashboard-ear/pom.xml primefaces-charts-dashboard-ear/

# Descargar deps del reactor (falla sin sources; se tolera / se completa en package)
RUN mvn -B -q -DskipTests dependency:go-offline || true

# Fuentes
COPY primefaces-charts-dashboard-ejb/src primefaces-charts-dashboard-ejb/src
COPY primefaces-charts-dashboard-web/src primefaces-charts-dashboard-web/src
COPY primefaces-charts-dashboard-ear/src primefaces-charts-dashboard-ear/src

RUN mvn -B -DskipTests clean package \
 && test -f primefaces-charts-dashboard-ear/target/primefaces-charts-dashboard.ear

# ---------------------------------------------------------------------------
# Stage 2 — runtime Payara 6 + JDK 21
# ---------------------------------------------------------------------------
FROM payara/server-full:6.2025.11-jdk21

LABEL org.opencontainers.image.title="PrimeFaces Charts Dashboard" \
      org.opencontainers.image.description="EAR Jakarta EE 10 / PrimeFaces 14 en Payara 6" \
      org.opencontainers.image.source="https://github.com/joker2599/Dashboard-Primefaces"

# $DEPLOY_DIR = /opt/payara/deployments (entrypoint de Payara autodeploya al arrancar)
# Ruta absoluta = $DEPLOY_DIR de la imagen oficial Payara
COPY --from=build --chown=payara:payara \
     /build/primefaces-charts-dashboard-ear/target/primefaces-charts-dashboard.ear \
     /opt/payara/deployments/primefaces-charts-dashboard.ear

# 8080 HTTP · 4848 Admin Console · 8181 HTTPS
EXPOSE 8080 4848 8181
