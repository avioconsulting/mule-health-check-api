%dw 2.0
output application/json
---
{     
	"statusCode" : "500",
	"error" : {    
		"errorType": "MODULE-HEALTH-CHECK:ERROR",
		"message" : error.detailedDescription
	}
}