import {
    getAllFoods,
    getDetails,
    getFoodDetailById,
    getAllRecipeDetail,
} from "../models/FoodModel.js";

// get all foods
export const getFoods = (req, res) => {
    getAllFoods((err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

export const getFoodDetail = (req, res) => {
    const data = req.body;
    getDetails(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

export const getFoodById = (req, res) => {
    const data = req.params.food_id;
    getFoodDetailById(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

export const getRecipeDetail = (req, res) => {
    const data = req.body;
    getAllRecipeDetail(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};
