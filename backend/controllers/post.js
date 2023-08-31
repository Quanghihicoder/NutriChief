import {
    getAllPosts,
    createUserPost
} from "../models/PostModel.js";

export const getPosts = (req, res) => {
    getAllPosts((err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

export const createPost = (req, res) => {
    const data = req.body;
    createUserPost(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

