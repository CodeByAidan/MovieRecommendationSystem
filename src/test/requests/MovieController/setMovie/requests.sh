#!/bin/bash

uri="http://localhost:8080/setGenre"

headers=$(
  cat <<EOF
Content-Type: application/json
EOF
)

body=$(
  cat <<EOF
{
    "genre": "Action|Crime|Drama",
    "movieId": 58559
}
EOF
)

response=$(curl -X POST -H "$headers" -d "$body" "$uri")

echo "$response"
