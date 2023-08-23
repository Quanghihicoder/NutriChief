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
router.post("/apis/otp/create", createOtp);

// verify otp user
router.post("/apis/otp/verify", verifyOtp);


////////////////////////// USER ////////////////////////////////
// get user by id
router.post("/apis/user/get", getUser);

// update user
router.post("/apis/user/update", updateUser);

// export default router
export default router;