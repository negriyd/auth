Get access token
http -a my-client:my-secret --form POST http://localhost:3555/oauth/token username=test@test.com password=tester grant_type=password

Request secured resource (via browser)
http://localhost:3555/api/hello?name=Seb&access_token=<access_token>