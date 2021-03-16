BEGIN
EXECUTE IMMEDIATE 'DROP TABLE ' || 'TestDateTimeTable';
EXCEPTION
WHEN OTHERS THEN
    IF SQLCODE != -942 THEN
        RAISE;
    END IF;
END;


CREATE TABLE TestDateTimeTable(
    PK NUMBER GENERATED ALWAYS AS IDENTITY,
    COL_DATE  DATE,
    COL_TIMESTAMP_1  TIMESTAMP (9),
    COL_TIMESTAMP_2  TIMESTAMP (9) WITH TIME ZONE,
    COL_TIMESTAMP_3  TIMESTAMP (9) WITH LOCAL TIME ZONE,
    COL_INTERVAL_YEAR_TO_MONTH INTERVAL YEAR TO MONTH,
    COL_INTERVAL_DAY_TO_SECOND INTERVAL DAY(9) TO SECOND(9),
    PRIMARY KEY(PK)
);