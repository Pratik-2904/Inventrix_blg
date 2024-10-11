package com.pss_dev.inventrix_blg.navigation

sealed class Screens(
    val title: String,
    val route: String
) {
    object home : Screens("Home", "home")
    object splash : Screens("Splash", "splash")
    object bills : Screens("Bills", "bills")
    object products : Screens("Products", "products")
    object parties : Screens("Parties", "parties")
    object more : Screens("More", "more")
    object login : Screens("Login", "login")
    object register : Screens("Register", "register")
    object terms : Screens("Terms", "terms")
}