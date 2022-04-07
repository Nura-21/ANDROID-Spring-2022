package com.example.todo.database

import androidx.room.*

@Entity(foreignKeys = [ForeignKey(entity = Categ::class,
    parentColumns = ["id"],
    childColumns = ["catId"])])
data class Todo (
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    val catId: Long,
    var title: String,
    var description: String,
    var datetime: String,
)

@Entity
data class Categ(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    var title: String,
)