#!/bin/bash

# Config
ENDPOINT="http://localhost:4566"
REGION="us-east-1"
ACCOUNT_ID="000000000000"

# Queues
QUEUES=("order-queue" "user-queue" "payment-queue")

# SES Email
SES_EMAIL="test@mail.com"

# Topics SNS
TOPICS=("order-events" "user-events" "payment-events")

echo "== Criando filas SQS =="
for QUEUE in "${QUEUES[@]}"; do
  aws --endpoint-url=$ENDPOINT sqs create-queue --queue-name $QUEUE --region $REGION
done

echo "== Verificando email no SES =="
aws --endpoint-url=$ENDPOINT ses verify-email-identity --email-address $SES_EMAIL --region $REGION

echo "== Criando tópicos SNS =="
for TOPIC in "${TOPICS[@]}"; do
  aws --endpoint-url=$ENDPOINT sns create-topic --name $TOPIC --region $REGION
done

echo "== Script finalizado com sucesso! =="
