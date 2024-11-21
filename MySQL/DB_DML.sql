USE `ssafy_pjt`;

-- users 테이블에 데이터 추가
INSERT INTO users (user_nickname, user_email, user_password, user_name, user_status)
VALUES 
    ('johndoe', 'john@example.com', 'password123', 'John Doe', TRUE),
    ('janedoe', 'jane@example.com', 'password456', 'Jane Doe', FALSE),

-- userInfo 테이블에 데이터 추가
INSERT INTO userInfo (user_id, gender, age, shape, goal, experience, location, frequency, duration)
VALUES 
    (1, 'MALE', '2030', 'SLIM', 'DIET', 2, 'FITNESS', 3, 3),
    (2, 'FEMALE', '4050', 'NORMAL', 'MUSCLE', 4, 'HOME', 4, 2);


-- program 테이블에 데이터 추가
INSERT INTO program (user_id, title, description, duration_weeks, level)
VALUES 
    (1, 'Full Body Workout', 'A complete workout plan', 12, 'BEGINNER'),
    (2, 'Advanced Cardio', 'High-intensity cardio program', 8, 'ADVANCED');

-- video 테이블에 데이터 추가
INSERT INTO video (video_id)
VALUES 
    ('xv9sT8n'),
    ('dFwS8j7');

-- program_video 테이블에 데이터 추가
INSERT INTO program_video (program_id, video_id)
VALUES 
    (1, 'xv9sT8n'),
    (2, 'dFwS8j7');

-- program_likes 테이블에 데이터 추가
INSERT INTO program_likes (program_id, user_id)
VALUES 
    (1, 2),
    (2, 1);

-- review 테이블에 데이터 추가
INSERT INTO review (user_id, program_id, title, content, star)
VALUES 
    (1, 1, 'Great Workout', 'Helped me stay fit!', 5),
    (2, 2, 'Intense Program', 'Challenging but effective!', 4), 
    (1, 2, '좋은 프로그램 입니다!', '너무 유익해요!!', 5);

-- question 테이블에 데이터 추가
INSERT INTO question (user_id, program_id, title, content)
VALUES 
    (1, 1, 'How many days a week?', 'Is it okay to do this workout daily?'),
    (2, 2, 'Duration of each session?', 'How long is each session?');

-- answer 테이블에 데이터 추가
INSERT INTO answer (question_id, user_id, content)
VALUES 
    (1, 2, 'It’s best to do it every other day.'),
    (2, 1, 'Each session should last about 30 minutes.');

-- files 테이블에 데이터 추가
INSERT INTO files (file_case, file_name, extension, user_id)
VALUES 
    ('PROFILE', 'profile_pic_john', 'jpg', 1),
    ('BOARD', 'board_post_img', 'png',  2);

-- todo 테이블에 데이터 추가
INSERT INTO todo (user_id, program_id, video_id, date, status, content)
VALUES 
    (1, 1, 'xv9sT8n', '2024-11-01', FALSE, 'Complete warm-up exercises'),
    (2, 2, 'dFwS8j7', '2024-11-02', TRUE, 'Cardio workout session');

-- todo_likes 테이블에 데이터 추가
INSERT INTO todo_likes (todo_id, user_id)
VALUES 
    (1, 2),
    (2, 1);
    
-- keyword 테이블에 데이터 추가
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


-- program_keyword 테이블에 데이터 추가
INSERT INTO program_keyword (program_id, keyword_code)
VALUES 
    (1, 1),
    (2, 2);

-- userInfo_keyword 테이블에 데이터 추가
INSERT INTO userInfo_keyword (user_id, keyword_code)
VALUES 
    (1, 1),
    (2, 2);

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
