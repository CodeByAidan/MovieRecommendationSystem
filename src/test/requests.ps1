$uri = "http://localhost:8080/getMoviesByGenre"
$headers = @{
    "Access-Control-Request-Method" = "POST"
    "Content-Type" = "application/json"
}
$body = @{
    "genre" = "Action"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri $uri -Method Post -Headers $headers -Body $body

$response