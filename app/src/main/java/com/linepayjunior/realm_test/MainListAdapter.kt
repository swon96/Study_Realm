package com.linepayjunior.realm_test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import io.realm.RealmResults
import kotlinx.android.synthetic.main.listview_item.view.*

class MainListAdapter(val context: Context, val realmResults : ArrayList<String>) :BaseAdapter(){

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val view : View = LayoutInflater.from(context).inflate(R.layout.listview_item, null)
        view.item_id.text = realmResults.get(p0)

        return view
    }

    override fun getItem(p0: Int): Any {
        return 0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return realmResults.size
    }

}