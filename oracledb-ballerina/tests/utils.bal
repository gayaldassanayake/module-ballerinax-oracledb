// Copyright (c) 2021 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/io;
import ballerina/sql;

isolated function getUntaintedData(record {}|error? value, string fieldName) returns @untainted anydata {
    if (value is record {}) {
        return value[fieldName];
    }
    return {};
}

isolated function getByteColumnChannel() returns @untainted io:ReadableByteChannel {
    io:ReadableByteChannel byteChannel = checkpanic io:openReadableFile("./tests/resources/files/byteValue.txt");
    return byteChannel;
}

isolated function getBlobColumnChannel() returns @untainted io:ReadableByteChannel {
    io:ReadableByteChannel byteChannel = checkpanic io:openReadableFile("./tests/resources/files/blobValue.txt");
    return byteChannel;
}

isolated function getClobColumnChannel() returns @untainted io:ReadableCharacterChannel {
    io:ReadableByteChannel byteChannel = checkpanic io:openReadableFile("./tests/resources/files/clobValue.txt");
    io:ReadableCharacterChannel sourceChannel = new (byteChannel, "UTF-8");
    return sourceChannel;
}

isolated function getTextColumnChannel() returns @untainted io:ReadableCharacterChannel {
    io:ReadableByteChannel byteChannel = checkpanic io:openReadableFile("./tests/resources/files/clobValue.txt");
    io:ReadableCharacterChannel sourceChannel = new (byteChannel, "UTF-8");
    return sourceChannel;
}

function dropTableIfExists(string tablename) returns sql:ExecutionResult|error {
    Client oracledbClient = checkpanic new(user, password, host, port, database);
    sql:ExecutionResult result = checkpanic oracledbClient->execute("BEGIN "+
        "EXECUTE IMMEDIATE 'DROP TABLE ' || '" + tablename + "'; "+
        "EXCEPTION "+
        "WHEN OTHERS THEN "+
            "IF SQLCODE != -942 THEN "+
                "RAISE; "+
            "END IF; "+
        "END;");
    checkpanic oracledbClient.close();
    return result;
}

// function dropTypeIfExists(string typename) returns sql:ExecutionResult|error {
//     Client oracledbClient = checkpanic new(user, password, host, port, database);
//     sql:ExecutionResult result = checkpanic oracledbClient->execute("BEGIN "+
//         "EXECUTE IMMEDIATE 'DROP TYPE ' || '" + typename + " FORCE'; "+
//         "EXCEPTION "+
//         "WHEN OTHERS THEN "+
//             "IF SQLCODE != 4043 THEN "+
//                 "RAISE; "+
//             "END IF; "+
//         "END;");
//     checkpanic oracledbClient.close();
//     return result;
// }