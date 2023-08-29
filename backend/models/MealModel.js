// import connection
import db from "../config/database.js";

export const getAllMeals = (data, result) => {
    if (data.user_id) {
        db.query("SELECT * FROM meal WHERE user_id = ?", [data.user_id], (err, results) => {
            if (err) {
                console.log(err);
                result({ "status": 0, "message": "Can not get user's meals", "data": [] });
            } else {
                if (results[0]) {
                    result({ "status": 1, "message": "Successfully get user's meals", "data": results });
                } else {
                    result({ "status": 0, "message": "Can not get user's meals", "data": [] });
                }
            }
        });
    } else {
        console.log(err);
        result({ "status": 0, "message": "Can not get user's meals", "data": [] });
    }
}

export const getDateMeal = (data, result) => {
    // YYYY-MM-DD

    if (data.user_id && data.meal_date) {
        db.query("SELECT * FROM meal WHERE user_id = ? AND meal_date = ?", [data.user_id, data.meal_date], (err, results) => {
            if (err) {
                console.log(err);
                result({ "status": 0, "message": "Can not get user's date meal", "data": [] });
            } else {
                if (results[0]) {
                    result({ "status": 1, "message": "Successfully get user's date meal", "data": results });
                } else {
                    result({ "status": 0, "message": "Can not get user's date meal", "data": [] });
                }
            }
        });
    } else {
        console.log(err);
        result({ "status": 0, "message": "Can not get user's date meal", "data": [] });
    }
}

function randomInRange(min, max) {
    return Math.floor(Math.random() * (max - min) + min);
}

const generateMeal = async (calories) => {
    var breakFast = calories / 10 * 2
    var lunch = calories / 10 * 4
    var dinner = calories / 10 * 4
    // var snack = calories / 10

    let breakFastPool = []
    async function getBreakFasts(type) {
        breakFastPool = await new Promise((resolve) => {
            db.query('SELECT f.food_id, food.food_name, food.food_desc, food.food_ctime, food.food_ptime, food.food_type, f.food_price, f.food_calories, f.food_carb, f.food_fat, f.food_protein FROM (SELECT r.food_id AS food_id, SUM(r.recipe_price) AS food_price, SUM(r.recipe_calories) AS food_calories, SUM(r.recipe_carb) AS food_carb, SUM(r.recipe_fat) AS food_fat, SUM(r.recipe_protein) AS food_protein FROM (SELECT recipe.food_id AS food_id, (recipe.recipe_qty / 100 * ingredient.ingre_price) AS recipe_price, (recipe.recipe_qty / 100 * ingredient.ingre_calo) AS recipe_calories, (recipe.recipe_qty / 100 * ingredient.ingre_carb) AS recipe_carb, (recipe.recipe_qty / 100 * ingredient.ingre_fat) AS recipe_fat, (recipe.recipe_qty / 100 * ingredient.ingre_protein) AS recipe_protein FROM recipe INNER JOIN ingredient ON recipe.ingre_id = ingredient.ingre_id) AS r GROUP BY food_id) AS f INNER JOIN food ON f.food_id = food.food_id WHERE food.food_type = 1 AND f.food_calories > ? AND f.food_calories < ?;', [type - 200, type + 200], (err, res) => {
                resolve(res)
            })
        }).then(res => {
            return res
        });
    }
    await getBreakFasts(breakFast)

    let lunchPool = []
    async function getLunches(type) {
        lunchPool = await new Promise((resolve) => {
            db.query('SELECT f.food_id, food.food_name, food.food_desc, food.food_ctime, food.food_ptime, food.food_type, f.food_price, f.food_calories, f.food_carb, f.food_fat, f.food_protein FROM (SELECT r.food_id AS food_id, SUM(r.recipe_price) AS food_price, SUM(r.recipe_calories) AS food_calories, SUM(r.recipe_carb) AS food_carb, SUM(r.recipe_fat) AS food_fat, SUM(r.recipe_protein) AS food_protein FROM (SELECT recipe.food_id AS food_id, (recipe.recipe_qty / 100 * ingredient.ingre_price) AS recipe_price, (recipe.recipe_qty / 100 * ingredient.ingre_calo) AS recipe_calories, (recipe.recipe_qty / 100 * ingredient.ingre_carb) AS recipe_carb, (recipe.recipe_qty / 100 * ingredient.ingre_fat) AS recipe_fat, (recipe.recipe_qty / 100 * ingredient.ingre_protein) AS recipe_protein FROM recipe INNER JOIN ingredient ON recipe.ingre_id = ingredient.ingre_id) AS r GROUP BY food_id) AS f INNER JOIN food ON f.food_id = food.food_id WHERE food.food_type = 2 AND f.food_calories > ? AND f.food_calories < ?;', [type - 200, type + 200], (err, res) => {
                resolve(res)
            })
        }).then(res => {
            return res
        });
    }
    await getLunches(lunch)

    // let snackPool = []
    // async function getSnacks(type) {
    //     snackPool = await new Promise((resolve) => {
    //         db.query('SELECT f.food_id, food.food_name, food.food_desc, food.food_ctime, food.food_ptime, food.food_type, f.food_price, f.food_calories, f.food_carb, f.food_fat, f.food_protein FROM (SELECT r.food_id AS food_id, SUM(r.recipe_price) AS food_price, SUM(r.recipe_calories) AS food_calories, SUM(r.recipe_carb) AS food_carb, SUM(r.recipe_fat) AS food_fat, SUM(r.recipe_protein) AS food_protein FROM (SELECT recipe.food_id AS food_id, (recipe.recipe_qty / 100 * ingredient.ingre_price) AS recipe_price, (recipe.recipe_qty / 100 * ingredient.ingre_calo) AS recipe_calories, (recipe.recipe_qty / 100 * ingredient.ingre_carb) AS recipe_carb, (recipe.recipe_qty / 100 * ingredient.ingre_fat) AS recipe_fat, (recipe.recipe_qty / 100 * ingredient.ingre_protein) AS recipe_protein FROM recipe INNER JOIN ingredient ON recipe.ingre_id = ingredient.ingre_id) AS r GROUP BY food_id) AS f INNER JOIN food ON f.food_id = food.food_id WHERE food.food_type = 3 AND f.food_calories > ? AND f.food_calories < ?;', [type - 100, type + 100], (err, res) => {
    //             resolve(res)
    //         })
    //     }).then(res => {
    //         return res
    //     });
    // }
    // await getSnacks(snack)

    let dinnerPool = []
    async function getDinners(type) {
        dinnerPool = await new Promise((resolve) => {
            db.query('SELECT f.food_id, food.food_name, food.food_desc, food.food_ctime, food.food_ptime, food.food_type, f.food_price, f.food_calories, f.food_carb, f.food_fat, f.food_protein FROM (SELECT r.food_id AS food_id, SUM(r.recipe_price) AS food_price, SUM(r.recipe_calories) AS food_calories, SUM(r.recipe_carb) AS food_carb, SUM(r.recipe_fat) AS food_fat, SUM(r.recipe_protein) AS food_protein FROM (SELECT recipe.food_id AS food_id, (recipe.recipe_qty / 100 * ingredient.ingre_price) AS recipe_price, (recipe.recipe_qty / 100 * ingredient.ingre_calo) AS recipe_calories, (recipe.recipe_qty / 100 * ingredient.ingre_carb) AS recipe_carb, (recipe.recipe_qty / 100 * ingredient.ingre_fat) AS recipe_fat, (recipe.recipe_qty / 100 * ingredient.ingre_protein) AS recipe_protein FROM recipe INNER JOIN ingredient ON recipe.ingre_id = ingredient.ingre_id) AS r GROUP BY food_id) AS f INNER JOIN food ON f.food_id = food.food_id WHERE (food.food_type = 4 OR food.food_type = 3) AND f.food_calories > ? AND f.food_calories < ?;', [type - 200, type + 200], (err, res) => {
                resolve(res)
            })
        }).then(res => {
            return res
        });
    }
    await getDinners(dinner)

    let results = []

    if (breakFastPool.length > 0) {
        results.push(breakFastPool[randomInRange(0, breakFastPool.length - 1)])
    }

    if (lunchPool.length > 0) {
        results.push(lunchPool[randomInRange(0, lunchPool.length - 1)])
    }

    if (dinnerPool.length > 0) {
        results.push(dinnerPool[randomInRange(0, dinnerPool.length - 1)])
    }

    // if (snackPool.length > 0) {
    //     results.push(snackPool[randomInRange(0, snackPool.length - 1)])
    // }

    // if (snackPool.length > 0) {
    //     results.push(snackPool[randomInRange(0, snackPool.length - 1)])
    // }

    console.log(results)
    return results
}

