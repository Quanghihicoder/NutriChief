// import connection
import db from "../config/database.js";

export const getAllFoods = (result) => {
    db.query(
        "SELECT f.food_id, food.food_name, food.food_desc, food.food_ctime, food.food_ptime, food.food_type, food.food_img, f.food_price, f.food_calories, f.food_carb, f.food_fat, f.food_protein FROM (SELECT r.food_id AS food_id, SUM(r.recipe_price) AS food_price, SUM(r.recipe_calories) AS food_calories, SUM(r.recipe_carb) AS food_carb, SUM(r.recipe_fat) AS food_fat, SUM(r.recipe_protein) AS food_protein FROM (SELECT recipe.food_id AS food_id, (recipe.recipe_qty / 100 * ingredient.ingre_price) AS recipe_price, (recipe.recipe_qty / 100 * ingredient.ingre_calo) AS recipe_calories, (recipe.recipe_qty / 100 * ingredient.ingre_carb) AS recipe_carb, (recipe.recipe_qty / 100 * ingredient.ingre_fat) AS recipe_fat, (recipe.recipe_qty / 100 * ingredient.ingre_protein) AS recipe_protein FROM recipe INNER JOIN ingredient ON recipe.ingre_id = ingredient.ingre_id) AS r GROUP BY food_id) AS f INNER JOIN food ON f.food_id = food.food_id;",
        [],
        (err, results) => {
            if (err) {
                console.log(err);
                result({ status: 0, message: "Can not get foods", data: [] });
            } else {
                if (results[0]) {
                    result({
                        status: 1,
                        message: "Successfully get foods",
                        data: results,
                    });
                } else {
                    result({
                        status: 0,
                        message: "Can not get foods",
                        data: [],
                    });
                }
            }
        }
    );
};

export const getFoodDetailById = (data, result) => {
    if (data) {
        db.query(
            "SELECT f.food_id, food.food_name, food.food_desc, food.food_ctime, food.food_ptime, food.food_type, food.food_img, f.food_price, f.food_calories, f.food_carb, f.food_fat, f.food_protein FROM (SELECT r.food_id AS food_id, SUM(r.recipe_price) AS food_price, SUM(r.recipe_calories) AS food_calories, SUM(r.recipe_carb) AS food_carb, SUM(r.recipe_fat) AS food_fat, SUM(r.recipe_protein) AS food_protein FROM (SELECT recipe.food_id AS food_id, (recipe.recipe_qty / 100 * ingredient.ingre_price) AS recipe_price, (recipe.recipe_qty / 100 * ingredient.ingre_calo) AS recipe_calories, (recipe.recipe_qty / 100 * ingredient.ingre_carb) AS recipe_carb, (recipe.recipe_qty / 100 * ingredient.ingre_fat) AS recipe_fat, (recipe.recipe_qty / 100 * ingredient.ingre_protein) AS recipe_protein FROM recipe INNER JOIN ingredient ON recipe.ingre_id = ingredient.ingre_id) AS r GROUP BY food_id) AS f INNER JOIN food ON f.food_id = food.food_id WHERE f.food_id = ?;",
            [data],
            (err, results) => {
                if (err) {
                    console.log(err);
                    result({
                        status: 0,
                        message: "Can not get foods",
                        data: [],
                    });
                } else {
                    if (results) {
                        result({
                            status: 1,
                            message: "Successfully get foods",
                            data: results,
                        });
                    } else {
                        result({
                            status: 0,
                            message: "Can not get foods",
                            data: [],
                        });
                    }
                }
            }
        );
    } else {
        result({ status: 0, message: "Can not get foods", data: [] });
    }
};

export const getDetails = (data, result) => {
    if (data.food_id) {
        db.query(
            `
            SELECT 
                ingredient.*,
                recipe.recipe_title,
                recipe.recipe_desc,
                recipe.recipe_qty,
                recipe.media_url,
                (recipe.recipe_qty / 100 * ingredient.ingre_price) AS recipe_price,
                (recipe.recipe_qty / 100 * ingredient.ingre_calo) AS recipe_calories,
                (recipe.recipe_qty / 100 * ingredient.ingre_carb) AS recipe_carb,
                (recipe.recipe_qty / 100 * ingredient.ingre_fat) AS recipe_fat,
                (recipe.recipe_qty / 100 * ingredient.ingre_protein) AS recipe_protein  
            FROM recipe 
            INNER JOIN ingredient 
            ON recipe.ingre_id = ingredient.ingre_id 
            WHERE recipe.food_id = ?`,
            [data.food_id],
            (err, results) => {
                if (err) {
                    console.log(err);
                    result({
                        status: 0,
                        message: "Can not get food's detail",
                        data: [],
                    });
                } else {
                    if (results[0]) {
                        result({
                            status: 1,
                            message: "Successfully get food's detail",
                            data: results,
                        });
                    } else {
                        result({
                            status: 0,
                            message: "Can not get food's detail",
                            data: [],
                        });
                    }
                }
            }
        );
    } else {
        result({ status: 0, message: "Can not get food's detail", data: [] });
    }
};
