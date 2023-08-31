// import connection
import db from "../config/database.js";

export const getAllCommentsByPost = (data, result) => {
    if (data.post_id) {
        db.query(
            "SELECT * FROM comment WHERE post_id = ? ORDER BY comment_id DESC",
            [data.post_id],
            (err, results) => {
                if (err) {
                    console.log(err);
                    result({
                        status: 0,
                        message: "Can not get comments",
                        data: [],
                    });
                } else {
                    if (results[0]) {
                        result({
                            status: 1,
                            message: "Successfully get comments",
                            data: results,
                        });
                    } else {
                        result({
                            status: 0,
                            message: "Can not get comments",
                            data: [],
                        });
                    }
                }
            }
        );
    } else {
        result({ status: 0, message: "Can not get comments", data: [] });
    }
};

export const createCommentsByUser = (data, result) => {
    if (data.user_id && data.post_id && data.comment_detail) {
        db.query(
            "INSERT INTO comment SET user_id = ?, post_id = ?, comment_detail = ?",
            [data.user_id, data.post_id, data.comment_detail],
            (err, results) => {
                if (err) {
                    console.log(err);
                    result({
                        status: 0,
                        message: "Can not create comment",
                        data: [],
                    });
                } else {
                    result({
                        status: 1,
                        message: "Successfully create comment",
                        data: [],
                    });

                }
            }
        );
    } else {
        result({ status: 0, message: "Can not create comment", data: [] });
    }
};


