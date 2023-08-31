// import connection
import db from "../config/database.js";

export const getAllPosts = (result) => {
    db.query("SELECT * FROM post ORDER BY post_id DESC", [], (err, results) => {
        if (err) {
            console.log(err);
            result({ status: 0, message: "Can not get posts", data: [] });
        } else {
            if (results[0]) {
                result({
                    status: 1,
                    message: "Successfully get posts",
                    data: results,
                });
            } else {
                result({ status: 0, message: "Can not get posts", data: [] });
            }
        }
    });
};

export const createUserPost = (data, result) => {
    if (data.user_id && data.post_title && data.post_detail) {
        db.query(
            "INSERT INTO post SET user_id = ?, post_title = ?, post_detail = ?",
            [data.user_id, data.post_title, data.post_detail],
            (err, results) => {
                if (err) {
                    console.log(err);
                    result({
                        status: 0,
                        message: "Can not create post",
                        data: [],
                    });
                } else {
                    result({
                        status: 1,
                        message: "Successfully create post",
                        data: [],
                    });
                }
            }
        );
    } else {
        result({ status: 0, message: "Can not create post", data: [] });
    }
};

// SELECT COUNT(*) FROM react WHERE post_id = 1 GROUP BY react_type;