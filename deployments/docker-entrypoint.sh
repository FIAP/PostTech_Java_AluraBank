#!/bin/bash

set -e

/opt/keycloak/bin/kc.sh start --http-port=$PORT --proxy=edge \
                              --hostname-strict=false --hostname-strict-https=false \
                              --db=postgres --db-url=$KC_DB_URL \
                              --db-username=$KC_DB_USER --db-password=$KC_DB_PASSWORD \
                              --health-enabled=true --metrics-enabled=true

