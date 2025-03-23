package com.codingera.musicplayer.networking

import com.codingera.musicplayer.model.DetailMusicResponse
import com.codingera.musicplayer.model.MusicResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    /*@GET("listmusic")
    fun getMusicList(): Call<MusicResponse>*/

    @GET("jsongetid/{id}")
    fun getMusicDetail(@Path("id") id: String): Call<DetailMusicResponse>

    @GET("recommended")
    fun getMusicList(): Call<MusicResponse>
}
