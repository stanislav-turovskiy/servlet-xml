CREATE SCHEMA servlet;

CREATE TABLE servlet.agent
(
  id       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  phone    VARCHAR(20)     NOT NULL,
  password VARCHAR(200)    NOT NULL
);
ALTER TABLE servlet.agent ADD CONSTRAINT unique_id UNIQUE(id);
ALTER TABLE servlet.agent ADD CONSTRAINT unique_phone UNIQUE(phone);

CREATE TABLE servlet.account
(
  id       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  agent_id INT             NOT NULL,
  balance  DECIMAL(20,2)   NOT NULL
);
ALTER TABLE servlet.account ADD CONSTRAINT unique_id UNIQUE(id);
ALTER TABLE servlet.account
ADD CONSTRAINT account_agent_fk
FOREIGN KEY(agent_id) REFERENCES agent(id);