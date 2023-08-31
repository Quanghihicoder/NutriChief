import {
    getAllCommentsByPost,
    createCommentsByUser
} from "../models/CommentModel.js";

export const getComments = (req, res) => {
    const data = req.body;
    getAllCommentsByPost(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

export const createComment = (req, res) => {
    const data = req.body;
    createCommentsByUser(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};
