package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: String,
    val firstName: String?,
    var lastName: String?,
    val avatar: String?,
    val rating: Int = 0,
    val respect: Int = 0,
    val lastVisit: Date? = Date(),
    val isOnline: Boolean = false
) {

    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    constructor(id: String) : this(id, "John", "Doe")

    init {

        println("It`s Alive!!!\n" +
                "${if(lastName=="Doe") "His name is $firstName $lastName" else "And his name is $firstName $lastName !!!" }\n")
    }


    companion object Factory {
        private var lastId : Int = -1
        fun makeUser(fullname:String?) : User{
            lastId++

            var (firstName, lastName) = Utils.parseFullName(fullname)

            return User(id = "$lastId", firstName = firstName, lastName = lastName)
        }
    }
}