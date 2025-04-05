const express = require('express');
const router = express.Router();
const multer = require('multer');
const { Storage } = require('@google-cloud/storage');
const ensureAuthenticated = require('../middleware/auth');
const prisma = require('../db'); // Import your Prisma client

// Initialize Google Cloud Storage client
const storage = new Storage({
 keyFilename:'D:\\Downloads\\skilled-booking-454911-g5-6a4c7c8c8495.json' // Replace with the actual path
// projectId: 'your-gcp-project-id', // Optional, but recommended
});
const bucketName = 'recovery_app_images'; // Replace with your Google Cloud Storage bucket name

// Configure multer for memory storage (we'll upload directly to GCS)
const upload = multer({
storage: multer.memoryStorage(),
 limits: {
fileSize: 5 * 1024 * 1024, // Limit file size to 5MB (adjust as needed)
 },
fileFilter: (req, file, cb) => {
 // Allow only image files
 if (file.mimetype.startsWith('image/')) {
 cb(null, true);
} else {
  cb(null, false);
 }
 },
});

// Route to handle image upload
router.post('/upload/image', ensureAuthenticated, upload.single('image'), async (req, res) => {
    try {
        if (!req.file) {
            return res.status(400).send('Please upload an image file.');
        }

        const userId = req.user.id; // Assuming your authentication middleware adds user info to req
        const filename = `images/${userId}-${Date.now()}-${req.file.originalname}`; // Unique filename in GCS

        const file = storage.bucket(bucketName).file(filename);
        const stream = file.createWriteStream({
            metadata: {
                contentType: req.file.mimetype,
            },
        });

        stream.on('error', (err) => {
            console.error('Error uploading to GCS:', err);
            res.status(500).send('Failed to upload image to cloud storage.');
        });

        stream.on('finish', async () => {
            const publicUrl = file.publicUrl(); // This will now be accessible publicly
           
            await prisma.user.update({
                where: { id: userId },
                data: {
                    image: publicUrl, // Update the user's profile image URL in your database
                },
            });
            res.status(200).json({ message: 'Image uploaded successfully.', imageUrl: publicUrl });
        });

        stream.end(req.file.buffer); // Pipe the buffer to the GCS stream

    } catch (error) {
        console.error('Error handling image upload:', error);
        res.status(500).send('Failed to process image upload.');
    }
});

module.exports = router;