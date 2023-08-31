// import connection
import db from "../config/database.js";

export const getUserReactByPost = (data, result) => {
    if (data.user_id && data.post_id) {
        db.query(
            "SELECT * FROM react WHERE user_id = ? AND post_id = ?",
            [data.user_id, data.post_id],
            (err, results) => {
                if (results[0]) {
                    result({
                        status: 1,
                        message: "Successfully get react",
                        data: [{ react_type: results[0].react_type }],
                    });
                } else {
                    result({
                        status: 1,
                        message: "Did not react",
                        data: [{ react_type: null }],
                    });
                }
            }
        );
    } else {
        result({
            status: 0,
            message: "Can not get react",
            data: [],
        });
    }
}

export const updateReactByUser = (data, result) => {
    db.query(
        "DELETE FROM react WHERE user_id = ? AND post_id = ?",
        [data.user_id, data.post_id],
        (err, results) => {

        }
    );

    if (data.user_id && data.post_id && data.react_type >= 0) {
        db.query(
            "INSERT INTO react SET user_id = ?, post_id = ?, react_type = ?",
            [data.user_id, data.post_id, data.react_type],
            (err, results) => {
                if (err) {
                    console.log(err);
                    result({
                        status: 0,
                        message: "Can not react",
                        data: [],
                    });
                } else {
                    result({
                        status: 1,
                        message: "Successfully react",
                        data: [],
                    });
                }
            }
        );
    }

    result({
        status: 1,
        message: "Successfully react",
        data: [],
    });
};
