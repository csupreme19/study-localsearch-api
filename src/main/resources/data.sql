INSERT INTO company (name, created_at) VALUES ('kakao', now());
INSERT INTO company (name, created_at) VALUES ('naver', now());

INSERT INTO api_key (company_id, key, created_at) VALUES ((SELECT id FROM COMPANY WHERE name='kakao'), '26a1ba63cc7907088c544a8a153d3928', now());
INSERT INTO api_key (company_id, client_id, client_secret, created_at) VALUES ((SELECT id FROM COMPANY WHERE name='naver'), 'FTab_JULwgCgeowGPJU_', '4BExoUK6g3', now());

INSERT INTO search_history (keyword, count, created_at) VALUES ('곱창', 10, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('은행', 6, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('순대', 1, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('갈비', 7, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('삼겹살', 3, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('돈까스', 5, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('약국', 5, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('선별진료소', 2, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('지하철', 15, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('버스 정류장', 4, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('맛집', 25, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('데이트코스', 15, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('카페', 3, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('전시회', 3, now());
INSERT INTO search_history (keyword, count, created_at) VALUES ('남산전망대', 1, now());