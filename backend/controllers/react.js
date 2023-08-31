import {
    getUserReactByPost,
    updateReactByUser
} from "../models/ReactModel.js";

export const getReact = (req, res) => {
    const data = req.body;
    getUserReactByPost(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

export const updateReact = (req, res) => {
    const data = req.body;
    updateReactByUser(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

