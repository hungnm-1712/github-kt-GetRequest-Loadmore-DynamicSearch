package com.example.youtubeapi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.youtubeapi.R
import com.example.youtubeapi.model.SearchResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Home"
        openLoadList()

        //Glide.with()

//         run("https://api.mixcloud.com/search/?q=hung&type=cloudcast&offset=0&limit=20")
    }


//    fun run(url: String) {
//
//
//    }
    fun openLoadList(){
        btnLoadList.setOnClickListener {
            startActivity( Intent(this, ListYoutubeActivity::class.java))
        }
    }


}
