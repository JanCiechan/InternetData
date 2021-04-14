package com.inf141202.iternetdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
private lateinit var commentAdapt: commentAdapter

class Comments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        val dbhelper=DBHelper(this)
        val idPostu=intent.getIntExtra("PostId",0)
        val lista = findViewById<RecyclerView>(R.id.rec4)
        commentAdapt = commentAdapter(mutableListOf())
        lista.adapter = commentAdapt
        lista.layoutManager = LinearLayoutManager(this)
        val excer=dbhelper.getComments(idPostu)
        for (Todo in excer){
            commentAdapt.add(Todo)

        }

        var dividerItemDecoration = DividerItemDecoration(lista.getContext(),
            getResources().getConfiguration().orientation)
        lista.addItemDecoration(dividerItemDecoration)
        val dolisty=findViewById<Button>(R.id.button)
        dolisty.setOnClickListener(){
            var intent= Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);
        }
    }
}