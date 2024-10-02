package com.fikrihaikal.haiflix.core.data.source


import com.fikrihaikal.haiflix.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


abstract class NetworkBoundResource<ResultType, RequestType>{
    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when(val apiResponse = createCall().first()){
            is ApiResponse.Success ->{
                emitAll(loadFromNetwork(apiResponse.data).map { resultType ->
                    Resource.Success(resultType)
                })
            }
            is ApiResponse.Error ->{
                emit(Resource.Error(apiResponse.errorMessage.toString()))
            }
            else ->{}
        }
    }
    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>
    protected abstract fun loadFromNetwork(data: RequestType): Flow<ResultType>
    fun asFlow(): Flow<Resource<ResultType>> = result
}