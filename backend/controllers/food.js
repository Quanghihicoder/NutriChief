import { getAllFoodsAndDetails } from "../models/FoodModel.js";

// get all foods
export const getAllFoods = (req, res) => {
    const data = req.body;
    getAllFoodsAndDetails(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};
