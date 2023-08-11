#!/bin/bash

uri="http://localhost:8080/loadtitle"

headers=$(
  cat <<EOF
Content-Type: application/json
EOF
)

response=$(curl -X POST -H "$headers" "$uri")

echo "$response"
