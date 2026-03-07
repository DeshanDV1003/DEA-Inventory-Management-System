#!/bin/bash
# Manual deploy script - pass service names as arguments
# Usage: ./deploy.sh frontend
# Usage: ./deploy.sh product-service user-service
# Usage: ./deploy.sh all

cd /home/ec2-user/DEA-Inventory-Management-System
git pull origin dev
cd backend

if [ "$1" = "all" ] || [ -z "$1" ]; then
  echo "Rebuilding all services..."
  docker compose down
  docker compose up -d --build
else
  for SERVICE in "$@"; do
    echo "Rebuilding $SERVICE..."
    docker compose up -d --build --no-deps "$SERVICE"
  done
fi

echo "Deployment completed at $(date)"
