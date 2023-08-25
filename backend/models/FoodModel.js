// import connection
import db from "../config/database.js";

const getFoodDetail = async (foodID) => {
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
        [foodID],
        (err, results) => {
            if (err) {
                console.log(err);
                return [];
            } else {
                if (results) {
                    return results;
                } else {
                    return [];
                }
            }
        }
    );
};

export const getAllFoodsAndDetails = (data, result) => {
    db.query("SELECT * FROM food", [], (err, results) => {
        if (err) {
            console.log(err);
            result({ status: 0, message: "Can not get foods", data: [] });
        } else {
            if (results) {
                var allFoods = results;
                for (var i = 0; i < allFoods.length; i++) {
                    if (allFoods[i].food_id) {
                        allFoods[i].food_recipes = getFoodDetail(
                            allFoods[i].food_id
                        );
                    }
                }
                result({
                    status: 1,
                    message: "Successfully get foods",
                    data: [allFoods],
                });
            } else {
                result({ status: 0, message: "Can not get foods", data: [] });
            }
        }
    });
};