export const createUserMeal = (data, result) => {
    if (data.user_id) {
        db.query("SELECT * FROM mealpref WHERE user_id = ?", [data.user_id], async (err, results) => {
            if (err) {
                console.log(err);
                result({ "status": 0, "message": "Can not generate user's meals", "data": [] });
            } else {
                if (results[0]) {
                    var userPref = results[0]
                    var date = new Date();

                    var getYear = date.toLocaleString("default", { year: "numeric" });
                    var getMonth = date.toLocaleString("default", { month: "2-digit" });
                    var getDay = date.toLocaleString("default", { day: "2-digit" });

                    var today = getYear + "-" + getMonth + "-" + getDay;

                    let r = await generateMeal(userPref.pref_calo)

                    result({ "status": 1, "message": "Successfully generate user's meals", "data": r });
                } else {
                    result({ "status": 0, "message": "Can not generate user's meals", "data": [] });
                }
            }
        });


        // db.query("SELECT * FROM meal WHERE user_id = ? AND meal_date = ?", [data.user_id, data.meal_date], (err, results) => {
        //     if (err) {
        //         console.log(err);
        //         result({ "status": 0, "message": "Can not get user's date meal", "data": [] });
        //     } else {
        //         if (results[0]) {
        //             result({ "status": 1, "message": "Successfully get user's date meal", "data": results });
        //         } else {
        //             result({ "status": 0, "message": "Can not get user's date meal", "data": [] });
        //         }
        //     }
        // });

        // SELECT r.food_id, SUM(r.recipe_price), SUM(r.recipe_calories), SUM(r.recipe_carb), SUM(r.recipe_fat), SUM(r.recipe_protein) FROM(SELECT recipe.food_id AS food_id, (recipe.recipe_qty / 100 * ingredient.ingre_price) AS recipe_price, (recipe.recipe_qty / 100 * ingredient.ingre_calo) AS recipe_calories, (recipe.recipe_qty / 100 * ingredient.ingre_carb) AS recipe_carb, (recipe.recipe_qty / 100 * ingredient.ingre_fat) AS recipe_fat, (recipe.recipe_qty / 100 * ingredient.ingre_protein) AS recipe_protein FROM recipe INNER JOIN ingredient ON recipe.ingre_id = ingredient.ingre_id) AS r GROUP BY food_id;
    } else {
        console.log(err);
        result({ "status": 0, "message": "Can not generate user's meals", "data": [] });
    }
}

// INSERT INTO meal (user_id,meal_food,meal_date) VALUES (1, "1,2,3", "2023-08-23")