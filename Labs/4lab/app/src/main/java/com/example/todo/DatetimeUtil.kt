package com.example.todo

import java.text.SimpleDateFormat
import java.util.*

object DatetimeUtil{
    fun getCurrentDate(): String{
        val date = Date()
        val formatter = SimpleDateFormat("HH:mm dd MMM yyyy", Locale.getDefault())
        return formatter.format(date)
    }
}