const express = require('express');

const app = express();

app.get('/apis', (req, res) => {
    res.set('Content-Type', 'text/html');
    res.status(200).send("<p>Welcome to NutriChief APIs</p>");
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
