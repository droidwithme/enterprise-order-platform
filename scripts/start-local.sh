#!/bin/bash
set -e

echo "Building services..."
./gradlew :order-service:bootJar
./gradlew :notification-service:bootJar
./gradlew :user-service:bootJar

echo "Building Docker images..."
docker build -t order-service:1.0 order-service
docker build -t notification-service:1.0 notification-service
docker build -t user-service:1.0 user-service

echo "Starting full system with Docker Compose..."
docker compose -f docker-compose.full.yml up -d

echo "Waiting for services..."
sleep 20

echo ""
echo "System is up!"
echo "Swagger: http://localhost:8081/swagger-ui.html"
echo "Postgres: localhost:5432 (user: postgres / pass: postgres)"
echo ""
echo "Test flow:"
echo "1. Open Swagger"
echo "2. Create Order"
echo "3. Approve Order"
echo "4. Check notifications/orders/outbox_event tables"
echo "5. Open Grafana dashboard -> http://localhost:3000/"
echo "6. Open Prometheus metrics -> http://localhost:9090/"
