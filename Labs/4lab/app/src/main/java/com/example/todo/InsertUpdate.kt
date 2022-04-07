package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.todo.database.AppDatabase
import com.example.todo.database.Categ
import com.example.todo.database.Todo
import kotlinx.android.synthetic.main.activity_insert_update.*

class InsertUpdate : AppCompatActivity() {

    private var id: Long = 0
    private val catAdapter : CatAdapter by lazy { CatAdapter() }
    var catId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_update)



        id = intent.getLongExtra("ID", 0)

        if(id == 0L){
            //Insert
            InsertUpdateTitle.text = "Insert Todo"
        }else{
            //Update
            InsertUpdateTitle.text = "Update Todo"
            //Load
            val todo = AppDatabase.getInstance(this).todoDao().getByID(id)
            EditTextTitle.setText(todo.title)
            EditTextDescription.setText(todo.description)
        }

        setListener()
        loadCategory()
    }

    override fun onResume() {
        super.onResume()
        val dropdown = findViewById<Spinner>(R.id.spinner)
        dropdown.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                catId = p3 + 1
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setListener(){
        Save.setOnClickListener{
            val message = validate()

            if(message == ""){
                //Insert or Update
                val title = EditTextTitle.text.toString()
                val description = EditTextDescription.text.toString()
                if(id == 0L){
                    insert(catId, title, description)
                }else{
                    update(id,catId, title, description)
                }
            }else{
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validate(): String{
        val title = EditTextTitle.text.toString().trim()
        val description = EditTextDescription.text.toString().trim()

        return when {
            title.isEmpty() -> "Please input title"
            description.isEmpty() -> "Please input description"
            else -> ""
        }
    }

    private fun insert(catId: Long, title: String, description: String){
        val todo = Todo(0, catId, title, description, DatetimeUtil.getCurrentDate())
        val id = AppDatabase.getInstance(this).todoDao().TodoInsert(todo)

        if(id > 0){
            Toast.makeText(this, "Insert Success", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this, "Insert Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun update(id: Long, catId: Long, title: String, description: String){
        val todo = Todo(id, catId, title, description, DatetimeUtil.getCurrentDate())
        val row = AppDatabase.getInstance(this).todoDao().TodoUpdate(todo)

        if(row > 0){
            Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadCategory(){
        val catList = AppDatabase.getInstance(this).todoDao().getCat()

        var catTitleList = listOf<String>()
        for(each in catList){
            catTitleList += (each.title)
        }

        val dropdown = findViewById<Spinner>(R.id.spinner)
        if(dropdown != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, catTitleList)
            dropdown.adapter = adapter
        }
        catAdapter.updateData(catList)

    }
}