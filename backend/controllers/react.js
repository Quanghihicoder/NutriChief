import {
    updateReactByUser
} from "../models/ReactModel.js";

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

