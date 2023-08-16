import {
    createOtpUser, verifyOtpUser
} from "../models/OTPModel.js";

// create otp user
export const createOtp = (req, res) => {
    const data = req.body;
    createOtpUser(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};


// create otp user
export const verifyOtp = (req, res) => {
    const data = req.body;
    verifyOtpUser(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};