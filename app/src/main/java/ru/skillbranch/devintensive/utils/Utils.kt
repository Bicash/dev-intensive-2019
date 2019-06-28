package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullname:String?):Pair<String?, String?>{
        //TODO FIX ME!!!
        val parts : List<String>? = fullname?.split(" ")

        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider:String = " "): String {
        //TODO
        return "nick"
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        //TODO
        return "DB"
    }
}