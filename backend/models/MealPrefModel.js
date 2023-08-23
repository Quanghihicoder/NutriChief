// import connection
import db from "../config/database.js";

export const getMealPrefInfo = (data, result) => {
    if (data.user_id) {
        db.query("SELECT * FROM mealpref WHERE user_id = ?", [data.user_id], (err, results) => {
            if (err) {
                console.log(err);
                result({ "status": 0, "message": "Can not get user's meal pref", "data": [] });
            } else {
                if (results[0]) {
                    result({ "status": 1, "message": "Successfully get user's meal pref", "data": [results[0]] });
                } else {
                    result({ "status": 0, "message": "Can not get user's meal pref", "data": [] });
                }
            }
        });
    } else {
        console.log(err);
        result({ "status": 0, "message": "Can not get user's meal pref", "data": [] });
    }
}

export const createMealPrefInfo = (data, result) => {
    if (data.user_id) {
        db.query("INSERT INTO user SET user_id = ?, pref_calo = ?, pref_time = ?, pref_goal = ?, pref_date_range = ?", [data.user_id, data.pref_calo, data.pref_time, pref_goal, pref_date_range], (err, results) => {
            if (err) {
                console.log(err);
                result({ "status": 0, "message": "Can not create user's meal pref", "data": [] });
            } else {
                if (results[0]) {
                    result({ "status": 1, "message": "Successfully create user's meal pref", "data": [results[0]] });
                } else {
                    result({ "status": 0, "message": "Can not create user's meal pref", "data": [] });
                }
            }
        });
    } else {
        console.log(err);
        result({ "status": 0, "message": "Can not create user's meal pref", "data": [] });
    }
}

export const updateMealPrefInfo = (data, result) => { 
    if (data.user_id) {

        
        db.query("INSERT INTO user SET user_id = ?, pref_calo = ?, pref_time = ?, pref_goal = ?, pref_date_range = ?", [data.user_id, data.pref_calo, data.pref_time, pref_goal, pref_date_range], (err, results) => {
            if (err) {
                console.log(err);
                result({ "status": 0, "message": "Can not update user's meal pref", "data": [] });
            } else {
                if (results[0]) {
                    result({ "status": 1, "message": "Successfully update user's meal pref", "data": [results[0]] });
                } else {
                    result({ "status": 0, "message": "Can not update user's meal pref", "data": [] });
                }
            }
        });
    } else {
        console.log(err);
        result({ "status": 0, "message": "Can not update user's meal pref", "data": [] });
    }
}