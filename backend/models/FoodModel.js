// import connection
import db from "../config/database.js";

export const getAllFoods = (result) => {
    db.query("SELECT * FROM food", [], (err, results) => {
        if (err) {
            console.log(err);
            result({ status: 0, message: "Can not get foods", data: [] });
        } else {
            if (results) {
                result({
                    status: 1,
                    message: "Successfully get foods",
                    data: results,
                });
            } else {
                result({ status: 0, message: "Can not get foods", data: [] });
            }
        }
    });
};

export const getDetails = (data, result) => {
    if (data.food_id) {
        db.query(
            `
            SELECT 
                ingredient.ingre_name,
                recipe.recipe_title,
                recipe.recipe_desc,
                recipe.recipe_qty,
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
                    result({ status: 0, message: "Can not get food's detail", data: [] });
                } else {
                    if (results) {
                        result({
                            status: 1,
                            message: "Successfully get food's detail",
                            data: results,
                        });
                    } else {
                        result({ status: 0, message: "Can not get food's detail", data: [] });
                    }
                }
            }
        );
    } else {
        result({ status: 0, message: "Can not get food's detail", data: [] });
    }
};


