// import connection
import db from "../config/database.js";

// import node mailer
import nodemailer from "nodemailer";

// create transporter
const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: 'hoangquangdeptrai2508@gmail.com',
        pass: 'gichuaugjzptkvtm'
    }
});

// send OTP
const sendOTP = (email, otp) => {
    var mailOptions = {
        from: 'hoangquangdeptrai2508@gmail.com',
        to: email,
        subject: 'NutriChief OTP',
        text: 'Here is your OTP code: ' + otp.toString()
    };

    transporter.sendMail(mailOptions, function (error, info) {
        if (error) {
            console.log(error);
        }
    });
}

// create user otp
export const createUserOtp = (data, result) => {
    const otp = Math.floor(100000 + Math.random() * 900000);

    // Check if otp exist 
    db.query("SELECT user_email FROM `otp` WHERE user_email = ?", [data.user_email], (err, results) => {
        if (err) {
            console.log(err);
            result({ "status": 0, "message": "Can not create OTP code", "data": [] });
        } else {
            if (results[0]) {
                // Update new otp if exist
                db.query("UPDATE otp SET otp_key = ? WHERE user_email = ?", [otp, data.user_email], (err, results) => {
                    if (err) {
                        console.log(err);
                        result({ "status": 0, "message": "Can not create OTP code", "data": [] });
                    } else {
                        result({ "status": 1, "message": "Successfully created OTP code", "data": [] });
                        sendOTP(data.user_email, otp)
                    }
                });
            }
            else {
                // Create new otp
                db.query("INSERT INTO otp SET user_email = ?, otp_key = ?", [data.user_email, otp], (err, results) => {
                    if (err) {
                        console.log(err);
                        result({ "status": 0, "message": "Can not create OTP code", "data": [] });
                    } else {
                        result({ "status": 1, "message": "Successfully created OTP code", "data": [] });
                        sendOTP(data.user_email, otp)
                    }
                });
            }
        }
    });
};


// verify user otp
export const verifyUserOtp = (data, result) => {
    // Check if otp correct 
    db.query("SELECT user_email FROM otp WHERE user_email = ? AND otp_key = ?", [data.user_email, data.otp_key], (err, results) => {
        if (err) {
            console.log(err);
            result({ "status": 0, "message": "Can not verify OTP code", "data": [] });
        } else {
            if (results[0]) {
                // Check if exist account
                db.query("SELECT * FROM user WHERE user_email = ?", [data.user_email], (err, results) => {
                    if (err) {
                        console.log(err);
                        result({ "status": 0, "message": "Can not verify OTP code", "data": [] });
                    } else {
                        if (results[0]) {
                            // Return existed user
                            result({ "status": 1, "message": "Successfully verified OTP code", "data": results });

                            // Delete OTP code
                            db.query("DELETE FROM otp WHERE user_email = ?", [data.user_email], (err, results) => {
                                if (err) {
                                    console.log(err);
                                }
                            });
                        }
                        else {
                            // Create new user
                            db.query("INSERT INTO user SET user_email = ?", [data.user_email], (err, results) => {
                                if (err) {
                                    console.log(err);
                                    result({ "status": 0, "message": "Can not verify OTP code", "data": [] });
                                } else {
                                    // Return created user
                                    db.query("SELECT * FROM user WHERE user_email = ?", [data.user_email], (err, results) => {
                                        if (err) {
                                            console.log(err);
                                            result({ "status": 0, "message": "Can not verify OTP code", "data": [] });
                                        } else {
                                            result({ "status": 1, "message": "Successfully verified OTP code", "data": results });

                                            // Delete OTP code
                                            db.query("DELETE FROM otp WHERE user_email = ?", [data.user_email], (err, results) => {
                                                if (err) {
                                                    console.log(err);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }
            else {
                result({ "status": 0, "message": "Incorrect OTP code", "data": [] });
            }
        }
    });
};
