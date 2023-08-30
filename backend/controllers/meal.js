import {
    getDateMeal, updateDateMeal, createUserMeal
} from "../models/MealModel.js";

// get user meal
export const getMeal = (req, res) => {
    const data = req.body;
    getDateMeal(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

// create user meal
export const updateMeal = (req, res) => {
    const data = req.body;
    updateDateMeal(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

// create user meal
export const createMeal = (req, res) => {
    const data = req.body;
    createUserMeal(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};