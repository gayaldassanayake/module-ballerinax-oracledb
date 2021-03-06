Ballerina OracleDB Library
===================

  [![Build](https://github.com/ballerina-platform/module-ballerinax-oracledb/workflows/Build/badge.svg)](https://github.com/ballerina-platform/module-ballerinax-oracledb/actions?query=workflow%3ABuild)
  [![GitHub Last Commit](https://img.shields.io/github/last-commit/ballerina-platform/module-ballerinax-oracledb.svg)](https://github.com/ballerina-platform/module-ballerinax-oracledb/commits/master)
  [![Github issues](https://img.shields.io/github/issues/ballerina-platform/ballerina-standard-library/module/oracledb.svg?label=Open%20Issues)](https://github.com/ballerina-platform/ballerina-standard-library/labels/module%2Foracledb)
  [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
  [![codecov](https://codecov.io/gh/ballerina-platform/module-ballerinax-oracledb/branch/master/graph/badge.svg)](https://codecov.io/gh/ballerina-platform/module-ballerinax-oracledb)

The OracleDB library is one of the standard library packages of the<a target="_blank" href="https://ballerina.io/"> Ballerina</a> language.

This provides the functionality required to access and manipulate data stored in an OracleDB database.  

For more information on the operations supported by the `oracledb:Client`, which includes the below, go to the [OracleDB Package](https://ballerina.io/learn/api-docs/ballerina/oracledb/).

- Pooling connections
- Querying data
- Inserting data
- Updating data
- Deleting data
- Updating data in batches
- Executing stored procedures
- Closing the client

## Issues and Projects 

The **Issues** and **Projects** tabs are disabled for this repository as this is part of the Ballerina Standard Library. To report bugs, request new features, start new discussions, view project boards, etc., please visit the Ballerina Standard Library [parent repository](https://github.com/ballerina-platform/ballerina-standard-library).

This repository only contains the source code for the package.

## Building from the Source

### Setting Up the Prerequisites

1. Download and install the Java SE Development Kit (JDK) version 11 (from one of the following locations).
   * [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
   * [OpenJDK](http://openjdk.java.net/install/index.html)

2. Download and install [Docker](https://www.docker.com/get-started).
   
3. Export your Github Personal access token with the read package permissions as follows.
        
        export packageUser=<Username>
        export packagePAT=<Personal access token>

### Building the Source

Execute the commands below to build from the source.

1. To build the library:

        ./gradlew clean build
        
2. To run the integration tests:

        ./gradlew clean test

3. To build the package without tests:

        ./gradlew clean build -x test

4. To run only specific tests:

        ./gradlew clean build -Pgroups=<Comma separated groups/test cases>

   **Tip:** The following groups of test cases are available.<br>
   Groups | Test Cases
   ---| ---
   connection | connection-init
   pool | pool
   execute | execute-basic<br>insert-time<br>insert-object<br>insert-varray
   query | query-simple-params<br>query-numeric-params<br>query-complex-params

5. To disable some specific groups during the test:

        ./gradlew clean build -Pdisable-groups=<Comma separated groups/test cases>

6. To debug the tests:

        ./gradlew clean build -Pdebug=<port>

7. To debug the package with the Ballerina language:

        ./gradlew clean build -PbalJavaDebug=<port>     

## Contributing to Ballerina

As an open-source project, Ballerina welcomes contributions from the community. 

For more information, go to the [contribution guidelines](https://github.com/ballerina-platform/ballerina-lang/blob/master/CONTRIBUTING.md).

## Code of Conduct

All contributors are encouraged to read the [Ballerina Code of Conduct](https://ballerina.io/code-of-conduct).

## Useful Links

* Discuss about code changes of the Ballerina project in [ballerina-dev@googlegroups.com](mailto:ballerina-dev@googlegroups.com).
* Chat live with us via our [Slack channel](https://ballerina.io/community/slack/).
* Post all technical questions on Stack Overflow with the [#ballerina](https://stackoverflow.com/questions/tagged/ballerina) tag.
