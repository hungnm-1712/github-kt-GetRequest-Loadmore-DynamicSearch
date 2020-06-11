package com.example.youtubeapi.activity

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.R
import com.example.youtubeapi.adapter.RecyclerViewAdapter
import com.example.youtubeapi.model.MakeCloudModel
import com.example.youtubeapi.model.SearchResponse
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_list_youtube.*
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class ListYoutubeActivity : YouTubeBaseActivity() {

    private val client = OkHttpClient()

    // Gson: Convert json to object
    val gson = Gson()

    // Array data from http
    var arrayData = arrayListOf<MakeCloudModel>()

    // Link 1
    var linkurl: String =
        "https://api.mixcloud.com/search/?limit=20&offset=0&q=hung&type=cloudcast";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_youtube)
        setTitle("List youtube")
        runHttp(linkurl)
        initScrollListener()
        initAdapter()
    }

    // Get request from http
    private fun runHttp(URL: String) {

        //Tạo request
        val request = Request.Builder()
            .url(URL)
            .build()

        //Đọc request = okhttp
        client.newCall(request).enqueue(object : Callback {
            //fail
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
            }

            override fun onResponse(call: Call, response: Response) {
                // Sử dụng thread để chạy ưu tiên, vì khi chạy bt thì  val body = response?.body()?.string() chạy sau nên không lên dữ liệu
                Thread(Runnable {
                    runOnUiThread {
                        try {
                            //Đọc json
                            val body = response?.body()?.string()

                            // JSON - > OBJECT
                            // Bóc json sang model SearchResponse
                            val searchResponse = gson.fromJson(body, SearchResponse::class.java)

                            linkurl = searchResponse.paging.next
                            arrayData?.addAll(searchResponse.data)

                            //add to RecyclerView
                            rvList.adapter?.notifyDataSetChanged()

                        } catch (error: Exception) {
                            println("Error: " + error)
                        }
                    }
                }).start()
            }
        })
    }

    // Add data to recyclerView
    fun initAdapter() {
        rvList.layoutManager =
            LinearLayoutManager(this@ListYoutubeActivity, RecyclerView.VERTICAL, false);
        rvList.adapter = RecyclerViewAdapter(this@ListYoutubeActivity, arrayData)
    }

    fun initScrollListener() {
        rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.adapter!!.itemCount != 0) {
                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition >= recyclerView.adapter!!.itemCount - 1) {
                        runHttp(linkurl)
                        // Add more here

                    }
                }
            }
        })
    }


}
