// import connection
import db from "../config/database.js";

export const getAllIngres = (data, result) => {
    db.query("SELECT * FROM ingredient", [], (err, results) => {
        if (err) {
            console.log(err);
            result({ status: 0, message: "Can not get ingredients", data: [] });
        } else {
            if (results) {
                result({
                    status: 1,
                    message: "Successfully get ingredients",
                    data: results,
                });
            } else {
                result({
                    status: 0,
                    message: "Can not get ingredients",
                    data: [],
                });
            }
        }
    });
};

export const getAllIngresFromRecipe = (data, result) => {
    db.query(
        "SELECT i.*, r.recipe_qty FROM recipe r JOIN ingredient i ON r.ingre_id = i.ingre_id WHERE r.food_id = ?",
        [data.food_id],
        (err, results) => {
            if (err) {
                console.log(err);
                result({
                    status: 0,
                    message: "Can not get ingredients",
                    data: [],
                });
            } else {
                if (results) {
                    result({
                        status: 1,
                        message: "Successfully get ingredients",
                        data: results,
                    });
                } else {
                    result({
                        status: 0,
                        message: "Can not get ingredients",
                        data: [],
                    });
                }
            }
        }
    );
};
