package com.handstandsam.shoppingapp.multiplatform

import com.handstandsam.shoppingapp.models.User

class Greeting {
    fun greeting(): String {
        val user = User("Sam", "Edwards")
        return "Hello ($user), ${Platform().platform}!"
    }
}