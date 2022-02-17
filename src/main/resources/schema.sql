DROP TABLE IF EXISTS search_history;

CREATE TABLE search_history (
	keyword VARCHAR PRIMARY KEY,
	count LONG,
	created_at TIMESTAMP,
	modified_at TIMESTAMP
);

DROP TABLE IF EXISTS company;

CREATE TABLE company (
	id LONG PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR,
	created_at TIMESTAMP,
	modified_at TIMESTAMP
);

DROP TABLE IF EXISTS api_key;

CREATE TABLE api_key (
	id LONG PRIMARY KEY AUTO_INCREMENT,
	company_id VARCHAR,
	client_id VARCHAR,
	client_secret VARCHAR,
	key VARCHAR,
	created_at TIMESTAMP,
	modified_at TIMESTAMP
);

ALTER TABLE api_key ADD FOREIGN KEY (company_id) REFERENCES company (id);