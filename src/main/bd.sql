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

DROP TABLE auth_user;

SELECT * FROM report;

INSERT INTO auth_user (enabled, password, role, username) VALUES
                                                              (1,'$2a$10$OFLR.7KU689uojrbyMlviOTPj1bAjoiNcYnA.SfCQPumcNPw5glpu', 'ADMIN', 'admin1');


