package ru.skillbranch.devintensive.utils

object Utils {
    private val cyr2Lat = mapOf(
        "а" to "a", "б" to "b",
        "в" to "v", "г" to "g",
        "д" to "d", "е" to "e",
        "ё" to "yo", "ж" to "zh",
        "з" to "z", "и" to "i",
        "й" to "y", "к" to "k",
        "л" to "l", "м" to "m",
        "н" to "n", "о" to "o",
        "п" to "p", "р" to "r",
        "с" to "s", "т" to "t",
        "у" to "u", "ф" to "f",
        "х" to "h", "ц" to "с",
        "ч" to "ch", "ш" to "sh",
        "щ" to "sch", "ъ" to "",
        "ы" to "y", "ь" to "",
        "э" to "e",  "ю" to "yu",
        "я" to "ya",

        "А" to "A", "Б" to "B",
        "В" to "V",  "Г" to "G",
        "Д" to "D", "Е" to "E",
        "Ё" to "Yo", "Ж" to "Zh",
        "З" to "Z", "И" to "I",
        "Й" to "Y", "К" to "K",
        "Л" to "L",  "М" to "M",
        "Н" to "N", "О" to "O",
        "П" to "P", "Р" to "R",
        "С" to "S", "Т" to "T",
        "У" to "U", "Ф" to "F",
        "Х" to "H",  "Ц" to "С",
        "Ч" to "Ch", "Ш" to "Sh",
        "Щ" to "Sch", "Ъ" to "",
        "Ы" to "Y", "Ь" to "",
        "Э" to "E", "Ю" to "Yu",
        "Я" to "Ya"
    )

    fun parseFullName(fullName:String?):Pair<String?, String?>{
        return when {
            fullName == null || fullName.isEmpty() || fullName.replace(" ", "").isEmpty() -> null to null
            else -> {
                val parts : List<String>? = fullName.split(" ")

                val firstName = parts?.getOrNull(0)
                val lastName = parts?.getOrNull(1)
                if (lastName == null) {
                    firstName to null
                } else {
                    firstName to lastName
                }
            }
        }
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val parts: List<String>? = payload.split(" ")
        val builder = StringBuilder()

        parts?.forEach { it ->
            val chars = it.toCharArray()
            chars.forEach {
                val russian = it.toString()
                val latin = cyr2Lat[russian]

                if (latin != null) {
                    builder.append(latin)
                } else {
                    builder.append(russian)
                }
            }
            builder.append(divider)
        }

        while (builder.endsWith(divider)) {
            builder.setLength(builder.length - 1)
        }

        return builder.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return when {
            (firstName == null || firstName.isEmpty() || firstName.replace(" ", "").isEmpty()) && (lastName == null || lastName.isEmpty() || lastName.replace(" ", "").isEmpty()) -> null
            (firstName != null && firstName.isNotEmpty()) && (lastName != null && lastName.isNotEmpty()) -> firstName[0].toUpperCase().toString() + lastName[0].toUpperCase().toString()
            else -> {
                if (firstName != null && firstName.isNotEmpty()) {
                    firstName[0].toUpperCase().toString()
                } else if (lastName != null && lastName.isNotEmpty()) {
                    lastName[0].toUpperCase().toString()
                } else {
                    ""
                }
            }
        }
    }
}