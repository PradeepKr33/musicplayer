package com.codingera.musicplayer.activities

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingera.musicplayer.adapter.ListMusicAdapter
import com.codingera.musicplayer.databinding.ActivityListMusicBinding
import com.codingera.musicplayer.model.ModelListMusic
import com.codingera.musicplayer.model.MusicResponse
import com.codingera.musicplayer.networking.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMusicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListMusicBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var listMusicAdapter: ListMusicAdapter
    private var modelListMusic: MutableList<ModelListMusic> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }

        if (Build.VERSION.SDK_INT >= 21) {
            window.statusBarColor = Color.TRANSPARENT
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Fetching music list...")

        binding.rvListMusic.layoutManager = LinearLayoutManager(this)

        // Fetch Data
        getListMusic()
    }

    private fun getListMusic() {
        progressDialog.show()
        Api.instance.getMusicList().enqueue(object : Callback<MusicResponse> {
            override fun onResponse(call: Call<MusicResponse>, response: Response<MusicResponse>) {
                progressDialog.dismiss()
                if (response.isSuccessful) {
                    modelListMusic = response.body()?.musicList?.toMutableList() ?: mutableListOf()
                    showListMusic()
                } else {
                    Toast.makeText(this@ListMusicActivity, "Failed to load data!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MusicResponse>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@ListMusicActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showListMusic() {
        listMusicAdapter = ListMusicAdapter(this, modelListMusic, this::onSelected)
        binding.rvListMusic.adapter = listMusicAdapter
    }

    private fun onSelected(modelListLagu: ModelListMusic) {
        val intent = Intent(this, DetailsMusicActivity::class.java)
        intent.putExtra("detailLagu", modelListLagu)
        startActivity(intent)
    }
}
