const express = require('express');
const router = express.Router();
const ensureAuthenticated = require('../middleware/auth');

router.get('/videos', ensureAuthenticated, async (req, res) => {
    const videoUrls=[
        "https://storage.cloud.google.com/recoveryapp/4K%20Video%20Downloader%2B/I%20Never%20Knew%20Sobriety%20Could%20Be%20So%20Much%20Fun!%20l%20True%20Stories%20of%20Addiction.mp4"
,
"https://storage.cloud.google.com/recoveryapp/4K%20Video%20Downloader%2B/Kundalini%20Yoga%20Meditation%20To%20Break%20Free%20Of%20Addictions%20%20%2020%20Minutes.mp4",
"https://storage.cloud.google.com/recoveryapp/4K%20Video%20Downloader%2B/Recovery%20Elevator%20-%20How%20to%20Make%20an%20Alcohol%20Addiction%20Dissolve.mp4",
"https://storage.cloud.google.com/recoveryapp/4K%20Video%20Downloader%2B/Recovery%20Elevator%20-%20Think%20Yourself%20Out%20of%20Drinking.mp4",
"https://storage.cloud.google.com/recoveryapp/4K%20Video%20Downloader%2B/Yoga%20For%20Depression%20-%20Yoga%20With%20Adriene.mp4"

    ]
    res.json(videoUrls);

});


router.get("/sounds",ensureAuthenticated, async (req, res) => {
    const soundurls=[
        "https://storage.cloud.google.com/recoveryapp/Music/ASMR%20For%20People%20With%20Anxiety%2C%20Depression%2C%20and%20Insomnia%20%20%20Ear%20Whispering%20Calming%20Personal%20Attention.m4a",
        "https://storage.cloud.google.com/recoveryapp/Music/ASMR%20For%20Those%20in%20Recovery..m4a",
        "https://storage.cloud.google.com/recoveryapp/Music/ASMR%20for%20DEPRESSION%20Relief%20%20%20Let%20Me%20Comfort%20You.m4a",
        "https://storage.cloud.google.com/recoveryapp/Music/Guided%20Mindfulness%20Meditation%20on%20Depression%20-%2020%20minutes%20-%20help%20to%20cope.m4a",
        "https://storage.cloud.google.com/recoveryapp/Music/Spoken%20%20Meditation%20for%20Addiction%20Help%20for%20Substance%2C%20Gambling%2C%20Alcohol%2C%20drugs%2C%20depression%2C%20asmr.m4a"
    ]

    res.json(soundurls);
})

module.exports = router;
