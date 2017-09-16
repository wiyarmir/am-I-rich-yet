package es.guillermoorellana.amirichyet.service.marketdata.data

import com.google.gson.annotations.SerializedName

data class MarketDataRaw(
        @SerializedName("name") val name: String,
        @SerializedName("unit") val unit: String,
        @SerializedName("values") val values: List<DataPoint>
)

data class DataPoint(
        @SerializedName("x") val x: Long,
        @SerializedName("y") val y: Double
)
