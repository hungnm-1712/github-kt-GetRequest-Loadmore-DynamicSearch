package com.example.youtubeapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubeapi.R
import com.example.youtubeapi.model.MakeCloudModel

// Kế thừa RecyclerView.ViewHolder
class RecyclerViewAdapter(var context: Context, var responseList: ArrayList<MakeCloudModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Trạng thái còn dữ liệu hay không:
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
/////////// Khi còn dữ liệu thì hiển thị, hết dữ liệu thì hiện ProgressBar
        return if (viewType === VIEW_TYPE_ITEM) {
            val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_youtube, parent, false)
            return DataViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.progress_bar, parent, false)
            return LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is DataViewHolder){
            showData(holder,position)
        }else if(holder is LoadingViewHolder){
            showLoadingView(holder,position)
        }

    }

    override fun getItemCount(): Int {
        return responseList.size +1
    }



    override fun getItemViewType(position: Int): Int {
        // Khi item Chạy đến ô cuối +1, thì hiện progressbar
        if (responseList.size == position) {
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }

    // Dành cho hàng trong list
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgThumbnai: ImageView
        var tvTitle: TextView
        var tvSinger: TextView
        var tvPlayCount: TextView

        init {
            imgThumbnai = itemView.findViewById(R.id.imgThumbnai)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvSinger = itemView.findViewById(R.id.tvSinger)
            tvPlayCount = itemView.findViewById(R.id.tvPlayCount)

        }
    }
    // Dành cho progressbar
    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.progressbar)
        }
    }

    //
    private fun showLoadingView(holder: LoadingViewHolder, position: Int) {
    }

    // Add data to row
    private fun showData(holder: DataViewHolder, position: Int) {
        var responseData: MakeCloudModel = responseList[position]
//        holder.imgThumbnai
        Glide.with(context).load(responseData.pictures.extra_large).into(holder.imgThumbnai)
        holder.tvTitle.text = responseData.name
        holder.tvSinger.text = "Ca sĩ: " + responseData.user.name
        holder.tvPlayCount.text = "Lượt nghe: " + responseData.play_count.toString()
    }
}