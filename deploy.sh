#!/bin/bash

# Check if kubectl is installed
if ! [ -x "$(command -v kubectl)" ]; then
  echo 'Error: kubectl is not installed.' >&2
  exit 1
fi

option=$1

case $option in
"database")
  echo "Deploying database..."
  kubectl apply -f k8s/db/
  ;;
"services")
  echo "Deploying services..."
  kubectl apply -f k8s/
  ;;
*)
  kubectl apply -f k8s/db/
  kubectl apply -f k8s/
  ;;
esac
