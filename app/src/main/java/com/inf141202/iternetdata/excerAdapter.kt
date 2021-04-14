package com.inf141202.iternetdata


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class excerAdapter (private val excer: MutableList<Todos>): RecyclerView.Adapter<excerAdapter.myViewHolder>() {
    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title2 = itemView.findViewById<TextView>(R.id.excertitle)
        val complet2 = itemView.findViewById<TextView>(R.id.excercomplt)

        fun bind(curTodos: Todos){
            Log.d("exin",curTodos.title)
            title2.text = curTodos.title.toString()
            complet2.text="finished"
            if(curTodos.completion.equals("false")){
                complet2.text="unfinished"
            }
            Log.d("exout","i jest")
        }



    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        /*holder.itemView.setOnClickListener{
            Log.d("pomoc",position.toString())}*/
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_excer, parent, false)
        )
    }
    fun clear(){
        excer.clear()}

    fun add(todos: Todos){
        excer.add(todos)
        notifyItemInserted(excer.size - 1)
        Log.d("todos",todos.title)
    }
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val Scores = excer[position]
        holder.bind(Scores)
    }

    override fun getItemCount(): Int {
        return excer.size
    }




}