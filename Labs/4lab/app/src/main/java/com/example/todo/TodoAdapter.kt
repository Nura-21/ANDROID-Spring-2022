package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.database.AppDatabase
import com.example.todo.database.Categ
import com.example.todo.database.Todo
import kotlinx.android.synthetic.main.list.view.*

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var listener: ((Todo) -> Unit)? = null
    private var deleteListener: ((Todo) -> Unit)? = null
    private val todoList = arrayListOf<Todo>()

    inner class TodoViewHolder(private val v : View) : RecyclerView.ViewHolder(v){
        fun bind(todo: Todo){

            v.apply{
                EachTitle.text = todo.title
                EachDescription.text = todo.description
                EachDate.text = "Last updated: " + todo.datetime
            }

            v.rootView.setOnClickListener{
                listener?.invoke(todo)
            }

            v.IconDelete.setOnClickListener{
                deleteListener?.invoke(todo)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false))
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    fun updateData(newList: List<Todo>){
        todoList.clear()
        todoList.addAll(newList)
        notifyDataSetChanged()
    }


    fun setOnClickListener(listener: (Todo) -> Unit){
        this.listener = listener
    }

    fun setOnDeleteListener(deleteListener: (Todo) -> Unit) {
        this.deleteListener = deleteListener
    }
}

