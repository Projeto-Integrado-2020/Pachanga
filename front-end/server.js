//Install express server
const express = require('express');
const path = require('path');
const sslRedirect = require('heroku-ssl-redirect');

const app = express();

// Serve only the static files form the dist directory
app.use(express.static(__dirname + '/dist/front-end'));

app.use(sslRedirect());

app.get('/*', function(req,res) {
    res.sendFile(path.join(__dirname+'/dist/front-end/index.html'));
});

app.get('/.well-known/acme-challenge/CytPHEoT_4qgVSJjraH0lpTP5KVjiROh4-eDyqKY4xQ', function(req, res) {
    res.send('CytPHEoT_4qgVSJjraH0lpTP5KVjiROh4-eDyqKY4xQ.z9Coz9YXNvl8QrQOr6k2A2tWbudvS5xiXHjd-tHQPIc');
});

// Start the app by listening on the default Heroku port
app.listen(process.env.PORT || 8080);