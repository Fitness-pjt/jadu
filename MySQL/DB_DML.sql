USE `ssafy_pjt`;

-- Users 더미데이터 (10개)
INSERT INTO users (user_nickname, user_email, user_password, user_name) VALUES
('fitness_king', 'king@email.com', '$2a$10$dP', '김철수'),
('health_master', 'master@email.com', '$2a$10$dP', '이영희' ),
('workout_pro', 'pro@email.com', '$2a$10$dP', '박지성'),
('gym_lover', 'lover@email.com', '$2a$10$dP', '최동욱'),
('exercise_guru', 'guru@email.com', '$2a$10$dP', '정미라'),
('fit_queen', 'queen@email.com', '$2a$10$dP', '강지혜'),
('muscle_king', 'muscle@email.com', '$2a$10$dP', '손흥민'),
('yoga_master', 'yoga@email.com', '$2a$10$dP', '김연아'),
('runner_pro', 'runner@email.com', '$2a$10$dP', '황희찬'),
('health_guru', 'health@email.com', '$2a$10$dP', '박세리');

-- UserInfo 더미데이터 (10개)
INSERT INTO userInfo (user_id, gender, age, shape, goal, experience, location, frequency, duration) VALUES

(1, 'MALE', '2030', 'NORMAL', 'MUSCLE', 2, 'FITNESS', 5, 3),
(2, 'FEMALE', '2030', 'SLIM', 'DIET', 1, 'HOME', 3, 4),
(3, 'MALE', '4050', 'FAT', 'DIET', 0, 'HOME', 4, 5),
(4, 'FEMALE', '2030', 'NORMAL', 'STAMINA', 3, 'FITNESS', 6, 9),
(5, 'MALE', '60', 'NORMAL', 'RECOVERY', 1, 'OUTSIDE', 3, 4),
(6, 'FEMALE', '2030', 'SLIM', 'MUSCLE', 2, 'FITNESS', 5, 6),
(7, 'MALE', '4050', 'NORMAL', 'MUSCLE', 4, 'FITNESS', 6, 7),
(8, 'FEMALE', '2030', 'NORMAL', 'DIET', 2, 'HOME', 4, 4),
(9, 'MALE', '2030', 'FAT', 'DIET', 1, 'HOME', 3, 3),
(10, 'FEMALE', '4050', 'SLIM', 'RECOVERY', 2, 'OUTSIDE', 4, 2);

-- Program 더미데이터 (10개)




-- Video 더미데이터 (10개)
INSERT IGNORE INTO video (video_id) VALUES
('xK68FIFWdnI'),
('tZbTY9j_L9o'),
('oye5d4e1N_U'),
('Lts-ddUgSFQ'),
('V9atzm_n12k'),
('URVOrAxBDq0'),
('U7IH4wyVMBU'),
('Sa0sBKUiOvU'),
('kO_b0D8P1Jg'),
('OJevd3OyNNo'),
('v6lc7IEXC8s'),
('js8z5wIZ0wg'),
('AQ7778lCNZY'),
('zcQ16cfJN9Q'),
('QSZ0mUGO_CA'),
('73oaPgayP8Q'),
('wsWcyetgIaE'),
('pDFuLG0xrsU'),
('KqhCpQtOqic'),
('21iOcDfoOGg'),
('sqQpL1wKW6M'),
('2Uv1B3kjCOI'),
('DWYDL-WxF1U'),
('A5MzlPgNcJM'),
('bce7hAfUmF8'),
('saXF2xH3A2U'),
('sDsuW9RaQK0'),
('A2GRyhU0V5M'),
('fFIL0rlRH78'),
('zKotOqfIrs0'),
('2DRvc74GgYM'),
('bOWoNq2nUgg'),
('DnPhKr_zaTA'),
('D3yExRi7EME'),
('AHasgA4OvXQ');



-- Program Likes 더미데이터 (10개)
INSERT INTO program_likes (program_id, user_id) VALUES
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 6),
(6, 7),
(7, 8),
(8, 9),
(9, 10),
(10, 1);

