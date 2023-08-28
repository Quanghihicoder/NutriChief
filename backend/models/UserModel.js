// import connection
import db from "../config/database.js";

export const getUserInfo = (data, result) => {
    if (data.user_id) {
        db.query(
            "SELECT * FROM user WHERE user_id = ?",
            [data.user_id],
            (err, results) => {
                if (err) {
                    console.log(err);
                    result({
                        status: 0,
                        message: "Can not get user",
                        data: [],
                    });
                } else {
                    if (results[0]) {
                        result({
                            status: 1,
                            message: "Successfully get user",
                            data: [results[0]],
                        });
                    } else {
                        result({
                            status: 0,
                            message: "Can not get user",
                            data: [],
                        });
                    }
                }
            }
        );
    } else if (data.user_email) {
        db.query(
            "SELECT * FROM user WHERE user_email = ?",
            [data.user_email],
            (err, results) => {
                if (err) {
                    console.log(err);
                    result({
                        status: 0,
                        message: "Can not get user",
                        data: [],
                    });
                } else {
                    if (results[0]) {
                        result({
                            status: 1,
                            message: "Successfully get user",
                            data: [results[0]],
                        });
                    } else {
                        result({
                            status: 0,
                            message: "Can not get user",
                            data: [],
                        });
                    }
                }
            }
        );
    } else {
        console.log(err);
        result({ status: 0, message: "Can not get user", data: [] });
    }
};

export const updateUserInfo = (data, result) => {
    const bmi =
        data.user_weight /
        (((data.user_height / 100) * data.user_height) / 100);

    const currentYear = new Date().getFullYear();

    var tdee = 0;

    if (data.user_gender == 0) {
        // female
        tdee =
            10 * data.user_weight +
            6.25 * data.user_height -
            5 * (currentYear - data.user_year_of_birth) -
            161;
    } else if (data.user_gender == 1) {
        // male
        tdee =
            10 * data.user_weight +
            6.25 * data.user_height -
            5 * (currentYear - data.user_year_of_birth) +
            5;
    }

    if (data.user_activity_level == 1) {
        tdee = tdee * 1.2;
    } else if (data.user_activity_level == 2) {
        tdee = tdee * 1.375;
    } else if (data.user_activity_level == 3) {
        tdee = tdee * 1.55;
    } else if (data.user_activity_level == 4) {
        tdee = tdee * 1.725;
    } else if (data.user_activity_level == 5) {
        tdee = tdee * 1.9;
    }

    db.query(
        "UPDATE user SET user_name = ?, user_year_of_birth = ?, user_gender = ?, user_weight = ?, user_height = ?, user_activity_level = ?, user_bmi = ?, user_tdee = ?  WHERE user_id = ?",
        [
            data.user_name,
            data.user_year_of_birth,
            data.user_gender,
            data.user_weight,
            data.user_height,
            data.user_activity_level,
            bmi,
            tdee,
            data.user_id,
        ],
        (err, results) => {
            if (err) {
                console.log(err);
                result({ status: 0, message: "Can not update user", data: [] });
            } else {
                result({
                    status: 1,
                    message: "Successfully update user",
                    data: [],
                });
            }
        }
    );
};
