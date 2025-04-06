// routes/auth.js
const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');
const passport = require('passport');
const prisma = require('../db'); // Import your Prisma client
const ensureAuthenticated = require('../middleware/auth'); // Import authentication middleware

// API Signup Route
router.post('/signup', async (req, res) => {
  try {
    const { email, password ,username,isTherapistFriend} = req.body;

    // Check if user already exists
    const existingUser = await prisma.user.findUnique({
      where: { email: email },
    });

    if (existingUser) {
      return res.status(400).json({ message: 'Email already registered.' });
    }

    // Hash the password
    const hashedPassword = await bcrypt.hash(password, 10);

    // Create the new user
    const newUser = await prisma.user.create({
      data: {
        email: email,
        password: hashedPassword,
        username:username,
        isTherapistFriend:isTherapistFriend
      },
    });

    req.logIn(newUser, (err) => {
        if (err) {
          return res.status(500).json({ message: 'Internal server error during login.' });
        }
        return res.status(201).json({user:newUser, message: 'User registered and logged in successfully!' });
      });
    } catch (error) {
      console.error('Signup error:', error);
      res.status(500).json({ error,
        message: 'Internal server error.' });
    }
  });

// API Login Route
router.post('/login', (req, res, next) => {
  passport.authenticate('local', (err, user, info) => {
    if (err) {
      return res.status(500).json({ message: 'Internal server error.' });
    }
    if (!user) {
      return res.status(401).json({ message: info.message || 'Login failed.' });
    }

    req.logIn(user, (err) => {
      if (err) {
        return res.status(500).json({ message: 'Internal server error.' });
      }
      return res.status(200).json({user:user, message: 'Login successful!' });
    });
  })(req, res, next);
});

router.get('/userInfo', ensureAuthenticated, async (req, res) => {
  try {
    if (req.user) {
      const userDetails = await prisma.user.findUnique({
        where: { id: req.user.id },
        select: {
          id: true,
          email: true,
          username: true,
          createdAt: true,
          
        },

      });
      if (userDetails) {
        return res.status(200).json(userDetails);
      } else {
        return res.status(404).json({ message: 'User not found.' });
      }
    } else {
    
      return res.status(401).json({ message: 'Not authenticated.' });
    }
  } catch (error) {
    console.error('Error fetching current user:', error);
    res.status(500).json({error, message: 'Internal server error.' });
  }
});


module.exports = router;