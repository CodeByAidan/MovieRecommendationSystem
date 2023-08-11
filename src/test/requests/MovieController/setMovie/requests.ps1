$uri = "http://localhost:8080/setMovie"
$headers = @{
    "Access-Control-Request-Method" = "POST"
    "Content-Type" = "application/json"
}
$body = @{
    "title" = "The Dark Knight"
    "genre" = "Action|Crime|Drama"
    "movieId" = 58559
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri $uri -Method Post -Headers $headers -Body $body

$response