package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list.view.*
import com.example.todo.database.Todo
import com.example.todo.database.Categ

class CatAdapter : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    private var listener: ((Categ) -> Unit)? = null
    private val todoList = arrayListOf<Categ>()

    inner class CatViewHolder(private val v : View) : RecyclerView.ViewHolder(v){
        fun bind(todo: Categ){
            v.rootView.setOnClickListener{
                listener?.invoke(todo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false))
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    fun updateData(newList: List<Categ>){
        todoList.clear()
        todoList.addAll(newList)
        notifyDataSetChanged()
    }
}