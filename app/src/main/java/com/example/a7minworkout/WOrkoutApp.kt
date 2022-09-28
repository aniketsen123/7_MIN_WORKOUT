package com.example.a7minworkout

import android.app.Application

class WOrkoutApp :Application(){
    val db by lazy {
        historydatabase.getinstance(this)
    }
}