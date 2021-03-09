package org.ballerinalang.oracledb.utils;

import io.ballerina.runtime.api.values.BObject;
import org.ballerinalang.oracledb.parameterprocessor.OracleDBResultParameterProcessor;
import org.ballerinalang.sql.parameterprocessor.DefaultResultParameterProcessor;

/**
 * This class provides functionality to call `sql:ProcedureCallResult` with a custom `ResultParameterProcessor` object.
 *
 * @since 0.1.0
 */
public class ProcedureCallResultUtils {

    public static Object getNextQueryResult(BObject customResultIterator, BObject callResult) {
        DefaultResultParameterProcessor resultParameterProcessor = OracleDBResultParameterProcessor.getInstance();
        return org.ballerinalang.sql.utils.ProcedureCallResultUtils.getNextQueryResult(
                callResult, resultParameterProcessor);
    }
}