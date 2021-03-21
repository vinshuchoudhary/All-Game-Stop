package com.example.gamestop

import com.example.gamestop.newmodel.ModelClass
import com.example.gamestop.newmodel.Result
import com.example.gamestop.particularmodel.NewModel
import com.example.gamestop.trailermodel.TrailerModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://api.rawg.io/api/games?key=06f90fc8b3204a6db115f77557cd2fc2&dates=2019-09-01,2019-09-30&platforms=18,1,7

const val BASE_URL = "https://api.rawg.io/"
interface GameInterface {

    @GET("api/games?key=06f90fc8b3204a6db115f77557cd2fc2")
    fun getGame(@Query("page") pageNum: Int): Call<ModelClass>

    @GET("api/games?key=06f90fc8b3204a6db115f77557cd2fc2")
    fun getOneGame(@Query("search") gameName: String): Call<ModelClass>

    @GET("api/games/{id}?key=06f90fc8b3204a6db115f77557cd2fc2")
    fun getFullData(@Path("id")slug : String) : Call<NewModel>

    @GET("api/games/{id}/movies?key=06f90fc8b3204a6db115f77557cd2fc2")
    fun getTrailer(@Path("id")slug : String) : Call<TrailerModel>

}

object GameObject {

    val gameInstance: GameInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        gameInstance = retrofit.create(GameInterface::class.java)
    }


}