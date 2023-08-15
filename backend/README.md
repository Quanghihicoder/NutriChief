-- Version

npm version: 8.1.2

node version 16.13.1

-- Run

npm install

npm start

-- Sử dụng APIs 

--- Register/Login using email and otp 

Ở frontend cho user nhập email xong call POST tới /apis/users/otp với body là user_email thì nó tự gửi otp tới email. 

Khi bấm nhập otp và bấm verify thì call GET tới /apis/users/otp với body là user_email, otp thì nó sẽ return tất cả thông tin của thằng user, nếu null user_name thì cho vào register, còn not null thì vô app.

Có cả resend otp cũng đc luôn nhé. 

