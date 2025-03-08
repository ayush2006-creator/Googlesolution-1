package com.example.sample_front_screen


import java.net.URL

data class Dataset(
    var signup: Signup,
    var login: Login,
    var friendrequest:FriendRequest,
    val friendaccept:Accept,
    var blogs: Blogs,
    val sound: Sound,
    val craft: Craft,
    val breathing: Breathing,
    val chekins: Chekins
)
data class Signup(
    var username: String,
    var email: String,
    var password : String,



)
data class SignupResponse(val message: String)
data class LoginResponse(val message: String)
data class  Login(
    var email: String,
    var password: String,
)
data class FriendRequest(
    val senderId: String,
    val receiverId:String
)
data class Accept(
    val requestId: String
)


data class Blogs(
    var title: String,
    var content: String,
    val authorId:String
)
data class Sound(
    val title:String,
    val audioUrl: URL
)
data class Craft(
    val title: String,
    val vedioUrl: URL
)
data class Breathing(
    val title: String,
    val steps:Array<String>
)
data class Chekins(
    val userId: String,
    val therapistFriendId:String
)
data class CheckinResponse(
    val message: String,
    val currentStreak: Int? = null,
    val Streak: Int? = null
)
