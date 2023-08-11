#!/bin/bash

uri="http://localhost:8080/getMovies"

headers=$(
  cat <<EOF
Content-Type: application/json
EOF
)

response=$(curl -X GET -H "$headers" "$uri")

echo "$response"
