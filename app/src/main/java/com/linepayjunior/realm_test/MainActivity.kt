package com.linepayjunior.realm_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {

    private val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertTodo()
        val realmResult : RealmResults<Todo> = realm.where<Todo>().findAll()
        Log.e("MainActivity realm:" , realmResult.toString())
    }

    private fun insertTodo() {

        realm.beginTransaction()

        val newItem = realm.createObject<Todo>(nextId())

        newItem.title = "title"
        newItem.date = 123

        realm.commitTransaction()

        Toast.makeText(this, "inetTodo 실행됨", Toast.LENGTH_LONG).show()


    }

    private fun nextId() : Int {

        val maxId = realm.where<Todo>().max("id")
        if(maxId != null){
            return maxId.toInt() + 1
        }
        return 0

    }

}
