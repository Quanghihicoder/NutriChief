// import functions from User model

import {
    getUserInfoByID,
    getUserInfoByEmail,
    updateUserInfo
} from "../models/UserModel.js";

// update user
export const getUserByID = (req, res) => {
    const data = req.body;
    getUserInfoByID(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

// update user
export const getUserByEmail = (req, res) => {
    const data = req.body;
    getUserInfoByEmail(data, (err, results) => {
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