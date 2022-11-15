package com.heyproject.githubapps.domain.model

import com.heyproject.githubapps.utils.DataDummy
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Written by Yayan Rahmat Wijaya on 11/15/2022 07:08
 * Github : https://github.com/yayanrw
 */

@RunWith(MockitoJUnitRunner::class)
class UserKtTest {

    @Test
    fun toEntity() {
        val dummyUserDto = DataDummy.generateDummyUserDto()
        val expect = dummyUserDto.toEntity()
        val actual = DataDummy.generateDummyUserEntity()

        assertEquals(expect.id, actual.id)
    }
}