-- Review 더미데이터 (10개)
INSERT INTO review (user_id, program_id, content, star) VALUES
(1, 2,  '일주일만에 효과봤어요', 5),
(2, 3,  '근력이 확실히 좋아졌어요', 4),
(3, 4,  '집에서 하기 좋아요', 4),
(4, 5, '유연성이 늘었어요', 5),
(5, 6,  '초보자에게 추천해요', 4),
(6, 7,  '전문적인 내용이에요', 5),
(7, 8,  '꾸준히 하고 있습니다', 4),
(8, 9,  '코어가 단단해졌어요', 5),
(9, 10,  '요가 입문자에게 좋아요', 5),
(10, 1,  '차근차근 늘어나는게 좋아요', 4);

-- Question 더미데이터 (10개)
INSERT INTO question (user_id, program_id, title, content) VALUES
(1, 1, '운동 강도 문의', '처음인데 너무 힘들어요'),
(2, 2, '식단 문의', '이 운동할 때 식단은 어떻게 하나요?'),
(3, 3, '운동 순서', '순서를 바꿔도 될까요?'),
(4, 4, '대체 운동', '무릎이 안 좋은데 대체 운동 추천해주세요'),
(5, 5, '통증 관련', '허리가 살짝 불편한데 계속해도 될까요?'),
(6, 6, '휴식 시간', '세트 간 휴식은 얼마나 하나요?'),
(7, 7, '운동 시간', '하루에 몇시간씩 해야하나요?'),
(8, 8, '준비운동', '준비운동 꼭 필요한가요?'),
(9, 9, '난이도 조절', '너무 쉬운데 난이도 올리려면?'),
(10, 10, '호흡법', '호흡은 어떻게 해야하나요?');

-- Answer 더미데이터 (10개)
INSERT INTO answer (question_id, user_id, content) VALUES
(1, 2, '처음에는 가볍게 시작하세요. 점진적으로 강도를 높이는 것이 좋습니다.'),
(2, 3, '프로틴과 닭가슴살 위주로 단백질 섭취를 늘리시면 좋습니다.'),
(3, 4, '기본 순서를 지키시는 것을 추천드립니다. 흐름이 잘 짜여있어요.'),
(4, 5, '무릎에 부담이 덜한 수영이나 자전거를 추천드립니다.'),
(5, 6, '통증이 심하시다면 전문의 상담을 받아보시는 것이 좋습니다.'),
(6, 7, '1분에서 1분 30초 정도가 적당합니다.'),
(7, 8, '초보자는 1시간 정도로 시작하세요.'),
(8, 9, '부상 방지를 위해 꼭 필요합니다!'),
(9, 10, '중량을 늘리거나 세트 수를 늘려보세요.'),
(10, 1, '동작에 따라 들숨 날숨을 잘 조절하는 것이 중요합니다.');



-- Todo 더미데이터 (10개)
INSERT INTO todo (user_id, program_id, video_id, date, status, content) VALUES
(1, 1, 'abc123xyz1', CURRENT_DATE, false, '전신운동 Day 1'),
(2, 2, 'def456uvw2', CURRENT_DATE, true, '다이어트 운동 하기'),
(3, 3, 'ghi789rst3', CURRENT_DATE, false, '상체 운동'),
(4, 4, 'jkl012opq4', CURRENT_DATE, true, '스트레칭 하기'),
(5, 5, 'mno345lmn5', CURRENT_DATE, false, '하체 운동'),
(6, 6, 'pqr678ijk6', CURRENT_DATE, true, '코어 운동'),
(7, 7, 'stu901ghi7', CURRENT_DATE, false, '유산소 운동'),
(8, 8, 'vwx234def8', CURRENT_DATE, true, '요가 수련'),
(9, 9, 'yza567abc9', CURRENT_DATE, false, '근력 운동'),
(10, 10, 'bcd890xyz0', CURRENT_DATE, true, '스트레칭');

-- Todo Likes 더미데이터 (10개)
INSERT INTO todo_likes (todo_id, user_id) VALUES
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 6),
(6, 7),
(7, 8),
(8, 9),
(9, 10),
(10, 1);

