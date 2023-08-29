import { getAllIngres, getAllFoods } from "../models/IngreModel.js";

// get all foods
export const getIngres = (req, res) => {
    getAllIngres((err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

export const getAllFoodsByIngre = (req, res) => {
    const data = req.body;
    getAllFoods(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};
