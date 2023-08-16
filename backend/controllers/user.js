// import functions from User model

import {
    getUserInfo,
    updateUserInfo
} from "../models/UserModel.js";

// get user
export const getUser = (req, res) => {
    const data = req.body;
    getUserInfo(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

// update user
export const updateUser = (req, res) => {
    const data = req.body;
    updateUserInfo(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};