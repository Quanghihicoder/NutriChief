// import express 
import express from "express";

import {
    createOtp, verifyOtp
} from "../controllers/otp.js";

import {
    getUser,
    updateUser
} from "../controllers/user.js";

// init express router
const router = express.Router();

////////////////////////// OTP ////////////////////////////////
// update otp user
router.post("/apis/otp/", createOtp);

// verify otp user
router.get("/apis/otp/", verifyOtp);


////////////////////////// USER ////////////////////////////////
// get user by id
router.get("/apis/user/", getUser);

// update user
router.put("/apis/user/", updateUser);

// export default router
export default router;