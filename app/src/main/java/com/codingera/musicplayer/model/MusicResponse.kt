package com.codingera.musicplayer.model

import com.google.gson.annotations.SerializedName

data class MusicResponse(
    @SerializedName("post") val musicList: List<ModelListMusic>
)
