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

import ballerina/file;
import ballerina/sql;

const string USER = "admin";
const string PASSWORD = "password";
const string HOST = "localhost";
const int PORT = 1521;
const int POOLPORT = 1522;
const string DATABASE = "ORCLCDB.localdomain";

string resourcePath = check file:getAbsolutePath("tests/resources");

final Options options = {
    loginTimeout: 1,
    autoCommit: true,
    connectTimeout: 1,
    socketTimeout: 3
};

final configurable int maxOpenConnections = 10;
final configurable decimal maxConnectionLifeTime = 2000.0;
final configurable int minIdleConnections = 5;

final sql:ConnectionPool connectionPool = {
   maxOpenConnections: maxOpenConnections,
   maxConnectionLifeTime: maxConnectionLifeTime,
   minIdleConnections: minIdleConnections
};
