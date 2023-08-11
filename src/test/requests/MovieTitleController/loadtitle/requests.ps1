$uri = "http://localhost:8080/loadtitle"
$headers = @{
    "Access-Control-Request-Method" = "POST"
    "Content-Type" = "application/json"
}

$response = Invoke-RestMethod -Uri $uri -Method Post -Headers $headers

$response