INSERT INTO keyword (keyword)
VALUES 
    ('상체'),
    ('하체'),
    ('가슴'),
    ('등'),
    ('어깨'),
    ('복부'),
    ('다이어트'),
    ('체력 증진'),
    ('근력 강화'),
    ('근육량 증가'),
    ('심폐지구력 향상'),
    ('자세 교정'),
    ('스트레스 해소'),
    ('체형 개선'),
    ('초급'),
    ('중급'),
    ('고급'),
    ('유산소 운동'),
    ('근력 훈련'),
    ('고강도 인터벌 트레이닝');


-- Program_Keyword 더미데이터 (10개)
INSERT INTO program_keyword (program_id, keyword_code) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

-- UserInfo_Keyword 더미데이터 (10개)
INSERT INTO userInfo_keyword (user_id, keyword_code) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

-- 유효 더미 데이터
-- Program 테이블 INSERT
INSERT INTO program (user_id, title, description, duration_weeks, level, video_cnt, program_img_path) VALUES
-- 팔+어깨 조합
(1, '탄탄한 상체 만들기 4주 프로젝트', '팔과 어깨를 중심으로 한 상체 운동으로, 탄탄한 근육을 만들어보세요', 4, 'BEGINNER', 4, 'https://img.youtube.com/vi/xK68FIFWdnI/mqdefault.jpg'),
(3, '어깨라인 완성 프로그램', '넓은 어깨와 탄탄한 팔 만들기에 집중하는 3주 프로그램입니다', 3, 'INTERMEDIATE', 5, 'https://img.youtube.com/vi/V9atzm_n12k/mqdefault.jpg'),

-- 상체+등 조합
(2, '전문가의 상체 집중 프로그램', '등과 상체 전반적인 근육을 발달시키는 프로그램입니다', 4, 'ADVANCED', 4, 'https://img.youtube.com/vi/U7IH4wyVMBU/mqdefault.jpg'),
(4, '자세교정을 위한 상체 운동', '등과 상체 스트레칭으로 바른 자세를 만드는 프로그램', 2, 'BEGINNER', 4, 'https://img.youtube.com/vi/v6lc7IEXC8s/mqdefault.jpg'),

-- 하체+코어(배) 조합
(5, '전신 다이어트 프로그램', '하체 운동과 코어 운동을 결합한 체지방 감소 프로그램', 4, 'INTERMEDIATE', 6, 'https://img.youtube.com/vi/js8z5wIZ0wg/mqdefault.jpg'),
(7, '탄탄한 하체와 복근 만들기', '하체와 코어를 동시에 단련하는 3주 프로그램', 3, 'ADVANCED', 5, 'https://img.youtube.com/vi/pDFuLG0xrsU/mqdefault.jpg'),

-- 상체+하체 조합
(6, '전신 근력 향상 프로그램', '상하체 균형잡힌 운동으로 전신 근력을 향상시키는 프로그램', 2, 'INTERMEDIATE', 6, 'https://img.youtube.com/vi/21iOcDfoOGg/mqdefault.jpg'),
(8, '초보자를 위한 전신 운동', '상하체 기초 운동으로 구성된 입문자 프로그램', 4, 'BEGINNER', 4, 'https://img.youtube.com/vi/A5MzlPgNcJM/mqdefault.jpg'),

-- 하체+지구력 조합
(9, '지구력 강화 하체 운동', '하체 운동과 유산소를 결합한 지구력 향상 프로그램', 3, 'INTERMEDIATE', 4, 'https://img.youtube.com/vi/js8z5wIZ0wg/mqdefault.jpg'),
(10, '다이어트를 위한 하체 중심 운동', '지구력 향상과 하체 라인 완성을 위한 프로그램', 4, 'BEGINNER', 4, 'https://img.youtube.com/vi/A2GRyhU0V5M/mqdefault.jpg'),

-- 등+자세교정 조합
(1, '자세교정 집중 프로그램', '등 운동과 스트레칭으로 바른 자세를 만드는 프로그램', 1, 'BEGINNER', 4, 'https://img.youtube.com/vi/Sa0sBKUiOvU/mqdefault.jpg'),
(3, '오피스워커 자세교정 운동', '등 운동과 스트레칭으로 거북목, 굽은 어깨를 교정하는 프로그램', 2, 'INTERMEDIATE', 5, 'https://img.youtube.com/vi/2DRvc74GgYM/mqdefault.jpg');

