// import connection
import db from "../config/database.js";

export const getMealPrefInfo = (data, result) => {
    if (data.user_id) {
        db.query(
            "SELECT * FROM mealpref WHERE user_id = ?",
            [data.user_id],
            (err, results) => {
                if (err) {
                    console.log(err);
                    result({
                        status: 0,
                        message: "Can not get user's meal pref",
                        data: [],
                    });
                } else {
                    if (results[0]) {
                        result({
                            status: 1,
                            message: "Successfully get user's meal pref",
                            data: results,
                        });
                    } else {
                        result({
                            status: 0,
                            message: "Can not get user's meal pref",
                            data: [],
                        });
                    }
                }
            }
        );
    } else {
        console.log(err);
        result({
            status: 0,
            message: "Can not get user's meal pref",
            data: [],
        });
    }
};

export const createMealPrefInfo = (data, result) => {
    if (data.user_id) {
        db.query(
            "INSERT INTO mealpref SET user_id = ?, pref_calo = ?, pref_time = ?, pref_goal = ?, pref_date_range = ?",
            [
                data.user_id,
                data.pref_calo,
                data.pref_time,
                data.pref_goal,
                data.pref_date_range,
            ],
            (err, results) => {
                if (err) {
                    console.log(err);
                    result({
                        status: 0,
                        message: "Can not create user's meal pref",
                        data: [],
                    });
                } else {
                    result({
                        status: 1,
                        message: "Successfully create user's meal pref",
                        data: [],
                    });
                }
            }
        );
    } else {
        console.log(err);
        result({
            status: 0,
            message: "Can not create user's meal pref",
            data: [],
        });
    }
};

export const updateMealPrefInfo = (data, result) => {
    if (data.user_id) {
        var query = "UPDATE mealpref SET";
        var queryData = [];

        if (data.pref_calo) {
            query += " pref_calo = ?,";
            queryData.push(data.pref_calo);
        }

        if (data.pref_time) {
            query += " pref_time = ?,";
            queryData.push(data.pref_time);
        }

        if (data.pref_goal >= 0) {
            query += " pref_goal = ?,";
            queryData.push(data.pref_goal);
        }

        if (data.pref_date_range) {
            query += " pref_date_range = ?,";
            queryData.push(data.pref_date_range);
        }

        query = query.slice(0, -1);
        query += " WHERE user_id = ?";
        queryData.push(data.user_id);

        db.query(query, queryData, (err, results) => {
            if (err) {
                console.log(err);
                result({
                    status: 0,
                    message: "Can not update user's meal pref",
                    data: [],
                });
            } else {
                result({
                    status: 1,
                    message: "Successfully update user's meal pref",
                    data: [],
                });
            }
        });
    } else {
        console.log(err);
        result({
            status: 0,
            message: "Can not update user's meal pref",
            data: [],
        });
    }
};
