require('dotenv').config();

const express = require('express');
const app = express();
const passport = require('passport');
const session = require('express-session');
const cors = require('cors');

// Passport Configuration
require('./passport')(passport);

// Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true })); // Use extended: true if needed

// CORS Configuration (Allow Credentials)
const corsOptions = {
    origin:true, // Replace with your frontend domain
    credentials: true,
};
app.use(cors(corsOptions));

// Session Configuration
app.use(session({
    secret: process.env.SESSION_SECRET || "secret", // Use env variable or default
    resave: false,
    saveUninitialized: true,
    cookie: {
        secure: 'auto', // Send cookies only over HTTPS in production
        httpOnly: true, // Prevent client-side JavaScript access
    },
}));

// Passport Initialization
app.use(passport.initialize());
app.use(passport.session());

// Routes
const authRoutes = require('./routes/auth');
const friendRoutes = require('./routes/friend');
const StreakRoutes = require('./routes/streaks');
const leaderboardRoutes = require('./routes/leaderboard');
const blogRoutes = require('./routes/blogs');

app.use('/api', authRoutes);
app.use('/api/friends', friendRoutes);
app.use('/api/streaks', StreakRoutes);
app.use('/api/leaderboard', leaderboardRoutes);
app.use('/api/blogs', blogRoutes);

app.get('/api', (req, res) => {
    res.json({ message: 'Welcome to the API!' }); // Use only res.json
});

const port = process.env.PORT || 3000;
app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});