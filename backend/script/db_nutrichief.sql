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
    food_type INT(1) NOT NULL,
    food_img VARCHAR(255),

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
    ingre_img VARCHAR(1000) NOT NULL,

    PRIMARY KEY (ingre_id)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `media` (
    media_id INT(11) NOT NULL,
    media_name VARCHAR(255) NOT NULL,
    media_url VARCHAR(255) NOT NULL,

    PRIMARY KEY (media_id)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `recipe` (
    food_id INT(11) NOT NULL,
    ingre_id INT(11) NOT NULL,
    media_url VARCHAR(1000) NOT NULL,
    recipe_qty FLOAT(11,1), -- grams
    recipe_desc VARCHAR(255) NOT NULL,
    recipe_title VARCHAR(255) NOT NULL,
    media_url VARCHAR(255) NOT NULL,

    PRIMARY KEY (food_id, ingre_id),
    FOREIGN KEY (food_id) REFERENCES food(food_id),
    FOREIGN KEY (ingre_id) REFERENCES ingredient(ingre_id)
)ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `meal` (
    user_id INT(11) NOT NULL,
    food_id INT(11) NOT NULL,
    meal_date DATE NOT NULL,
    meal_checked INT (1) NOT NULL, -- 0 - false; 1 - true 

    PRIMARY KEY (user_id, food_id, meal_date),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (food_id) REFERENCES food(food_id)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `otp` (
    user_email VARCHAR(255) NOT NULL,
    otp_key INT(6) NOT NULL,

    PRIMARY KEY (user_email),
    UNIQUE (user_email)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `post` (
    post_id INT(11) NOT NULL AUTO_INCREMENT,
    user_id INT(11) NOT NULL,
    post_title VARCHAR(255) NOT NULL,
    post_detail TEXT NOT NULL,

    PRIMARY KEY (post_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `comment` (
    comment_id INT(11) NOT NULL AUTO_INCREMENT,
    user_id INT(11) NOT NULL,
    post_id INT(11) NOT NULL,
    comment_detail TEXT NOT NULL,
    
    PRIMARY KEY (comment_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (post_id) REFERENCES post(post_id)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `react` (
    user_id INT(11) NOT NULL,
    post_id INT(11) NOT NULL,
    react_type INT (1) NOT NULL, 
    
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (post_id) REFERENCES post(post_id)
) ENGINE=INNODB;

INSERT INTO `ingredient` (ingre_name, ingre_price, ingre_calo, ingre_fat, ingre_protein, ingre_carb, ingre_img)
VALUES
('Apple', 0.60, 52, 0.2, 0.3, 10.4, "https://5.imimg.com/data5/AK/RA/MY-68428614/apple.jpg"),
('Apricot', 1.00, 48, 0.4, 0.5, 9.7, "https://cdn.britannica.com/36/160636-050-B1DC5C0A/Laetrile-apricot-pits.jpg"),
('Avocado', 2.00, 160, 15, 2, 9, "https://images.immediate.co.uk/production/volatile/sites/30/2020/02/Avocados-3d84a3a.jpg"),
('Banana', 0.40, 90, 0.3, 1, 22.8, "https://www.organics.ph/cdn/shop/products/banana-cavendish-1kg-per-hand-fruits-vegetables-fresh-produce-221293_800x.jpg"),
('Cantaloupe', 0.70, 34, 0.1, 0.4, 8.1, "https://chefol.com/wp-content/uploads/2023/07/athena-cantaloupe.jpg"),
('Grapefruit', 0.70, 42, 0.1, 0.5, 9.6, "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/Grapefruits_-_whole-halved-segments.jpg/1200px-Grapefruits_-_whole-halved-segments.jpg"),
('Honeydew melon', 0.60, 36, 0.1, 0.4, 9.5, "https://upload.wikimedia.org/wikipedia/commons/f/f5/Honeydew.jpg"),
('Kiwi', 1.20, 49, 0.5, 1, 10, "https://images.immediate.co.uk/production/volatile/sites/30/2020/02/Kiwi-fruits-582a07b.jpg"),
('Lemon', 0.50, 29, 0.1, 0.3, 9, "https://product.hstatic.net/1000282430/product/219006809431_master.jpg"),
('Lime', 0.50, 17, 0.1, 0.1, 4.7, "https://farzana.ae/images/thumbs/0004930_lime_500.jpeg"),
('Mango', 1.00, 60, 0.5, 0.7, 16, "https://images.immediate.co.uk/production/volatile/sites/30/2018/08/mango-fee0d79-e1648560084294.jpg"),
('Orange', 0.60, 47, 0.1, 0.8, 11.2, "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/Oranges_-_whole-halved-segment.jpg/640px-Oranges_-_whole-halved-segment.jpg"),
('Papaya', 0.70, 43, 0.1, 0.5, 10.4, "https://www.dole.com/-/media/project/dole/produce-images/fruit/papaya_cut_web.png"),
('Peach', 0.80, 49, 0.3, 0.6, 9.7, "https://static.libertyprim.com/files/familles/peche-large.jpg"),
('Pear', 0.70, 57, 0.2, 0.4, 11.8, "https://static.libertyprim.com/files/familles/poire-large.jpg"),
('Pineapple', 0.80, 50, 0.1, 0.1, 13, "https://static.libertyprim.com/files/familles/poire-large.jpg"),
('Plum', 0.80, 47, 0.1, 0.4, 9.7, "https://skinkraft.com/cdn/shop/articles/Evidence-Based_0eff67fd-86c2-400a-b4a1-7fe2d9a14db5_1024x400.jpg?v=1639470268"),
('Raspberry', 1.50, 53, 0.7, 1.2, 9.3, "https://www.incredibleseeds.ca/cdn/shop/products/Tree-BlackRaspberry_grande.jpg?v=1669396460"),
('Strawberries', 1.50, 33, 0.3, 0.7, 7.7, "https://i.pinimg.com/originals/db/f0/04/dbf004bfe8522da759eaf94190290aa3.png"),
('Watermelon', 0.50, 30, 0.2, 0.6, 7.5, "https://hips.hearstapps.com/hmg-prod/images/fresh-ripe-watermelon-slices-on-wooden-table-royalty-free-image-1684966820.jpg"),
('Blackberry', 1.50, 49, 0.7, 1.2, 9, "https://seed2plant.in/cdn/shop/products/blackberry.jpg?v=1679738543"),
('Blueberries', 1.50, 57, 0.4, 0.8, 10, "https://img.imageboss.me/fourwinds/width/425/dpr:2/shop/products/shutterstock_167872100blueberry1.jpg?v=1531795854"),
('Cherries', 1.00, 50, 0.2, 0.8, 12, "https://file.hstatic.net/1000304337/file/cherry-1-min__1__cee638753ee84177bc2a0ca6e0e1d7c4_1024x1024.jpg"),
('Grapes', 1.20, 69, 0.3, 0.9, 15.4, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZDtyDuapdxX5rQ6TModDtK2Z3pJCcackoNQ&usqp=CAU"),
('Black pepper', 0.50, 25, 0.7, 2.3, 2.4, "https://hanoismallgoods.com/wp-content/uploads/2021/05/black-pepper-powder.jpg"),
('Chili powder', 1.00, 290, 10, 4.5, 20, "https://www.daringgourmet.com/wp-content/uploads/2019/01/Chili-Powder-1-square-edited.jpg"),
('Garlic powder', 1.00, 380, 14, 5, 27, "https://hanoismallgoods.com/wp-content/uploads/2021/05/garlic-powder.jpg"),
('Ginger powder', 1.20, 340, 11, 4.4, 23, "https://vn-test-11.slatic.net/p/f908107feac5e2811a5d6638f9d6df7e.jpg"),
('Salt', 0.20, 0, 0, 0, 0, "https://health.clevelandclinic.org/wp-content/uploads/sites/3/2022/02/TooMuchSodiuml-1051727580-770x533-1-650x428.jpg"),
('Sesame seeds', 1.50, 500, 30, 10, 23, "https://www.prideofindia.co/cdn/shop/products/shutterstock_164078822.jpg?v=1571500292"),
('Cinnamon powder', 1.00, 250, 8, 2, 17, "https://hanoismallgoods.com/wp-content/uploads/2021/05/cinnamon-powder.jpg"),
('Cumin powder', 1.00, 300, 10, 3.8, 23, "https://www.viyanagro.com/wp-content/uploads/coriander-cumin-powder.jpg"),
('Oregano', 1.50, 270, 10, 4.5, 19, "https://cdn.tgdd.vn/Files/2020/11/18/1307706/la-oregano-tuoi-va-kho-loai-nao-tot-hon-202011182120311890.jpg"),
('Parsley', 1.50, 25, 0.3, 2.5, 3.7, "https://hstatic.net/520/1000107520/1/2016/8-17/ngo_tay_-_parsley_grande.jpeg"),
('Rosemary', 1.50, 180, 1, 2, 15, "https://www.thespicehouse.com/cdn/shop/articles/Rosemary_720x.jpg?v=1639676021"),
('Thyme', 1.50, 130, 1, 1.5, 11, "https://exoticvegetablespune.com/wp-content/uploads/2019/03/Thyme.jpg"),
('Sugar', 0.50, 400, 0, 0, 100, "https://www.tasteofhome.com/wp-content/uploads/2019/11/sugar-shutterstock_615908132.jpg?fit=700%2C800"),
('Honey', 2.00, 300, 0, 0, 81, "https://images.immediate.co.uk/production/volatile/sites/30/2020/02/honey-pot-4d7c98d.jpg?quality=90&resize=556,505"),
('Maple syrup', 2.50, 260, 0, 0, 53, "https://i.cbc.ca/1.5913747.1613256030!/cumulusImage/httpImage/maple-syrup-shutterstock.jpg"),
('Soy sauce', 1.00, 80, 1, 3, 6, "https://insanelygoodrecipes.com/wp-content/uploads/2022/05/Soy-Sauce-in-a-Small-Container-with-Soy-Beans.jpg"),
('BBQ sauce', 1.00, 80, 0, 2, 17, "https://carlsbadcravings.com/wp-content/uploads/2020/05/Homemade-Barbecue-Sauce-v14.jpg"),
('Teriyaki sauce', 1.50, 90, 0, 2, 18, "https://www.daringgourmet.com/wp-content/uploads/2013/05/Teriyaki-Sauce-1-square.jpg"),
('Honey mustard sauce', 1.50, 70, 2, 2, 15, "https://www.twopeasandtheirpod.com/wp-content/uploads/2021/09/Honey-Mustard-Sauce-5-650x975.jpg"),
('Mayonnaise', 1.00, 700, 70, 0, 0, "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/mayonnaise-8a626ad.jpg"),
('Ketchup', 0.50, 100, 0, 0, 20, "https://www.skinnytaste.com/wp-content/uploads/2012/06/homemade-ketchup.jpg"),
('Mustard', 1.00, 60, 0, 0, 10, "https://www.budgetbytes.com/wp-content/uploads/2019/05/Honey-Mustard-Sauce-Spoon-Drip-V1.jpg"),
('Vinegar', 0.50, 0, 0, 0, 0, "https://media.istockphoto.com/id/1368376384/vector/white-vinegar-in-glass-bottle-isolated-on-white-background-vector-cartoon-flat-illustration.jpg?s=170667a&w=0&k=20&c=eyHAW3EkGYf4rBcDmZJSIFLyDUnv0ZV6xiId_JtiF2Y="),
('Olive oil', 2.00, 880, 90, 0, 0, "https://cdn.shopify.com/s/files/1/2395/7673/files/Almond-Oil-2-_1_480x480.jpg?v=1661251331"),
('Peanut oil', 2.00, 880, 90, 0, 0, "https://origin.club/media/catalog/product/cache/86eaafd287624d270d87c663dd3976d5/g/r/groundnut_oil_2__3.jpg"),
('Fish sauce', 1.50, 80, 0, 4, 6, "https://static.vinwonders.com/production/phu-quoc-fish-sauce-top.jpg"),
('Hoisin sauce', 1.50, 100, 10, 2, 18, ""),
('Gochujang', 2.00, 150, 10, 3, 18, ""),
('Tuna', 3.99, 110, 1.0, 24.0, 0.0, ""),
('Salmon', 10.99, 180, 10.0, 20.0, 0.0, ""),
('Chicken', 3.99, 165, 3.6, 31.0, 0.0, ""),
('Beef', 4.99, 227, 20.0, 22.0, 0.0, ""),
('Pork', 5.99, 140, 5.5, 26.0, 0.0, ""),
('Lamb', 7.99, 230, 15.0, 25.0, 0.0, ""),
('Duck', 14.99, 190, 12.0, 15.0, 0.0, ""),
('Crab', 10.99, 85, 0.5, 17.0, 0.0, ""),
('Shrimp', 12.99, 90, 0.5, 18.0, 0.0, ""),
('Squid', 10.99, 100, 1.0, 18.0, 0.0, ""),
('Mussels', 4.99, 70, 1.0, 11.0, 0.0, ""),
('Octopus', 12.99, 80, 0.5, 1.0, 0.0, ""),
('Milk', 0.75, 60, 4, 3.2, 5, ""),
('Yogurt', 1, 100, 5, 5, 5, ""),
('Cheese', 3, 110, 10, 8, 1, ""),
('Butter', 2, 71, 80, 0, 0, ""),
('Cream cheese', 2, 330, 22, 25, 2, ""),
('Ice cream', 3, 200, 15, 5, 25, ""),
('Whipped cream', 4, 240, 20, 3, 6, ""),
('Egg', 0.5, 77, 5.3, 6.2, 0.6, ""),
('Black beans', 0.7, 240, 2.4, 8.9, 40.4, ""),
('Rice', 0.5, 130, 0.7, 2.8, 28.7, ""),
('Tofu', 1.0, 76, 4.8, 8.0, 1.9, ""),
('Miso', 1.0, 40, 1.0, 6.0, 9.0, ""),
('Potato', 0.50, 77, 0.1, 1.9, 19.1, ""),
('Carrot', 0.60, 41, 0.2, 0.9, 9.6, ""),
('Onion', 0.30, 40, 0.1, 1.1, 9.3, ""),
('Tomato', 0.70, 18, 0.2, 0.9, 3.9, ""),
('Garlic', 1.00, 14, 0.6, 0.5, 10.2, ""),
('Spring onion', 0.40, 33, 0.1, 0.9, 5.8, ""),
('Seaweed', 2.00, 5, 0.1, 0.5, 9.3, ""),
('Almonds', 2.99, 576, 49, 21, 22, ""),
('Peanut', 1.99, 567, 49.2, 25.8, 16.1, ""),
('Spinach', 0.89, 23, 0.4, 3.6, 4.3, ""),
('Asparagus', 1.99, 20, 0.4, 2.2, 4.2, ""),
('Broccoli', 1.49, 34, 0.4, 2.8, 6.6, ""),
('Cauliflower', 0.99, 25, 0.4, 2.4, 5.0, ""),
('Cucumber', 0.49, 16, 0.1, 0.7, 3.6, ""),
('Mushrooms', 1.99, 22, 1.5, 2.2, 3.2, ""),
('Bell Pepper', 0.99, 29, 0.4, 1.2, 5.9, ""),
('Bacon', 4.99, 500, 30.0, 25.0, 0.0, ""),
('Cabbage', 0.99, 25, 0.2, 2.0, 4.8, ""),
('Coconut Milk', 1.99, 230, 21.0, 2.4, 5.0, ""),
('Corn', 0.39, 96, 1.9, 2.9, 21.4, ""),
('Pomegranate', 2.99, 83, 0.4, 1.5, 15.3, ""),
('Zucchini', 0.99, 25, 0.4, 1.2, 4.8, ""),
('Basil', 1.99, 25, 0.4, 2.2, 2.8, ""),
('Cilantro', 1.99, 23, 0.3, 1.7, 2.0, ""),
('Pumpkin', 0.99, 26, 0.1, 1.5, 5.1, ""),
('Celery', 0.99, 16, 0.3, 1.0, 2.9, ""),
('Cheddar Cheese', 5.99, 408, 30.0, 25.0, 0.0, ""),
('Lettuce', 0.99, 15, 0.1, 0.9, 2.4, ""),
('Kimchi', 1.99, 23, 0.8, 2.5, 4.9, ""),
('Bamboo shoot', 1.29, 20, 0.2, 1.2, 4.6, ""),
('Sausage', 2.99, 280, 22, 12, 1.4, ""),
('Clam', 3.99, 80, 1.6, 10.2, 2.2, ""),
('Water', 0.99, 0, 0, 0, 0, ""),
('Coca-Cola', 1.99, 140, 0, 0, 39, ""),
('Wine', 12.99, 120, 0, 0, 4.7, ""),
('Rice noodles', 0.79, 130, 0.4, 2.2, 28.1, ""),
('Bread', 1.00, 265, 2.5, 4.8, 53.0, ""),
('Vanilla extract', 3.00, 23, 0.1, 0.0, 0.0, ""),
('Peas', 0.75, 80, 0.4, 5.3, 15.3, ""),
('Dill', 0.25, 43, 0.9, 2.5, 7.1, ""),
('Lemon Juice', 0.30, 22, 0.1, 0.4, 7.3, ""),
('Smoked Salmon', 3.50, 195, 13.5, 20.5, 0.5, ""),
('Peanut Butter', 2.50, 190, 16, 7, 6, ""),
('Red Onion', 0.50, 40, 0.1, 1.1, 9, ""),
('Caper', 1.00, 23, 0.9, 0.9, 3.4, ""),
('Mint', 0.30, 70, 0.6, 3.3, 14.9, ""),
('Balsamic Glaze', 3.00, 80, 0.1, 0.5, 18, ""),
('Parmesan Cheese', 2.00, 431, 29, 38, 4, ""),
('Green Beans', 1.20, 31, 0.1, 1.8, 6.9, ""),
('Lemon Zest', 0.50, 29, 0.3, 1.1, 8.6, ""),
('Brussels Sprouts', 1.50, 43, 0.3, 3.4, 8.9, ""),
('Cherry Tomato', 0.30, 18, 0.2, 0.9, 3.9, ""),
('Mozzarella Cheese', 2.50, 300, 22, 21, 2.2, ""),
('Feta Cheese', 2.00, 264, 21.3, 14.2, 4.1, ""),
('Bread Crumbs', 1.00, 405, 5.2, 13.3, 74.9, ""),
('Nutmeg', 0.75, 525, 36.3, 5.8, 49.3, ""),
('Tortilla Chips', 2.50, 152, 7.4, 2, 18.9, ""),
('Baguette', 2.00, 270, 1, 7, 56, ""),
('Dijon Mustard', 1.00, 66, 3.6, 3.6, 5.7, ""),
('Old Bay Seasoning', 1.50, 0, 0, 0, 0, ""),
('Heavy Cream', 3.00, 340, 36, 2.2, 3.4, ""),
('Egg Noodles', 2.50, 211, 1.8, 8.1, 42.9, ""),
('Chicken Broth', 1.50, 15, 0.2, 1.5, 1, ""),
('Vegetable Broth', 1.50, 15, 0.1, 0.5, 2.9, ""),
('Butternut Squash', 2.00, 45, 0.1, 1, 12, ""),
('Cannellini Beans', 1.50, 333, 1.5, 21, 63, ""),
('Pasta', 1.00, 200, 1, 7, 42, ""),
('Curry Powder', 1.50, 325, 14.3, 13.9, 53.7, ""),
('Leek', 1.00, 61, 0.2, 1.5, 14.2, ""),
('Sticky Rice', 2.00, 150, 0.2, 2.7, 34, ""),
('Ginseng', 5.00, 4, 0, 0.2, 1, ""),
('Jujube', 1.00, 79, 0.2, 1.2, 20.2, ""),
('Ginger', 0.50, 80, 0.9, 1.8, 17.8, ""),
('Udon Noodles', 2.50, 143, 0.2, 3, 31.9, ""),
('Fish Cake', 1.50, 77, 1.2, 6, 10, ""),
('Dashi Broth', 1.00, 5, 0.1, 0.3, 1, ""),
('Pulled Pork', 3.00, 250, 16, 25, 0, ""),
('Arborio Rice', 2.00, 160, 0.5, 3, 36, ""),
('Wood Ear Mushroom', 2.00, 7, 0.1, 1.5, 0.1, ""),
('Glass Noodles', 1.50, 160, 0.2, 2, 40, ""),
('Water Spinach', 1.00, 19, 0.2, 2.6, 2.7, ""),
('Roasted Seaweed', 2.00, 10, 0.5, 1, 1, ""),
('Bean Paste', 1.50, 100, 3, 5, 12, ""),
('Jjajangmyeon Noodles', 2.50, 200, 1, 4, 42, ""),
('Flour', 1.00, 364, 1.2, 10.3, 76.3, ""),
('Cornstarch', 1.00, 381, 0.1, 0.3, 95.2, ""),
('Vegetable Oil', 2.00, 240, 28, 0, 0, ""),
('Cooking Instructions', 0, 0, 0, 0, 0, "");

-- ChatGPT example: Generate ingredients include bread, vanilla extract, peas which must include name, price, calories, fat, protein, carb. And based on 100 gram of each

-- ctime: cook time, ptime: prepare time
-- meal: 1=breakfast, 2=lunch, 3=snack, 4=dinner,drinks
INSERT INTO `food` (food_name, food_desc, food_ctime, food_ptime, food_type, food_img)
VALUES
("Avocado Toast", "Mashed avocado on toasted bread.", 5, 10, 1, "https://vancouverwithlove.com/wp-content/uploads/2023/05/high-protein-avocado-toast-featured-500x500.jpg"),
("Omelette with Vegetables", "Fluffy omelette filled with assorted vegetables.", 10, 5, 1, "https://yumeating.com/wp-content/uploads/2020/08/Easy-pizza-omelette-with-vegetables-recipe.jpg"),
("French Toast", "Bread soaked in egg batter and pan-fried.", 10, 5, 1, "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/french-toast_1-5bbce73.jpg"),
("Tomato Cucumber Salad", "Refreshing salad with tomatoes and cucumbers.", 0, 10, 2, "https://food.fnr.sndimg.com/content/dam/images/food/fullset/2020/02/07/QK207_tomato-and-cucumber-salad_s4x3.jpg.rend.hgtvcom.616.462.suffix/1599178185129.jpeg"),
("Creamy Coleslaw", "Cabbage and carrot coleslaw in a creamy dressing.", 0, 10, 2, "https://food.fnr.sndimg.com/content/dam/images/food/fullset/2019/9/3/0/FNK_the-best-creamy-cole-slaw_H_s4x3.jpg.rend.hgtvcom.616.462.suffix/1567536810752.jpeg"),
("Russian Salad", "Diced vegetables in a creamy dressing.", 10, 10, 2, "https://healthytasteoflife.com/wp-content/uploads/2020/02/russian-potato-salad-olivier-image-500x500.jpg"),
("Chicken Salad", "Mixed salad with grilled chicken strips.", 15, 10, 2, "https://www.skinnytaste.com/wp-content/uploads/2022/06/Grilled-Chicken-Salad-with-Strawberries-5.jpg"),
-- Breakfast
("Scrambled Eggs with Smoked Salmon", "Fluffy scrambled eggs served with smoked salmon, fresh dill, and a squeeze of lemon juice.", 10, 5, 1, "https://food.fnr.sndimg.com/content/dam/images/food/fullset/2008/10/2/1/scrambled-eggs-salmon_s4x3.jpg.rend.hgtvcom.616.462.suffix/1382988614898.jpeg"),
("Breakfast Hash", "A hearty breakfast dish made with diced potatoes, onions, bell peppers, sausage, and a sunny-side-up egg on top.", 15, 10, 1, "https://www.budgetbytes.com/wp-content/uploads/2017/04/Chorizo-Breakfast-Hash-V2.jpg"),
("Shakshuka", "A Middle Eastern favorite featuring a savory tomato and bell pepper stew with poached eggs, garlic, and a touch of cumin powder.", 20, 15, 1, "https://ottolenghi.co.uk/pub/media/contentmanager/content/cache/646x458//Shakshuka%202.jpg"),
("Bread with Cream Cheese and Smoked Salmon", "Toasted bread topped with creamy cream cheese, smoked salmon, thinly sliced red onion, and zesty capers.", 5, 5, 1, "https://food.fnr.sndimg.com/content/dam/images/food/plus/fullset/2020/03/10/0/FNP_Jeppe-Kil-Anderson_Open-Rye-Sandwich-with-Smoked-Salmon-Herb-Cream-Cheese-and-Radishes_s4x3.jpg.rend.hgtvcom.616.462.suffix/1583847749599.jpeg"),
("Peanut Butter Banana Toast", "A simple yet satisfying breakfast with sliced banana and a drizzle of honey over peanut butter spread on toasted bread.", 5, 5, 1, "https://kidneystonediet.com/wp-content/uploads/sites/5/2023/04/58-Banana-French-Toast.jpeg"),
("Breakfast BLT", "A breakfast twist on the classic BLT sandwich, featuring crispy bacon, fresh lettuce, tomato slices, a fried egg, and a spread of mayonnaise on bread.", 10, 5, 1, "https://www.foodandwine.com/thmb/SUnWI6kD-EwiG8khBaFN7wTykuk=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/HD-fw200707_blt-8e0c465c7c8a4fadb4e62ba1cfc7e4f3.jpg"),
-- Salads
("Mexican Corn Salad", "A refreshing salad made with grilled corn, black beans, bell pepper, chili, cilantro, and a zesty lime dressing.", 0, 10, 3, "https://cdn.loveandlemons.com/wp-content/uploads/2013/06/corn-salad.jpg"),
("Grapefruit Salad (Goi Buoi)", "A vibrant Vietnamese salad featuring grapefruit, shrimp, chicken, peanuts, mint leaves, basil, and a flavorful dressing made with lime, fish sauce, and sugar.", 0, 15, 3, "https://cdn.tgdd.vn/Files/2021/10/22/1392687/hoc-ngay-cach-lam-goi-buoi-dui-ga-thom-ngon-hap-dan-tai-nha-202201111236511793.jpeg"),
("Mango Salad (Nom Xoai)", "A delightful Southeast Asian salad combining ripe mango, shrimp, red onion, peanuts, mint leaves, and a zingy dressing made with fish sauce and lime.", 0, 15, 3, "https://img-global.cpcdn.com/recipes/c9a1f36989f3f7f2/680x482cq70/g%E1%BB%8Fi-xoai-tom-kho-recipe-main-photo.jpg"),
("Caprese Salad", "A classic Italian salad showcasing slices of ripe tomato, fresh mozzarella cheese, fragrant basil leaves, and a drizzle of balsamic glaze.", 0, 5, 3, "https://images.immediate.co.uk/production/volatile/sites/30/2022/05/Caprese-salad-8f218a3.png"),
-- Side dishes
("Roasted Garlic Parmesan Potatoes", "Roasted potatoes seasoned with garlic powder, Parmesan cheese, and drizzled with olive oil.", 30, 15, 2, "https://breadboozebacon.com/wp-content/uploads/2020/05/Garlic-Parmesan-Roasted-Red-Potatoes-SQUARE-1.jpg"),
("Grilled Asparagus with Lemon Butter", "Grilled asparagus spears topped with a drizzle of lemon butter and a sprinkle of black pepper.", 15, 10, 2, "https://diethood.com/wp-content/uploads/2016/03/Asparagus-Lemon-Butter-Sauce-Recipe.jpg"),
("Sesame Ginger Green Beans", "Tender green beans tossed with sesame seeds, ginger powder, and a splash of soy sauce.", 20, 10, 2, "https://www.flavourandsavour.com/wp-content/uploads/2015/07/Sesame-Ginger-Green-Beans-sq-500x500.jpg"),
("Garlic Butter Green Beans", "Crisp green beans sautéed in garlic-infused butter, finished with a touch of lemon zest.", 20, 10, 2, "https://www.recipetineats.com/wp-content/uploads/2021/10/Garlic-Sauteed-Green-Beans_08-SQ.jpg?w=500&h=500&crop=1"),
("Roasted Brussels Sprouts with Balsamic Glaze", "Oven-roasted Brussels sprouts drizzled with balsamic glaze and a hint of olive oil.", 25, 10, 2, "https://reciperunner.com/wp-content/uploads/2014/11/Balsamic-Maple-Roasted-Brussels-Sprouts-Bacon-Photo-720x720.jpg"),
("Caprese Skewers", "Skewers of cherry tomatoes, fresh mozzarella cheese, and basil leaves drizzled with balsamic glaze.", 15, 10, 2, "https://bellyfull.net/wp-content/uploads/2020/06/Caprese-Skewers-blog-2.jpg"),
("Sweet and Spicy Roasted Cauliflower", "Cauliflower florets roasted with a blend of chili powder, sugar, and olive oil.", 25, 15, 2, "https://www.howsweeteats.com/wp-content/uploads/2018/04/sweet-spicy-cauliflower-I-howsweeteats.com-4.jpg"),
("Spinach and Feta Stuffed Mushrooms", "Mushroom caps stuffed with a mixture of spinach, feta cheese, garlic, and olive oil.", 20, 15, 2, "https://www.recipegirl.com/wp-content/uploads/2007/02/Feta-Stuffed-Mushrooms.jpg"),
("Cheesy Broccoli Casserole", "Broccoli florets baked with cheddar cheese, a sprinkle of garlic powder, and topped with bread crumbs.", 30, 15, 2, "https://diethood.com/wp-content/uploads/2019/07/Cheesy-Broccoli-Casserole-7.jpg"),
("Baked Parmesan Zucchini Fries", "Zucchini sticks coated in Parmesan cheese, bread crumbs, and garlic powder, then baked until crispy.", 25, 15, 2, "https://rasamalaysia.com/wp-content/uploads/2018/04/parmesan-zucchini-fries-thumb.jpg"),
("Creamed Spinach", "Sautéed spinach in a creamy sauce made with cream cheese, garlic, onion, and a touch of nutmeg.", 20, 15, 2, "https://assets.kraftfoods.com/recipe_images/opendeploy/52837_640x428.jpg"),
("Fruit Salsa with Cinnamon Chips", "Fresh fruit salsa made with apple, apricot, kiwi, and lemon, served with crispy cinnamon-dusted tortilla chips.", 15, 15, 2, "https://www.easttexasfoodbank.org/wp-content/uploads/2021/04/exps34611_BOS13149327C02_08_3b_WEB-4.jpg"),
("Avocado Bruschetta", "Toasted baguette slices topped with a mixture of diced avocado, tomato, garlic, and basil.", 15, 10, 2, "https://www.litehousefoods.com/wp-content/uploads/2021/09/RECIPE-MAIN-AvocadoBruschettaToast1_InvitationtoDineandLitehouse-May2020-aspect-ratio-782-521.jpg"),
("Crab Cakes", "Savory crab cakes made with crab meat, bread crumbs, mayonnaise, Dijon mustard, lemon, and Old Bay seasoning.", 25, 20, 2, "https://food.fnr.sndimg.com/content/dam/images/food/fullset/2020/11/16/0/FNK_Air-Fryer-Crab-Cakes_H2_s4x3.jpg.rend.hgtvcom.616.462.suffix/1605561144978.jpeg"),
("Thai Beef Lettuce Wraps", "Lettuce wraps filled with seasoned beef, sautéed onions, garlic, ginger, and a drizzle of soy sauce.", 25, 15, 2, "https://thehealthycookingblog.com/public/uploads/2017/07/Thai-Bee-Lettuce-Wraps.jpg"),
("Smoked Salmon Cucumber Bites", "Cucumber slices topped with cream cheese, smoked salmon, and a sprig of dill.", 20, 10, 2, "https://dearmica.com/wp-content/uploads/2022/03/keto-smoked-salmon-cucumber-bites-covers.jpeg"),
("Tomato Basil Bruschetta", "Classic bruscheta with diced tomatoes, fresh basil, minced garlic, and a drizzle of olive oil on toasted baguette slices.", 15, 10, 2, "https://food.unl.edu/recipes/tomato-and-basil-bruschetta.jpg"),
-- Soup
("Creamy Tomato Basil Soup", "A rich and velvety tomato soup infused with the flavors of onion, garlic, basil, cream, and olive oil.", 30, 20, 3, "https://garlicsaltandlime.com/wp-content/uploads/2022/09/Creamy-tomato-basil-soup-1-500x500.jpg"),
("Chicken Noodle Soup", "A comforting soup made with tender chicken, carrots, celery, onion, egg noodles, and nourishing chicken broth.", 40, 25, 3, "https://diethood.com/wp-content/uploads/2021/01/chicken-noodle-soup-9.jpg"),
("Spicy Black Bean Soup", "A hearty soup featuring black beans, onion, garlic, chili powder, cumin, and vegetable broth for a touch of heat.", 35, 20, 3, "https://www.triedandtruerecipe.com/wp-content/uploads/2020/11/Spicy-Black-Bean-Soup_MidPage-%E2%80%93-1-1.jpg"),
("Butternut Squash Soup", "Smooth butternut squash soup with notes of onion, garlic, nutmeg, vegetable broth, and a creamy finish.", 40, 25, 3, "https://zardyplants.com/wp-content/uploads/2019/10/Vegan-Butternut-Squash-Soup-04.jpg"),
("Minestrone Soup", "A classic Italian soup filled with tomato, carrot, celery, onion, cannellini beans, pasta, and hearty vegetable broth.", 45, 30, 3, "https://cdn.loveandlemons.com/wp-content/uploads/2021/11/minestrone-soup-500x500.jpg"),
("Coconut Curry Chicken Soup", "A flavorful soup combining tender chicken, creamy coconut milk, curry powder, ginger, garlic, and an assortment of vegetables.", 40, 25, 3, "https://www.cookingclassy.com/wp-content/uploads/2016/08/thai_coconut_curry_chicken_soup9..jpg"),
("Potato Leek Soup", "A creamy soup made with potato, leek, onion, garlic, vegetable broth, and a touch of cream for a luxurious texture.", 35, 20, 3, "https://carlsbadcravings.com/wp-content/uploads/2020/02/Potato-Leek-Soup-v20.jpg"),
("Pumpkin Soup", "A fall favorite featuring pumpkin, onion, garlic, nutmeg, cinnamon, vegetable broth, and a touch of cream.", 40, 25, 3, "https://www.wcrf-uk.org/wp-content/uploads/2021/10/Pumpkin-soup-09-SQ.jpg"),
("Creamy Spinach and Parmesan Soup", "A velvety spinach soup with sautéed onion, garlic, Parmesan cheese, vegetable broth, and a luxurious creaminess.", 35, 20, 3, "https://www.closetcooking.com/wp-content/uploads/2015/02/CreamyParmesanMushroomandSpinachTortelliniSoup8006167.jpg"),
("Creamy Broccoli Cheddar Soup", "A comforting soup combining broccoli florets, cheddar cheese, onion, garlic, vegetable broth, and a creamy finish.", 40, 25, 3, "https://www.thechunkychef.com/wp-content/uploads/2017/02/Creamy-Broccoli-Cheddar-Soup-bowl-768x926.jpg"),
-- Main courses
("Ginseng Chicken Soup", "A nourishing chicken soup with tender chicken, sticky rice, ginseng, garlic, jujubes, and a hint of ginger, served with ginseng tea.", 45, 30, 4, "https://jeanetteshealthyliving.com/wp-content/uploads/2017/01/Korean-Chicken-Ginseng-Soup-7.jpg"),
("Creamy Mushroom Risotto", "Creamy risotto made with Arborio rice, mushrooms, and Parmesan cheese.", 25, 20, 4, "https://www.threeolivesbranch.com/wp-content/uploads/2020/12/creamy-mushroom-risotto-threeolivesbranch-6-500x375.jpg"),
("Black Pepper Beef Stir-Fry", "Beef stir-fry with black pepper, onions, garlic, and savory sauces.", 20, 15, 4, "https://www.seriouseats.com/thmb/q2KRYqxpJx_H5BqRWuB9uD_wKfY=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/__opt__aboutcom__coeus__resources__content_migration__serious_eats__seriouseats.com__recipes__images__2012__06__20120610-stir-fry-grill-wok-21-2fe9b5e714af455d8c786a68ccff8a45.jpg"),
("Glass Noodle with Chicken - Mien Ga", "Delicious dish with chicken, wood ear mushroom, ginger, and glass noodles.", 15, 10, 4, "https://www.wokandkin.com/wp-content/uploads/2020/12/Mien-Ga-saved-for-web.png"),
("Stir-Fried Water Spinach - Rau muong xao toi", "Stir-fried water spinach with garlic and peanut oil.", 10, 5, 4, "https://media-cdn.tripadvisor.com/media/photo-s/0f/91/0a/35/stir-fried-water-spinach.jpg"),
("Avocado Kimbap", "Korean dish with avocado, rice, seaweed, carrot, cucumber, egg, and sesame seeds.", 20, 15, 4, "https://www.fifteenspatulas.com/wp-content/uploads/2011/12/kimbop2bright1.jpg"),
("Jajangmyeon", "Classic Korean dish with noodles, pork, black bean paste, onion, zucchini, and potatoes.", 30, 15, 4, "https://daynauan.info.vn/wp-content/uploads/2016/01/mi-tuong-den.jpg"),
("Korean Fried Chicken", "Crispy Korean fried chicken made with chicken, flour, cornstarch, soy sauce, garlic, ginger, honey, Korean chili paste, and vegetable oil.", 30, 20, 4, "https://hips.hearstapps.com/hmg-prod/images/190130-korean-fried-chicken-horizontal-041-1549304734.jpg"),
("Bacon Wrapped Asparagus", "Asparagus wrapped in bacon, seasoned and roasted.", 20, 15, 4, "https://lifeloveandgoodfood.com/wp-content/uploads/2016/04/bacon-wrapped-asparagus_04_1200x1200-500x375.jpg");


INSERT INTO `recipe` (food_id, ingre_id, media_url, recipe_qty, recipe_desc, recipe_title)
VALUES
(1,3, NULL, 100, "Cut the ripe avocado in half, remove the pit, and scoop out the flesh into a bowl. Use a fork to mash the avocado until it reaches your desired consistency.", "1 ripe avocado"),
(1,113, NULL, 50, "Toast the bread.", "2 slices of bread"),
(1,93, NULL, 50, "Cook the bacon until crispy.", "2 slices of bacon"),
(1,29, "https://www.shutterstock.com/shutterstock/videos/1055810363/preview/stock-footage-close-up-selective-focus-shot-of-hand-of-male-cook-adding-salt-to-boiling-water-in-metal-pot-while.webm", 3, "Add salt.", "1/2 teaspoon salt"),
(1,25, NULL, 1.5, "Add black pepper.", "1/4 teaspoon black pepper"),
(1,9, NULL, 15, "Add lemon juice.", "1 tablespoon lemon juice"),

(2,72, "https://www.shutterstock.com/shutterstock/videos/1031672702/preview/stock-footage-female-hands-holding-a-cracked-egg-breaking-an-egg-black-background-slow-motion.webm", 100, "Prepare eggs.", "2 eggs"),
(2,29, "https://www.shutterstock.com/shutterstock/videos/1055810363/preview/stock-footage-close-up-selective-focus-shot-of-hand-of-male-cook-adding-salt-to-boiling-water-in-metal-pot-while.webm", 3, "Add salt.", "1/2 teaspoon salt"),
(2,25, NULL, 1.5, "Add black pepper.", "1/4 teaspoon black pepper"),
(2,65, NULL, 30, "Add milk.", "1/4 cup milk"),
(2,48, NULL, 15, "Prepare olive oil.", "1 tablespoon olive oil"),
(2,79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 75, "Prepare onion.", "1/2 onion, chopped"),
(2,92, NULL, 50, "Prepare bell pepper.", "1/2 green/red/yellow bell pepper, chopped"),
(2,91, NULL, 50, "Prepare mushrooms.", "1/2 cup mushrooms, sliced"),
(2,80, "https://www.shutterstock.com/shutterstock/videos/1106911463/preview/stock-footage-chop-tomato-close-up-diced-tomatoes-on-a-cutting-board-for-italian-sauce-chef-cut-tomatoes-with.webm", 75, "Prepare tomatoes.", "1/2 cup tomatoes, diced"),
(2,67, NULL, 20, "Add cheese.", "1/4 cup grated cheese"),

(3,113, NULL, 100, "Prepare bread.", "3 thick slices bread"),
(3,65, NULL, 50, "Add milk.", "1/2 cup milk"),
(3,72, "https://www.shutterstock.com/shutterstock/videos/1031672702/preview/stock-footage-female-hands-holding-a-cracked-egg-breaking-an-egg-black-background-slow-motion.webm", 100, "Prepare eggs.", "2 large eggs"),
(3,114, NULL, 6, "Add vanilla extract.", "1 teaspoon vanilla extract"),
(3,31, NULL, 3, "Add cinnamon powder.", "1/2 teaspoon cinnamon powder"),
(3,29, "https://www.shutterstock.com/shutterstock/videos/1055810363/preview/stock-footage-close-up-selective-focus-shot-of-hand-of-male-cook-adding-salt-to-boiling-water-in-metal-pot-while.webm", 1.5, "Add salt.", "1/4 teaspoon salt"),
(3,68, NULL, 15, "Prepare butter.", "1 tablespoon butter"),
(3,39, NULL, 30, "Add maple syrup.", "2 tablespoon maple syrup"),

(4,80, "https://www.shutterstock.com/shutterstock/videos/1106911463/preview/stock-footage-chop-tomato-close-up-diced-tomatoes-on-a-cutting-board-for-italian-sauce-chef-cut-tomatoes-with.webm", 100, "Prepare tomato.", "tomato, diced"),
(4,90, NULL, 50, "Prepare cucumber.", "cucumber, diced"),
(4,79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 50, "Prepare onion.", "onion, diced"),
(4,47, NULL, 15, "Add vinegar.", "1 tablespoon vinegar"),
(4,48, NULL, 15, "Prepare olive oil.", "1 tablespoon olive oil"),
(4,29, "https://www.shutterstock.com/shutterstock/videos/1055810363/preview/stock-footage-close-up-selective-focus-shot-of-hand-of-male-cook-adding-salt-to-boiling-water-in-metal-pot-while.webm", 3, "Add salt.", "1/2 teaspoon salt"),
(4,25, NULL, 1.5, "Add black pepper.", "1/4 teaspoon black pepper"),

(5,94, NULL, 100, "Prepare cabbage.", "cabbage, shredded"),
(5,78, NULL, 50, "Prepare carrot.", "carrot, shredded"),
(5,44, NULL, 30, "Add mayonnaise.", "2 tablespoons mayonnaise"),
(5,47, NULL, 15, "Add vinegar.", "1 tablespoon vinegar"),
(5,37, "https://www.shutterstock.com/shutterstock/videos/1058013685/preview/stock-footage-a-full-spoon-of-sugar-sugar-falling-from-spoon-in-slow-motion.webm", 15, "Add sugar.", "1 tablespoon sugar"),
(5,29, "https://www.shutterstock.com/shutterstock/videos/1055810363/preview/stock-footage-close-up-selective-focus-shot-of-hand-of-male-cook-adding-salt-to-boiling-water-in-metal-pot-while.webm", 3, "Add salt.", "1/2 teaspoon salt"),
(5,25, "https://www.shutterstock.com/shutterstock/videos/1069616947/preview/stock-footage-close-up-view-of-pepper-grinding-peppers-directly-into-black-cast-iron-pan-concept-of-restaurant.webm", 1.5, "Add black pepper.", "1/4 teaspoon black pepper"),

(6,77, NULL, 100, "Prepare potatoes.", "potatoes, peeled and diced"),
(6,78, NULL, 50, "Prepare carrots.", "carrots, peeled and diced"),
(6,115, NULL, 50, "Prepare peas.", "peas, cooked"),
(6,93, NULL, 50, "Prepare bacon.", "bacon, diced"),
(6,44, NULL, 15, "Add mayonnaise.", "1 tablespoons mayonnaise"),
(6,29, "https://www.shutterstock.com/shutterstock/videos/1055810363/preview/stock-footage-close-up-selective-focus-shot-of-hand-of-male-cook-adding-salt-to-boiling-water-in-metal-pot-while.webm", 3, "Add salt.", "1/2 teaspoon salt"),
(6,25, "https://www.shutterstock.com/shutterstock/videos/1069616947/preview/stock-footage-close-up-view-of-pepper-grinding-peppers-directly-into-black-cast-iron-pan-concept-of-restaurant.webm", 1.5, "Add black pepper.", "1/4 teaspoon black pepper"),

(7,55, "https://www.shutterstock.com/shutterstock/videos/1062774610/preview/stock-footage-close-up-of-cook-chef-hands-woman-cuts-raw-meat-chicken-breast-on-a-wooden-board.webm", 100, "Prepare chicken.", "cooked chicken, shredded"),
(7,94, NULL, 100, "Prepare cabbage.", "cabbage, shredded"),
(7,78, NULL, 50, "Prepare carrot.", "carrot, shredded"),
(7,10, NULL, 15, "Add lime juice.", "1 tablespoon lime juice"),
(7,50, NULL, 15, "Add fish sauce.", "1 tablespoon fish sauce"),
(7,37, "https://www.shutterstock.com/shutterstock/videos/1058013685/preview/stock-footage-a-full-spoon-of-sugar-sugar-falling-from-spoon-in-slow-motion.webm", 7.5, "Add sugar.", "1/2 tablespoon sugar"),
(7,26, NULL, 1.5, "Add chili powder.", "1/4 teaspoon chili powder"),

-- BREAKFAST
(8, 72, "https://www.shutterstock.com/shutterstock/videos/1031672702/preview/stock-footage-female-hands-holding-a-cracked-egg-breaking-an-egg-black-background-slow-motion.webm", 120, "Crack the eggs into a bowl and beat them until well mixed.", "2 large eggs"),
(8, 118, NULL, 50, "Slice the smoked salmon into thin strips.", "50g smoked salmon"),
(8, 116, NULL, 5, "Chop the dill finely.", "1 tablespoon chopped dill"),
(8, 117, NULL, 5, "Squeeze some fresh lemon juice.", "1 teaspoon lemon juice"),
(8, 164, NULL, NULL, "Heat a non-stick pan over medium heat. Pour in the beaten eggs and gently scramble them with a spatula until they're softly set.\n\nAdd the sliced smoked salmon and chopped dill to the eggs. Continue to cook for another minute, stirring gently.\n\nDrizzle the lemon juice over the eggs and salmon. Stir briefly to combine.\n\nRemove from heat and serve the scrambled eggs with smoked salmon hot.", "Cooking Instructions"),

(9, 77, NULL, 100, "Peel and dice the potatoes into small cubes.", "100g potatoes"),
(9, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 150, "Dice the onion.", "1 medium onion"),
(9, 92, NULL, 50, "Dice the bell pepper.", "1 medium bell pepper"),
(9, 107, NULL, 50, "Remove the casing from the sausage and crumble it.", "50g sausage"),
(9, 72, "https://www.shutterstock.com/shutterstock/videos/1031672702/preview/stock-footage-female-hands-holding-a-cracked-egg-breaking-an-egg-black-background-slow-motion.webm", 100, "Crack the eggs into a bowl and beat them.", "2 large eggs"),
(9, 164, NULL, NULL, "Heat a skillet over medium heat. Add a bit of oil.\n\nAdd the diced potatoes and cook until they're golden and crispy.", "Cooking Instructions"),

(10, 80, "https://www.shutterstock.com/shutterstock/videos/1106911463/preview/stock-footage-chop-tomato-close-up-diced-tomatoes-on-a-cutting-board-for-italian-sauce-chef-cut-tomatoes-with.webm", 300, "Dice the tomatoes.", "300g tomatoes"),
(10, 92, NULL, 150, "Dice the bell pepper.", "1 medium bell pepper"),
(10, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 100, "Dice the onion.", "1 medium onion"),
(10, 81, NULL, 10, "Mince the garlic.", "2 cloves garlic"),
(10, 72, "https://www.shutterstock.com/shutterstock/videos/1031672702/preview/stock-footage-female-hands-holding-a-cracked-egg-breaking-an-egg-black-background-slow-motion.webm", 100, "Crack the eggs into a bowl.", "2 large eggs"),
(10, 32, NULL, 5, "Add cumin powder.", "1 teaspoon cumin powder"),
(10, 164, NULL, NULL, "Heat a skillet and sauté the onion and bell pepper until softened. Add the garlic and cook briefly.\n\nAdd the diced tomatoes and cumin powder. Simmer until the sauce thickens.\n\nMake wells in the sauce and crack the eggs into them. Cover and cook until the eggs are set.\n\nServe hot.", "Cooking Instructions"),

(11, 113, NULL, 50, "Prepare the bread slices.", "2 slices bread"),
(11, 69, NULL, 20, "Spread cream cheese on the bread slices.", "20g cream cheese"),
(11, 118, NULL, 30, "Place smoked salmon slices on top of the cream cheese.", "30g smoked salmon"),
(11, 120, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 10, "Thinly slice the red onion.", "1 small red onion"),
(11, 121, NULL, 5, "Sprinkle capers over the top.", "5 capers"),
(11, 164, NULL, NULL, "Arrange smoked salmon, red onion, and capers over the cream cheese. Serve open-faced.", "Assembling Instructions"),

(12, 113, NULL, 50, "Prepare the bread slices.", "2 slices bread"),
(12, 119, NULL, 20, "Spread peanut butter on the bread slices.", "20g Peanut Butter"),
(12, 4, NULL, 80, "Slice the banana.", "1 medium banana"),
(12, 38, NULL, 15, "Drizzle honey over the top.", "1 tablespoon honey"),
(12, 164, NULL, NULL, "Arrange banana slices over the peanut butter. Drizzle with honey.", "Assembling Instructions"),

(13, 93, NULL, 60, "Cook the bacon until crispy.", "2 slices bacon"),
(13, 104, NULL, 30, "Tear the lettuce leaves.", "2 lettuce leaves"),
(13, 80, "https://www.shutterstock.com/shutterstock/videos/1054805054/preview/stock-footage-woman-s-hands-using-kitchen-knife-cutting-fresh-tomato-on-wooden-cutting-board-healthy-eating.webm", 100, "Slice the tomatoes.", "1 medium tomato"),
(13, 72, "https://www.shutterstock.com/shutterstock/videos/1031672702/preview/stock-footage-female-hands-holding-a-cracked-egg-breaking-an-egg-black-background-slow-motion.webm", 2, "Fry or scramble the egg.", "1 large egg"),
(13, 113, NULL, 50, "Prepare bread slices.", "2 slices bread"),
(13, 44, NULL, 10, "Spread mayonnaise on one side of each bread slice.", "10g Mayonaise"),
(13, 164, NULL, NULL, "Assemble the sandwich by layering bacon, lettuce, tomato, and egg between the mayo-spread slices of bread.", "Assembling Instructions"),

-- SALADS
(14, 96, NULL, 200, "Prepare corn and cook it.", "2 cups corn"),
(14, 73, NULL, 150, "Prepare black beans.", "1 cup black beans"),
(14, 92, NULL, 100, "Dice the bell pepper.", "1 bell pepper, diced"),
(14, 26, NULL, 2, "Add chili powder.", "1/4 teaspoon chili powder"),
(14, 100, NULL, 15, "Chop cilantro.", "1/4 cup chopped cilantro"),
(14, 10, NULL, 10, "Squeeze lime juice.", "1 lime, juiced"),
(14, 164, NULL, NULL, "Mix corn, black beans, bell pepper, chili powder, chopped cilantro, and lime juice in a bowl. Serve chilled.", "Assembling Instructions"),

(15, 6, NULL, 200, "Prepare grapefruit and segment it.", "2 grapefruits"),
(15, 61, NULL, 150, "Cook shrimp.", "150g shrimp, cooked"),
(15, 55, "https://www.shutterstock.com/shutterstock/videos/1062774610/preview/stock-footage-close-up-of-cook-chef-hands-woman-cuts-raw-meat-chicken-breast-on-a-wooden-board.webm", 150, "Cook chicken.", "150g chicken, cooked and shredded"),
(15, 85, NULL, 30, "Chop peanuts.", "30g chopped peanuts"),
(15, 122, NULL, 10, "Tear mint leaves.", "10 mint leaves"),
(15, 99, NULL, 5, "Tear basil leaves.", "5 basil leaves"),
(15, 10, NULL, 10, "Squeeze lime juice.", "1 lime, juiced"),
(15, 50, NULL, 5, "Add fish sauce.", "1 teaspoon fish sauce"),
(15, 37, "https://www.shutterstock.com/shutterstock/videos/1058013685/preview/stock-footage-a-full-spoon-of-sugar-sugar-falling-from-spoon-in-slow-motion.webm", 5, "Add sugar.", "1 teaspoon sugar"),
(15, 164, NULL, NULL, "Mix grapefruit segments, shrimp, chicken, chopped peanuts, torn mint leaves, torn basil leaves, lime juice, fish sauce, and sugar in a bowl. Serve chilled.", "Assembling Instructions"),

(16, 119, NULL, 200, "Prepare mango and dice it.", "1 ripe mango, diced"),
(16, 11, NULL, 150, "Cook shrimp.", "150g shrimp, cooked"),
(16, 51, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 50, "Thinly slice red onion.", "1 small red onion, thinly sliced"),
(16, 43, NULL, 30, "Chop peanuts.", "30g chopped peanuts"),
(16, 146, NULL, 10, "Tear mint leaves.", "10 mint leaves"),
(16, 50, NULL, 10, "Add fish sauce.", "2 teaspoons fish sauce"),
(16, 10, NULL, 10, "Squeeze lime juice.", "1 lime, juiced"),
(16, 164, NULL, NULL, "Mix diced mango, cooked shrimp, sliced red onion, chopped peanuts, torn mint leaves, fish sauce, and lime juice in a bowl. Serve chilled.", "Assembling Instructions"),

(17, 80, "https://www.shutterstock.com/shutterstock/videos/1054805054/preview/stock-footage-woman-s-hands-using-kitchen-knife-cutting-fresh-tomato-on-wooden-cutting-board-healthy-eating.webm", 200, "Slice tomatoes.", "2 large tomatoes, sliced"),
(17, 67, NULL, 150, "Slice mozzarella cheese.", "150g mozzarella cheese, sliced"),
(17, 99, NULL, 10, "Tear basil leaves.", "10 fresh basil leaves"),
(17, 123, NULL, 20, "Drizzle balsamic glaze.", "2 tablespoons balsamic glaze"),
(17, 164, NULL, NULL, "Alternate slices of tomato and mozzarella cheese on a serving plate. Tuck torn basil leaves in between. Drizzle balsamic glaze over the top.", "Assembling Instructions"),

-- SIDE DISHES
-- Roasted Garlic Parmesan Potatoes
(18, 77, NULL, 200, "Dice the potatoes.", "200g potatoes, diced"),
(18, 27, NULL, 5, "Sprinkle garlic powder.", "1 teaspoon garlic powder"),
(18, 124, NULL, 50, "Sprinkle grated Parmesan cheese.", "1/2 cup grated Parmesan cheese"),
(18, 48, NULL, 30, "Coat with olive oil.", "2 tablespoons olive oil"),
(18, 164, NULL, NULL, "Preheat oven. Toss diced potatoes with garlic powder, Parmesan cheese, and olive oil. Spread on a baking sheet. Roast in the oven until golden and crispy.", "Cooking Instructions"),

-- Grilled Asparagus with Lemon Butter
(19, 87, NULL, 200, "Trim asparagus.", "200g asparagus"),
(19, 10, NULL, 10, "Squeeze lemon juice.", "1 lemon, juiced"),
(19, 68, NULL, 20, "Melt butter.", "2 tablespoons butter"),
(19, 25, "https://www.shutterstock.com/shutterstock/videos/1069616947/preview/stock-footage-close-up-view-of-pepper-grinding-peppers-directly-into-black-cast-iron-pan-concept-of-restaurant.webm", 2, "Add black pepper.", "1/4 teaspoon black pepper"),
(19, 164, NULL, NULL, "Preheat grill. Toss asparagus with lemon juice. Grill until tender. Drizzle with melted butter and sprinkle black pepper.", "Cooking Instructions"),

-- Sesame Ginger Green Beans
(20, 125, "", 300, "Trim green beans.", "300g green beans"),
(20, 30, "", 5, "Add sesame seeds.", "1 teaspoon sesame seeds"),
(20, 28, "", 5, "Add ginger powder.", "1/2 teaspoon ginger powder"),
(20, 40, "", 10, "Add soy sauce.", "2 teaspoons soy sauce"),
(20, 164, "", 0, "Blanch green beans. Toss with sesame seeds, ginger powder, and soy sauce.", "Cooking Instructions"),

-- Garlic Butter Green Beans
(21, 125, "", 300, "Trim green beans.", "300g green beans"),
(21, 81, "", 5, "Mince garlic.", "2 cloves garlic, minced"),
(21, 68, "", 20, "Melt butter.", "2 tablespoons butter"),
(21, 126, "", 3, "Add lemon zest.", "1 teaspoon lemon zest"),
(21, 164, "", 0, "Blanch green beans. In a pan, sauté minced garlic in melted butter. Add blanched green beans and lemon zest. Toss to coat.", "Cooking Instructions"),

-- Roasted Brussels Sprouts with Balsamic Glaze
(22, 127, NULL, 300, "Trim Brussels sprouts.", "300g Brussels sprouts"),
(22, 123, NULL, 30, "Drizzle balsamic glaze.", "2 tablespoons balsamic glaze"),
(22, 48, NULL, 20, "Coat with olive oil.", "2 tablespoons olive oil"),
(22, 29, "https://www.shutterstock.com/shutterstock/videos/1055810363/preview/stock-footage-close-up-selective-focus-shot-of-hand-of-male-cook-adding-salt-to-boiling-water-in-metal-pot-while.webm", 3, "Add salt.", "1/2 teaspoon salt"),
(22, 164, NULL, NULL, "Preheat oven. Toss trimmed Brussels sprouts with olive oil and salt. Roast in the oven until caramelized. Drizzle with balsamic glaze.", "Cooking Instructions"),

-- Caprese Skewers
(23, 128, "", 20, "Thread cherry tomatoes.", "20 cherry tomatoes"),
(23, 67, "", 100, "Thread mozzarella cheese cubes.", "100g mozzarella cheese, cubed"),
(23, 99, "", 20, "Thread basil leaves.", "20 fresh basil leaves"),
(23, 123, "", 30, "Drizzle balsamic glaze.", "2 tablespoons balsamic glaze"),
(23, 164, "", 0, "Thread cherry tomatoes, mozzarella cheese cubes, and fresh basil leaves onto skewers. Drizzle with balsamic glaze.", "Assembling Instructions"),

-- Sweet and Spicy Roasted Cauliflower
(24, 89, NULL, 500, "Cut cauliflower into florets.", "500g cauliflower florets"),
(24, 26, NULL, 5, "Sprinkle chili powder.", "1 teaspoon chili powder"),
(24, 37, "https://www.shutterstock.com/shutterstock/videos/1058013685/preview/stock-footage-a-full-spoon-of-sugar-sugar-falling-from-spoon-in-slow-motion.webm", 5, "Add sugar.", "1 teaspoon sugar"),
(24, 48, NULL, 30, "Coat with olive oil.", "2 tablespoons olive oil"),
(24, 164, NULL, NULL, "Preheat oven. Toss cauliflower florets with chili powder, sugar, and olive oil. Roast in the oven until tender and caramelized.", "Cooking Instructions"),

-- Spinach and Feta Stuffed Mushrooms
(25, 91, "", 12, "Remove stems from mushrooms.", "12 large mushrooms"),
(25, 86, "", 100, "Chop spinach.", "100g spinach, chopped"),
(25, 138, "", 100, "Crumble feta cheese.", "100g feta cheese, crumbled"),
(25, 81, "", 10, "Mince garlic.", "2 cloves garlic, minced"),
(25, 48, "", 30, "Coat with olive oil.", "2 tablespoons olive oil"),
(25, 164, "", 0, "Preheat oven. Remove stems from mushrooms and chop them. Sauté chopped stems, minced garlic, and chopped spinach in olive oil. Fill mushroom caps with the mixture and top with crumbled feta. Bake in the oven until mushrooms are tender.", "Cooking Instructions"),

-- Cheesy Broccoli Casserole
(26, 88, "", 500, "Cut broccoli into florets.", "500g broccoli florets"),
(26, 103, "", 150, "Shred Cheddar cheese.", "1 1/2 cups shredded Cheddar cheese"),
(26, 27, "", 5, "Sprinkle garlic powder.", "1 teaspoon garlic powder"),
(26, 131, "", 50, "Sprinkle bread crumbs.", "1/2 cup bread crumbs"),
(26, 164, "", 0, "Steam broccoli florets until tender. In a baking dish, layer steamed broccoli, shredded Cheddar cheese, and garlic powder. Top with bread crumbs. Bake until cheese is melted and bubbly.", "Cooking Instructions"),

-- Baked Parmesan Zucchini Fries
(27, 98, "", 300, "Cut zucchini into fries.", "300g zucchini, cut into fries"),
(27, 124, "", 50, "Sprinkle grated Parmesan cheese.", "1/2 cup grated Parmesan cheese"),
(27, 131, "", 50, "Sprinkle bread crumbs.", "1/2 cup bread crumbs"),
(27, 27, "", 5, "Sprinkle garlic powder.", "1 teaspoon garlic powder"),
(27, 164, "", 0, "Preheat oven. Toss zucchini fries with grated Parmesan cheese, bread crumbs, and garlic powder. Arrange on a baking sheet and bake until golden and crispy.", "Cooking Instructions"),

-- Creamed Spinach
(28, 86, NULL, 200, "Chop spinach.", "200g spinach, chopped"),
(28, 69, NULL, 100, "Cube cream cheese.", "100g cream cheese, cubed"),
(28, 81, NULL, 10, "Mince garlic.", "2 cloves garlic, minced"),
(28, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 120, "Dice onion.", "1 small onion, diced"),
(28, 132, NULL, 5, "Add nutmeg.", "1/2 teaspoon nutmeg"),
(28, 68, NULL, 30, "Melt butter.", "2 tablespoons butter"),
(28, 164, NULL, NULL, "Sauté diced onion and minced garlic in melted butter. Add chopped spinach and cook until wilted. Stir in cubed cream cheese and nutmeg until creamy.", "Cooking Instructions"),

-- Fruit Salsa with Cinnamon Chips
(29, 1, NULL, 200, "Dice apple.", "1 apple, diced"),
(29, 2, NULL, 100, "Chop apricot.", "100g apricot, chopped"),
(29, 8, NULL, 100, "Peel and dice kiwi.", "2 kiwi, peeled and diced"),
(29, 9, NULL, 10, "Squeeze lemon juice.", "1 lemon, juiced"),
(29, 37, "https://www.shutterstock.com/shutterstock/videos/1058013685/preview/stock-footage-a-full-spoon-of-sugar-sugar-falling-from-spoon-in-slow-motion.webm", 15, "Add sugar.", "1 tablespoon sugar"),
(29, 31, NULL, 2, "Add cinnamon powder.", "1/4 teaspoon cinnamon powder"),
(29, 133, NULL, 100, "Cut tortilla into chips.", "100g tortilla chips"),
(29, 164, NULL, NULL, "Mix diced apple, chopped apricot, diced kiwi, lemon juice, sugar, and cinnamon powder. Serve with cinnamon chips.", "Assembling Instructions"),

-- Avocado Bruschetta
(30, 3, NULL, 200, "Mash avocado.", "1 ripe avocado, mashed"),
(30, 80, "https://www.shutterstock.com/shutterstock/videos/1106911463/preview/stock-footage-chop-tomato-close-up-diced-tomatoes-on-a-cutting-board-for-italian-sauce-chef-cut-tomatoes-with.webm", 100, "Dice tomato.", "1 medium tomato, diced"),
(30, 81, NULL, 10, "Mince garlic.", "2 cloves garlic, minced"),
(30, 99, NULL, 10, "Tear basil leaves.", "10 fresh basil leaves, torn"),
(30, 134, NULL, 100, "Slice baguette.", "100g baguette, sliced"),
(30, 164, NULL, NULL, "Mix mashed avocado, diced tomato, minced garlic, and torn basil leaves. Toast baguette slices and top with avocado mixture.", "Assembling Instructions"),

-- Crab Cakes
(31, 60, NULL, 200, "Pick through crab meat.", "200g crab meat, picked through"),
(31, 131, NULL, 50, "Sprinkle bread crumbs.", "1/2 cup bread crumbs"),
(31, 44, NULL, 30, "Add mayonnaise.", "2 tablespoons mayonnaise"),
(31, 135, NULL, 10, "Add Dijon mustard.", "1 teaspoon Dijon mustard"),
(31, 9, NULL, 10, "Squeeze lemon juice.", "1 lemon, juiced"),
(31, 136, NULL, 5, "Sprinkle Old Bay seasoning.", "1/2 teaspoon Old Bay seasoning"),
(31, 37, "https://www.shutterstock.com/shutterstock/videos/1058013685/preview/stock-footage-a-full-spoon-of-sugar-sugar-falling-from-spoon-in-slow-motion.webm", 5, "Add sugar.", "1/2 teaspoon sugar"),
(31, 164, NULL, NULL, "Mix crab meat, bread crumbs, mayonnaise, Dijon mustard, lemon juice, Old Bay seasoning, and sugar. Form into patties and pan-fry until golden.", "Cooking Instructions"),

-- Thai Beef Lettuce Wraps
(32, 56, NULL, 200, "Cook beef.", "200g beef, cooked and thinly sliced"),
(32, 104, NULL, 100, "Separate lettuce leaves.", "100g lettuce leaves"),
(32, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 100, "Dice onion.", "1 small onion, diced"),
(32, 81, NULL, 10, "Mince garlic.", "2 cloves garlic, minced"),
(32, 28, NULL, 5, "Add ginger powder.", "1/2 teaspoon ginger powder"),
(32, 40, NULL, 10, "Add soy sauce.", "2 teaspoons soy sauce"),
(32, 164, NULL, NULL, "Cook beef and thinly slice it. Sauté diced onion and minced garlic. Add sliced beef, ginger powder, and soy sauce. Serve in lettuce leaves.", "Assembling Instructions"),

-- Smoked Salmon Cucumber Bites
(33, 90, "", 20, "Slice cucumber.", "20 cucumber slices"),
(33, 118, "", 30, "Slice smoked salmon.", "30g smoked salmon, sliced"),
(33, 69, "", 50, "Spread cream cheese.", "50g cream cheese"),
(33, 116, "", 2, "Sprinkle chopped dill.", "1 teaspoon chopped dill"),
(33, 164, "", 0, "Top cucumber slices with smoked salmon. Spread cream cheese on top and sprinkle with chopped dill.", "Assembling Instructions"),

-- Tomato Basil Bruschetta
(34, 80, "https://www.shutterstock.com/shutterstock/videos/1106911463/preview/stock-footage-chop-tomato-close-up-diced-tomatoes-on-a-cutting-board-for-italian-sauce-chef-cut-tomatoes-with.webm", 200, "Dice tomatoes.", "2 medium tomatoes, diced"),
(34, 99, NULL, 10, "Tear basil leaves.", "10 fresh basil leaves, torn"),
(34, 81, NULL, 10, "Mince garlic.", "2 cloves garlic, minced"),
(34, 134, NULL, 100, "Slice baguette.", "100g baguette, sliced"),
(34, 48, NULL, 20, "Coat with olive oil.", "2 tablespoons olive oil"),
(34, 164, NULL, NULL, "Mix diced tomatoes, torn basil leaves, and minced garlic. Toast baguette slices and top with tomato mixture.", "Assembling Instructions"),

-- SOUP
-- Creamy Tomato Basil Soup
(35, 80, "https://www.shutterstock.com/shutterstock/videos/1106911463/preview/stock-footage-chop-tomato-close-up-diced-tomatoes-on-a-cutting-board-for-italian-sauce-chef-cut-tomatoes-with.webm", 400, "Dice tomatoes", "4 medium tomatoes, diced"),
(35, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 100, "Dice onion", "1 small onion, diced"),
(35, 81, NULL, 10, "Mince garlic", "2 cloves garlic, minced"),
(35, 99, NULL, 10, "Tear basil leaves", "10 fresh basil leaves, torn"),
(35, 137, NULL, 100, "Prepare heavy cream", "100g heavy cream"),
(35, 48, NULL, 20, "Coat with olive oil", "2 tablespoons olive oil"),
(35, 164, NULL, NULL, "Sauté diced onion and minced garlic. Add diced tomatoes and torn basil leaves. Simmer until tomatoes are soft. Blend mixture until smooth. Return to heat, add cream, and stir until heated through.", "Cooking Instructions"),

-- Chicken Noodle Soup
(36, 55, "https://www.shutterstock.com/shutterstock/videos/1062774610/preview/stock-footage-close-up-of-cook-chef-hands-woman-cuts-raw-meat-chicken-breast-on-a-wooden-board.webm", 200, "Cook chicken", "200g chicken, cooked and shredded"),
(36, 78, NULL, 100, "Dice carrot", "1 medium carrot, diced"),
(36, 102, NULL, 100, "Dice celery", "1 stalk celery, diced"),
(36, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 100, "Dice onion", "1 small onion, diced"),
(36, 138, NULL, 100, "Add egg noodles", "100g egg noodles"),
(36, 139, NULL, 400, "Add chicken broth", "400ml chicken broth"),
(36, 164, NULL, NULL, "In a pot, combine shredded chicken, diced carrot, diced celery, diced onion, and chicken broth. Simmer until vegetables are tender. Add egg noodles and cook until tender.", "Cooking Instructions"),

-- Spicy Black Bean Soup
(37, 73, NULL, 200, "Cook black beans", "200g black beans, cooked"),
(37, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 100, "Dice onion", "1 small onion, diced"),
(37, 81, NULL, 10, "Mince garlic", "2 cloves garlic, minced"),
(37, 48, NULL, 20, "Coat with olive oil", "2 tablespoons olive oil"),
(37, 26, NULL, 5, "Sprinkle chili powder", "1 teaspoon chili powder"),
(37, 32, NULL, 5, "Add cumin powder", "1/2 teaspoon cumin powder"),
(37, 140, NULL, 400, "Add vegetable broth", "400ml vegetable broth"),
(37, 164, NULL, NULL, "Sauté diced onion and minced garlic. Add cooked black beans, chili powder, cumin powder, and vegetable broth. Simmer until flavors meld.", "Cooking Instructions"),

-- Butternut Squash Soup
(38, 141, NULL, 500, "Dice butternut squash", "500g butternut squash, diced"),
(38, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 100, "Dice onion", "1 small onion, diced"),
(38, 81, NULL, 10, "Mince garlic", "2 cloves garlic, minced"),
(38, 132, NULL, 5, "Add nutmeg", "1/2 teaspoon nutmeg"),
(38, 140, NULL, 400, "Add vegetable broth", "400ml vegetable broth"),
(38, 137, NULL, 100, "Cube cream", "100g cream, cubed"),
(38, 164, NULL, NULL, "Sauté diced onion and minced garlic. Add diced butternut squash, nutmeg, and vegetable broth. Simmer until squash is soft. Blend mixture until smooth. Return to heat, add cream, and stir until heated through.", "Cooking Instructions"),

-- Minestrone Soup
(39, 80, "https://www.shutterstock.com/shutterstock/videos/1106911463/preview/stock-footage-chop-tomato-close-up-diced-tomatoes-on-a-cutting-board-for-italian-sauce-chef-cut-tomatoes-with.webm", 400, "Dice tomatoes", "4 medium tomatoes, diced"),
(39, 78, NULL, 100, "Dice carrot", "1 medium carrot, diced"),
(39, 102, NULL, 100, "Dice celery", "1 stalk celery, diced"),
(39, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 100, "Dice onion", "1 small onion, diced"),
(39, 142, NULL, 200, "Cook cannellini beans", "200g cannellini beans, cooked"),
(39, 143, NULL, 100, "Add pasta", "100g pasta"),
(39, 140, NULL, 400, "Add vegetable broth", "400ml vegetable broth"),
(39, 164, NULL, NULL, "In a pot, combine diced tomatoes, diced carrot, diced celery, diced onion, cooked cannellini beans, pasta, and vegetable broth. Simmer until vegetables are tender.", "Cooking Instructions"),

-- Coconut Curry Chicken Soup
(40, 55, "https://www.shutterstock.com/shutterstock/videos/1062774610/preview/stock-footage-close-up-of-cook-chef-hands-woman-cuts-raw-meat-chicken-breast-on-a-wooden-board.webm", 200, "Cook chicken", "200g chicken, cooked and shredded"),
(40, 95, NULL, 400, "Add coconut milk", "400ml coconut milk"),
(40, 144, NULL, 5, "Sprinkle curry powder", "1 teaspoon curry powder"),
(40, 28, NULL, 5, "Add ginger powder", "1/2 teaspoon ginger powder"),
(40, 81, NULL, 10, "Mince garlic", "2 cloves garlic, minced"),
(40, 164, NULL, NULL, "In a pot, combine cooked and shredded chicken, coconut milk, curry powder, ginger powder, minced garlic, and vegetables. Simmer until heated through.", "Cooking Instructions"),

-- Potato Leek Soup
(41, 77, NULL, 400, "Dice potatoes", "400g potatoes, diced"),
(41, 145, NULL, 100, "Slice leek", "1 leek, sliced"),
(41, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 100, "Dice onion", "1 small onion, diced"),
(41, 81, NULL, 10, "Mince garlic", "2 cloves garlic, minced"),
(41, 140, NULL, 400, "Add vegetable broth", "400ml vegetable broth"),
(41, 137, NULL, 100, "Cube cream", "100g cream, cubed"),
(41, 164, NULL, NULL, "Sauté sliced leek, diced onion, and minced garlic. Add diced potatoes and vegetable broth. Simmer until potatoes are soft. Blend mixture until smooth. Return to heat, add cream, and stir until heated through.", "Cooking Instructions"),

-- Pumpkin Soup
(42, 101, NULL, 500, "Dice pumpkin", "500g pumpkin, diced"),
(42, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 100, "Dice onion", "1 small onion, diced"),
(42, 81, NULL, 10, "Mince garlic", "2 cloves garlic, minced"),
(42, 132, NULL, 5, "Add nutmeg", "1/2 teaspoon nutmeg"),
(42, 31, NULL, 5, "Add cinnamon powder", "1/2 teaspoon cinnamon powder"),
(42, 140, NULL, 400, "Add vegetable broth", "400ml vegetable broth"),
(42, 137, NULL, 100, "Cube cream", "100g cream, cubed"),
(42, 164, NULL, NULL, "Sauté diced onion and minced garlic. Add diced pumpkin, nutmeg, cinnamon powder, and vegetable broth. Simmer until pumpkin is soft. Blend mixture until smooth. Return to heat, add cream, and stir until heated through.", "Cooking Instructions"),

-- Creamy Spinach and Parmesan Soup
(43, 86, NULL, 200, "Chop spinach", "200g spinach, chopped"),
(43, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 100, "Dice onion", "1 small onion, diced"),
(43, 81, NULL, 10, "Mince garlic", "2 cloves garlic, minced"),
(43, 124, NULL, 200, "Cube Parmesan cheese", "200g Parmesan cheese, cubed"),
(43, 140, NULL, 400, "Add vegetable broth", "400ml vegetable broth"),
(43, 137, NULL, 100, "Cube cream", "100g cream, cubed"),
(43, 164, NULL, NULL, "Sauté diced onion and minced garlic. Add chopped spinach, cubed Parmesan cheese, and vegetable broth. Simmer until spinach is wilted. Blend mixture until smooth. Return to heat, add cream, and stir until heated through.", "Cooking Instructions"),

-- Creamy Broccoli Cheddar Soup
(44, 88, NULL, 300, "Divide broccoli into florets", "300g broccoli florets"),
(44, 103, NULL, 200, "Dice Cheddar cheese", "200g Cheddar cheese, diced"),
(44, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 100, "Dice onion", "1 small onion, diced"),
(44, 81, NULL, 10, "Mince garlic", "2 cloves garlic, minced"),
(44, 140, NULL, 400, "Add vegetable broth", "400ml vegetable broth"),
(44, 137, NULL, 100, "Cube cream", "100g cream, cubed"),
(44, 164, NULL, NULL, "Steam broccoli florets. Sauté diced onion and minced garlic. Add steamed broccoli florets, diced Cheddar cheese, and vegetable broth. Simmer until broccoli is tender. Blend mixture until smooth. Return to heat, add cream, and stir until heated through.", "Cooking Instructions"),

-- MAIN COURSES
-- Ginseng Chicken Soup
(45, 55, "https://www.shutterstock.com/shutterstock/videos/1062774610/preview/stock-footage-close-up-of-cook-chef-hands-woman-cuts-raw-meat-chicken-breast-on-a-wooden-board.webm", 500, "Cook chicken", "500g chicken, cooked"),
(45, 146, NULL, 100, "Cook sticky rice", "100g sticky rice, cooked"),
(45, 147, NULL, 75, "Add ginseng", "3 ginseng roots"),
(45, 81, NULL, 10, "Mince garlic", "2 cloves garlic, minced"),
(45, 148, NULL, 10, "Add jujubes", "5 jujubes"),
(45, 149, NULL, 5, "Add ginger slices", "3 slices ginger"),
(45, 164, NULL, NULL, "Brew ginseng tea and serve it with the soup", "Cooking Instructions"),

-- "Creamy Mushroom Risotto"
(46, 154, "", 200, "Cook the Arborio rice.", "200g Arborio Rice"),
(46, 91, "", 150, "Sauté the mushrooms.", "150g mushrooms"),
(46, 111, "", 100, "Pour in the white wine.", "100ml white wine"),
(46, 124, "", 50, "Stir in the Parmesan cheese.", "50g Parmesan Cheese"),
(46, 140, "", 500, "Add vegetable broth.", "500ml Vegetable Broth"),

-- "Black Pepper Beef Stir-Fry"
(47, 56, NULL, 200, "Slice the beef.", "200g beef"),
(47, 25, "https://www.shutterstock.com/shutterstock/videos/1069616947/preview/stock-footage-close-up-view-of-pepper-grinding-peppers-directly-into-black-cast-iron-pan-concept-of-restaurant.webm", 5, "Add black pepper.", "1 teaspoon black pepper"),
(47, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 100, "Dice the onion.", "1 onion"),
(47, 81, NULL, 10, "Mince the garlic.", "2 cloves garlic"),
(47, 40, NULL, 15, "Stir in the soy sauce.", "1 tablespoon soy sauce"),
(47, 50, NULL, 15, "Mix with fish sauce.", "1 tablespoon fish sauce"),
(47, 74, NULL, 200, "Serve with steamed rice.", "200g steamed rice"),

-- Glass Noodle with Chicken - Mien Ga
(48, 55, "https://www.shutterstock.com/shutterstock/videos/1062774610/preview/stock-footage-close-up-of-cook-chef-hands-woman-cuts-raw-meat-chicken-breast-on-a-wooden-board.webm", 100, "Prepare chicken.", "cooked chicken, shredded"),
(48, 155, NULL, 50, "Prepare wood ear mushroom.", "wood ear mushroom, soaked and sliced"),
(48, 149, NULL, 10, "Prepare ginger.", "1 small piece of ginger, minced"),
(48, 156, NULL, 50, "Prepare glass noodle.", "glass noodle, soaked and drained"),
(48, 164, NULL, NULL, "Cook glass noodle according to package instructions. Drain and set aside.", "Cooking Instructions"),

-- Stir-Fried Water Spinach - Rau muong xao toi
(49, 157, "", 100, "Prepare water spinach.", "water spinach, washed and cut into pieces"),
(49, 81, "", 10, "Prepare garlic.", "2-3 cloves of garlic, minced"),
(49, 49, "", 15, "Prepare peanut oil.", "1 tablespoon peanut oil"),
(49, 164, "", 0, "Heat a pan with peanut oil. Add minced garlic and sauté until fragrant.", "Cooking Instructions"),

-- Avocado Kimbap
(50, 3, NULL, 1, "Prepare avocado.", "1 ripe avocado, sliced"),
(50, 74, NULL, 200, "Prepare rice.", "1 cup cooked rice"),
(50, 158, NULL, 5, "Prepare roasted seaweed.", "5 sheets of roasted seaweed"),
(50, 78, NULL, 50, "Prepare carrot.", "1 carrot, julienned"),
(50, 90, NULL, 50, "Prepare cucumber.", "1 cucumber, julienned"),
(50, 72, "https://www.shutterstock.com/shutterstock/videos/1031672702/preview/stock-footage-female-hands-holding-a-cracked-egg-breaking-an-egg-black-background-slow-motion.webm", 2, "Prepare egg.", "2 eggs, scrambled"),
(50, 30, NULL, 5, "Prepare sesame seeds.", "1 tablespoon sesame seeds"),
(50, 164, NULL, NULL, "Place a sheet of roasted seaweed on a bamboo mat or plastic wrap. Evenly spread a thin layer of cooked rice on the seaweed, leaving a border at the top.", "Cooking Instructions"),

-- Jajangmyeon
(51, 57, NULL, 100, "Prepare pork.", "100g pork, thinly sliced"),
(51, 159, NULL, 50, "Prepare black bean paste.", "1/4 cup black bean paste"),
(51, 79, "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm", 50, "Prepare onion.", "1 onion, chopped"),
(51, 98, NULL, 50, "Prepare zucchini.", "1 small zucchini, julienned"),
(51, 77, NULL, 50, "Prepare potatoes.", "1 small potato, diced"),
(51, 29, "https://www.shutterstock.com/shutterstock/videos/1055810363/preview/stock-footage-close-up-selective-focus-shot-of-hand-of-male-cook-adding-salt-to-boiling-water-in-metal-pot-while.webm", 5, "Add salt.", "1/2 teaspoon salt"),
(51, 25, "https://www.shutterstock.com/shutterstock/videos/1069616947/preview/stock-footage-close-up-view-of-pepper-grinding-peppers-directly-into-black-cast-iron-pan-concept-of-restaurant.webm", 2.5, "Add black pepper.", "1/4 teaspoon black pepper"),
(51, 48, NULL, 15, "Prepare olive oil.", "1 tablespoon olive oil"),
(51, 160, NULL, 200, "Prepare noodles.", "200g jajangmyeon noodles, cooked according to package instructions"),
(51, 164, NULL, NULL, "Prepare jajangmyeon noodles according to package instructions. Drain and set aside.", "Cooking Instructions"),

-- Korean Fried Chicken
(52, 55, "https://www.shutterstock.com/shutterstock/videos/1062774610/preview/stock-footage-close-up-of-cook-chef-hands-woman-cuts-raw-meat-chicken-breast-on-a-wooden-board.webm", 100, "Prepare chicken.", "chicken wings or drumettes, washed and patted dry"),
(52, 161, NULL, 50, "Prepare flour.", "1/2 cup all-purpose flour"),
(52, 162, NULL, 50, "Prepare cornstarch.", "1/2 cup cornstarch"),
(52, 40, NULL, 30, "Prepare soy sauce.", "2 tablespoons soy sauce"),
(52, 81, NULL, 5, "Prepare garlic.", "3-4 cloves of garlic, minced"),
(52, 149, NULL, 2, "Prepare ginger.", "1 small piece of ginger, minced"),
(52, 38, NULL, 30, "Prepare honey.", "2 tablespoons honey"),
(52, 52, NULL, 10, "Prepare Korean chili paste.", "1 tablespoon gochujang"),
(52, 163, NULL, 15, "Prepare vegetable oil.", "1 tablespoon vegetable oil"),
(52, 164, NULL, NULL, "In a bowl, mix the all-purpose flour and cornstarch. Dredge the chicken pieces in the flour mixture to coat evenly.", "Cooking Instructions"),

-- "Bacon Wrapped Asparagus"
(53, 87, NULL, 200, "Trim the asparagus.", "200g Asparagus"),
(53, 93, NULL, 60, "Prepare the bacon strips.", "2 slices bacon"),
(53, 48, NULL, 15, "Use olive oil.", "1 tablespoon olive oil"),
(53, 29, "https://www.shutterstock.com/shutterstock/videos/1055810363/preview/stock-footage-close-up-selective-focus-shot-of-hand-of-male-cook-adding-salt-to-boiling-water-in-metal-pot-while.webm", 3, "Season with salt.", "1/2 teaspoon salt"),
(53, 25, "https://www.shutterstock.com/shutterstock/videos/1069616947/preview/stock-footage-close-up-view-of-pepper-grinding-peppers-directly-into-black-cast-iron-pan-concept-of-restaurant.webm", 1.5, "Add black pepper.", "1/4 teaspoon black pepper");

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