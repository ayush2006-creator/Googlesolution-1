// routes/friends.js (or routes/auth.js)
const express = require('express');
const router = express.Router();
const prisma = require('../db'); // Import your Prisma client
const ensureAuthenticated = require('../middleware/auth'); // Import authentication middleware

// Send Friend Request
router.post('/sendRequest/:friendId', ensureAuthenticated, async (req, res) => {
  try {
    const userId = req.user.id; // Get the user ID from the authenticated request
    const friendId = req.params.friendId; // Get the friend ID from the URL parameter

    // Check if users are the same
    if (userId === friendId) {
      return res.status(400).json({ message: 'You cannot send a friend request to yourself.' });
    }

    // Check if friendship already exists
    const existingFriendship = await prisma.friendships.findFirst({
      where: {
        OR: [
          { userId: userId, friendId: friendId },
          { userId: friendId, friendId: userId },
        ],
      },
    });

    if (existingFriendship) {
      return res.status(400).json({ message: 'Friend request already sent or users are already friends.' });
    }

    // Create the friend request
    await prisma.friendships.create({
      data: {
        userId: userId,
        friendId: friendId,
      },
    });

    res.status(201).json({ message: 'Friend request sent successfully!' });
  } catch (error) {
    console.error('Send friend request error:', error);
    res.status(500).json({ message: 'Internal server error.' });
  }
});

router.put('/acceptRequest/:friendshipId', ensureAuthenticated, async (req, res) => {
    try {
      const userId = req.user.id;
      const friendshipId = req.params.friendshipId;
  
      // Find the friendship
      const friendship = await prisma.friendships.findUnique({
        where: { id: friendshipId },
      });
  
      if (!friendship) {
        return res.status(404).json({ message: 'Friend request not found.' });
      }
  
      // Check if the current user is the friend in the request
      if (friendship.friendId !== userId) {
        return res.status(403).json({ message: 'You are not authorized to accept this friend request.' });
      }
  
      // Update the friendship status to ACCEPTED
      await prisma.friendships.update({
        where: { id: friendshipId },
        data: { status: 'ACCEPTED' },
      });
  
      res.status(200).json({ message: 'Friend request accepted!' });
    } catch (error) {
      console.error('Accept friend request error:', error);
      res.status(500).json({ message: 'Internal server error.' });
    }
  });

  //routes/friends.js
//Get pending friend requests
router.get('/pendingRequests', ensureAuthenticated, async (req, res) => {
    try {
      const userId = req.user.id;
  
      const pendingFriendRequests = await prisma.friendships.findMany({
        where: {
          friendId: userId,
          status: 'PENDING',
        },
        include: {
          user: {
            select: {
              id: true,
              email: true,
              //Add any other user fields you want to return
            }
          }
        }
      });
  
      res.status(200).json(pendingFriendRequests);
  
    } catch (error) {
      console.error("Get pending friend requests error: ", error);
      res.status(500).json({message: "Internal server error"});
    }
  })

module.exports = router;