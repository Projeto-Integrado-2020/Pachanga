//Install express server
const express = require('express');
const path = require('path');
const enforce = require('express-sslify');

const app = express();

// Serve only the static files form the dist directory
app.use(express.static(__dirname + '/dist/front-end'));

// Middleware para forçar uso do SSL (HTTPS)
app.use(enforce.HTTPS({ trustProtoHeader: true }))

app.get('/*', function(req,res) {
  res.sendFile(path.join(__dirname+'/dist/front-end/index.html'));
});

// Start the app by listening on the default Heroku port
app.listen(process.env.PORT || 8080);