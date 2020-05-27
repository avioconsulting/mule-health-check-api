# Health Check API
The  **health check api**  is a reusabale library consisting of mule flows and java classes packaged into a  **mule application**  . This  can be added as dependency to your project through Maven . This Mule Library when imported into your Mule project gives your application additional resources and capabilities to  **GET**  general statistical information of the Operating System Server , Mule Runtime and the Java Virtual Machine your application is deployed into . In additional to this it can also get specific application related information about the connectivity status of your mule application from the deployed environment to other application/services/systems which you  **configure**  it to interact with .

There a multiple parts that needs handling for this library to make is seamlessly integrate with you application.


## Adding a pom dependency :

Under <repositories> make sure to include anypoint-exchange which is required for AVIO custom logger which for this app.

```
<repository> 
    <id>anypoint-exchange</id> 
    <name>Anypoint Exchange</name>
    <url>https://maven.anypoint.mulesoft.com/api/v1/maven</url> 
    <layout>default</layout> 
</repository>
```

## Properties Configuration :

There are a couple of properties that need to be set in your properties file for properly configuring Health Check API.

#### 1. HTTP Configuration (Non-Optional):

This is a non-optional property that is expected by Health Check HTTP listener and exposes endpoints on top of that listener.

>***healthcheck.https.configuration.reference=Your_HTTP_Listener_Configuration_Ref***

such that YOUR_HTTP_Listener_Configuration is the HTTP listener config-ref of your implementing API.

```
<http:listener doc:name="Listener" config-ref="Your_HTTP_Listener_config_Ref" path="/*"> 
</http:listener>
```

#### 2. Global Configuration Import (Non-Optional):

```
<import doc:name="Import" file="health-check-api.xml"/>
```
This is a non-optional configuration. health-check-api.xml contains the actual implementation flows of the Health Check which will be invoked.

#### 3. Logging Configuration (Optional) :

Health Check configuration has a default logging category and is configured as com.avioconsulting.healthcheck.

>***log.healthcheck.category=com.avioconsulting.healthcheck (by default)***

If you are implementing health-check on multiple projects its ideal to override this property.

>***log.healthcheck.category=com.avioconsulting.{YOUR-PROJECT}.healthcheck***

#### 4. Testing Application Dependencies (Optional):

>***healthcheck.flows=db-health-flow,http-endpoint-subflow***

These are the implementations of an API developer who wants to test their apps external dependencies to database, AWS, REST and SOAP services, etc.

Property should have a value of at least one flow and if-more comma-separated name that matches the implementing flow.

```
<flow name="db-health-flow"> 
    <!--Implementation Logic--> 
</flow> 

<sub-flow name="http-endpoint-subflow"> 
	<!--Implementation Logic--> 
</sub-flow>

```

The flow should return a java payload with required Status ENUM being OK, ERROR or UNKNOWN.

OK - Developer should send OK status if the flow is successful.
```
{ 
	systemName: "DB", 
	connectionConfig: "jdbc:sqlserver://<server-url>;<db>;<user>", 
	user : "N/A", 
	status: "OK", 
	timeStamp : "2020-02-02 11:12:13" 
}
```

ERROR - Developer should send ERROR status if the flow is unsuccessful.
```
{ 
	"systemName": "Amazon S3", 
	"connectionConfig": "N/A", 
	"user": "N/A", 
	"status": "ERROR", 
	"exception": "This machine does not have access to the bucket", 
	"timestamp": "2020-01-07 10:43:21" 
}
```

UNKNOWN- Behaviour of the health check API when it doesn't find the flow. Developers shouldn't send this status in response.
```
{ 
	"systemName": "<Incorrect flow Name Value>", "connectionConfig": "N/A", 
	"user": "N/A", 
	"status": "UNKNOWN", 
	"exception": "Could Not Find the Flow", 
	"timestamp": "2020-01-07 10:43:21" 
}
```

#### 5. Overriding health-check autodiscovery on implementation (Non-Optional)

API discovery for Health Check API needs to be added in the properties file so that certain policies can be applied to it regardless of implementing API policies.

>***healthcheck.apiId= Autodiscovery_API_ID***


### Requesting access to health-check-api on Exchange

Once health check API is implemented on your project you need to request access to health-check-api from your application.


## RESOURCES

The following resources will be added to the base uri of your application :

***<base_uri>/monitor/health***
    
***<base_uri>/monitor/health/runtime***
    
***<base_uri>/monitor/health/app***

Eg :  base_uri= http://api.test.com/example/api/v1

if everything configured correctly 
GET to http://api.test.com/example/api/v1/monitor/health  should give the following result :

```
{
    "muleProperties": {
        "muleRuntime": "4.2.2",
        "apiName": "health-check-api",
        "server": "AC0084",
        "url": "http://localhost:8081/lib/health/check",
        "ip": "0.0.0.0",
        "timeZone": "America/Chicago",
        "environment": "DEV"
    },
    "jvmProperties": {
        "pid": "29488@AC0084",
        "vmName": "Java HotSpot(TM) 64-Bit Server VM",
        "vmVendor": "Oracle Corporation",
        "vmVersion": "1.8.0_231",
        "locale": "English (United States)",
        "memory": {
            "heapMemoryUsage": {
                "init": 264241152,
                "used": 212208352,
                "committed": 513802240,
                "max": 3756523520
            },
            "nonHeapMemoryUsage": {
                "init": 2555904,
                "used": 61171016,
                "committed": 62193664,
                "max": -1
            }
        },
        "threadCount": {
            "activeThreadCount": 54,
            "daemonThreadCount": 48
        },
        "garbageCollection": [{
                "gcName": "PS Scavenge",
                "collectionCount": 8,
                "collectionTime": 67
            }, {
                "gcName": "PS MarkSweep",
                "collectionCount": 2,
                "collectionTime": 221
            }
        ]
    },
    "osProperties": {
        "osName": "Windows 10",
        "osVersion": "10.0",
        "osArchitechture": "amd64",
        "systemLoadAverage": -1.0,
        "availableProcessors": 12,
        "freePhysicalMemorySize": 3750350848,
        "processCpuTime": 14437500000,
        "systemCpuLoad": 0.0,
        "processCpuLoad": 0.05657935521623634
    },
    "timestamp": "2019-12-27T11:23:18.168-06:00",
    "correlationId": "925bfc10-28cd-11ea-b774-105bad3268d7",
    "systems": [{
            "systemName": "HTTPS",
            "connectionConfig": "api-dev.srs.aws.avioconsulting.com:443/orders-system-api",
            "user": "N/A",
            "status": "OK",
            "exception": "N/A",
            "timestamp": "2020-01-07 10:43:11"
        }, {
            "systemName": "Amazon S3",
            "connectionConfig": "N/A",
            "user": "N/A",
            "status": "ERROR",
            "exception": "N/A",
            "timestamp": "2020-01-07 10:43:21"
        }, {
            "systemName": "SQLServer",
            "connectionConfig": "jdbc:sqlserver://localhost;ODSAPI;ODSApiApp",
            "user": "N/A",
            "status": "UNKNOWN",
            "exception": "N/A",
            "timestamp": "2020-01-07 10:43:34"
        }
    ]
}
```

call to other resources will return their specific set of data. 

For more information about health check api and client specific implementation please refer to the following [knowledge base article link ]([https://avioconsulting.atlassian.net/wiki/spaces/AOUM/pages/721748341/Implement+Health+Check+API+on+Mule+4](https://avioconsulting.atlassian.net/wiki/spaces/AOUM/pages/721748341/Implement+Health+Check+API+on+Mule+4))