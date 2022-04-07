package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todo.database.AppDatabase
import com.example.todo.database.Categ
import kotlinx.android.synthetic.main.activity_category.*


class Category : AppCompatActivity() {

    private var id: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)


        id = intent.getLongExtra("catID", 0)

        if(id == 0L){
            CategoryTitle.text = "Insert Category"
        }
        setListener()
    }

    private fun setListener(){
        catDone.setOnClickListener{
            val message = validate()

            if(message == ""){
                val title = EditTextName.text.toString()
                if(id == 0L){
                    insert(title)
                }
            }else{
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validate(): String{
        val title = EditTextName.text.toString().trim()
        return when {
            title.isEmpty() -> "Please input category name"
            else -> ""
        }
    }

    private fun insert(title: String){
        val each = Categ(0,  title)
        val id = AppDatabase.getInstance(this).todoDao().CatInsert(each)

        if(id > 0){
            Toast.makeText(this, "Insert Success", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this, "Insert Failed", Toast.LENGTH_SHORT).show()
        }
    }
}