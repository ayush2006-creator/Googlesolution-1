// routes/streaks.js
const express = require('express');
const router = express.Router();
const prisma = require('../db');
const ensureAuthenticated = require('../middleware/auth'); // Import if you want auth

// Get Leaderboard (based on current streak)
router.get('/global/current',ensureAuthenticated, async (req, res) => {
  try {
    const leaderboard = await prisma.streak.findMany({
      orderBy: {
        currentStreak: 'desc', // Sort by current streak in descending order
      },
      include: {
        user: {
          select: {
            id: true,
            email: true,
            // Add other user fields you want to include
          },
        },
      }// Limit to top 10 (adjust as needed)
    });

    // Format the leaderboard data
    const formattedLeaderboard = leaderboard.map((streak) => ({
      userId: streak.user.id,
      email: streak.user.email,
      currentStreak: streak.currentStreak,
      // Add other relevant data
    }));

    res.status(200).json(formattedLeaderboard);
  } catch (error) {
    console.error('Get leaderboard error:', error);
    res.status(500).json({ message: 'Internal server error.' });
  }
});

// Get Leaderboard (based on current streak)
router.get('/global/longest',ensureAuthenticated, async (req, res) => {
    try {
      const leaderboard = await prisma.streak.findMany({
        orderBy: {
          longestStreak: 'desc', // Sort by current streak in descending order
        },
        include: {
          user: {
            select: {
              id: true,
              email: true,
              // Add other user fields you want to include
            },
          },
        }
         // Limit to top 10 (adjust as needed)
      });
  
      // Format the leaderboard data
      const formattedLeaderboard = leaderboard.map((streak) => ({
        userId: streak.user.id,
        email: streak.user.email,
        longestStreak: streak.longestStreak,
        // Add other relevant data
      }));
  
      res.status(200).json(formattedLeaderboard);
    } catch (error) {
      console.error('Get leaderboard error:', error);
      res.status(500).json({ message: 'Internal server error.' });
    }
  });

  router.get('/friends/current', ensureAuthenticated, async (req, res) => {
    try {
      const userId = req.user.id;
  
      // Find all friendships for the user
      const friendships = await prisma.friendships.findMany({
        where: {
          OR: [
            { userId: userId, status: 'ACCEPTED' },
            { friendId: userId, status: 'ACCEPTED' },
          ],
        },
      });
  
      // Extract friend IDs
      const friendIds = friendships.map((friendship) =>
        friendship.userId === userId ? friendship.friendId : friendship.userId
      );
  
      // Get streaks for friends
      const friendsStreaks = await prisma.streak.findMany({
        where: {
          userId: {
            in: friendIds,
          },
        },
        orderBy: {
          currentStreak: 'desc',
        },
        include: {
          user: {
            select: {
              id: true,
              email: true,
              // Add other user fields
            },
          },
        },
      });
  
      // Format the leaderboard data
      const formattedLeaderboard = friendsStreaks.map((streak) => ({
        userId: streak.user.id,
        email: streak.user.email,
        currentStreak: streak.currentStreak,
        // Add other relevant data
      }));
  
      res.status(200).json(formattedLeaderboard);
    } catch (error) {
      console.error('Get friends leaderboard error:', error);
      res.status(500).json({ message: 'Internal server error.' });
    }
  });

  router.get('/friends/longest', ensureAuthenticated, async (req, res) => {
    try {
      const userId = req.user.id;
  
      // Find all friendships for the user
      const friendships = await prisma.friendships.findMany({
        where: {
          OR: [
            { userId: userId, status: 'ACCEPTED' },
            { friendId: userId, status: 'ACCEPTED' },
          ],
        },
      });
  
      // Extract friend IDs
      const friendIds = friendships.map((friendship) =>
        friendship.userId === userId ? friendship.friendId : friendship.userId
      );
  
      // Get streaks for friends
      const friendsStreaks = await prisma.streak.findMany({
        where: {
          userId: {
            in: friendIds,
          },
        },
        orderBy: {
          longestStreak: 'desc',
        },
        include: {
          user: {
            select: {
              id: true,
              email: true,
              // Add other user fields
            },
          },
        },
      });
  
      // Format the leaderboard data
      const formattedLeaderboard = friendsStreaks.map((streak) => ({
        userId: streak.user.id,
        email: streak.user.email,
        longestStreak: streak.longestStreak,
        // Add other relevant data
      }));
  
      res.status(200).json(formattedLeaderboard);
    } catch (error) {
      console.error('Get friends leaderboard error:', error);
      res.status(500).json({ message: 'Internal server error.' });
    }
  });

module.exports = router;