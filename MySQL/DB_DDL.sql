DROP DATABASE IF EXISTS `ssafy_pjt`;
CREATE DATABASE `ssafy_pjt`;

USE `ssafy_pjt`;

-- Users and related tables
CREATE TABLE `users` (
    `user_id` INT NOT NULL AUTO_INCREMENT,
    `user_nickname` VARCHAR(20) NOT NULL,
    `user_email` VARCHAR(50) NOT NULL UNIQUE,
    `user_password` VARCHAR(255) NOT NULL,
    `user_name` VARCHAR(20) NOT NULL,
    `user_created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `user_status` BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (`user_id`)
);

SELECT * FROM users;

CREATE TABLE `userInfo` (
    `user_id` INT NOT NULL,
    `goal` ENUM('DIET','MUSCLE','RECOVERY','STAMINA'),
    `gender` ENUM('MALE', 'FEMALE') NOT NULL,
    `age` ENUM('10','2030','4050','60'),
    `shape` ENUM('SLIM' , 'NORMAL', 'FAT'),
    `experience` ENUM('1', '2', '3','4','5'),
    `location` ENUM('HOME','FITNESS','OUTSIDE','ETC'),
    `frequency` INT ,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

-- Program and Video tables
CREATE TABLE `program` (
    `program_id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `title` VARCHAR(100) NOT NULL,
    `description` VARCHAR(255),
    `duration_weeks` INT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `level` ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED'),
    PRIMARY KEY (`program_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `video` (
    `video_id` VARCHAR(50) NOT NULL,  -- YouTube ID를 직접 PK로 사용
    PRIMARY KEY (`video_id`)
);

CREATE TABLE `program_video` (
    `program_id` INT NOT NULL,
    `video_id` VARCHAR(50) NOT NULL,  
    PRIMARY KEY (`program_id`, `video_id`),
    FOREIGN KEY (`program_id`) REFERENCES `program` (`program_id`) ON DELETE CASCADE,
    FOREIGN KEY (`video_id`) REFERENCES `video` (`video_id`) ON DELETE CASCADE
   
);

-- Program likes
CREATE TABLE `program_likes` (
    `program_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    PRIMARY KEY (`program_id`, `user_id`),
    FOREIGN KEY (`program_id`) REFERENCES `program` (`program_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

-- Community features
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
    `review_id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `program_id` INT NOT NULL,
    `title` VARCHAR(100) NOT NULL,
    `content` TEXT NOT NULL,
    `star` INT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`review_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`program_id`) REFERENCES `program` (`program_id`) ON DELETE CASCADE
);

CREATE TABLE `question` (
    `question_id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `program_id` INT NOT NULL,
    `title` VARCHAR(100) NOT NULL,
    `content` TEXT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`question_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`program_id`) REFERENCES `program` (`program_id`) ON DELETE CASCADE
);

CREATE TABLE `answer` (
    `answer_id` INT NOT NULL AUTO_INCREMENT,
    `question_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `content` TEXT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`answer_id`),
    FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

-- File management
CREATE TABLE `files` (
    `file_id`  INT NOT NULL AUTO_INCREMENT,
    `file_case` ENUM('PROFILE', 'BOARD', 'QUESTION', 'REVIEW'),
    `file_name` VARCHAR(100) NOT NULL,
    `extension` VARCHAR(20) NOT NULL,
    `file_path` VARCHAR(255) NOT NULL,
    `user_id` INT NOT NULL,
    `question_id` INT,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`file_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE
);

-- Todo system
CREATE TABLE `todo` (
    `todo_id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `program_id` INT NOT NULL,
    `video_id` VARCHAR(50) NOT NULL,  
    `date` DATE NOT NULL,
    `status` BOOLEAN DEFAULT FALSE,
    `content` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`todo_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`program_id`) REFERENCES `program` (`program_id`) ON DELETE CASCADE,
    FOREIGN KEY (`video_id`) REFERENCES `video` (`video_id`) ON DELETE CASCADE
);

CREATE TABLE `todo_likes` (
    `todo_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    PRIMARY KEY (`todo_id`, `user_id`),
    FOREIGN KEY (`todo_id`) REFERENCES `todo` (`todo_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

-- Keywords system
CREATE TABLE `keyword` (
    `keyword_code` INT NOT NULL AUTO_INCREMENT,
    `keyword` VARCHAR(20) NOT NULL UNIQUE,
    PRIMARY KEY (`keyword_code`)
);

CREATE TABLE `program_keyword` (
    `program_id` INT NOT NULL,
    `keyword_code` INT NOT NULL,
    PRIMARY KEY (`program_id`, `keyword_code`),
    FOREIGN KEY (`program_id`) REFERENCES `program` (`program_id`) ON DELETE CASCADE,
    FOREIGN KEY (`keyword_code`) REFERENCES `keyword` (`keyword_code`) ON DELETE CASCADE
);

CREATE TABLE `userInfo_keyword` (
    `user_id` INT NOT NULL,
    `keyword_code` INT NOT NULL,
    PRIMARY KEY (`user_id`, `keyword_code`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`keyword_code`) REFERENCES `keyword` (`keyword_code`) ON DELETE CASCADE
);