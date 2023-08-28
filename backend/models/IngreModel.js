// import connection
import db from "../config/database.js";

export const getAllIngres = (result) => {
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


export const getAllFoods = (data, result) => {
    db.query("SELECT f.food_id, f.food_name, f.food_desc, f.food_ctime, f.food_ptime FROM food AS f INNER JOIN (SELECT food_id FROM recipe WHERE ingre_id = ?) AS r ON f.food_id = r.food_id;", [data.ingre_id], (err, results) => {
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
                result({
                    status: 0,
                    message: "Can not get foods",
                    data: [],
                });
            }
        }
    });
};

