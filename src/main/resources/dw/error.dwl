%dw 2.0
output application/json
---
{     
	"statusCode" : "500",
	"error" : {    
		"errorType": (error.errorType.namespace default "MULE") ++ ":" ++ (error.errorType.identifier default "ERROR"),
		"message" : error.detailedDescription
	}
}