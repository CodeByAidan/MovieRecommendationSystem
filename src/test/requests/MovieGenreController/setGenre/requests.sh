#!/bin/bash

uri="http://localhost:8080/setTitle"

headers=$(
  cat <<EOF
Content-Type: application/json
EOF
)

body=$(
  cat <<EOF
{
    "movieId": 58559
    "title": "The Dark Knight",
}
EOF
)

response=$(curl -X POST -H "$headers" -d "$body" "$uri")

echo "$response"
