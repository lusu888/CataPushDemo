# Requirement: Python3 HTTP Client library (see https://docs.python.org/3/library/http.client.html)
import http.client

conn = http.client.HTTPSConnection("api.catapush.com")

payload = '{"mobileAppId":2266,"text":"action test", "optionalData": {"buttonLabel": "Tap me", "buttonAction": "com.example.app.INTENT_ACTION_NOTIFICATION_BUTTON"},"recipients":[{"identifier":"lusu888"}]}'
headers = {
    'accept': "application/json",
    'content-type': "application/json",
    'authorization': "Bearer c54336fa865ca563a9ecfde09e0ab6db0b304d5c"
    }

conn.request("POST", "/1/messages", payload, headers)

res = conn.getresponse()
data = res.read()

print(data.decode("utf-8"))
