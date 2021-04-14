package com.inf141202.iternetdata

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.net.URL

class DBHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,
    null, DATABASE_VER) {
    companion object {
        private val DATABASE_VER = 5
        private val DATABASE_NAME = "EDMTDB.db"
        //Table
        private val TABLE_NAME = "UserInfo"
        private val COL_NAME = "IMIE"
        private val COL_VORNAME = "Nazwisko"
        private val COL_EMAIL = "Email"
        private val COL_ID="UserID"
        private val COL_EXCER ="ZadaniaZrobione"
        private val COL_EXCER2= "ZadaniaDoZrobienia"
        private val COL_POSTS = "Posty"

        private val TABLE_POSTS="POST"
        private val COL_POST_ID="ID"
        private val COL_USER_ID="UserID"
        private val COL_TITLE="tytul"
        private val COL_BODY="tresc"

        private val TABLE_COMMENTS="KOMENTARZE"
        private val POST_ID="ID_POSTU"
        private val ID="ID"
        private val NAME="name"
        private val email="email"
        private val body="body"

        private val TABLE_TODOS="TODO"
        private val USER_ID="UserID"
        private val TITLE="Title"
        private val COMPLETION="Completed"
    }
    fun dropit(){
        var db=this.writableDatabase
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_POSTS")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_COMMENTS")

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_TODOS")

        onCreate(db!!)

    }
    override fun onCreate(db: SQLiteDatabase?) {

        var CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY,$COL_NAME TEXT, $COL_VORNAME TEXT, $COL_EMAIL TEXT,$COL_EXCER INTEGER,$COL_EXCER2 INTEGER,$COL_POSTS INTEGER)")
        db!!.execSQL(CREATE_TABLE_QUERY)
        CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_POSTS ($COL_POST_ID INTEGER PRIMARY KEY,$COL_USER_ID INTEGER, $COL_TITLE TEXT, $COL_BODY TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
        CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_COMMENTS ($ID INTEGER PRIMARY KEY,$POST_ID INTEGER, $NAME TEXT, $email TEXT,$body TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
        CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_TODOS ($ID INTEGER PRIMARY KEY,$USER_ID INTEGER, $TITLE TEXT, $COMPLETION TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_POSTS")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_COMMENTS")

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_TODOS")

        onCreate(db!!)
    }
    fun updateUsers(){
        val userlist=getAllUsers()
        var db = this.writableDatabase

        for(user in userlist){

            //update zrobione
            var query="SELECT COUNT(*) FROM $TABLE_TODOS WHERE $USER_ID = '${user.id}' and $COMPLETION like 'true'"
            var cursor =  db.rawQuery(query, null)
            if(cursor.moveToFirst()){
                var count= cursor.getInt(0);
                var cv=ContentValues();
                Log.d("done",count.toString())

                cv.put(COL_EXCER,count)
                db.update(TABLE_NAME,cv,"$COL_ID =${user.id}",null)

            }

            //update do zrobienia
            query="SELECT COUNT(*) FROM $TABLE_TODOS WHERE $USER_ID LIKE ${user.id} and $COMPLETION like 'false'"
            cursor =  db.rawQuery(query, null)
            if(cursor.moveToFirst()){
            var count= cursor.getInt(0);
                Log.d("todo",count.toString())

                var cv=ContentValues();
            cv.put(COL_EXCER2,count)
                db.update(TABLE_NAME,cv,"$COL_ID =${user.id}",null)
            }
            //update posty
            query="SELECT * FROM $TABLE_POSTS WHERE $COL_USER_ID LIKE ${user.id}"
            cursor =  db.rawQuery(query, null)
           // var count = DatabaseUtils.queryNumEntries(db, TABLE_POSTS,
                 //   "$COL_USER_ID='${user.id}' ", null);
            if(cursor.moveToFirst()) {
                var count = cursor.getInt(0);
                var cv = ContentValues();
                count = DatabaseUtils.queryNumEntries(db, TABLE_POSTS,
                           "$COL_USER_ID=${user.id} ", null).toInt();
                Log.d("posty",count.toString())
                cv.put(COL_POSTS, count.toInt())
                db.update(TABLE_NAME, cv, "$COL_ID ='${user.id}'", null)
            }
        }
        db.close()
    }
    /*
    fun savecurrscore(login: String, wynik: Int):Boolean{

        val db=this.writableDatabase

        val cv = ContentValues()
        cv.put(SAVED_GAME, wynik)
        db.update(TABLE_NAME, cv, "$COL_VORNAME='$login'", null)

        //val query="UPDATE TABLE $TABLE_NAME SET $SAVED_GAME = '$wynik' where $COL_LOGIN='$login'"
        //db.execSQL(query)
        db.close()
        return true

    }
    fun getsavedscore(login: String):Int{
        val db=this.writableDatabase

        val query="SELECT $SAVED_GAME FROM $TABLE_NAME WHERE $COL_VORNAME = '$login'"
        val cursor = db.rawQuery(query, null)

        if(cursor.moveToFirst()){
            db.close()
            return cursor.getInt(cursor.getColumnIndex(SAVED_GAME))
        }
        db.close()
        return 0
    }*/
    fun register(login: String, password: String):Boolean{

        val db=this.writableDatabase

        val values = ContentValues()
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COL_VORNAME = '$login'"
        val cursor = db.rawQuery(selectQuery, null)

        if(!cursor.moveToFirst()){
            values.put(COL_VORNAME, login)
            values.put(COL_EMAIL, password)
            db.insert(TABLE_NAME, null, values)

            db.close()
            return true
        }
        else {

            db.close()
        }
        return false
    }
    /*fun getscores():List<String>{

        val listScores=ArrayList<String>()
        val selectQuery = "SELECT * FROM $HIGH_SCORES ORDER BY $SCORE DESC"
        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)

        if(cursor.moveToFirst()){
            do {
                var nazwa = String()
                nazwa =cursor.getString(cursor.getColumnIndex(COL_VORNAME))
                var wynik = String()
                wynik =cursor.getInt(cursor.getColumnIndex(SCORE)).toString()

                listScores.add(nazwa)
                listScores.add(wynik)
            } while (cursor.moveToNext())
        }
        db.close()
        return listScores
    }
    fun registerscore(login: String, score: Int){
        val db=this.writableDatabase
        val values = ContentValues()
        values.put(COL_VORNAME, login)
        values.put(SCORE, score)
        db.insert(HIGH_SCORES, null, values)

        db.close()
    }*/
    fun logowanie(login: String, password: String):Boolean{
        val db=this.writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COL_VORNAME = '$login'"

        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            val id = Integer.parseInt(cursor.getString(0))
            val login2 = cursor.getString(1)
            val password2 = cursor.getString(2)

            cursor.close()
            if(password2==password){
                db.close()
                return true
            }
            else{
                db.close()
                return false
            }
        }
        cursor.close()
        db.close()
        return false
    }
    fun addUser(userData: UserData){
        val db=this.writableDatabase
        val values=ContentValues()
        values.put(COL_NAME,userData.imie)
        values.put(COL_VORNAME,userData.nazwisko)
        values.put(COL_EMAIL,userData.email)
        values.put(COL_EXCER,userData.ilezadan)
        values.put(COL_EXCER2,userData.todo)
        values.put(COL_POSTS,userData.ilepostow)
        values.put(COL_ID,userData.id)
        db.insert(TABLE_NAME,null,values)
        //db.close()

    }
    fun getAllUsers():List<UserData> {
        val users = ArrayList<UserData>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
        do {
            val user = UserData(
                    cursor.getString(cursor.getColumnIndex(COL_ID)).toInt(),
                    cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_VORNAME)),
                    cursor.getString(cursor.getColumnIndex(COL_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COL_EXCER)).toInt(),
                    cursor.getString(cursor.getColumnIndex(COL_EXCER2)).toInt(),
                    cursor.getString(cursor.getColumnIndex(COL_POSTS)).toInt()

            )
            users.add(user)
        } while (cursor.moveToNext())
    }
    db.close()
    return users
    }
    fun getExcer(id:Int):List<Todos>{
        val excer = ArrayList<Todos>()
        val selectQuery = "SELECT * FROM $TABLE_TODOS where $USER_ID = $id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val todo = Todos(
                    cursor.getString(cursor.getColumnIndex(ID)).toInt(),

                    cursor.getString(cursor.getColumnIndex(USER_ID)).toInt(),

                    cursor.getString(cursor.getColumnIndex(TITLE)),
                    cursor.getString(cursor.getColumnIndex(COMPLETION)),


                )

                excer.add(todo)
            } while (cursor.moveToNext())
        }
        db.close()
        return excer

    }
    fun getPosts(id:Int):List<Posts>{
        val post = ArrayList<Posts>()
        val selectQuery = "SELECT * FROM $TABLE_POSTS where $USER_ID = $id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val pos = Posts(
                    cursor.getString(cursor.getColumnIndex(COL_POST_ID)).toInt(),

                    cursor.getString(cursor.getColumnIndex(COL_USER_ID)).toInt(),

                    cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COL_BODY)),


                    )
                post.add(pos)
            } while (cursor.moveToNext())
        }
        db.close()
        return post

    }
    fun getComments(id:Int):List<Comment>{
        val comments = ArrayList<Comment>()
        val selectQuery = "SELECT * FROM $TABLE_COMMENTS where $POST_ID = $id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val com = Comment(
                    cursor.getString(cursor.getColumnIndex(POST_ID)).toInt(),

                    cursor.getString(cursor.getColumnIndex(ID)).toInt(),

                    cursor.getString(cursor.getColumnIndex(NAME)),
                    cursor.getString(cursor.getColumnIndex(email)),
                    cursor.getString(cursor.getColumnIndex(body))


                    )
                comments.add(com)
            } while (cursor.moveToNext())
        }
        db.close()
        return comments

    }
    fun addPost(posts: Posts){
        val db=this.writableDatabase
        val values=ContentValues()
        values.put(COL_POST_ID,posts.id)
        values.put(COL_USER_ID,posts.userID)
        values.put(COL_TITLE,posts.title)
        values.put(COL_BODY,posts.body)
        db.insert(TABLE_POSTS,null,values)
        //db.close()

    }
    fun addTodo(todos: Todos){
        val db=this.writableDatabase
        val values=ContentValues()
        values.put(ID,todos.id)
        values.put(USER_ID,todos.userID)
        values.put(TITLE,todos.title)
        values.put(COMPLETION,todos.completion)
        db.insert(TABLE_TODOS,null,values)
        //.close()

    }
    fun addComment(comment: Comment){
        val db=this.writableDatabase
        val values=ContentValues()
        values.put(ID,comment.id)
        values.put(POST_ID,comment.postId)
        values.put(NAME,comment.name)
        values.put(email,comment.email)
        values.put(body,comment.body)

        db.insert(TABLE_COMMENTS,null,values)
        //db.close()

    }
    fun closedb() {
        val db = this.writableDatabase
        db.close()
    }
}