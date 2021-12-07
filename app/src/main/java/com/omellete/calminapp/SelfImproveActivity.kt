package com.omellete.calminapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omellete.calminapp.adapter.ListArtikelAdapter
import com.omellete.calminapp.model.Artikel

class SelfImproveActivity : AppCompatActivity() {
    private lateinit var rvArtikel: RecyclerView
    private var list : ArrayList<Artikel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_improve)
        rvArtikel = findViewById(R.id.rv_artikel)
        rvArtikel.setHasFixedSize(true)
        list.addAll(listData)
        showRecyclerList()
    }

    val listData : ArrayList<Artikel>
        get() {
            val dataJudulArtikel = resources.getStringArray(R.array.judul)
            val dataPenulisArtikel = resources.getStringArray(R.array.penulis)
            val dataImg = resources.obtainTypedArray(R.array.gambarArtikel)
            val dataIsiArtikel = resources.getStringArray(R.array.isiArtikel)
            val list = arrayListOf<Artikel>()
            for (position in dataJudulArtikel.indices){
                val artikel = Artikel(
                    dataJudulArtikel[position],
                    dataPenulisArtikel[position],
                    dataImg.getResourceId(position,-1),
                    dataIsiArtikel[position]
                )
                list.add(artikel)
            }
            dataImg.recycle()
            return list
        }

    private fun showRecyclerList(){
        rvArtikel.layoutManager = LinearLayoutManager(this)
        val listIdolAdapter = ListArtikelAdapter(list)
        rvArtikel.adapter = listIdolAdapter

    }
}