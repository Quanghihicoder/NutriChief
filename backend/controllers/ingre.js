import { getAllIngres, getAllIngresFromRecipe } from "../models/IngreModel.js";

// get all foods
export const getIngres = (req, res) => {
    const data = req.body;
    getAllIngres(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};

export const getIngresFromRecipe = (req, res) => {
    const data = req.body;
    getAllIngresFromRecipe(data, (err, results) => {
        if (err) {
            res.send(err);
        } else {
            res.json(results);
        }
    });
};