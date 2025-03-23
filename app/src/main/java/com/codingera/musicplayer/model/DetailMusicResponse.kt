package com.codingera.musicplayer.model

import com.google.gson.annotations.SerializedName

data class DetailMusicResponse(
    @SerializedName("data") val details: List<DetailMusic>
)

data class DetailMusic(
    @SerializedName("judulmusic") val title: String,
    @SerializedName("namaband") val bandName: String,
    @SerializedName("linkmp3") val mp3Url: String
)
