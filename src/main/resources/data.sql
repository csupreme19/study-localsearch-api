INSERT INTO company (name, created_at) VALUES ('kakao', now());
INSERT INTO company (name, created_at) VALUES ('naver', now());

INSERT INTO api_key (company_id, key, created_at) VALUES ((SELECT id FROM COMPANY WHERE name='kakao'), '26a1ba63cc7907088c544a8a153d3928', now());
INSERT INTO api_key (company_id, client_id, client_secret, created_at) VALUES ((SELECT id FROM COMPANY WHERE name='naver'), 'FTab_JULwgCgeowGPJU_', '4BExoUK6g3', now());