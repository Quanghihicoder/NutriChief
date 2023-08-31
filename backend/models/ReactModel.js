// import connection
import db from "../config/database.js";

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
