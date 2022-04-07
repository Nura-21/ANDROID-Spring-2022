package com.example.todo.database

import androidx.room.*

@Dao
interface TodoDAO{


    @Insert
    fun TodoInsert(todo: Todo) : Long

    @Insert
    fun CatInsert(cat : Categ) : Long

    @Update
    fun TodoUpdate(todo: Todo) : Int

    @Update
    fun CatUpdate(cat : Categ) : Int

    @Delete
    fun TodoDelete(todo: Todo) : Int

    @Delete
    fun CatDelete(cat : Categ) : Int

    @Query("SELECT * FROM Todo ORDER BY datetime DESC")
    fun get(): List<Todo>

    @Query("SELECT * FROM Todo WHERE id = :id")
    fun getByID(id: Long) : Todo

    @Query("SELECT * FROM Categ")
    fun getCat(): List<Categ>

    @Query("SELECT title FROM Categ WHERE id = :id")
    fun getByIDCat(id: Long) : String

    @Query("SELECT id FROM Categ WHERE title = :title")
    fun getIdOf(title: String) : Long


    @Query("SELECT * FROM Todo WHERE catId = :catId")
    fun getTodoAndCat(catId : Long): List<Todo>

}

