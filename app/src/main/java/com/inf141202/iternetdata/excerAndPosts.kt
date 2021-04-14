package com.inf141202.iternetdata

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var excerAdapt: excerAdapter
private lateinit var postAdapt: postAdapter

class excerAndPosts : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excer_and_posts)
        val dbhelper=DBHelper(this)
        val idUsera=intent.getIntExtra("UserId",0)
        val lista = findViewById<RecyclerView>(R.id.recyclerView3)
        excerAdapt = excerAdapter(mutableListOf())
        lista.adapter = excerAdapt
        lista.layoutManager = LinearLayoutManager(this)
        val excer=dbhelper.getExcer(idUsera)
        for (Todo in excer){
            excerAdapt.add(Todo)
            Log.d("send",Todo.userID.toString())


        }
        var dividerItemDecoration = DividerItemDecoration(lista.getContext(),
            getResources().getConfiguration().orientation);
        lista.addItemDecoration(dividerItemDecoration);


        val lista2 = findViewById<RecyclerView>(R.id.recyclerView2)
        postAdapt = postAdapter(mutableListOf())
        lista2.adapter = postAdapt
        lista2.layoutManager = LinearLayoutManager(this)
        val post=dbhelper.getPosts(idUsera)
        for (Posts in post){
            postAdapt.add(Posts)

        }
        var dividerItemDecoration2 = DividerItemDecoration(lista.getContext(),
            getResources().getConfiguration().orientation);
        lista2.addItemDecoration(dividerItemDecoration2);


    }
}