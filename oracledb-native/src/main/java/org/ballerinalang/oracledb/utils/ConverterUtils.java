package org.ballerinalang.oracledb.utils;

import io.ballerina.runtime.api.TypeTags;
import io.ballerina.runtime.api.types.ArrayType;
import io.ballerina.runtime.api.types.Field;
import io.ballerina.runtime.api.types.StructureType;
import io.ballerina.runtime.api.types.Type;
import io.ballerina.runtime.api.utils.TypeUtils;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import oracle.xdb.XMLType;
import org.ballerinalang.oracledb.Constants;
import org.ballerinalang.sql.exception.ApplicationError;

import static io.ballerina.runtime.api.utils.StringUtils.fromString;

/**
 * This class converts ballerina custom types to driver specific objects.
 *
 * @since 0.1.0
 */
public class ConverterUtils {

    /**
     * Converts IntervalYearToMonthValue value to String.
     * @param value Custom IntervalYearToMonthValue value
     * @return String of INTERVAL_YEAR_TO_MONTH
     * @throws ApplicationError error thrown if invalid types are passed
     */
    public static String convertIntervalYearToMonth(Object value) throws ApplicationError, SQLException {
        Type type = TypeUtils.getType(value);
        if (type.getTag() != TypeTags.RECORD_TYPE_TAG) {
            throwApplicationErrorForInvalidTypes(Constants.Types.OracleDbTypes.INTERVAL_YEAR_TO_MONTH);
        }

        Map<String, Object> fields = getRecordData(value);
        Object yearObject = fields.get(Constants.Types.IntervalYearToMonth.YEAR);
        Object monthObject = fields.get(Constants.Types.IntervalYearToMonth.MONTH);

        String year = getIntervalString(yearObject, Constants.Types.OracleDbTypes.INTERVAL_YEAR_TO_MONTH);
        String month = getIntervalString(monthObject, Constants.Types.OracleDbTypes.INTERVAL_YEAR_TO_MONTH);

        return year + "-" + month;
    }

    /**
     * Converts IntervalDayToSecondValue value to String.
     * @param value Custom IntervalDayToSecond value
     * @return String of INTERVAL_DAY_TO_SECOND
     * @throws ApplicationError error thrown if invalid types are passed
     */
    public static String convertIntervalDayToSecond(Object value) throws ApplicationError, SQLException {
        Type type = TypeUtils.getType(value);
        if (type.getTag() != TypeTags.RECORD_TYPE_TAG) {
            throwApplicationErrorForInvalidTypes(Constants.Types.OracleDbTypes.INTERVAL_DAY_TO_SECOND);
        }

        Map<String, Object> fields = getRecordData(value);
        Object dayObject = fields.get(Constants.Types.IntervalDayToSecond.DAY);
        Object hourObject = fields.get(Constants.Types.IntervalDayToSecond.HOUR);
        Object minuteObject = fields.get(Constants.Types.IntervalDayToSecond.MINUTE);
        Object secondObject = fields.get(Constants.Types.IntervalDayToSecond.SECOND);

        String day = getIntervalString(dayObject, Constants.Types.OracleDbTypes.INTERVAL_DAY_TO_SECOND);
        String hour = getIntervalString(hourObject, Constants.Types.OracleDbTypes.INTERVAL_DAY_TO_SECOND);
        String minute = getIntervalString(minuteObject, Constants.Types.OracleDbTypes.INTERVAL_DAY_TO_SECOND);
        String second = getIntervalString(secondObject, Constants.Types.OracleDbTypes.INTERVAL_DAY_TO_SECOND);

        return day + " " + hour + ":" + minute + ":" + second;
    }

    /**
     * Converts BfileValue value to String.
     * @param value Custom Bfile value
     * @return String of BFILE
     */
    public static String convertBfile(Object value) throws ApplicationError, SQLException {
        Type type = TypeUtils.getType(value);
        if (type.getTag() != TypeTags.RECORD_TYPE_TAG) {
            throwApplicationErrorForInvalidTypes(Constants.Types.OracleDbTypes.BFILE);
        }
        Map<String, Object> fields = getRecordData(value);
        String directory = ((BString) fields.get(Constants.Types.Bfile.DIRECTORY)).getValue();
        String file = ((BString) fields.get(Constants.Types.Bfile.FILE)).getValue();

        return "bfilename('" + directory + "', '" + file + "')";
    }

     /**
      * Converts OracleObjectValue value to oracle.sql.STRUCT.
      * @param value Custom Bfile value
      * @return String of BFILE
      */
     public static Struct convertOracleObject(Connection connection, Object value)
             throws ApplicationError, SQLException {
         Type type = TypeUtils.getType(value);
         if (type.getTag() != TypeTags.RECORD_TYPE_TAG) {
             throwApplicationErrorForInvalidTypes(Constants.Types.OracleDbTypes.OBJECT_TYPE);
         }
         Map<String, Object> fields = getRecordData(value);

         String objectTypeName = ((BString) fields.get(Constants.Types.OracleObject.TYPE_NAME)).getValue();
         Object[] attributes = (Object[]) fields.get(
                 Constants.Types.OracleObject.ATTRIBUTES);
         return connection.createStruct(objectTypeName, attributes);
     }

