import {
    createUserOtp, verifyUserOtp
} from "../models/OTPModel.js";

// create user otp
export const createOtp = (req, res) => {
    const data = req.body;
    createUserOtp(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};


// create user otp
export const verifyOtp = (req, res) => {
    const data = req.body;
    verifyUserOtp(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};