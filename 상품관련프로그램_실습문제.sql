CREATE TABLE PRODUCT(
    PRODUCT_ID VARCHAR2(50) PRIMARY KEY,
    P_NAME VARCHAR2(30) NOT NULL,
    PRICE NUMBER NOT NULL,
    DESCRIPTION VARCHAR2(30),
    STOCK NUMBER NOT NULL
);


INSERT INTO PRODUCT VALUES('nb_ss7', '삼성노트북', 1570000, '시리즈7', 10);
INSERT INTO PRODUCT VALUES('nb_ama4', '맥북에어', 1200000, 'xcode4', 20);
INSERT INTO PRODUCT VALUES('pc_ibm', 'ibmPC', 750000, 'window8', 5);


CREATE TABLE PRODUCT_IO(
    IO_NUM NUMBER PRIMARY KEY,
    PRODUCT_ID VARCHAR2(50) REFERENCES PRODUCT ON DELETE CASCADE,
    IO_DATE DATE DEFAULT SYSDATE NOT NULL,
    AMOUNT NUMBER NOT NULL,
    STATUS CHAR(6) CHECK(STATUS IN ('입고', '출고'))
);

CREATE SEQUENCE SEQ_IONO;

CREATE OR REPLACE TRIGGER TRG_PRODUCT
AFTER INSERT ON PRODUCT_IO
FOR EACH ROW
BEGIN
    IF :NEW.STATUS = '입고'
        THEN
            UPDATE PRODUCT
               SET STOCK = STOCK + :NEW.AMOUNT
             WHERE PRODUCT_ID = :NEW.PRODUCT_ID;
    ELSIF :NEW.STATUS = '출고'
        THEN
            UPDATE PRODUCT
               SET STOCK = STOCK - :NEW.AMOUNT
             WHERE PRODUCT_ID = :NEW.PRODUCT_ID;
    END IF;
END;
/

INSERT INTO PRODUCT_IO VALUES(SEQ_IONO.NEXTVAL, 'nb_ss7', '19/07/01', 30, '입고');
INSERT INTO PRODUCT_IO VALUES(SEQ_IONO.NEXTVAL, 'nb_ss7', '19/07/02', 3, '출고');
INSERT INTO PRODUCT_IO VALUES(SEQ_IONO.NEXTVAL, 'pc_ibm', '19/07/02', 10, '입고');

COMMIT;

