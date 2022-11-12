package com.heyproject.githubapps.data.utils

import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
Written by Yayan Rahmat Wijaya on 11/12/2022 17:15
Github : https://github.com/yayanrw
 **/

class AppExecutors @VisibleForTesting constructor(
    private val diskIO: Executor
) {
    constructor() : this(
        Executors.newSingleThreadExecutor()
    )

    fun diskIO(): Executor = diskIO
}