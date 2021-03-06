package com.linepayjunior.realm_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_layout.*
import kotlinx.android.synthetic.main.dialog_layout.view.*
import kotlinx.android.synthetic.main.dialog_remove_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val realm = Realm.getDefaultInstance()
    val realmResult : RealmResults<Todo> = realm.where<Todo>().findAll()

    private val title_array = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val list_adapter = MainListAdapter(this, title_array)
        realm_listview.adapter = list_adapter

        realm_listview.setOnItemClickListener{ adapterView, view, i, l ->

            removeTodo(realmResult.get(i)!!.id)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }


        for (i in 0 until realmResult.size){
            title_array.add(realmResult.get(i)!!.title)
        }


        addButton.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)

            builder.setView(dialogView).show()

            dialogView.dialog_button.setOnClickListener {

                insertTodo(dialogView.dialog_input.text.toString())

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }

        }

//        removeButton.setOnClickListener{
//            val builder = AlertDialog.Builder(this)
//            val dialogView = layoutInflater.inflate(R.layout.dialog_remove_layout, null)
//
//            builder.setView(dialogView).show()
//
//            dialogView.dialog_remove_button.setOnClickListener{
//                removeTodo(dialogView.dialog_remove_input.text.toString().toLong())
//
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//        }


    }

    private fun insertTodo(str : String) {

        realm.beginTransaction()

        val newItem = realm.createObject<Todo>(nextId())

        newItem.title = str
        newItem.date = Calendar.getInstance().timeInMillis

        realm.commitTransaction()

        Toast.makeText(this, "insertTodo 실행됨", Toast.LENGTH_LONG).show()
        Log.e("MainActivity realm:" , realmResult.toString())

//        // Add 버튼 눌러도 dialog와 화면이 그대로 유지되는 현상을 막기 위하여 MainActivity 를 한번 불러주고 Finish 한다.
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//        finish()

    }

    private fun removeTodo (id : Long) {

        realm.beginTransaction()

        val deleteItem = realm.where<Todo>().equalTo("id", id).findFirst()
        deleteItem?.deleteFromRealm()

        realm.commitTransaction()

        Toast.makeText(this,"removeTodo가 실행되었습니다", Toast.LENGTH_LONG).show()
        Log.e("MainActivity realm:" , realmResult.toString())


    }

    private fun nextId() : Int {

        val maxId = realm.where<Todo>().max("id")
        if(maxId != null){
            return maxId.toInt() + 1
        }
        return 0

    }

}
