package com.omellete.calminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.omellete.calminapp.R
import com.omellete.calminapp.model.Artikel

class DetailArtikelActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PERSON: String = "extra_person"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_artikel)
        val actionBar: ActionBar?
        actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val artikelGambar: ImageView = findViewById(R.id.img_item_photo)
        val tvArtikelJudul: TextView = findViewById(R.id.tv_judul_artikel)
        val tvArtikelSumber: TextView = findViewById(R.id.tv_sumber_artikel)
        val tvArtikelPenulis: TextView = findViewById(R.id.tv_penulis_artikel)
        val tvArtikelIsiArtikel: TextView = findViewById(R.id.tv_isi_artikel)

        val artikel = intent.getParcelableExtra<Artikel>(EXTRA_PERSON) as Artikel
        val artikelNameFinal = "${artikel.judul}"
        setTitle("${artikel.judul}")
        tvArtikelPenulis.text =artikel.penulis
        Glide.with(this)
            .load(artikel.gambar)
            .apply(RequestOptions())
            .into(artikelGambar)
        tvArtikelJudul.text = artikelNameFinal
        tvArtikelSumber.text = artikel.sumber
        tvArtikelPenulis.text = artikel.penulis
        tvArtikelIsiArtikel.text = artikel.isiartikel


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}