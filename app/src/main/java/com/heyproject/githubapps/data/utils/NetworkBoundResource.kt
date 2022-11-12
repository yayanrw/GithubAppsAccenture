package com.heyproject.githubapps.data.utils

import com.heyproject.githubapps.common.ViewResource
import kotlinx.coroutines.flow.*

/**
Written by Yayan Rahmat Wijaya on 11/12/2022 17:16
Github : https://github.com/yayanrw
 **/

abstract class NetworkBoundResource<ResultType, RequestType> {
    private var result: Flow<ViewResource<ResultType>> = flow {
        emit(ViewResource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(ViewResource.Loading())
            when (val apiResponse = createCall().first()) {
                is DataResource.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map {
                        ViewResource.Success(
                            it
                        )
                    })
                }
                is DataResource.Empty -> {
                    emitAll(loadFromDB().map {
                        ViewResource.Success(
                            it
                        )
                    })
                }
                is DataResource.Error -> {
                    onFetchFailed()
                    emit(
                        ViewResource.Error(
                            apiResponse.errorMessage
                        )
                    )
                }
            }
        } else {
            emitAll(loadFromDB().map { ViewResource.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<DataResource<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<ViewResource<ResultType>> = result
}