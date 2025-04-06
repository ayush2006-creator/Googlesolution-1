const express= require('express');
const router = express.Router();    
const prisma = require('../db');
const ensureAuthenticated = require('../middleware/auth');

router.post('/like/:blogId', ensureAuthenticated, async (req, res) => {
    const { blogId } = req.params;
    try{
        const blog = await prisma.blog.findUnique({
            where: { id: blogId },
            data: {
                likes:{
                    increment: 1,
                }
        }});
        if (!blog) {
            return res.status(404).json({ message: 'Blog not found.' });
        }

        res.status(200).json({ message: 'Blog liked successfully!',likes: blog.likes });

    }
    catch (error) {
        res.status(500).json({ error,message: 'Internal server error.' });
    }
})

router.post('/unlike/:blogId', ensureAuthenticated, async (req, res) => {
    const { blogId } = req.params;
    try{
        const blog = await prisma.blog.findUnique({
            where: { id: blogId },
            data: {
                likes:{
                    decrement: 1,
                }
        }});
        if (!blog) {
            return res.status(404).json({ message: 'Blog not found.' });
        }

        res.status(200).json({ message: 'Blog liked successfully!',likes: blog.likes });

    }
    catch (error) {
        res.status(500).json({ error,message: 'Internal server error.' });
    }
})

router.post('/comment/:blogId',ensureAuthenticated,async (req,res)=>{
    const { blogId } = req.params;
    const { comment } = req.body;
    const userId = req.user.id;
  
    try {
      const newComment = await prisma.comments.create({
        data: {
          content: comment,
          blogId: blogId,
          userId: userId,
        },
      });
      res.status(201).json(newComment); // Respond with the created comment
    } catch (error) {
      console.error('Error creating comment:', error);
      res.status(500).json({ message: 'Failed to add comment.' });
    }
})

router.post('/bookmark/:blogId',ensureAuthenticated,async (req,res)=>{
    const { blogId } = req.params;
    const userId = req.user.id;
  
    try {
      const bookmark = await prisma.bookmarks.create({
        data: {
          blogId: blogId,
          userId: userId,
        },
      });
      res.status(201).json({message:"bookmark created successfully",bookmark}); // Respond with the created bookmark
    } catch (error) {
      console.error('Error creating bookmark:', error);
      res.status(500).json({ message: 'Failed to add bookmark.' });
    }
})

router.get('/bookmarks',ensureAuthenticated,async (req,res)=>{
    const userId = req.user.id;
  
    try {
      const bookmarks = await prisma.bookmarks.findMany({
        where: {
          userId: userId,
        },
        include: {
          blog: true, // Include blog details if needed
        },
      });
      res.status(200).json(bookmarks); // Respond with the user's bookmarks
    } catch (error) {
      console.error('Error fetching bookmarks:', error);
      res.status(500).json({ message: 'Failed to fetch bookmarks.' });
    }
})

module.exports = router;