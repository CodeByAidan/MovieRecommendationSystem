#!/bin/bash

uri="http://localhost:8080/getMoviesByGenre"

headers=$(
  cat <<EOF
Content-Type: application/json
EOF
)

body=$(
  cat <<EOF
{
    "genre": "Action"
}
EOF
)

response=$(curl -X POST -H "$headers" -d "$body" "$uri")

echo "$response"
