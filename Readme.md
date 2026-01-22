<img src='/icon/icon.svg' width='100'>

# Health Check API
The  **Health Check API** is a XML connector implemented with the XML SDK. When added as a maven dependency into your Mule project it provides the application additional resources and capabilities to **GET** general statistical information of the Operating System Server, Mule Runtime, and the Java Virtual Machine your application is deployed into. In addition to this it can also be configured to get the connectivity status of application/services/systems being used by your application.


## Setup
### Project Changes

1. Add the maven dependency to the applications **pom.xml** file.  Make sure to use the latest version.
```
<dependency>
    <groupId>com.avioconsulting.mule</groupId>
    <artifactId>mule-health-check-api</artifactId>
    <version>3.0.0</version>
    <classifier>mule-plugin</classifier>
</dependency>
```
2. Create a Global Configuration for the **AVIO Health Check Config**.

![AVIO Health Check Config](./icon/health-check-config.png)
- **attributes**: Use default value. Should always be the attributes predefined variable.
- **environment**: Environment Name. It defaults to ‘env’ property.
- **external systems**: comma separated list of flow names to handle external systems. See [External System Montoring](#external-system-monitoring) for more information.

3. Logging setup for Health check related functionality uses [mule-custom-logger](https://github.com/avioconsulting/mule-custom-logger) module. The logger configuration requires following environment properties defined by the consumer application -
- `env` defining the Mule Environment name
- `app.name` defining the name of the application being monitored
- `app.version` defining the version of the application being monitored
- `healthcheck.log.category` defaulted to `com.avioconsulting.mule.healthcheck` and can be used to control health check logging

4. Add a new flow to your project, with its own http listener. This should use the same global HTTP listener configuration already defined in the application. Make sure to set the listener Path to `/monitor/*`

5. Drop in the **Health Check** operation.

![health-check-flow](./icon/health-check-flow.png)

Sample health-check-api.xml configuration file. Add this, and update a global configuration.
```
<?xml version="1.0" encoding="UTF-8"?>
<mule xmlns:module-health-check="http://www.mulesoft.org/schema/mule/module-health-check" xmlns:http="http://www.mulesoft.org/schema/mule/http"
  xmlns="http://www.mulesoft.org/schema/mule/core"
  xmlns:doc="http://www.mulesoft.org/schema/mule/documentation" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.mulesoft.org/schema/mule/core http://www.mulesoft.org/schema/mule/core/current/mule.xsd
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd
http://www.mulesoft.org/schema/mule/module-health-check http://www.mulesoft.org/schema/mule/module-health-check/current/mule-module-health-check.xsd">
  <module-health-check:config name="AVIO_Health_Check_Config" doc:name="AVIO Health Check Config"/>
  <flow name="health-check-api-flow" doc:id="2cfc7c99-3d1a-4846-99da-37821c01fcba" >
    <http:listener doc:name="Health Check Listener" doc:id="a37c88e3-8ebc-4f37-ab11-13395f9aa5a0" config-ref="HTTP_Listener_config" path="/monitor/*"/>
    <module-health-check:health-check doc:name="Health check" doc:id="6d166d27-a8d3-474f-a61e-abb43478188b" config-ref="AVIO_Health_Check_Config"/>
  </flow>
</mule>
```

### Securing the health-check

To secure the health-check-api with its own client application credentials, the RAML must be added to the organizations Design Center and an API Instance must be created.
1. Design Center:
   1. Zip `src/main/resources/api` directory in the source code
   2. “Create New → Import from File” Design Center project “Health Check API” with the Zipped specification
   3. Publish specification to **Exchange** (Asset Version 1.0.0 or as applicable)
2. API Manager:
   1. Begin with lower environments first
   2. Create a new API from the exchange published API - “Manage API → Manage API From Exchange”
   3. Lookup the newly published “Health Check API for Mule 4” and Save.
   4. Apply *Client ID Enforcement* Policy to this API.
3. Exchange:
   1. Search for the *Health Check API* in **Exchange** and request access to the applicable application.
4. Project
   1. Create an **API Autodiscovery** configuration in your project, with the *API Id* from API Manager (use best practices and store this value in an environment property file) and the name of the http listener flow that was created.

## Usage

The following resources will be added to the base URI of your application :

- <base_uri>**/monitor/health** - Most commonly used endpoint that aggregates the runtime and app endpoints
- <base_uri>**/monitor/health/runtime** - retrieves just the JVM runtime information
- <base_uri>**/monitor/health/app** - retrieves information about the application

Sample response from `/monitor/health`:
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


## External System Monitoring

The health check can be used to monitor external systems. This is done through custom health flows that are added to an application. The flow should contain a simple operation or query that validates the health of the system. This typically would be a simple `GET` http request to a target system, or simple `SELECT` SQL query on a database. The implementation is left up to the developer. See [system-check.raml](./src/main/resources/api/datatypes/system-check.raml), for a RAML reference to the response object. The response must include `systemName`, `status` with values of **OK**, **ERROR**, or **UNKNOWN**, and `timestamp` fields. Make sure to add the flow names to the `external systems` parameter in the Health Check configuration.

Below are some sample responses from external health check flows.

 ```
{
  "name": "HTTPS",
  "connectionConfig": "api-dev.srs.aws.avioconsulting.com:443/orders-system-api",
  "status": "OK",
  "timestamp": "2019-12-27T11:23:18.168-06:00",
  "executionTime" : 10.7
}
```
```
{
  "name" : "Amazon S3",
  "status" : "ERROR",
  "exception": "Unable to locate S3 Bucket",
  "timestamp": "2019-12-27T11:23:18.168-06:00",
  "executionTime" : 18.3
}
```
```
{
  "name": "SQLServer",
  "connectionConfig": "jdbc:sqlserver://localhost;ODSAPI;ODSApiApp",
  "user": "sql-user",
  "status": "OK",
  "timestamp": "2019-12-27T11:23:18.168-06:00"
}
  ```

## SNAPSHOT or Local Testing

If a `SNAPSHOT` version is desired, either a local install (`mvn install`) can be done, or add the Sonatype repository to the project **pom.xml** file under `pluginRepositories`.

Check [Nexus Repository](https://oss.sonatype.org/#nexus-search;quick~mule-health-check-api) for a list of all available versions.
```
<pluginRepository>
    <id>sonatype</id>
    <name>Nexus Repository</name>
    <layout>default</layout>
    <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
</pluginRepository>
```
