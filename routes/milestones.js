const express=require('express');
const router=express.Router();  
const ensureAuthenticated = require('../middleware/auth');
const prisma = require('../db'); // Import your Prisma client

router.post('/create',ensureAuthenticated, async (req, res) => {
    try{
        const userId = req.user.id; // Assuming user ID is available in req.user
        const  title = req.body; // Extract data from request body
        if(!title){
            return res.status(400).json({message:'Title is required'});
        }
        const newMilestone = await prisma.milestones.create({
            data:{
                title:title,
                userId:userId
            }
        });

    }
    catch(error){
        console.error('Error creating milestone:', error);
        res.status(500).json({message:'Internal server error'});
    }
    res.status(201).json({message:'Milestone created successfully'});
})


router.get('/all',ensureAuthenticated, async (req, res) => {
    try{
        const userId = req.user.id; // Assuming user ID is available in req.user
        const milestones = await prisma.milestones.findMany({
            where:{
                userId:userId
            }
        });
        res.status(200).json(milestones);
    }
    catch(error){
        console.error('Error fetching milestones:', error);
        res.status(500).json({message:'Internal server error'});
    }
})

module.exports=router;