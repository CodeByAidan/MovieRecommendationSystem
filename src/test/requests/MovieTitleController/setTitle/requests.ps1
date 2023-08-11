$uri = "http://localhost:8080/setTitle"
$headers = @{
    "Access-Control-Request-Method" = "POST"
    "Content-Type" = "application/json"
}
$body = @{
    "movieId" = 58559
    "title" = "The Dark Knight"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri $uri -Method Post -Headers $headers -Body $body

$response