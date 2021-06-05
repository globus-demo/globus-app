package com.technopolis_education.globusapp.api

import com.technopolis_education.globusapp.model.RegResponse
import com.technopolis_education.globusapp.model.UserAuthRequest
import com.technopolis_education.globusapp.model.UserInfo
import com.technopolis_education.globusapp.model.UserRegistrationRequest
import com.technopolis_education.globusapp.model.UserToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WebServices {
    // Регисрация пользователя
    @POST("/registration")
    fun reg(
        @Body body: UserRegistrationRequest
    ): Call<UserToken>

    // Авторизация пользователя
    @POST("/getuser")
    fun auth(
        @Body body: UserAuthRequest
    ): Call<UserToken>

    // Получение информации о пользователе
    @POST("/userinfo")
    fun userInfo(
        @Body body: RegResponse
    ): Call<UserInfo>
}