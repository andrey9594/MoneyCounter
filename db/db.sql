-- Postgress 

CREATE DATABASE money;

CREATE TABLE OperationType(id SERIAL PRIMARY KEY, name TEXT);
INSERT INTO OperationType(name) VALUES ('Доход');
INSERT INTO OperationType(name) VALUES ('Расход');


CREATE TABLE OperationCategory(id SERIAL PRIMARY KEY, name TEXT);
INSERT INTO OperationCategory(name) VALUES ('Продукты');
INSERT INTO OperationCategory(name) VALUES ('Столовая');
INSERT INTO OperationCategory(name) VALUES ('Зарплата');
INSERT INTO OperationCategory(name) VALUES ('Проезд');
INSERT INTO OperationCategory(name) VALUES ('Лекарства');
INSERT INTO OperationCategory(name) VALUES ('Оплата услуг');
INSERT INTO OperationCategory(name) VALUES ('Отдых');
INSERT INTO OperationCategory(name) VALUES ('Иное');


CREATE TABLE OperationData(id SERIAL PRIMARY KEY, add_data TIMESTAMP DEFAULT CURRENT_TIMESTAMP, operation_id INT REFERENCES OperationType(id), operation_data TIMESTAMP, category_id INT REFERENCES OperationCategory(id), sum NUMERIC(10, 2), comment TEXT);