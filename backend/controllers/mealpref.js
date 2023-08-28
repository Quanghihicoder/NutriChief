import {
    getMealPrefInfo,
    createMealPrefInfo,
    updateMealPrefInfo,

} from "../models/MealPrefModel.js";

// get user meal pref
export const getMealPref = (req, res) => {
    const data = req.body;
    getMealPrefInfo(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

// create user meal pref
export const createMealPref = (req, res) => {
    const data = req.body;
    createMealPrefInfo(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

// update user meal pref
export const updateMealPref = (req, res) => {
    const data = req.body;
    updateMealPrefInfo(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};
