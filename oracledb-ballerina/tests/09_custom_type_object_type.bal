// Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied. See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/sql;
import ballerina/test;

@test:BeforeGroups { value:["insert-object"] }
function beforeInsertObjectFunc() returns sql:Error? {
   string OID = "19A57209ECB73F91E03400400B40BB23";

   Client oracledbClient = check new(user, password, host, port, database);
   sql:ExecutionResult result = check dropTableIfExists("TestObjectTypeTable");

   result = check oracledbClient->execute(
       "CREATE OR REPLACE TYPE OBJECT_TYPE OID '"+ OID +"' AS OBJECT(" +
       "ATTR1 VARCHAR(20), "+
       "ATTR2 VARCHAR(20), "+
       "ATTR3 VARCHAR(20), "+
       "MAP MEMBER FUNCTION GET_ATTR1 RETURN NUMBER "+
       ") "
   );

   result = check oracledbClient->execute(
       "CREATE OR REPLACE TYPE BODY OBJECT_TYPE AS "+
           "MAP MEMBER FUNCTION GET_ATTR1 RETURN NUMBER IS "+
           "BEGIN "+
               "RETURN ATTR1; "+
           "END; "+
       "END; "
   );

   result = check oracledbClient->execute("CREATE TABLE TestObjectTypeTable(" +
       "PK NUMBER GENERATED ALWAYS AS IDENTITY, "+
       "COL_OBJECT OBJECT_TYPE, " +
       "PRIMARY KEY(PK) "+
       ")"
   );

   check oracledbClient.close();
}



@test:Config {
   enable: true,
   groups:["execute","insert-object"]
}
function insertObjectTypeWithString() returns sql:Error? {
   Client oracledbClient = check new(user, password, host, port, database);

   string attr1 = "Hello1";
   string attr2 = "Hello2";
   string attr3 = "Hello3";

   sql:ParameterizedQuery insertQuery = `INSERT INTO TestObjectTypeTable(COL_OBJECT) VALUES(OBJECT_TYPE(${attr1}, ${attr2}, ${attr3}))`;
   sql:ExecutionResult result = check oracledbClient->execute(insertQuery);
   result = check oracledbClient->execute(insertQuery);

   test:assertExactEquals(result.affectedRowCount, 1, "Affected row count is different.");
   var insertId = result.lastInsertId;
   test:assertTrue(insertId is string, "Last Insert id should be string");

   check oracledbClient.close();
}

@test:Config {
   enable: true,
   groups:["execute","insert-object"]
   // dependsOn: [insertObjectTypeWithString]
}
function insertObjectTypeWithCustomType() returns sql:Error? {
   Client oracledbClient = check new(user, password, host, port, database);

   string attr1 = "Hello1";
   string attr2 = "Hello2";
   string attr3 = "Hello3";

   ObjectTypeValue objectType = new({typename: "OBJECT_TYPE", attributes: [ attr1, attr2, attr3 ]});

   sql:ParameterizedQuery insertQuery = `INSERT INTO TestObjectTypeTable(COL_OBJECT) VALUES(${objectType}))`;
   sql:ExecutionResult result = check oracledbClient->execute(insertQuery);
   result = check oracledbClient->execute(insertQuery);

   test:assertExactEquals(result.affectedRowCount, 1, "Affected row count is different.");
   var insertId = result.lastInsertId;
   test:assertTrue(insertId is string, "Last Insert id should be string");

   check oracledbClient.close();
}

@test:Config {
   enable: true,
   groups:["execute","insert-object"],
   dependsOn: [insertObjectTypeWithCustomType]
}
function insertObjectTypeNull() returns sql:Error? {
   Client oracledbClient = check new(user, password, host, port, database);

   ObjectTypeValue objectType = new();

   sql:ParameterizedQuery insertQuery = `INSERT INTO TestObjectTypeTable(COL_OBJECT) VALUES(${objectType}))`;
   sql:ExecutionResult result = check oracledbClient->execute(insertQuery);
   result = check oracledbClient->execute(insertQuery);

   test:assertExactEquals(result.affectedRowCount, 1, "Affected row count is different.");
   var insertId = result.lastInsertId;
   test:assertTrue(insertId is string, "Last Insert id should be string");

   check oracledbClient.close();
}
