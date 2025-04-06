package com.example.sample_front_screen

sealed class Screens (val route :String){

    object screen2:Screens("screen2")
    object screen4:Screens("screen4")
    object screen7:Screens("screen7")

    object welcome :Screens(route = "welcome")
    object task1:Screens(route = "task1")
    object addictionstart:Screens(route = "addictionstart")
    object addiction:Screens(route = "addiction")

    object signin:Screens(route = "signin")
    object signup:Screens(route = "signup")

    object enemychose:Screens(route = "enemychose")
    object expenditure:Screens(route = "expenditure")
    object triggers:Screens(route = "triggers")

    object breathscreen1:Screens(route = "breathescreen1")
    object breathscreen2:Screens(route = "breathescreen2")
    object breathscreen3:Screens(route = "breathescreen3")
    object breathscreen4:Screens(route = "breathescreen4")
    object breathscreen5:Screens(route = "breathescreen5")

    object lighttower:Screens(route = "lighttower")

    object dashboard1:Screens(route = "dashboard")

    object progress:Screens(route = "dashboard")
    object community :Screens(route = "community")
    object maytasks:Screens(route = "mytasks")
    object mygoals:Screens(route="mygoals")
    object talktome:Screens(route="talktome")
    object distractme:Screens(route="distractme")
    object myrecoverygarden:Screens(route = "myrecoverygarden")
    object milestones : Screens(route= "milestones")
     object  lighttoweronboard: Screens(route = "lighttoweronboard")

    object new:Screens(route = "new")

}