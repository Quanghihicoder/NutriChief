CREATE DATABASE IF NOT EXISTS db_nutrichief;

USE db_nutrichief;

CREATE TABLE IF NOT EXISTS `user` (
    user_id INT(11) NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(255) NOT NULL, 
    user_email VARCHAR(255) NOT NULL, 
    user_year_of_birth INT(4) NOT NULL,
    user_gender INT(1) NOT NULL,
    user_weight FLOAT(4,1) NOT NULL,
    user_height FLOAT(4,1) NOT NULL,
    user_activity_level INT(2) NOT NULL,
    user_bmi FLOAT(4,1) NOT NULL,
    user_tdee INT(5) NOT NULL,
    
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
    media_url VARCHAR(255) NOT NULL,

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
    UNIQUE (user_email),
    FOREIGN KEY (user_email) REFERENCES user(user_email)
) ENGINE=INNODB;


INSERT INTO ingredient (ingre_name, ingre_price, ingre_calo, ingre_fat, ingre_protein, ingre_carb, media_url)
VALUES
    ('Almond Milk', 2.00, 13, 1.1, 0.5, 0.5, 'https://example.com/almond_milk.jpg'),
    ('Almonds', 0.40, 576, 49.4, 21.2, 21.6, 'https://example.com/almonds.jpg'),
    ('Avocado', 1.75, 160, 14.7, 2, 8.5, 'https://example.com/avocado.jpg'),
    ('Bell Pepper', 0.90, 31, 0.3, 1.3, 6, 'https://example.com/bell_pepper.jpg'),
    ('Black Beans', 0.60, 132, 0.9, 8.9, 23.7, 'https://example.com/black_beans.jpg'),
    ('Blueberries', 2.00, 57, 0.3, 0.7, 14.5, 'https://example.com/blueberries.jpg'),
    ('Canned Tuna', 1.80, 116, 0.4, 26, 0, 'https://example.com/canned_tuna.jpg'),
    ('Carrots', 0.70, 41, 0.2, 0.9, 10, 'https://example.com/carrots.jpg'),
    ('Cashews', 2.70, 553, 43.8, 18.2, 30.2, 'https://example.com/cashews.jpg'),
    ('Celery', 0.70, 6, 0.1, 0.3, 1.2, 'https://example.com/celery.jpg'),
    ('Chia Seeds', 1.20, 486, 30.8, 16.5, 42.1, 'https://example.com/chia_seeds.jpg'),
    ('Chickpeas', 0.90, 164, 2.6, 8.9, 27.4, 'https://example.com/chickpeas.jpg'),
    ('Coconut Milk', 1.70, 230, 23.8, 2.3, 5.5, 'https://example.com/coconut_milk.jpg'),
    ('Cottage Cheese', 1.80, 206, 10.4, 11, 10.5, 'https://example.com/cottage_cheese.jpg'),
    ('Cucumber', 0.75, 15, 0.1, 0.6, 3.6, 'https://example.com/cucumber.jpg'),
    ('Ground Beef', 4.50, 250, 17, 26, 0, 'https://example.com/ground_beef.jpg'),
    ('Ground Turkey', 3.00, 149, 7.5, 20, 0, 'https://example.com/ground_turkey.jpg'),
    ('Green Beans', 1.20, 31, 0.1, 1.8, 7.1, 'https://example.com/green_beans.jpg'),
    ('Greek Yogurt', 1.80, 59, 3.3, 5.5, 3.6, 'https://example.com/greek_yogurt.jpg'),
    ('Honey', 1.90, 304, 0, 0.3, 82.4, 'https://example.com/honey.jpg'),
    ('Lentils', 0.50, 116, 0.4, 9, 20, 'https://example.com/lentils.jpg'),
    ('Lettuce', 0.80, 5, 0.1, 0.5, 1, 'https://example.com/lettuce.jpg'),
    ('Mango', 2.50, 60, 0.4, 0.8, 15, 'https://example.com/mango.jpg'),
    ('Mushrooms', 1.20, 22, 0.3, 3.1, 3.3, 'https://example.com/mushrooms.jpg'),
    ('Oats', 0.30, 389, 6.9, 16.9, 66.3, 'https://example.com/oats.jpg'),
    ('Orange', 0.50, 43, 0.2, 0.9, 9, 'https://example.com/orange.jpg'),
    ('Peanut Butter', 3.00, 588, 50, 25, 20, 'https://example.com/peanut_butter.jpg'),
    ('Pineapple', 1.80, 50, 0.1, 0.5, 13.1, 'https://example.com/pineapple.jpg'),
    ('Pork Chops', 4.50, 143, 8.4, 15.6, 0, 'https://example.com/pork_chops.jpg'),
    ('Pumpkin Seeds', 1.40, 446, 19.4, 29.8, 54, 'https://example.com/pumpkin_seeds.jpg'),
    ('Quinoa', 1.50, 120, 1.9, 4.1, 21, 'https://example.com/quinoa.jpg'),
    ('Raspberries', 2.80, 53, 0.7, 1.2, 12.8, 'https://example.com/raspberries.jpg'),
    ('Red Bell Pepper', 1.00, 31, 0.3, 1.3, 6, 'https://example.com/red_bell_pepper.jpg'),
    ('Red Lentils', 0.75, 116, 0.4, 9, 20, 'https://example.com/red_lentils.jpg'),
    ('Rice', 0.40, 130, 0.3, 2.7, 28.7, 'https://example.com/rice.jpg'),
    ('Salmon', 5.75, 206, 13.4, 20, 0, 'https://example.com/salmon.jpg'),
    ('Sardines', 2.20, 208, 11, 25, 0, 'https://example.com/sardines.jpg'),
    ('Soy Milk', 2.00, 33, 1.8, 3.3, 1.8, 'https://example.com/soy_milk.jpg'),
    ('Spinach', 1.00, 23, 0.4, 2.9, 3.6, 'https://example.com/spinach.jpg'),
    ('Strawberries', 1.50, 29, 0.3, 0.7, 7.7, 'https://example.com/strawberries.jpg'),
    ('Sweet Potato', 1.25, 86, 0.1, 1.6, 20.1, 'https://example.com/sweet_potato.jpg'),
    ('Tofu', 2.20, 144, 8, 15, 2, 'https://example.com/tofu.jpg'),
    ('Turkey Breast', 3.50, 135, 1, 30, 0, 'https://example.com/turkey_breast.jpg'),
    ('Whole Wheat Bread', 2.50, 247, 3.5, 9, 49, 'https://example.com/whole_wheat_bread.jpg'),
    ('Yogurt', 1.20, 61, 0.4, 10, 4.7, 'https://example.com/yogurt.jpg'),
    ('Zucchini', 1.10, 17, 0.3, 1.2, 3.1, 'https://example.com/zucchini.jpg'),
    ENGINE=INNODB;