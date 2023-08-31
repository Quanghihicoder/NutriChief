// import connection
import db from "../config/database.js";

export const getAllPosts = (result) => {
    db.query("SELECT a.post_id, a.user_id, a.post_title, a.post_detail, a.post_dislike, b.post_like FROM ((SELECT post.post_id, post.user_id, post.post_title, post.post_detail, IFNULL(d.d,0) AS post_dislike FROM post LEFT JOIN (SELECT post_id ,COUNT(user_id) AS d FROM react WHERE react_type = 0 GROUP BY post_id) as d ON post.post_id = d.post_id)) AS a LEFT JOIN ((SELECT post.post_id, IFNULL(l.l,0) AS post_like FROM post LEFT JOIN (SELECT post_id ,COUNT(user_id) AS l FROM react WHERE react_type = 1 GROUP BY post_id) as l ON post.post_id = l.post_id)) AS b ON a.post_id = b.post_id ORDER BY post_id DESC", [], (err, results) => {
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