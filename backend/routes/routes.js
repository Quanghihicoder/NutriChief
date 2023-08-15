// import express 
import express from "express";

import {
    createOtp, verifyOtp
} from "../controllers/user.js";

// init express router
const router = express.Router();

////////////////////////// USER ////////////////////////////////
// update otp user
router.post("/apis/users/otp", createOtp);

// verify otp user
router.get("/apis/users/otp", verifyOtp);

// export default router
export default router;