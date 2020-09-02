//Install express server
const express = require('express');
const path = require('path');
const compression = require('compression');
const sts = require('strict-transport-security');
const csp = require('content-security-policy');
const helmet = require('helmet');
const featurePolicy = require('feature-policy');
const referrerPolicy = require('referrer-policy');
const app = express();

const cspPolicy = {
  'report-uri': '/reporting',
  'default-src': csp.SRC_NONE,
  'script-src': [ csp.SRC_SELF, csp.SRC_DATA ]
};


const globalCSP = csp.getCSP(csp.STARTER_OPTIONS);

const globalSTS = sts.getSTS({'max-age': 31536000, 'includeSubDomains': true});
 
// This will apply this policy to all requests
app.use(referrerPolicy({ policy: 'same-origin' }));
app.use(globalSTS);
app.use(globalCSP);
app.use(helmet.frameguard())
app.use(featurePolicy({
  features: {
    fullscreen: ["'self'"],
    vibrate: ["'none'"],
    payment: ['example.com'],
    syncXhr: ["'none'"]
  }
}));

// Serve only the static files form the dist directory
app.use(express.static(__dirname + '/dist/front-end'));

// Enable text compression - PWA requisit
app.use(compression({ filter: shouldCompress }))

function shouldCompress (req, res) {
  if (req.headers['x-no-compression']) {
    // don't compress responses with this request header
    return false
  }

  // fallback to standard filter function
  return compression.filter(req, res)
}


app.get('/*', function(req,res) { 
    res.sendFile(path.join(__dirname+'/dist/front-end/index.html'));
});

// Start the app by listening on the default Heroku port
app.listen(process.env.PORT || 8080);