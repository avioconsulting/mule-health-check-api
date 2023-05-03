# Health Check API
The  **health check api** is a connector (version 3 was implemented using the **XML SDK**) it can be added as a maven dependency. When imported into your Mule project gives your application additional resources and capabilities to **GET** general statistical information of the Operating System Server , Mule Runtime and the Java Virtual Machine your application is deployed into. In addition to this it can also be configured to get the connectivity status of application/services/systems being used by your app.


## Setup
### Repository and dependency

1. Grab latest code from repo and configure the pom.xml <distributionManagement> to deploy to either exchange or your client repository manager. Be sure to have a <server> with the same id configured on your .m2/settings.xml

Exchange example:
```
<distributionManagement>
    <repository>
        <id>anypoint-exchange-v3</id>
        <name>Exchange Repository</name>
        <url>https://maven.anypoint.mulesoft.com/api/v3/organizations/${project.groupId}/maven</url>
    </repository>
    <snapshotRepository>
        <id>anypoint-exchange-v3</id>
        <name>Exchange Snapshot Repository</name>
        <url>https://maven.anypoint.mulesoft.com/api/v3/organizations/${project.groupId}/maven</url>
    </snapshotRepository>
</distributionManagement>
```

2. Add dependency to your project
```
<dependency>
  <groupId>com.avioconsulting.mule</groupId>
  <artifactId>mule-health-check-api</artifactId>
  <version>3.0.0-SNAPSHOT</version>
</dependency>
```

### RAML and API Instance

If you want the health-check to have it’s own client application credentials (we usually do) then we need to create an API instance. We need to copy the RAML to Design Studio, publish to Exchange and create an API instance from it.

## Usage

Create a listener / http configuration:

- Use public port 8081 or 8082
- Use /monitor/* as the Listener Path

If needed create and API Autodiscovery configuration with the Instance ID created previously.
- select the flow that contains the health-check Listener

The functionality now lives inside one **operation**, we only need to create a connector configuration with the following properties:
- **attributes**: Use default value. Should always be the attributes predefined var.
- **environment**: Environment Name. It defaults to ‘env’ property.
- **log category**: Required value without default to force user to enter value.
- **external systems**: comma separated list of flow names to handle external systems
  - The flow should return a payload with required status enum (OK, ERROR, UNKNOWN)
  - **OK** - Developer should send OK status if the flow is successful:
  ```
  { 
    systemName: "DB", 
    connectionConfig: "jdbc:sqlserver://<server-url>;<db>;<user>", 
    user : "N/A", 
    status: "OK", 
    timeStamp : "2020-02-02 11:12:13" 
  }
  ```
  
  - **ERROR** - Developer should send ERROR status if the flow is unsuccessful:
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
  
  - **UNKNOWN** - Behaviour of the health check API when it doesn't find the flow. Developers shouldn't send this status in response:
  ```
  { 
    "systemName": "<Incorrect flow Name Value>", "connectionConfig": "N/A", 
    "user": "N/A", 
    "status": "UNKNOWN", 
    "exception": "Could Not Find the Flow", 
    "timestamp": "2020-01-07 10:43:21" 
  }
  ```

## Resources

The following resources will be added to the base URI of your application :

<base_uri>**/monitor/health**

<base_uri>**/monitor/health/runtime**

<base_uri>**/monitor/health/app**

/monitor/health response example:
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
