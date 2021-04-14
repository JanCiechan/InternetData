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

class myAdapter (private  val userzy: MutableList<UserData>): RecyclerView.Adapter<myAdapter.myViewHolder>() {
    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val numer = itemView.findViewById<TextView>(R.id.imie)
        val nazwa = itemView.findViewById<TextView>(R.id.nazwisko)
        val email = itemView.findViewById<TextView>(R.id.email)
        val zadania = itemView.findViewById<TextView>(R.id.zadania)
        val posty=itemView.findViewById<TextView>(R.id.posty)
        fun bind(curUser: UserData){
            numer.text = curUser.imie.toString()
            nazwa.text = curUser.nazwisko
            email.text = curUser.email
            zadania.text=curUser.todo.toString()+"/"+(curUser.todo+curUser.ilezadan).toString()
            posty.text=curUser.ilepostow.toString()

        }






    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.itemView.setOnClickListener{
            var intent=Intent(holder.itemView.getContext(), excerAndPosts::class.java)
            intent.putExtra("UserId",userzy[position].id)
        holder.itemView.getContext().startActivity(intent)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.listitem, parent, false)
        )
    }
    fun clear(){
        userzy.clear()}

    fun add(users: UserData){
        userzy.add(users)
        notifyItemInserted(userzy.size - 1)
    }
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val Scores = userzy[position]
        holder.bind(Scores)
    }

    override fun getItemCount(): Int {
        return userzy.size
    }




}