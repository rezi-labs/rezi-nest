POST {{target}}/api/chat
api-key: very-secure
Content-Type: application/json
[Options]
very-verbose: true
file,prompt.json;


HTTP 200

[Asserts]
jsonpath "$.content" exists