CREATE DATABASE IF NOT EXISTS db_nutrichief;

USE db_nutrichief;

CREATE TABLE IF NOT EXISTS `user` (
    user_id INT(11) NOT NULL AUTO_INCREMENT, 
    user_name VARCHAR(255), 
    user_email VARCHAR(255) NOT NULL, 
    user_year_of_birth INT(4), 
    user_gender INT(1), -- 0 female, 1 - male 
    user_weight FLOAT(4,1), -- kg
    user_height FLOAT(4,1), -- cm 
    user_activity_level INT(2), -- 1,2,3,4,5
    user_bmi FLOAT(4,1),
    user_tdee INT(5),
    
    PRIMARY KEY (user_id),
    UNIQUE (user_email)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `mealpref` (
    user_id INT(11) NOT NULL,
    pref_calo INT(5) NOT NULL,  
    pref_time INT(5) NOT NULL, -- mins
    pref_goal INT(1) NOT NULL, -- loss - 0, maintain - 1, gain - 2
    pref_date_range INT(2) NOT NULL, -- daily - 1, weekly - 7, monthly - 30

    PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `food` (
    food_id INT(11) NOT NULL AUTO_INCREMENT,
    food_name VARCHAR(255) NOT NULL, 
    food_desc VARCHAR(255) NOT NULL,
    food_ctime INT(5) NOT NULL, -- mins
    food_ptime INT(5) NOT NULL, -- mins
    
    PRIMARY KEY (food_id)
) ENGINE=INNODB; 

CREATE TABLE IF NOT EXISTS `ingredient` (
    ingre_id INT(11) NOT NULL AUTO_INCREMENT,
    ingre_name VARCHAR(255) NOT NULL, 
    ingre_price FLOAT(11,1) NOT NULL, 
    ingre_calo INT(5) NOT NULL, 
    ingre_fat FLOAT(8,1) NOT NULL, -- grams
    ingre_protein FLOAT(8,1) NOT NULL, -- grams
    ingre_carb FLOAT(8,1) NOT NULL, -- grams
    ingre_img VARCHAR(255) NOT NULL,
    
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
    recipe_qty FLOAT(11,1) NOT NULL, -- grams
    recipe_desc VARCHAR(255) NOT NULL,
    recipe_title VARCHAR(255) NOT NULL,
    

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

INSERT INTO `ingredient` (ingre_name, ingre_price, ingre_calo, ingre_fat, ingre_protein, ingre_carb, ingre_img)
VALUES
('Apple', 0.60, 52, 0.2, 0.3, 10.4, null),
('Apricot', 1.00, 48, 0.4, 0.5, 9.7, null),
('Avocado', 2.00, 160, 15, 2, 9, null),
('Banana', 0.40, 90, 0.3, 1, 22.8, null),
('Cantaloupe', 0.70, 34, 0.1, 0.4, 8.1, null),
('Grapefruit', 0.70, 42, 0.1, 0.5, 9.6, null),
('Honeydew melon', 0.60, 36, 0.1, 0.4, 9.5, null),
('Kiwi', 1.20, 49, 0.5, 1, 10, null),
('Lemon', 0.50, 29, 0.1, 0.3, 9, null),
('Lime', 0.50, 17, 0.1, 0.1, 4.7, null),
('Mango', 1.00, 60, 0.5, 0.7, 16, null),
('Orange', 0.60, 47, 0.1, 0.8, 11.2, null),
('Papaya', 0.70, 43, 0.1, 0.5, 10.4, null),
('Peach', 0.80, 49, 0.3, 0.6, 9.7, null),
('Pear', 0.70, 57, 0.2, 0.4, 11.8, null),
('Pineapple', 0.80, 50, 0.1, 0.1, 13, null),
('Plum', 0.80, 47, 0.1, 0.4, 9.7, null),
('Raspberry', 1.50, 53, 0.7, 1.2, 9.3, null),
('Strawberries', 1.50, 33, 0.3, 0.7, 7.7, null),
('Watermelon', 0.50, 30, 0.2, 0.6, 7.5, null),
('Blackberry', 1.50, 49, 0.7, 1.2, 9, null),
('Blueberries', 1.50, 57, 0.4, 0.8, 10, null),
('Cherries', 1.00, 50, 0.2, 0.8, 12, null),
('Grapes', 1.20, 69, 0.3, 0.9, 15.4, null),
('Black pepper', 0.50, 25, 0.7, 2.3, 2.4, null),
('Chili powder', 1.00, 290, 10, 4.5, 20, null),
('Garlic powder', 1.00, 380, 14, 5, 27, null),
('Ginger powdeMayonaiser', 1.20, 340, 11, 4.4, 23, null),
('Salt', 0.20, 0, 0, 0, 0, null),
('Sesame seeds', 1.50, 500, 30, 10, 23, null),
('Cinnamon powder', 1.00, 250, 8, 2, 17, null),
('Cumin powder', 1.00, 300, 10, 3.8, 23, null),
('Oregano', 1.50, 270, 10, 4.5, 19, null),
('Parsley', 1.50, 25, 0.3, 2.5, 3.7, null),
('Rosemary', 1.50, 180, 1, 2, 15, null),
('Thyme', 1.50, 130, 1, 1.5, 11, null),
('Sugar', 0.50, 400, 0, 0, 100, null),
('Honey', 2.00, 300, 0, 0, 81, null),
('Maple syrup', 2.50, 260, 0, 0, 53, null),
('Soy sauce', 1.00, 80, 1, 3, 6, null),
('BBQ sauce', 1.00, 80, 0, 2, 17, null),
('Teriyaki sauce', 1.50, 90, 0, 2, 18, null),
('Honey mustard sauce', 1.50, 70, 2, 2, 15, null),
('Mayonnaise', 1.00, 700, 70, 0, 0, null),
('Ketchup', 0.50, 100, 0, 0, 20, null),
('Mustard', 1.00, 60, 0, 0, 10, null),
('Vinegar', 0.50, 0, 0, 0, 0, null),
('Olive oil', 2.00, 880, 90, 0, 0, null),
('Peanut oil', 2.00, 880, 90, 0, 0, null),
('Fish sauce', 1.50, 80, 0, 4, 6, null),
('Hoisin sauce', 1.50, 100, 10, 2, 18, null),
('Gochujang', 2.00, 150, 10, 3, 18, null),
('Tuna', 3.99, 110, 1.0, 24.0, 0.0, null),
('Salmon', 10.99, 180, 10.0, 20.0, 0.0, null),
('Chicken', 3.99, 165, 3.6, 31.0, 0.0, null),
('Beef', 4.99, 227, 20.0, 22.0, 0.0, null),
('Pork', 5.99, 140, 5.5, 26.0, 0.0, null),
('Lamb', 7.99, 230, 15.0, 25.0, 0.0, null),
('Duck', 14.99, 190, 12.0, 15.0, 0.0, null),
('Crab', 10.99, 85, 0.5, 17.0, 0.0, null),
('Shrimp', 12.99, 90, 0.5, 18.0, 0.0, null),
('Squid', 10.99, 100, 1.0, 18.0, 0.0, null),
('Mussels', 4.99, 70, 1.0, 11.0, 0.0, null),
('Octopus', 12.99, 80, 0.5, 1.0, 0.0, null),
('Milk', 0.75, 60, 4, 3.2, 5, null),
('Yogurt', 1, 100, 5, 5, 5, null),
('Cheese', 3, 110, 10, 8, 1, null),
('Butter', 2, 71, 80, 0, 0, null),
('Cream cheese', 2, 330, 22, 25, 2, null),
('Ice cream', 3, 200, 15, 5, 25, null),
('Whipped cream', 4, 240, 20, 3, 6, null),
('Egg', 0.5, 77, 5.3, 6.2, 0.6, null),
('Black beans', 0.7, 240, 2.4, 8.9, 40.4, null),
('Rice', 0.5, 130, 0.7, 2.8, 28.7, null),
('Tofu', 1.0, 76, 4.8, 8.0, 1.9, null),
('Miso', 1.0, 40, 1.0, 6.0, 9.0, null),
('Potato', 0.50, 77, 0.1, 1.9, 19.1, null),
('Carrot', 0.60, 41, 0.2, 0.9, 9.6, null),
('Onion', 0.30, 40, 0.1, 1.1, 9.3, null),
('Tomato', 0.70, 18, 0.2, 0.9, 3.9, null),
('Garlic', 1.00, 14, 0.6, 0.5, 10.2, null),
('Spring onion', 0.40, 33, 0.1, 0.9, 5.8, null),
('Seaweed', 2.00, 5, 0.1, 0.5, 9.3, null),
('Almonds', 2.99, 576, 49, 21, 22, null),
('Peanut', 1.99, 567, 49.2, 25.8, 16.1, null),
('Spinach', 0.89, 23, 0.4, 3.6, 4.3, null),
('Asparagus', 1.99, 20, 0.4, 2.2, 4.2, null),
('Broccoli', 1.49, 34, 0.4, 2.8, 6.6, null),
('Cauliflower', 0.99, 25, 0.4, 2.4, 5.0, null),
('Cucumber', 0.49, 16, 0.1, 0.7, 3.6, null),
('Mushrooms', 1.99, 22, 1.5, 2.2, 3.2, null),
('Bell Pepper', 0.99, 29, 0.4, 1.2, 5.9, null),
('Bacon', 4.99, 500, 30.0, 25.0, 0.0, null),
('Cabbage', 0.99, 25, 0.2, 2.0, 4.8, null),
('Coconut Milk', 1.99, 230, 21.0, 2.4, 5.0, null),
('Corn', 0.39, 96, 1.9, 2.9, 21.4, null),
('Pomegranate', 2.99, 83, 0.4, 1.5, 15.3, null),
('Zucchini', 0.99, 25, 0.4, 1.2, 4.8, null),
('Basil', 1.99, 25, 0.4, 2.2, 2.8, null),
('Cilantro', 1.99, 23, 0.3, 1.7, 2.0, null),
('Pumpkin', 0.99, 26, 0.1, 1.5, 5.1, null),
('Celery', 0.99, 16, 0.3, 1.0, 2.9, null),
('Cheddar Cheese', 5.99, 408, 30.0, 25.0, 0.0, null),
('Lettuce', 0.99, 15, 0.1, 0.9, 2.4, null),
('Kimchi', 1.99, 23, 0.8, 2.5, 4.9, null),
('Bamboo shoot', 1.29, 20, 0.2, 1.2, 4.6, null),
('Sausage', 2.99, 280, 22, 12, 1.4, null),
('Clam', 3.99, 80, 1.6, 10.2, 2.2, null),
('Water', 0.99, 0, 0, 0, 0, null),
('Coca-Cola', 1.99, 140, 0, 0, 39, null),
('Wine', 12.99, 120, 0, 0, 4.7, null),
('Rice noodles', 0.79, 130, 0.4, 2.2, 28.1, null),
('Bread', 1.00, 265, 2.5, 4.8, 53.0, null),
('Vanilla extract', 3.00, 23, 0.1, 0.0, 0.0, null),
('Peas', 0.75, 80, 0.4, 5.3, 15.3, null);
-- ChatGPT example: Generate ingredients include bread, vanilla extract, peas which must include name, price, calories, fat, protein, carb. And based on 100 gram of each

INSERT INTO `food` (food_name, food_desc, food_ctime, food_ptime)
VALUES
("Avocado Toast", "", 5/60, 10/60),
("Omelette with Vegetables", "", 10/60, 5/60),
("French Toast", "", 10/60, 5/60),
("Tomato Cucumber Salad", "", 0/60, 10/60),
("Creamy Coleslaw", "", 0/60, 10/60),
("Russian Salad", "", 10/60, 10/60),
("Chicken Salad", "", 15/60, 10/60);

INSERT INTO `recipe` (food_id, ingre_id, media_id, recipe_qty, recipe_desc, recipe_title)
VALUES
(1,3, NULL, 200, "Cut the ripe avocado in half, remove the pit, and scoop out the flesh into a bowl. Use a fork to mash the avocado until it reaches your desired consistency.", "1 ripe avocado"),
(1,113, NULL, 200, "Toast the bread.", "2 slices of bread"),
(1,93, NULL, 100, "Cook the bacon until crispy.", "2 slices of bacon"),
(1,29, NULL, 3, "Add salt.", "1/2 teaspoon salt"),
(1,25, NULL, 1.5, "Add black pepper.", "1/4 teaspoon black pepper"),
(1,9, NULL, 15, "Add lemon juice.", "1 tablespoon lemon juice"),

(2,72, NULL, 100, "", "2 eggs"),
(2,29, NULL, 3, "Add salt.", "1/2 teaspoon salt"),
(2,25, NULL, 1.5, "Add black pepper.", "1/4 teaspoon black pepper"),
(2,65, NULL, 60, "Add Milk", "1/4 cup milk"),
(2,48, NULL, 15, "", "1 tablespoon olive oil"),
(2,79, NULL, 75, "", "1/2 onion, chopped"),
(2,92, NULL, 75, "", "1/2 green/red/yellow bell pepper, chopped"),
(2,91, NULL, 75, "", "1/2 cup mushrooms, sliced"),
(2,80, NULL, 75, "", "1/2 cup tomatoes, diced"),
(2,67, NULL, 30, "", "1/4 cup grated cheese"),

(3,113, NULL, 720, "", "3 thick slices bread"),
(3,65, NULL, 120, "", "1/2 cup milk"),
(3,72, NULL, 200, "", "2 large eggs"),
(3,114, NULL, 6, "", "1 teaspoon vanilla extract"),
(3,31, NULL, 3, "", "1/2 teaspoon cinnamon powder"),
(3,29, NULL, 1.5, "", "1/4 teaspoon salt"),
(3,68, NULL, 15, "", "1 tablespoon butter"),
(3,39, NULL, 30, "", "2 tablespoon maple syrup"),

(4,80, NULL, 100, "", "tomato, diced"),
(4,90, NULL, 100, "", "cucumber, diced"),
(4,79, NULL, 50, "", "onion, diced"),
(4,47, NULL, 15, "", "1 tablespoon vinegar"),
(4,48, NULL, 15, "", "1 tablespoon olive oil"),
(4,29, NULL, 3, "Add salt.", "1/2 teaspoon salt"),
(4,25, NULL, 1.5, "Add black pepper.", "1/4 teaspoon black pepper"),


(5,94, NULL, 100, "", "cabbage, shredded"),
(5,78, NULL, 50, "", "carrot, shredded"),
(5,44, NULL, 30, "", "2 tablespoons mayonnaise"),
(5,47, NULL, 15, "", "1 tablespoon vinegar"),
(5,37, NULL, 15, "", "1 tablespoon sugar"),
(5,29, NULL, 3, "Add salt.", "1/2 teaspoon salt"),
(5,25, NULL, 1.5, "Add black pepper.", "1/4 teaspoon black pepper"),

(6,77, NULL, 100, "", "potatoes, peeled and diced"),
(6,78, NULL, 50, "", "carrots, peeled and diced"),
(6,115, NULL, 50, "", "peas, cooked"),
(6,93, NULL, 50, "", "bacon, diced"),
(6,44, NULL, 15, "", "1 tablespoons mayonnaise"),
(6,29, NULL, 3, "Add salt.", "1/2 teaspoon salt"),
(6,25, NULL, 1.5, "Add black pepper.", "1/4 teaspoon black pepper"),

(7,55, NULL, 100, "", "cooked chicken, shredded"),
(7,94, NULL, 100, "", "cabbage, shredded"),
(7,78, NULL, 50, "", "carrot, shredded"),
(7,10, NULL, 15, "", "1 tablespoon lime juice"),
(7,50, NULL, 15, "", "1 tablespoon fish sauce"),
(7,37, NULL, 7.5, "", "1/2 tablespoon sugar"),
(7,26, NULL, 1.5, "", "1/4 teaspoon chili powder");

-- ChatGPT example: how to make Omelette with Vegetables from Bell Pepper, Onion, Tomato, Mushroom, Cheese, Eggs include the amount in gram, cook time, prepare time

-- ("Creamy Garlic Mashed Potatoes", "")  Potato, Garlic, Butter, Milk, Salt
-- ("Garlic Butter Mushrooms", "") Mushrooms, Garlic, Butter, Parsley
-- ("Ratatouille", "") Eggplant, Zucchini, Bell Pepper, Tomato, Onion, Garlic, Herbs (such as Oregano and Basil)
-- ("Tuna Tartare", "") Fresh Tuna, Soy Sauce, Sesame Oil, Avocado, Spring Onion
-- ("Creamy Mushroom Soup", ""): Mushrooms, Onion, Garlic, Thyme, Vegetable Broth, Cream
-- ("Thai Coconut Shrimp Soup", ""): Shrimp, Coconut Milk, Lemongrass, Ginger, Garlic, Lime, Chili Pepper
-- ("Clam Chowder", ""): Clams, Bacon, Potato, Onion, Celery, Garlic, Cream, Fish Broth
-- ("Cauliflower Soup", ""): Cauliflower, Onion, Garlic, Vegetable Broth, Cream
-- ("Thai Tom Yum Soup", ""): Shrimp/Chicken/Tofu, Lemongrass, Ginger, Lime, Chili Pepper, Mushrooms, Coconut Milk
-- ("Crab and Sweet Corn Soup", "") Crab Meat, Sweet Corn, Chicken Broth, Eggs
-- ("Miso Soup", ""): Miso Paste, Tofu, Wakame Seaweed, Green Onions
-- ("Egg Drop Soup", "") Chicken Broth, Eggs, Cornstarch, Green Onions

-- ("Teriyaki Chicken Stir-Fry", "") Chicken, Bell Pepper, Onion, Broccoli, Carrot, Teriyaki Sauce, Rice
-- ("Beef and Broccoli Stir-Fry", "") Beef, Broccoli, Garlic, Soy Sauce, Rice
-- ("Thai Red Curry Chicken", "") Chicken, Red Curry Paste, Coconut Milk, Bell Pepper, Thai Basil, Rice
-- ("Black Pepper Steak", "") Beef, Black Pepper, Soy Sauce, Garlic Powder, Onion Powder
-- ("Rosemary Roasted Lamb", "") Lamb Chops, Rosemary, Garlic, Olive Oil, Lemon
-- ("Garlic Butter Shrimp Pasta", "") Shrimp, Spaghetti, Garlic, Butter, Parsley
-- ("Lemongrass Chicken Skewers", ""): Chicken, Lemongrass, Garlic, Lime Juice, Fish Sauce, Peanut Oil, White Rice
-- ("Bibimbap", ""): Rice, Vegetables (Carrot, Spinach, Bean Sprouts, Zucchini), Beef, Egg, Gochujang Sauce
-- ("Pork Belly Kimchi Stew", ""): Pork Belly, Kimchi, Tofu, Red Pepper Flakes, Garlic, Onion
-- ("Korean Beef BBQ", ""): Beef, Soy Sauce, Sesame Oil, Garlic, Ginger, Sugar, Pear, Green Onions
-- ("Grilled Salmon Teriyaki", ""): Salmon, Teriyaki Sauce, Sesame Seeds, Steamed Rice
-- ("Beef Teriyaki", ""): Beef, Teriyaki Sauce, Sesame Seeds, Steamed Rice
-- ("Chicken Teriyaki", ""): Chicken, Teriyaki Sauce, Steamed Rice, Spring Onion
-- ("Salmon Nigiri", ""): Salmon, Sushi Rice, Soy Sauce, Wasabi
-- ("Sesame Shrimp", "") Shrimp, Sesame Seeds, Soy Sauce, Honey, Garlic, Ginger
-- ("Mussels in White Wine and Garlic Sauce", "") mussels, olive oil, garlic, onion,  white wine, vegetable broth, butter, parsley, salt, black pepper

-- ("Apple Pie", ""): Apples, Cinnamon Powder, Sugar, Butter, Pie Crust
-- ("Raspberry Cheesecake Bars", ""): Raspberries, Cream Cheese, Graham Cracker Crust, Sugar
-- ("Mango Sticky Rice", "") Mango, Sweet Glutinous Rice, Coconut Milk, Sesame Seeds
-- ("Mango Bingsu", ""): Shaved Ice, Mango, Condensed Milk, Red Beans, Rice Cakes, Whipped Cream
-- ("Nutella Banana Crepes", ""): Banana, Nutella, Crepes, Whipped Cream
-- ("Apple","")
-- ("Grapefruit","")
-- ("Grapes","")
-- ("Watermelon","")
-- ("Orange","")
-- ("Mango","")

-- ("Matcha Latte", ""): Matcha Powder, Milk, Sugar
-- ("Watermelon Soju Cocktail", ""): Watermelon, Soju, Lime Juice, Mint Leaves
-- ("Kiwi Limeade", ""): Kiwi, Lime, Sugar, Water
-- ("Peach Iced Tea", ""): Peach, Black Tea, Sugar, Ice
-- ("Lemon Limeade", ""): Lemon, Lime, Sugar, Water, Ice
-- ("Coca-Cola","")

