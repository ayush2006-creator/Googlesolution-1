// routes/blogs.js
const express = require('express');
const router = express.Router();
const prisma = require('../db');
const ensureAuthenticated = require('../middleware/auth');

// Create a new blog post
router.post('/create', ensureAuthenticated, async (req, res) => {
  try {
    const userId = req.user.id;
    const { title, content } = req.body;

    if (!title || !content) {
      return res.status(400).json({ message: 'Title and content are required.' });
    }

    const newBlog = await prisma.blog.create({
      data: {
        title: title,
        content: content,
        authorId: userId,
      },
    });

    res.status(201).json({ message: 'Blog post created successfully!', blog: newBlog });
  } catch (error) {
    console.error('Create blog error:', error);
    res.status(500).json({ message: 'Internal server error.' });
  }
});
router.get('/all',ensureAuthenticated, async (req, res) => {
    try {
      const blogs = await prisma.blog.findMany({
        include: {
          author: {
            select: {
              id: true,
              email: true,
              username: true,
              // Add other user fields you want to return
            },
          },
          comments: {
            select: { 
              content: true,
              createdAt: true,
              user: {
                select: {
                  id: true,
                  username: true,
                },
              },
            }
        },
        orderBy: {
          createdAt: 'desc', // Order by creation date (newest first)
        },
      }});
  
      res.status(200).json(blogs);
    } catch (error) {
      console.error('Get all blogs error:', error);
      res.status(500).json({ message: 'Internal server error.' });
    }
  });

  router.get('/search', async (req, res) => {
    try {
        const { query } = req.query;

        let whereClause = {}; // Default: no filtering

        if (query) {
            whereClause = {
                OR: [
                    { title: { contains: query, mode: 'insensitive' } },
                    { content: { contains: query, mode: 'insensitive' } },
                    {author: { username: { contains: query, mode: 'insensitive' } }}, // Assuming you want to search by author's username too
                ],
            };
        }

        const blogs = await prisma.blog.findMany({
            where: whereClause,
            include: {
                author: {
                    select: {
                        id: true,
                        username: true,
                        email: true,
                    },
                },
                comments: {
                    select: { 
                        content: true,
                        createdAt: true,
                        user: {
                            select: {
                                id: true,
                                username: true,
                            },
                        },
                    }
                },
            },
            orderBy: {
              createdAt: 'desc', // Order by creation date (newest first)
            },
        });

        res.json(blogs);
    } catch (error) {
        console.error('Blog search error:', error);
        res.status(500).json({ message: 'Internal server error.' });
    }
});

module.exports = router;