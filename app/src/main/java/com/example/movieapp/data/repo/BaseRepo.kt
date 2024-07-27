package com.example.movieapp.data.repo

import com.example.movieapp.data.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

//abstract class BaseRepo() {
//    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response: Response<T> = apiToBeCalled()
//
//                if (response.isSuccessful) {
//                    Resource.Success(data = response.body()!!)
//                } else {
//                    val errorResponse: ExampleErrorResponse? = convertErrorBody(response.errorBody())
//                    Resource.Error(errorMessage = errorResponse?.failureMessage ?: "Something went wrong")
//                }
//
//            } catch (e: HttpException) {
//                Resource.Error(errorMessage = e.message ?: "Something went wrong")
//            } catch (e: IOException) {
//                Resource.Error("Please check your network connection")
//            } catch (e: Exception)
//                Resource.Error(errorMessage = "Something went wrong")
//            }
//        }
//    }
//
//    private fun convertErrorBody(errorBody: ResponseBody?): ExampleErrorResponse? {
//        return try {
//            errorBody?.source()?.let {
//                val moshiAdapter = Moshi.Builder().build().adapter(ExampleErrorResponse::class.java)
//                moshiAdapter.fromJson(it)
//            }
//        } catch (exception: Exception) {
//            null
//        }
//    }
//}
