package es.guillermoorellana.amirichyet.service.bitcoindata

import com.google.gson.annotations.SerializedName

data class BitcoinDataRaw(
        @SerializedName("name") val name: String,
        @SerializedName("unit") val unit: String,
        @SerializedName("values") val values: List<RawValue>
)

data class RawValue(
        @SerializedName("x") val x: Long,
        @SerializedName("y") val y: Double
)
