package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list.view.*

class TodoAdapter(var todoList : List<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {


    inner class TodoViewHolder(private val v : View) : RecyclerView.ViewHolder(v){
        fun bind(todo: Todo){

            v.apply{
                EachTitle.text = todo.title
                EachDescription.text = todo.id.toString()
                EachDate.text = todo.completed.toString()
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
        todoList.toMutableList().clear()
        todoList.toMutableList().addAll(newList)
        todoList = todoList.toList()
        notifyDataSetChanged()
    }

}

