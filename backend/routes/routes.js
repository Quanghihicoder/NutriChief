// import express
import express from "express";

import { createOtp, verifyOtp } from "../controllers/otp.js";

import { getUser, updateUser } from "../controllers/user.js";

import {
    getMealPref,
    createMealPref,
    updateMealPref,
} from "../controllers/mealpref.js";

import {
    getFoods,
    getFoodDetail,
    getFoodById,
    getRecipeDetail,
} from "../controllers/food.js";

import { getIngres, getAllFoodsByIngre } from "../controllers/ingre.js";

import { getMeal, createMeal, updateMeal } from "../controllers/meal.js";

import { webViewFood } from "../controllers/webview.js";

import { getPosts, createPost } from "../controllers/post.js";

import { getComments, createComment } from "../controllers/comment.js";

import { getReact, updateReact } from "../controllers/react.js";

// init express router
const router = express.Router();

////////////////////////// OTP ////////////////////////////////
// update otp user
router.post("/apis/otp/create", createOtp);

// verify otp user
router.post("/apis/otp/verify", verifyOtp);

////////////////////////// USER ////////////////////////////////
// get user by id
router.post("/apis/user/get", getUser);

// update user
router.post("/apis/user/update", updateUser);

//////////////////////// MEAL PREF //////////////////////////////
// get meal pref by user id
router.post("/apis/mealpref/get", getMealPref);

// create meal pref
router.post("/apis/mealpref/create", createMealPref);

// update meal pref
router.post("/apis/mealpref/update", updateMealPref);

////////////////////////// FOOD ////////////////////////////////
// get foods
router.post("/apis/food", getFoods);

//get food by id
router.get("/apis/food/:food_id", getFoodById);

// get foods with basic info
router.post("/apis/food/detail", getFoodDetail);

// get foods with recipe detail
router.post("/apis/food/recipe/detail", getRecipeDetail);

////////////////////////// INGRE ////////////////////////////////
// get ingres
router.post("/apis/ingre", getIngres);

// get ingres
router.post("/apis/ingre/foods", getAllFoodsByIngre);

////////////////////////// MEAL ////////////////////////////////
// get meals
router.post("/apis/meal/get", getMeal);

// create meals
router.post("/apis/meal/create", createMeal);

// update meals
router.post("/apis/meal/update", updateMeal);

////////////////////////// WEB VIEW ////////////////////////////////
// return html
router.get("/food/:food_id", webViewFood);

////////////////////////// POST ////////////////////////////////
// get posts
router.post("/apis/post/get", getPosts)

// create posts
router.post("/apis/post/create", createPost)

////////////////////////// COMMENT ////////////////////////////////
// get comments
router.post("/apis/comment/get", getComments)

// create comment
router.post("/apis/comment/create", createComment)

////////////////////////// REACT ////////////////////////////////
// create react
router.post("/apis/react/get", getReact)

// create react
router.post("/apis/react/create", updateReact)

// export default router
export default router;
