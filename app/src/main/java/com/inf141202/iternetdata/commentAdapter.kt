package com.inf141202.iternetdata

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import java.security.AccessController.getContext

class commentAdapter (private  val komentarze: MutableList<Comment>): RecyclerView.Adapter<commentAdapter.myViewHolder>() {
    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val mail = itemView.findViewById<TextView>(R.id.comment_mail)
        val title = itemView.findViewById<TextView>(R.id.comment_title)
        val body = itemView.findViewById<TextView>(R.id.comment_body)

        fun bind(curComment: Comment){
            mail.text=curComment.email
            title.text=curComment.name
            body.text=curComment.body
            /*numer.text = curUser.imie.toString()
            nazwa.text = curUser.nazwisko
            email.text = curUser.email
            zadania.text=curUser.todo.toString()+"/"+(curUser.todo+curUser.ilezadan).toString()
            posty.text=curUser.ilepostow.toString()*/

        }






    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.itemView.setOnClickListener{
           /* var intent=Intent(holder.itemView.getContext(), excerAndPosts::class.java)
            intent.putExtra("UserId",komentarze[position].id)
            holder.itemView.getContext().startActivity(intent)*/
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        )
    }
    fun clear(){
        komentarze.clear()}

    fun add(comment: Comment){
        komentarze.add(comment)
        notifyItemInserted(komentarze.size - 1)
    }
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val Scores = komentarze[position]
        holder.bind(Scores)
    }

    override fun getItemCount(): Int {
        return komentarze.size
    }




}