    /**
     *
     * @param value
     * @return
     * @throws ApplicationError
     */
    public static Array convertVarray(Object value) throws ApplicationError, SQLException {
        Type type = TypeUtils.getType(value);
        if (type.getTag() != TypeTags.RECORD_TYPE_TAG) {
            throwApplicationErrorForInvalidTypes(Constants.Types.OracleDbTypes.VARRAY);
        }
        Map<String, Object> fields = getRecordData(value);
        return (Array) fields.get(Constants.Types.Varray.ELEMENTS);
    }

    /**
     *
     * @param value
     * @return
     * @throws ApplicationError
     */
    public static Array convertNestedTable(Object value) throws ApplicationError, SQLException {
        Type type = TypeUtils.getType(value);
        if (type.getTag() != TypeTags.RECORD_TYPE_TAG) {
            throwApplicationErrorForInvalidTypes(Constants.Types.OracleDbTypes.NESTED_TABLE);
        }
        Map<String, Object> fields = getRecordData(value);
        return (Array) fields.get(Constants.Types.Varray.ELEMENTS);
    }

    /**
     *
     * @param connection
     * @param value
     * @return
     * @throws ApplicationError
     */
    public static XMLType convertXml(Connection connection, Object value) throws ApplicationError, SQLException {
        Type type = TypeUtils.getType(value);
        if (type.getTag() != TypeTags.RECORD_TYPE_TAG) {
            throwApplicationErrorForInvalidTypes(Constants.Types.OracleDbTypes.NESTED_TABLE);
        }
        Map<String, Object> fields = getRecordData(value);
        String xml = (String) fields.get(Constants.Types.Xml.XML);
        return XMLType.createXML(connection, xml, "oracle.xml.parser.XMLDocument.THIN");
    }

     public Array convertUri(Object value) throws ApplicationError, SQLException {
         Type type = TypeUtils.getType(value);
         if (type.getTag() != TypeTags.RECORD_TYPE_TAG) {
             throwApplicationErrorForInvalidTypes(Constants.Types.OracleDbTypes.NESTED_TABLE);
         }
         Map<String, Object> fields = getRecordData(value);
         return (Array) fields.get(Constants.Types.Varray.ELEMENTS);
     }

    private static String getIntervalString(Object param, String typeName) throws ApplicationError {
        String value = null;
        if (param instanceof BString) {
            value = ((BString) param).getValue();
        } else if (param instanceof Long || param instanceof Double) {
            value = param.toString();
        } else {
            throwApplicationErrorForInvalidTypes(typeName);
        }
        return value;
    }

    private static Map<String, Object> getRecordData(Object value) throws SQLException, ApplicationError {
        Type type = TypeUtils.getType(value);
        Map<String, Field> structFields = ((StructureType) type).getFields();
        int fieldCount = structFields.size();
        Iterator<Field> fieldIterator = structFields.values().iterator();
        HashMap<String, Object> structData = new HashMap<>();
        for (int i = 0; i < fieldCount; i++) {
            Field field = fieldIterator.next();
            Object bValue = ((BMap) value).get(fromString(field.getFieldName()));
            int typeTag = field.getFieldType().getTag();
            // need to expand to cover all ballerina/SQL types
            switch (typeTag) {
                case TypeTags.INT_TAG:
                case TypeTags.FLOAT_TAG:
                case TypeTags.STRING_TAG:
                case TypeTags.BOOLEAN_TAG:
                case TypeTags.DECIMAL_TAG:
                    structData.put(field.getFieldName(), bValue);
                    break;
                case TypeTags.ARRAY_TAG:
                    structData.put(field.getFieldName(), getArrayData(field, bValue));
                    break;
                case TypeTags.RECORD_TYPE_TAG:
                    structData.put(field.getFieldName(), getRecordData(bValue));
                    break;
                default:
                    break;
            }
        }
        return structData;
    }

    protected static Object getArrayData(Field field, Object bValue)
            throws ApplicationError {
        // need to expand to cover all ballerina/SQL types
        Type elementType = ((ArrayType) field.getFieldType()).getElementType();
        if (elementType.getTag() == TypeTags.BYTE_TAG) {
            return ((BArray) bValue).getBytes();
        } else {
            throw new ApplicationError("unsupported data type for array specified for struct parameter");
        }
    }

    private static void throwApplicationErrorForInvalidTypes(String sqlTypeName) throws ApplicationError {
        throw new ApplicationError("Invalid data types for " + sqlTypeName);
    }

}
