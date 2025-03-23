package com.codingera.musicplayer.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelListMusic(
    @SerializedName("id") var strId: String? = null,
    @SerializedName("judulmusic") var strJudulMusic: String? = null,
    @SerializedName("namaband") var strNamaBand: String? = null,
    @SerializedName("coverartikel") var strCoverLagu: String? = null
) : Serializable
