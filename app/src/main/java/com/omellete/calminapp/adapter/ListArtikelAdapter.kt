package com.omellete.calminapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.omellete.calminapp.DetailArtikelActivity
import com.omellete.calminapp.R
import com.omellete.calminapp.model.Artikel

class ListArtikelAdapter(private val listArtikel: ArrayList<Artikel>) :
    RecyclerView.Adapter<ListArtikelAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Artikel)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_row_artikel, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(listArtikel[position].gambar)
            .apply(RequestOptions().override(55, 55))
            .into(holder.gambarBerita)
        holder.tvJudulArtikel.text = listArtikel[position].judul
        holder.tvPenulisArtikel.text = listArtikel[position].penulis
        val mContext = holder.itemView.context
        holder.itemView.setOnClickListener {
            val moveDetail = Intent(mContext, DetailArtikelActivity::class.java)
            val person = listArtikel[position]
            moveDetail.putExtra(DetailArtikelActivity.EXTRA_PERSON, person)

            mContext.startActivity(moveDetail)
        }
    }

    override fun getItemCount(): Int {
        return listArtikel.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvJudulArtikel: TextView = itemView.findViewById(R.id.tv_judul_artikel)
        var tvPenulisArtikel: TextView = itemView.findViewById(R.id.tv_penulis_artikel)
        var gambarBerita: ImageView = itemView.findViewById(R.id.img_item_photo)
    }
}