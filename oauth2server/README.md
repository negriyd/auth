Get access token
http -a my-client:my-secret --form POST http://localhost:3555/oauth/token username=test@test.com password=tester grant_type=password

Result:
{
    "error": "unsupported_grant_type",
    "error_description": "Unsupported grant type: &#39;password&#39;"
}