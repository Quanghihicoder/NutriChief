CREATE DATABASE IF NOT EXISTS db_nutrichief;

USE db_nutrichief;

CREATE TABLE IF NOT EXISTS `user` (
    user_id INT(11) NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(255), 
    user_email VARCHAR(255) NOT NULL, 
    user_year_of_birth INT(4),
    user_gender INT(1),
    user_weight FLOAT(4,1),
    user_height FLOAT(4,1),
    user_activity_level INT(2),
    user_bmi FLOAT(4,1),
    user_tdee INT(5),
    
    PRIMARY KEY (user_id),
    UNIQUE (user_email)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `mealpref` (
    user_id INT(11) NOT NULL,
    pref_calo INT(5) NOT NULL, 
    pref_time FLOAT(7,2) NOT NULL, 
    pref_goal VARCHAR(255) NOT NULL,
    pref_date_range INT(1) NOT NULL,

    PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `food` (
    food_id INT(11) NOT NULL AUTO_INCREMENT,
    food_name VARCHAR(255) NOT NULL, 
    food_desc VARCHAR(255) NOT NULL,
    food_price FLOAT(11,1) NOT NULL,
    food_time FLOAT(7,2) NOT NULL,
    
    PRIMARY KEY (food_id)
) ENGINE=INNODB; 

CREATE TABLE IF NOT EXISTS `ingredient` (
    ingre_id INT(11) NOT NULL AUTO_INCREMENT,
    ingre_name VARCHAR(255) NOT NULL, 
    ingre_price FLOAT(11,1) NOT NULL,
    ingre_calo INT(5) NOT NULL,
    ingre_fat FLOAT(8,1) NOT NULL,
    ingre_protein FLOAT(8,1) NOT NULL,
    ingre_carb FLOAT(8,1) NOT NULL,
    
    PRIMARY KEY (ingre_id)
) ENGINE=INNODB; 


CREATE TABLE IF NOT EXISTS `media` (
    media_id INT(11) NOT NULL AUTO_INCREMENT,
    media_name VARCHAR(255) NOT NULL, 
    media_url VARCHAR(255) NOT NULL,
    
    PRIMARY KEY (media_id)
) ENGINE=INNODB; 

CREATE TABLE IF NOT EXISTS `recipe` (
    food_id INT(11) NOT NULL,
    ingre_id INT(11) NOT NULL,
    media_id INT(11),
    recipe_qty INT(11) NOT NULL,
    recipe_desc VARCHAR(255) NOT NULL,
    recipe_title VARCHAR(255) NOT NULL,
    recipe_ctime FLOAT(7,2) NOT NULL,
    recipe_ptime FLOAT(7,2) NOT NULL,

    PRIMARY KEY (food_id, ingre_id),
    FOREIGN KEY (food_id) REFERENCES food(food_id),
    FOREIGN KEY (ingre_id) REFERENCES ingredient(ingre_id),
    FOREIGN KEY (media_id) REFERENCES media(media_id)
)ENGINE=INNODB; 

CREATE TABLE IF NOT EXISTS `meal` (
    meal_id INT(11) NOT NULL AUTO_INCREMENT,
    user_id INT(11) NOT NULL, 
    meal_food VARCHAR(255) NOT NULL,
    meal_date DATE NOT NULL,
    meal_type VARCHAR(255) NOT NULL,

    PRIMARY KEY (meal_id, user_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `otp` (
    user_email VARCHAR(255) NOT NULL,
    otp_key INT(6) NOT NULL,

    PRIMARY KEY (user_email),
    UNIQUE (user_email)
) ENGINE=INNODB;