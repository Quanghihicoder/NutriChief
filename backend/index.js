// using nodemon so that you do not need to type node index.js every time new code saved

// import express - is for building the Rest apis
import express from 'express';

// import body-parser - helps to parse the request and create the req.body object
import bodyParser from "body-parser";

// import cors - provides Express middleware to enable CORS with various options, connect frontend
import cors from "cors";

// import routes
import router from "./routes/routes.js";

// init express
const app = express();

// use express json
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

//use cors
app.use(cors());

// use router
app.use(router);

app.get('/apis', (req, res) => {
    res.set('Content-Type', 'text/html');
    res.status(200).send("<p>Welcome to NutriChief APIs</p>");
});

app.use(function (req, res) {
    res.status(404);

    if (req.accepts('html')) {
        res.set('Content-Type', 'text/html');
        res.status(404).send(`
            <!doctype html>
            <html lang="en">
            <head>
                <meta charset="utf-8">
                <title>NutriChief</title>
                <meta name="description" content="Description Goes Here">
            </head>
            <body>
                <p>Not Found! Please check your url.</p>
            </body>
            </html>
        `);
        return;
    }

    // respond with json
    if (req.accepts('json')) {
        res.json({ status: 0, message: "API not found!", data: [] });
        return;
    }

});

const PORT = process.env.PORT || 8001;
app.listen(PORT, (error) => {
    if (!error) {
        console.log(`Server is running on port ${PORT}.`);
        console.log(`Open localhost:${PORT}/apis`);
    }
    else
        console.log("Error occurred, server can't start", error);
});
