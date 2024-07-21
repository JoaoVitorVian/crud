CREATE TABLE establishment (
id                      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name                    VARCHAR(100) NOT NULL
);

CREATE TABLE role (
id                      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name                    VARCHAR(100) NOT NULL
);

CREATE TABLE person (
id                      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name                    VARCHAR(100) NOT NULL,
cpf                     VARCHAR(14) NOT NULL UNIQUE,
birth_date              DATE NOT NULL,
admission_date          DATE NOT NULL,
email                   VARCHAR(100) NOT NULL,
establishment_id        BIGINT,
role_id                 BIGINT,
FOREIGN KEY (establishment_id) REFERENCES establishment(id),
FOREIGN KEY (role_id)          REFERENCES role(id)
);
