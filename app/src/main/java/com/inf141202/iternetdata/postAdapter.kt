package com.inf141202.iternetdata


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class postAdapter (private val posty: MutableList<Posts>): RecyclerView.Adapter<postAdapter.myViewHolder>() {
    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.post_title)
        val body = itemView.findViewById<TextView>(R.id.post_body)

        fun bind(curPosts: Posts){
            title.text=curPosts.title
            body.text=curPosts.body

        }



    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.itemView.setOnClickListener{
            var intent= Intent(holder.itemView.getContext(), Comments::class.java)
            intent.putExtra("PostId",posty[position].id)
            holder.itemView.getContext().startActivity(intent)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_post, parent, false)
        )
    }
    fun clear(){
        posty.clear()}

    fun add(posts: Posts){
        posty.add(posts)
        notifyItemInserted(posty.size - 1)
    }
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val Scores = posty[position]
        holder.bind(Scores)
    }

    override fun getItemCount(): Int {
        return posty.size
    }




}