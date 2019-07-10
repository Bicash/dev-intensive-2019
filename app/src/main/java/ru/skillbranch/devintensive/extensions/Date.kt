package ru.skillbranch.devintensive.extensions

import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value:Int, units: TimeUnits = TimeUnits.SECOND):Date{
    var time = this.time

    time +=when (units){
        TimeUnits.SECOND-> value * SECOND
        TimeUnits.MINUTE-> value * MINUTE
        TimeUnits.HOUR-> value * HOUR
        TimeUnits.DAY-> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    //TODO
    var difference = (date.time - this.time) / 1000

    var min = difference / 60
    var hour = min / 60
    var day = hour / 24

    if (difference < 0) {
        difference--
        min = difference / 60
        hour = min / 60
        day = hour / 24
        min = abs(min)
        hour = abs(hour)
        day = abs(day)
    }

    return when(difference) {
        in -1..0 -> "только что"
        in -45..-1 -> "несколько секунд назад"
        in -75..-45 -> "минуту назад"
        in -45 * 60..-75 -> "через $min ${if ((min % 10 == 0L) or (min % 10 == 5L) or (min % 10 == 6L) or (min % 10 == 7L) or (min % 10 == 8L) or (min % 10 == 9L) or ((min > 10L) and (min < 20L))) "минут" else if ((min % 10 == 2L) or (min % 10 == 3L) or (min % 10 == 4L)) "минуты" else "минуту"}"
        in -75 * 60..-45 * 60 -> "через час"
        in -22 * 60 * 60..-75 * 60 -> "через $hour ${if ((min % 10 == 0L) or (min % 10 == 5L) or (min % 10 == 6L) or (min % 10 == 7L) or (min % 10 == 8L) or (min % 10 == 9L) or ((min > 10L) and (min < 20L))) "часов" else if ((min % 10 == 2L) or (min % 10 == 3L) or (min % 10 == 4L)) "часа" else "час"}"
        in -26 * 60 * 60..-22 * 60 * 60 -> "через день"
        in -360 * 24 * 60 * 60..-26 * 60 * 60 -> "через $day ${if ((min % 10 == 0L) or (min % 10 == 5L) or (min % 10 == 6L) or (min % 10 == 7L) or (min % 10 == 8L) or (min % 10 == 9L) or ((min > 10L) and (min < 20L))) "дней" else if ((min % 10 == 2L) or (min % 10 == 3L) or (min % 10 == 4L)) "дня" else "день"}"
        in 0..1 -> "только что"
        in 1..45 -> "через несколько секунд"
        in 45..75 -> "минуту назад"
        in 75..45 * 60 -> "$min ${if ((min % 10 == 0L) or (min % 10 == 5L) or (min % 10 == 6L) or (min % 10 == 7L) or (min % 10 == 8L) or (min % 10 == 9L) or ((min > 10L) and (min < 20L))) "минут" else if ((min % 10 == 2L) or (min % 10 == 3L) or (min % 10 == 4L)) "минуты" else "минуту"} назад"
        in 45 * 60..75 * 60 -> "час назад"
        in 75 * 60..22 * 60 * 60 -> "$hour ${if ((hour % 10 == 0L) or (hour % 10 == 5L) or (hour % 10 == 6L) or (hour % 10 == 7L) or (hour % 10 == 8L) or (hour % 10 == 9L) or ((hour > 10L) and (hour < 20L))) "часов" else if ((hour % 10 == 2L) or (hour % 10 == 3L) or (hour % 60 == 4L)) "часа" else "час"} назад"
        in 22 * 60 * 60..26 * 60 * 60 -> "день назад"
        in 26 * 60 * 60..360 * 24 * 60 * 60 -> "$day ${if ((min % 10 == 0L) or (min % 10 == 5L) or (min % 10 == 6L) or (min % 10 == 7L) or (min % 10 == 8L) or (min % 10 == 9L) or ((min > 10L) and (min < 20L))) "дней" else if ((min % 10 == 2L) or (min % 10 == 3L) or (min % 10 == 4L)) "дня" else "день"} назад"
        else -> {
            when {
                difference > 360 * 24 * 60 * 60 -> "более года назад"
                else -> "более чем через год"
            }
        }
    }
}

enum class TimeUnits{
    SECOND {
        override fun plural(value: Int): String {
            return "$value ${intToString(value, "секунду", "секунды", "секунд")}"
        }
    },
    MINUTE {
        override fun plural(value: Int): String {
            return "$value ${intToString(value, "минуту", "минуты", "минут")}"
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            return "$value ${intToString(value, "час", "часа", "часов")}"
        }
    },
    DAY {
        override fun plural(value: Int): String {
            return "$value ${intToString(value, "день", "дня", "дней")}"
        }
    };
    abstract fun plural(value: Int): String

    fun intToString(value: Int, single: String, few: String, many: String): String
    {
        return when {
            value % 10 == 0 -> many
            value % 100 in 11..19 -> many
            value % 10 == 1 -> single
            value % 10 in 1..4 -> few
            else -> many
        }
    }
}