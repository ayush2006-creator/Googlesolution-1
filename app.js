require('dotenv').config();

const express=require('express');
const app=express();
const passport = require('passport')
const session = require('express-session')
require('./passport')(passport);

const authRoutes= require('./routes/auth');
const friendRoutes=require('./routes/friend')
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

  app.listen(3000, () => {
    console.log(`Server is running on port 3000`);
  });
  
  