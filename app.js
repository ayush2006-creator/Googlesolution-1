require('dotenv').config();

const express=require('express');
const app=express();
const passport = require('passport')
const session = require('express-session')
app.use(express.json());
require('./passport')(passport);

const authRoutes= require('./routes/auth');
const friendRoutes=require('./routes/friend')
const StreakRoutes=require('./routes/streaks')
const leaderboardRoutes=require('./routes/leaderboard');
const blogRoutes=require('./routes/blogs');
const { blog } = require('./db');
app.use(session({
    secret: "secret",
    resave: false ,
    saveUninitialized: true ,
  }))

  app.use(passport.initialize());
  app.use(passport.session());
  
  // Body parser
  app.use(express.urlencoded({ extended: false }));
  app.use(express.json());

app.use('/api',authRoutes);
app.use('/api/friends',friendRoutes)
app.use('/api/streaks',StreakRoutes)
app.use('/api/leaderboard',leaderboardRoutes)
app.use('/api/blogs',blogRoutes)

app.get('/api', (req, res) => {
  res.send('Welcome to the API!');
    res.json({ message: 'Welcome to the API!' });
  });

  app.listen(3000, () => {
    console.log(`Server is running on port 3000`);
  });
  
  