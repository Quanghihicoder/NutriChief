// import connection
import db from "../config/database.js";

export const getAllIngres = (data, result) => {
    db.query("SELECT * FROM ingredient", [], (err, results) => {
        if (err) {
            console.log(err);
            result({ "status": 0, "message": "Can not get ingredients", "data": [] });
        } else {
            if (results) {
                result({ "status": 1, "message": "Successfully get ingredients", "data": results });
            } else {
                result({ "status": 0, "message": "Can not get ingredients", "data": [] });
            }
        }
    });
}