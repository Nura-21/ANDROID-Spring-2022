package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var ID = 2;
    private var todos : List<Todo> = emptyList();
    var users = arrayListOf<Int>()
    val todoAdapter : TodoAdapter by lazy { TodoAdapter(emptyList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Hide top bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportActionBar?.hide();

        setContentView(R.layout.activity_main)

        launch{
            todos = getTodoRetrofit()

//            recyclerView.adapter = TodoAdapter(todos.filter { it.userId == ID })
            for(each in todos.distinctBy { it.userId }){
                users.add(each.userId)
            }

            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.adapter = TodoAdapter(todos)

        }
    }

    override fun onResume() {
        super.onResume()
        val dropdown = findViewById<Spinner>(R.id.spinner)
        if(dropdown != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, users)
            dropdown.adapter = adapter
        }
        dropdown.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                ID = p2 + 1
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }



//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = TodoAdapter(todos.filter { it.userId == ID })


    }

    suspend fun getTodoRetrofit(): List<Todo>{
        val todoApi = TodoRepository.todoApi
        val response = todoApi.getTodos()
        return if(response.isSuccessful){
            response.body()!!
        }else{
            emptyList()
        }

    }

}