// import express 
import express from "express";

import {
    createOtp, verifyOtp
} from "../controllers/otp.js";

import {
    getUserByID,
    getUserByEmail,
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
router.get("/apis/user/id", getUserByID);

// get user by email
router.get("/apis/user/email", getUserByEmail);

// update user
router.put("/apis/user/", updateUser);

// export default router
export default router;