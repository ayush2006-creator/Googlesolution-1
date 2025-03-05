
const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');
const passport = require('passport');
const prisma = require('../db');


router.post('/signup', async (req, res) => {
  try {
    const { email, password} = req.body;

    // Check if therapist already exists
    const existingTherapist = await prisma.therapist.findUnique({
      where: { email: email },
    });

    if (existingTherapist) {
      return res.status(400).json({ message: 'Email already registered.' });
    }

    // Hash the password
    const hashedPassword = await bcrypt.hash(password, 10);

    // Create the new therapist
    const newTherapist = await prisma.therapist.create({
      data: {
        email: email,
        password: hashedPassword,
      },
    });

    req.logIn(newTherapist, (err) => {
        if (err) {
          return res.status(500).json({ message: 'Internal server error during login.' });
        }
        return res.status(201).json({ message: 'Therapist registered and logged in successfully!' });
      });
    } catch (error) {
      console.error('Signup error:', error);
      res.status(500).json({ message: 'Internal server error.' });
    }
});

// Therapist Login Route
router.post('/login', (req, res, next) => {
  passport.authenticate('therapistLocal', (err, therapist, info) => {
    if (err) {
      return res.status(500).json({ message: 'Internal server error.' });
    }
    if (!therapist) {
      return res.status(401).json({ message: info.message || 'Login failed.' });
    }

    req.logIn(therapist, (err) => {
      if (err) {
        return res.status(500).json({ message: 'Internal server error.' });
      }
      return res.status(200).json({ message: 'Login successful!' });
    });
  })(req, res, next);
});

router.post('/sendRequest', ensureAuthenticated, async (req, res) => {
    try {
      const therapistId = req.user.id;
      const { userEmail } = req.body;
  
      const user = await prisma.user.findUnique({ where: { email: userEmail } });
      if (!user) {
        return res.status(404).json({ message: 'User not found.' });
      }
  
      // Check if therapist already has a user
      const existingTherapistUser = await prisma.therapist.findUnique({ where: { id: therapistId }, select: { userId: true } });
      if (existingTherapistUser.userId) {
        return res.status(400).json({ message: 'Therapist already has an associated user.' });
      }
  
      // Check if user already has a therapist
      if (user.therapistId) {
        return res.status(400).json({ message: 'User already has an associated therapist.' });
      }
  
      const existingRequest = await prisma.therapistRequest.findFirst({
          where:{
              therapistId: therapistId,
              userId: user.id
          }
      });
  
      if(existingRequest){
          return res.status(400).json({message: "Request already sent to this user"});
      }
  
      const request = await prisma.therapistRequest.create({
        data: { therapistId, userId: user.id },
      });
  
    //   // Send email notification (Nodemailer)
    //   const transporter = nodemailer.createTransport({
    //     service: 'gmail',
    //     auth: {
    //       user: process.env.EMAIL_USER,
    //       pass: process.env.EMAIL_PASS,
    //     },
    //   });
  
    //   const mailOptions = {
    //     from: process.env.EMAIL_USER,
    //     to: user.email,
    //     subject: 'Therapist Request',
    //     html: `<p>You have a therapist request from ${req.user.email}.</p>
    //            <a href="${process.env.FRONTEND_URL}/accept-request/${request.id}">Accept</a>
    //            <a href="${process.env.FRONTEND_URL}/reject-request/${request.id}">Reject</a>`,
    //   };
  
    //   transporter.sendMail(mailOptions, (error, info) => {
    //     if (error) {
    //       console.error('Email error:', error);
    //     } else {
    //       console.log('Email sent:', info.response);
    //     }
    //   });
  
      res.status(201).json({ message: 'Therapist request sent.', request });
    } catch (error) {
      console.error('Send therapist request error:', error);
      res.status(500).json({ message: 'Internal server error.' });
    }
  })

// Accept Therapist Request
router.put('/accept/:requestId', ensureAuthenticated, async (req, res) => {
    try {
      const userId = req.user.id;
      const requestId = req.params.requestId;
  
      const request = await prisma.therapistRequest.findUnique({
        where: { id: requestId },
        include: { therapist: true, user: true },
      });
  
      if (!request) {
        return res.status(404).json({ message: 'Request not found.' });
      }
  
      if (request.userId !== userId) {
        return res.status(403).json({ message: 'You are not authorized to accept this request.' });
      }
  
      // Check if therapist already has a user
      if (request.therapist.userId) {
        return res.status(400).json({ message: 'Therapist already has an associated user.' });
      }
  
      // Check if user already has a therapist
      if (request.user.therapistId) {
        return res.status(400).json({ message: 'User already has an associated therapist.' });
      }
  
      await prisma.$transaction([
        prisma.therapistRequest.update({
          where: { id: requestId },
          data: { status: 'ACCEPTED' },
        }),
        prisma.user.update({
          where: { id: userId },
          data: { therapistId: request.therapistId },
        }),
        prisma.therapist.update({
          where: { id: request.therapistId },
          data: { userId: userId },
        }),
      ]);
  
      res.status(200).json({ message: 'Therapist request accepted.' });
    } catch (error) {
      console.error('Accept therapist request error:', error);
      res.status(500).json({ message: 'Internal server error.' });
    }
  });


module.exports = router;