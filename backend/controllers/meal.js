import {
    getAllMeals, getDateMeal, createUserMeal
} from "../models/MealModel.js";

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