INSERT INTO program_video (program_id, video_id) VALUES
-- 팔+어깨 프로그램 1
(1, 'xK68FIFWdnI'),
(1, 'tZbTY9j_L9o'),
(1, 'oye5d4e1N_U'),
(1, 'Lts-ddUgSFQ'),

-- 팔+어깨 프로그램 2
(2, 'V9atzm_n12k'),
(2, 'URVOrAxBDq0'),
(2, 'xK68FIFWdnI'),
(2, 'tZbTY9j_L9o'),
(2, 'oye5d4e1N_U'),

-- 상체+등 프로그램 1
(3, 'U7IH4wyVMBU'),
(3, 'Sa0sBKUiOvU'),
(3, 'kO_b0D8P1Jg'),
(3, 'OJevd3OyNNo'),

-- 상체+등 프로그램 2
(4, 'v6lc7IEXC8s'),
(4, 'Sa0sBKUiOvU'),
(4, 'kO_b0D8P1Jg'),
(4, 'OJevd3OyNNo'),

-- 하체+코어 프로그램 1
(5, 'js8z5wIZ0wg'),
(5, 'AQ7778lCNZY'),
(5, 'zcQ16cfJN9Q'),
(5, 'QSZ0mUGO_CA'),
(5, '73oaPgayP8Q'),
(5, 'wsWcyetgIaE'),

-- 하체+코어 프로그램 2
(6, 'pDFuLG0xrsU'),
(6, 'KqhCpQtOqic'),
(6, 'zcQ16cfJN9Q'),
(6, '73oaPgayP8Q'),
(6, 'wsWcyetgIaE'),

-- 상체+하체 프로그램 1,2 생략...
(7,'21iOcDfoOGg'),
(7,'sqQpL1wKW6M'),
(7,'73oaPgayP8Q'),
(7,'2Uv1B3kjCOI'),
(7,'pDFuLG0xrsU'),
(7,'DWYDL-WxF1U'),

(8,'A5MzlPgNcJM'),
(8,'bce7hAfUmF8'),
(8,'zcQ16cfJN9Q'),
(8,'QSZ0mUGO_CA'),
-- 하체+지구력 프로그램 1
(9, 'js8z5wIZ0wg'),
(9, 'AQ7778lCNZY'),
(9, 'saXF2xH3A2U'),
(9, 'sDsuW9RaQK0'),

(10, 'A2GRyhU0V5M'),
(10, 'AQ7778lCNZY'),
(10, 'saXF2xH3A2U'),
(10, 'sDsuW9RaQK0'),

-- 등+자세교정 프로그램 1
(11, 'Sa0sBKUiOvU'),
(11, 'kO_b0D8P1Jg'),
(11, 'fFIL0rlRH78'),
(11, 'zKotOqfIrs0'),


(12 ,'2DRvc74GgYM'),
(12 ,'bOWoNq2nUgg'),
(12, 'DnPhKr_zaTA'),
(12, 'D3yExRi7EME'),
(12, 'AHasgA4OvXQ');





-- 모든 테이블에 대한 SELECT 문
SELECT * FROM users;
SELECT * FROM userInfo;
SELECT * FROM program;
SELECT * FROM video;
SELECT * FROM program_video;
SELECT * FROM program_likes;
SELECT * FROM review;
SELECT * FROM question;
SELECT * FROM answer;
SELECT * FROM files;
SELECT * FROM todo;
SELECT * FROM todo_likes;
SELECT * FROM keyword;
SELECT * FROM program_keyword;
SELECT * FROM userInfo_keyword;

SELECT
        ui.user_id,
        ui.goal,
        ui.gender,
        ui.age,
        ui.shape,
        ui.experience,
        ui.location,
        ui.frequency,
        ui.duration,
        COALESCE(GROUP_CONCAT(uik.keyword_code SEPARATOR ','), '') AS keyword
        FROM userInfo ui
        LEFT JOIN userInfo_keyword uik ON ui.user_id = uik.user_id
        WHERE ui.user_id = 11
        GROUP BY ui.user_id, ui.goal, ui.gender, ui.age, ui.shape, ui.experience,
        ui.location, ui.frequency, ui.duration;


