package org.ballerinalang.oracledb.parameterprocessor;

import io.ballerina.runtime.api.creators.ValueCreator;
import io.ballerina.runtime.api.types.Type;
import io.ballerina.runtime.api.utils.XmlUtils;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BXml;
import org.ballerinalang.oracledb.Constants;
import org.ballerinalang.sql.exception.ApplicationError;
import org.ballerinalang.sql.parameterprocessor.DefaultResultParameterProcessor;
import org.ballerinalang.oracledb.utils.ModuleUtils;
import org.ballerinalang.sql.utils.Utils;

import java.sql.SQLException;
import java.sql.SQLXML;

/**
 * This class overrides DefaultResultParameterProcessor to implement methods required convert SQL types into
 * ballerina types and other methods that process the parameters of the result.
 *
 * @since 0.1.0
 */
public class OracleDBResultParameterProcessor extends DefaultResultParameterProcessor {
    private static final Object lock = new Object();
    private static volatile OracleDBResultParameterProcessor instance;
    private static final Object iteratorLock = new Object();
    private static volatile BObject iterator;

    /**
     * Singleton static method that returns an instance of `OracleDBResultParameterProcessor`.
     * @return OracleDBResultParameterProcessor
     */
    public static OracleDBResultParameterProcessor getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new OracleDBResultParameterProcessor();
                }
            }
        }
        return instance;
    }

    @Override
    protected BObject getIteratorObject() {
        if (iterator == null) {
            synchronized (iteratorLock) {
                if (iterator == null) {
                    ValueCreator.createObjectValue(
                            ModuleUtils.getModule(), Constants.CUSTOM_RESULT_ITERATOR_OBJECT, new Object[0]);
                }
            }
        }
        return iterator;
    }

    @Override
    public Object convertXml(SQLXML value, int sqlType, Type type) throws ApplicationError, SQLException {
        Utils.validatedInvalidFieldAssignment(sqlType, type, "SQL XML");
        if (value != null) {
            if (type instanceof BXml) {
                return XmlUtils.parse(value.getBinaryStream());
            } else {
                throw new ApplicationError("The ballerina type that can be used for SQL struct should be record type," +
                        " but found " + type.getName() + " .");
            }
        } else {
            return null;
        }
    }
}
