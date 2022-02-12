DROP TABLE IF EXISTS search_history;

CREATE TABLE search_history (
	id INT PRIMARY KEY AUTO_INCREMENT,
	keyword VARCHAR,
	created_at TIMESTAMP,
	modified_at TIMESTAMP
);

DROP TABLE IF EXISTS company;

CREATE TABLE company (
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR,
	created_at TIMESTAMP,
	modified_at TIMESTAMP
);

DROP TABLE IF EXISTS api_key;

CREATE TABLE api_key (
	id INT PRIMARY KEY AUTO_INCREMENT,
	company_id VARCHAR,
	client_id VARCHAR,
	client_secret VARCHAR,
	token VARCHAR,
	created_at TIMESTAMP,
	modified_at TIMESTAMP
);

ALTER TABLE api_key ADD FOREIGN KEY (company_id) REFERENCES company (id);