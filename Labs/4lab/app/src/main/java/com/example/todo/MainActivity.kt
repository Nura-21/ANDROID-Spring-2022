package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.database.AppDatabase
import com.example.todo.database.Todo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_insert_update.*


class MainActivity : AppCompatActivity() {

    private val todoAdapter : TodoAdapter by lazy { TodoAdapter() }
    private val catAdapter : CatAdapter by lazy { CatAdapter() }
    var catId : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Hide top bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportActionBar?.hide();

        setContentView(R.layout.activity_main)

        setListener()
        setAdapter()
        setListenerCat()
        loadCategory()
    }

    override fun onResume() {
        super.onResume()
        loadCategory()
        val dropdown = findViewById<Spinner>(R.id.spinner)
        dropdown.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                catId = p3 + 1
                loadData()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun navigate(id: Long){
        val intent = Intent(this, InsertUpdate::class.java)
        intent.putExtra("ID", id)
        startActivity(intent)
    }

    private fun navigateCat(id : Long){
        val intent = Intent(this, Category::class.java)
        intent.putExtra("catID", id)
        startActivity(intent)
    }

    private fun setListener(){
        Add.setOnClickListener{
            navigate(0)
        }
    }

    private fun setListenerCat(){
        AddCat.setOnClickListener{
            navigateCat(0)
        }
    }

    private fun setAdapter(){
        todoAdapter.setOnClickListener {
            navigate(it.id)
        }
        todoAdapter.setOnDeleteListener {
            AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure?")
                .setNegativeButton("No"){
                    dialog, _ -> dialog.dismiss()
                }
                .setPositiveButton("Yes"){
                        dialog, _ ->
                    AppDatabase.getInstance(this).todoDao().TodoDelete(it)
                    Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT)
                    loadData()
                    dialog.dismiss()
                }
                .show()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = todoAdapter
    }


    private fun loadData(){
        val todoList = AppDatabase.getInstance(this).todoDao().getTodoAndCat(catId)
        todoAdapter.updateData(todoList)
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