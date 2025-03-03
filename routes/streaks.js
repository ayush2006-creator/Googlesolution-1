// routes/streaks.js
const express = require('express');
const router = express.Router();
const prisma = require('../db');
const ensureAuthenticated = require('../middleware/auth');

// Check-in for a Streak
// Check-in for a Streak
router.post('/checkin', ensureAuthenticated, async (req, res) => {
    try {
      const userId = req.user.id;
      const now = new Date();
  
      // Find the streak for the user
      let streak = await prisma.streak.findUnique({
        where: { userId: userId },
      });
  
      // Check if the last check-in was within the last 24 hours
      if (streak && streak.lastCheckin) {
        const lastCheckin = streak.lastCheckin;
        const timeDifference = now.getTime() - lastCheckin.getTime();
        const hoursDifference = timeDifference / (1000 * 60 * 60);
  
        if (hoursDifference < 24) {
          return res.status(400).json({ message: 'You can only check in once every 24 hours.' });
        }
  
        // Check if the time difference is more than 24 hours
        if (hoursDifference >= 24) {
          streak = await prisma.streak.update({
            where: { id: streak.id },
            data: {
              currentStreak: 0, // Reset the current streak
              lastCheckin: now,
            },
          });
          return res.status(200).json({ message: 'Streak reset. Check-in successful!', currentStreak: 1, longestStreak: streak.longestStreak });
        }
      }
  
      // Create the streak if it doesn't exist or update it
      if (!streak) {
        streak = await prisma.streak.create({
          data: {
            userId: userId,
            lastCheckin: now,
          },
        });
      } 
        // Update the streak
        let currentStreak = streak.currentStreak;
        let longestStreak = streak.longestStreak;
  
        currentStreak++;
  
        if (currentStreak > longestStreak) {
          longestStreak = currentStreak;
        }
  
        streak = await prisma.streak.update({
          where: { id: streak.id },
          data: {
            currentStreak: currentStreak,
            longestStreak: longestStreak,
            lastCheckin: now,
          },
        });
      
  
      res.status(200).json({ message: 'Check-in successful!', currentStreak: streak.currentStreak, longestStreak: streak.longestStreak });
    } catch (error) {
      console.error('Check-in error:', error);
      res.status(500).json({ message: 'Internal server error.' });
    }
  });
  
  

router.get('/data',ensureAuthenticated,async(req,res)=>{
    try{
        const userId = req.user.id;
        const streak = await prisma.streak.findUnique({
            where:{userId:userId}
        });

        if (!streak) {
            return res.status(404).json({ message: 'Streak not found for this user.' });
          }
        res.status(200).json(streak);
    }
    catch (error) {
        console.error('Could not retrieve data:', error);
        res.status(500).json({ message: 'Internal server error.' });
      }
})

module.exports = router;