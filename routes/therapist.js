
// const express = require('express');
// const router = express.Router();
// const bcrypt = require('bcryptjs');
// const passport = require('passport');
// const prisma = require('../db');


// router.post('/signup', async (req, res) => {
//   try {
//     const { email, password} = req.body;

//     // Check if therapist already exists
//     const existingTherapist = await prisma.therapist.findUnique({
//       where: { email: email },
//     });

//     if (existingTherapist) {
//       return res.status(400).json({ message: 'Email already registered.' });
//     }

//     // Hash the password
//     const hashedPassword = await bcrypt.hash(password, 10);

//     // Create the new therapist
//     const newTherapist = await prisma.therapist.create({
//       data: {
//         email: email,
//         password: hashedPassword,
//       },
//     });

//     req.logIn(newTherapist, (err) => {
//         if (err) {
//           return res.status(500).json({ message: 'Internal server error during login.' });
//         }
//         return res.status(201).json({ message: 'Therapist registered and logged in successfully!' });
//       });
//     } catch (error) {
//       console.error('Signup error:', error);
//       res.status(500).json({ message: 'Internal server error.' });
//     }
// });

// // Therapist Login Route
// router.post('/login', (req, res, next) => {
//   passport.authenticate('therapistLocal', (err, therapist, info) => {
//     if (err) {
//       return res.status(500).json({ message: 'Internal server error.' });
//     }
//     if (!therapist) {
//       return res.status(401).json({ message: info.message || 'Login failed.' });
//     }

//     req.logIn(therapist, (err) => {
//       if (err) {
//         return res.status(500).json({ message: 'Internal server error.' });
//       }
//       return res.status(200).json({ message: 'Login successful!' });
//     });
//   })(req, res, next);
// });

// module.exports = router;