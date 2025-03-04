const passport = require('passport');
const LocalStrategy = require('passport-local').Strategy;
const Prisma = require('./db');
const bcrypt = require('bcryptjs');


module.exports = (passport) => {
    passport.use(
      new LocalStrategy({ usernameField: 'email' }, async (email, password, done) => {
        try {
          const user = await Prisma.user.findUnique({
            where: { email: email },
          });
  
          if (!user) {
            return done(null, false, { message: 'Incorrect email.' });
          }
  
          const isMatch = await bcrypt.compare(password, user.password);
  
          if (isMatch) {
            return done(null, user);
          } else {
            return done(null, false, { message: 'Incorrect password.' });
          }
        } catch (err) {
          return done(err);
        }
      })
    );
  
    passport.serializeUser((user, done) => {
      done(null, user.id);
    });
  
    passport.deserializeUser(async (id, done) => {
      try {
        const user = await Prisma.user.findUnique({
          where: { id: id },
        });
        done(null, user);
      } catch (err) {
        done(err);
      }
    });

    passport.use(
      'therapistLocal',
      new LocalStrategy({ usernameField: 'email' }, async (email, password, done) => {
        try {
          const therapist = await prisma.therapist.findUnique({ where: { email: email } });
  
          if (!therapist) {
            return done(null, false, { message: 'Incorrect email or password.' });
          }
  
          bcrypt.compare(password, therapist.password, (err, isMatch) => {
            if (err) throw err;
  
            if (isMatch) {
              return done(null, therapist);
            } else {
              return done(null, false, { message: 'Incorrect email or password.' });
            }
          });
        } catch (err) {
          return done(err);
        }
      })
    );
  
    passport.serializeUser((therapist, done) => {
      if (therapist.password) {
        done(null, { id: therapist.id, type: 'therapist' });
      } else {
        done(null, { id: therapist.id, type: 'user' });
      }
  
    });
  
    passport.deserializeUser(async (user, done) => {
      try {
        if (user.type === 'therapist') {
          const therapist = await prisma.therapist.findUnique({ where: { id: user.id } });
          done(null, therapist);
        } else {
          const userFound = await prisma.user.findUnique({ where: { id: user.id } });
          done(null, userFound);
        }
      } catch (err) {
        done(err);
      }
    });
  };