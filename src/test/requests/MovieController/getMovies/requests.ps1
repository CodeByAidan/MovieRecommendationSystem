$uri = "http://localhost:8080/getMovies"
$headers = @{
    "Access-Control-Request-Method" = "GET"
    "Content-Type" = "application/json"
}

$response = Invoke-RestMethod -Uri $uri -Method Get -Headers $headers

$response