CREATE TABLE employee(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    salary INT NOT NULL
);
INSERT INTO employee (name, salary)
VALUES ('Anna', 150000);

CREATE TABLE position(
    id_position BIGSERIAL NOT NULL PRIMARY KEY,
    name_position VARCHAR(50) NOT NULL
);

ALTER TABLE employee ADD FOREIGN KEY (id_position) REFERENCES  position(id_position);

CREATE TABLE report(
    id_report BIGSERIAL NOT NULL PRIMARY KEY,
    report VARCHAR NOT NULL
);

DROP TABLE employee;
DROP TABLE position;
DROP TABLE report;
DROP TABLE app_user;
DROP TABLE databasechangelog;
DROP TABLE databasechangeloglock;


SELECT * FROM report;

INSERT INTO app_user (password, role, username) VALUES
                                                              ('$2a$10$OFLR.7KU689uojrbyMlviOTPj1bAjoiNcYnA.SfCQPumcNPw5glpu', 'ADMIN', 'admin'),
                                                              ('$2a$10$6Lhfl4UV9mZtnIuNtbc3BuAXAngFA6n1oIsQCWeO0FKXmrMAdyLH6', 'USER', 'user');


