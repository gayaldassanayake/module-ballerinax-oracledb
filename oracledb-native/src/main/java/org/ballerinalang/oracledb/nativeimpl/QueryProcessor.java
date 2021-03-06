/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.oracledb.nativeimpl;

import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BStream;
import org.ballerinalang.oracledb.parameterprocessor.OracleDBResultParameterProcessor;
import org.ballerinalang.oracledb.parameterprocessor.OracleDBStatementParameterProcessor;
import org.ballerinalang.sql.parameterprocessor.DefaultResultParameterProcessor;
import org.ballerinalang.sql.parameterprocessor.DefaultStatementParameterProcessor;

/**
 * This class provides the methods for query processing which executes sql queries.
 * 
 * @since 0.1.0
 */
public class QueryProcessor {
    private QueryProcessor() {}

    /**
     * Query the database and return results.
     * @param client client object
     * @param paramSQLString SQL string of the query
     * @param recordType type description of the result record
     * @return result stream or error
     */
    public static BStream nativeQuery(BObject client, Object paramSQLString,
                                      Object recordType) {
        DefaultStatementParameterProcessor statementParametersProcessor = OracleDBStatementParameterProcessor
                .getInstance();
        DefaultResultParameterProcessor resultParametersProcessor = OracleDBResultParameterProcessor
                .getInstance();
        return org.ballerinalang.sql.nativeimpl.QueryProcessor.nativeQuery(client, paramSQLString, recordType,
                statementParametersProcessor, resultParametersProcessor);
    }
}
