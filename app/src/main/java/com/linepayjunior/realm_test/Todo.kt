package com.linepayjunior.realm_test

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Todo(
    // data는 id, title, date
    @PrimaryKey var id : Long = 0,
    var title : String = "",
    var date : Long = 0

):RealmObject()