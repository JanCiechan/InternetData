package com.inf141202.iternetdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.net.URL
private lateinit var mojAdapter: myAdapter
class MainActivity : AppCompatActivity() {
    fun nextActi(){
        val intent = Intent(this, excerAndPosts::class.java)

        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbhelper=DBHelper(this)

        val thread = Thread(){
            run{
                dbhelper.dropit()
                var url="https://jsonplaceholder.typicode.com/users"
                var body= URL(url).readText()
                var jsonarray: JSONArray? = null
                jsonarray= JSONArray(body)
                for (i in 0 until jsonarray.length()) {
                    val item = jsonarray.getJSONObject(i)
                    //Log.d("pomoc",item.has("id").toString())
                    val user=UserData(item.get("id").toString().toInt(),
                            item.get("name").toString(),
                            item.get("username").toString(),
                            item.get("email").toString(),0,0,0)
                    dbhelper.addUser(user)

                }
                url="https://jsonplaceholder.typicode.com/posts"
                body= URL(url).readText()
                jsonarray= JSONArray(body)
                for (i in 0 until jsonarray.length()) {
                    val item = jsonarray.getJSONObject(i)
                    val post=Posts(item.get("id").toString().toInt(),
                            item.get("userId").toString().toInt(),
                            item.get("title").toString(),
                            item.get("body").toString())
                    dbhelper.addPost(post)

                }
                url="https://jsonplaceholder.typicode.com/todos"
                body= URL(url).readText()
                jsonarray= JSONArray(body)
                for (i in 0 until jsonarray.length()) {
                    val item = jsonarray.getJSONObject(i)
                    val todo=Todos(item.get("id").toString().toInt(),
                            item.get("userId").toString().toInt(),
                            item.get("title").toString(),
                            item.get("completed").toString())
                    dbhelper.addTodo(todo)

                }
                url="https://jsonplaceholder.typicode.com/comments"
                body= URL(url).readText()
                jsonarray= JSONArray(body)
                for (i in 0 until jsonarray.length()) {
                    val item = jsonarray.getJSONObject(i)
                    val comment=Comment(item.get("id").toString().toInt(),
                            item.get("postId").toString().toInt(),
                            item.get("name").toString(),
                            item.get("email").toString(),
                            item.get("body").toString())
                    dbhelper.addComment(comment)

                }
                dbhelper.closedb()
                dbhelper.updateUsers()

            }
        }
        runOnUiThread(){
            val lista = findViewById<RecyclerView>(R.id.recyclerView)
            mojAdapter = myAdapter(mutableListOf())
            lista.adapter = mojAdapter
            lista.layoutManager = LinearLayoutManager(this)
            val userlist=dbhelper.getAllUsers()
            Log.d("user","jest")

            for (UserData in userlist){
                mojAdapter.add(UserData)
                Log.d("user",UserData.todo.toString())

            }
            var dividerItemDecoration = DividerItemDecoration(lista.getContext(),
                    getResources().getConfiguration().orientation);
            lista.addItemDecoration(dividerItemDecoration);
            //mojAdapter.add(curscore)


    }
        thread.start()
}}