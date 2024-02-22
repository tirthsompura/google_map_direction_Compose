package com.example.googlemapdirection.response


data class UserResponse(
    var userId: String = "",
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var userType: String = "",
    var empLat: Double = 0.0,
    var empLong: Double = 0.0,
    var userLat: Double = 0.0,
    var userLong: Double = 0.0,
)

