// import connection
import db from "../config/database.js";

export const webViewFood = async (req, res) => {
    const data = req.params.food_id;

    let food = []
    async function getFoods() {
        food = await new Promise((resolve) => {
            db.query('SELECT f.food_id, food.food_name, food.food_desc, food.food_ctime, food.food_ptime, food.food_type, f.food_price, f.food_calories, f.food_carb, f.food_fat, f.food_protein FROM (SELECT r.food_id AS food_id, SUM(r.recipe_price) AS food_price, SUM(r.recipe_calories) AS food_calories, SUM(r.recipe_carb) AS food_carb, SUM(r.recipe_fat) AS food_fat, SUM(r.recipe_protein) AS food_protein FROM (SELECT recipe.food_id AS food_id, (recipe.recipe_qty / 100 * ingredient.ingre_price) AS recipe_price, (recipe.recipe_qty / 100 * ingredient.ingre_calo) AS recipe_calories, (recipe.recipe_qty / 100 * ingredient.ingre_carb) AS recipe_carb, (recipe.recipe_qty / 100 * ingredient.ingre_fat) AS recipe_fat, (recipe.recipe_qty / 100 * ingredient.ingre_protein) AS recipe_protein FROM recipe INNER JOIN ingredient ON recipe.ingre_id = ingredient.ingre_id) AS r GROUP BY food_id) AS f INNER JOIN food ON f.food_id = food.food_id WHERE f.food_id = ?;', [data], (err, res) => {
                resolve(res)
            })
        }).then(res => {
            return res
        });
    }
    await getFoods()

    if (food[0]) {
        res.set('Content-Type', 'text/html');
        res.status(200).send(`
            <!doctype html>
            <html lang="en">
            <head>
                <meta charset="utf-8">
                <title>NutriChief</title>
                <meta name="description" content="Description Goes Here">
            </head>
            <body>
                <p>Food name: ${food[0].food_name}</p>
                <p>Description: ${food[0].food_desc}</p>
                <p>Cook time: ${food[0].food_ctime} minutes</p>
                <p>Prepare time: ${food[0].food_ptime} minutes</p>
                <p>Average price: $${food[0].food_price}</p>
                <p>Total calories: ${food[0].food_calories}</p>
                <p>Total carb: ${food[0].food_carb} grams</p>
                <p>Total fat: ${food[0].food_fat} grams</p>
                <p>Total protein: ${food[0].food_protein} grams</p>
            </body>
            </html>
        `);
    } else {
        res.set('Content-Type', 'text/html');
        res.status(200).send(`
            <!doctype html>
            <html lang="en">
            <head>
                <meta charset="utf-8">
                <title>NutriChief</title>
                <meta name="description" content="Description Goes Here">
            </head>
            <body>
                <p>Food not found!</p>
            </body>
            </html>
        `);
    }
};