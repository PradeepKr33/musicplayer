package com.codingera.musicplayer.activities

import android.app.ProgressDialog
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codingera.musicplayer.databinding.ActivityDetailsMusicBinding
import com.codingera.musicplayer.model.DetailMusicResponse
import com.codingera.musicplayer.model.ModelListMusic
import com.codingera.musicplayer.networking.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DetailsMusicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsMusicBinding
    private var progressDialog: ProgressDialog? = null
    private var modelListMusic: ModelListMusic? = null
    private var mediaPlayer: MediaPlayer? = null
    private var musicUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }

        if (Build.VERSION.SDK_INT >= 21) {
            window.statusBarColor = Color.TRANSPARENT
        }

        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Please wait")
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Loading song details...")

        modelListMusic = intent.getSerializableExtra("detailLagu") as ModelListMusic?
        modelListMusic?.let {
            Glide.with(this)
                .load(it.strCoverLagu)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgCover)

            getDetailMusic(it.strId!!)
        }

        binding.imgPlay.setOnClickListener { playMusic() }
        binding.imgPause.setOnClickListener { pauseMusic() }
    }


    private fun getDetailMusic(id: String) {
        progressDialog?.show()
        Api.instance.getMusicDetail(id).enqueue(object : Callback<DetailMusicResponse> {
            override fun onResponse(call: Call<DetailMusicResponse>, response: Response<DetailMusicResponse>) {
                progressDialog?.dismiss()
                response.body()?.details?.firstOrNull()?.let {
                    binding.tvTitleMusic.text = it.title
                    binding.tvBand.text = it.bandName
                    musicUrl = it.mp3Url
                }
            }

            override fun onFailure(call: Call<DetailMusicResponse>, t: Throwable) {
                progressDialog?.dismiss()
                Toast.makeText(this@DetailsMusicActivity, "Failed to load song details!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun pauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    private fun playMusic() {
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(musicUrl)
            prepare()
            start()
        }
    }
}
