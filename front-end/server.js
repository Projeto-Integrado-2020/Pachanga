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
  'default-src': csp.SRC_ANY,
  'style-src': [  csp.SRC_SELF,
                  csp.SRC_USAFE_INLINE,
                  'https://fonts.googleapis.com/'
                ],
  'script-src': [ csp.SRC_SELF,
                  csp.SRC_USAFE_INLINE,
                  csp.SRC_UNSAFE_EVAL,
                  'https://fonts.googleapis.com/',
                  'http://apis.google.com/',
                  'http://connect.facebook.net/',
                ],
  'connect-src': csp.SRC_ANY,
  'img-src': [csp.SRC_DATA, csp.SRC_ANY],
  'font-src': csp.SRC_ANY,
  'child-src': csp.SRC_ANY,
  'form-action': csp.SRC_ANY,
  'frame-ancestors': csp.SRC_ANY
};


const globalCSP = csp.getCSP(cspPolicy);

const globalSTS = sts.getSTS({'max-age': 31536000, 'includeSubDomains': true});
 
// This will apply this policy to all requests
app.use(referrerPolicy({ policy: 'same-origin' }));
app.use(globalSTS);
app.use(globalCSP);
app.use(helmet.noSniff());
app.use(helmet.frameguard());
app.use(featurePolicy({
  features: {
    fullscreen: ["'self'"],
    payment: ['https://pachanga.herokuapp.com/'],
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