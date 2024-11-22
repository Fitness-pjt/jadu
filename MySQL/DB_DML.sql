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
(1, 'MALE', '2030', 'NORMAL', 'MUSCLE', 2, 'FITNESS', 5, 60),
(2, 'FEMALE', '2030', 'SLIM', 'DIET', 1, 'HOME', 3, 45),
(3, 'MALE', '4050', 'FAT', 'DIET', 0, 'HOME', 4, 30),
(4, 'FEMALE', '2030', 'NORMAL', 'STAMINA', 3, 'FITNESS', 6, 90),
(5, 'MALE', '60', 'NORMAL', 'RECOVERY', 1, 'OUTSIDE', 3, 45),
(6, 'FEMALE', '2030', 'SLIM', 'MUSCLE', 2, 'FITNESS', 5, 60),
(7, 'MALE', '4050', 'NORMAL', 'MUSCLE', 4, 'FITNESS', 6, 90),
(8, 'FEMALE', '2030', 'NORMAL', 'DIET', 2, 'HOME', 4, 45),
(9, 'MALE', '2030', 'FAT', 'DIET', 1, 'HOME', 3, 30),
(10, 'FEMALE', '4050', 'SLIM', 'RECOVERY', 2, 'OUTSIDE', 4, 45);

-- Program 더미데이터 (10개)
INSERT INTO program (user_id, title, description, duration_weeks, level, video_cnt, program_img_path) VALUES
(1, '초보자를 위한 전신운동', '처음 시작하는 분들을 위한 프로그램', 2, 'BEGINNER', 10, '/programs/full_body.jpg'),
(2, '다이어트 특급 프로그램', '체지방 감소에 초점', 1, 'INTERMEDIATE', 7, '/programs/diet.jpg'),
(3, '근력 강화 프로그램', '근육량 증가를 위한 운동', 2, 'ADVANCED', 14, '/programs/strength.jpg'),
(4, '홈트레이닝 기초', '집에서 할 수 있는 운동', 1, 'BEGINNER', 5, '/programs/home.jpg'),
(5, '스트레칭 마스터', '유연성 향상 프로그램', 1, 'BEGINNER', 6, '/programs/stretch.jpg'),
(6, '체중감량 챌린지', '한달 완성 다이어트', 4, 'INTERMEDIATE', 28, '/programs/weight_loss.jpg'),
(7, '파워 리프팅', '기초 근력 향상 프로그램', 3, 'ADVANCED', 15, '/programs/power.jpg'),
(8, '시니어 건강운동', '노년층을 위한 운동', 2, 'BEGINNER', 8, '/programs/senior.jpg'),
(9, '코어 강화 운동', '복근 및 코어 강화', 2, 'INTERMEDIATE', 12, '/programs/core.jpg'),
(10, '요가 플로우', '요가 기초부터 고급까지', 4, 'INTERMEDIATE', 20, '/programs/yoga.jpg');

-- Video 더미데이터 (10개)
INSERT INTO video (video_id) VALUES
('abc123xyz1'),
('def456uvw2'),
('ghi789rst3'),
('jkl012opq4'),
('mno345lmn5'),
('pqr678ijk6'),
('stu901ghi7'),
('vwx234def8'),
('yza567abc9'),
('bcd890xyz0');

-- Program_Video 더미데이터 (10개)
INSERT INTO program_video (program_id, video_id) VALUES
(1, 'abc123xyz1'),
(2, 'def456uvw2'),
(3, 'ghi789rst3'),
(4, 'jkl012opq4'),
(5, 'mno345lmn5'),
(6, 'pqr678ijk6'),
(7, 'stu901ghi7'),
(8, 'vwx234def8'),
(9, 'yza567abc9'),
(10, 'bcd890xyz0');

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
INSERT INTO review (user_id, program_id, title, content, star) VALUES
(1, 2, '정말 좋아요', '일주일만에 효과봤어요', 5),
(2, 3, '강추합니다', '근력이 확실히 좋아졌어요', 4),
(3, 4, '괜찮네요', '집에서 하기 좋아요', 4),
(4, 5, '효과만점', '유연성이 늘었어요', 5),
(5, 6, '좋은 프로그램', '초보자에게 추천해요', 4),
(6, 7, '탁월한 선택', '전문적인 내용이에요', 5),
(7, 8, '만족스러워요', '꾸준히 하고 있습니다', 4),
(8, 9, '추천합니다', '코어가 단단해졌어요', 5),
(9, 10, '최고에요', '요가 입문자에게 좋아요', 5),
(10, 1, '잘 따라가고 있어요', '차근차근 늘어나는게 좋아요', 4);

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

-- Files 더미데이터 (10개)
INSERT INTO files (file_case, file_name, extension, user_id, question_id) VALUES
('PROFILE', 'profile1', 'jpg', 1, NULL),
('BOARD', 'board1', 'png', 2, NULL),
('QUESTION', 'question1', 'jpg', 3, 1),
('PROGRAM', 'program1', 'mp4', 4, NULL),
('PROFILE', 'profile2', 'png', 5, NULL),
('BOARD', 'board2', 'jpg', 6, NULL),
('QUESTION', 'question2', 'png', 7, 2),
('PROGRAM', 'program2', 'jpg', 8, NULL),
('PROFILE', 'profile3', 'jpg', 9, NULL),
('BOARD', 'board3', 'png', 10, NULL);

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
