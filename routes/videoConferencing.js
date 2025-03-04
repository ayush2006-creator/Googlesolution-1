// const express = require('express');
// const { AccessToken } = require('livekit-server-sdk');
// const ensureAuthenticated = require('../middleware/auth');
// const router = express.Router();

// const LIVEKIT_API_KEY = env('LIVEKIT_API_KEY');
// const LIVEKIT_API_SECRET = env('LIVEKIT_API_SECRET');
// const LIVEKIT_HOST = env('LIVEKIT_HOST');


// router.post('/getLiveKitToken',ensureAuthenticated, (req, res) => {
//     const { roomName, identity } = req.body;
  
//     if (!roomName || !identity) {
//       return res.status(400).send('Room name and identity are required.');
//     }
  
//     const at = new AccessToken(LIVEKIT_API_KEY, LIVEKIT_API_SECRET, {
//       identity: identity,
//     });
  
//     at.addGrant({ roomJoin: true, room: roomName });
  
//     const token = at.toJwt();
//     res.json({ token, host: LIVEKIT_HOST });
//   });