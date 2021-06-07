package com.technopolis_education.globusapp.api

import com.technopolis_education.globusapp.model.FriendInfoResponse
import com.technopolis_education.globusapp.model.OneEmailRequest
import com.technopolis_education.globusapp.model.TwoEmailRequest
import com.technopolis_education.globusapp.model.RegResponse
import com.technopolis_education.globusapp.model.UserAuthRequest
import com.technopolis_education.globusapp.model.FriendsInfo
import com.technopolis_education.globusapp.model.UserInfo
import com.technopolis_education.globusapp.model.UserRegistrationRequest
import com.technopolis_education.globusapp.model.UserToken
import retrofit2.Call
import retrofit2.http.Body
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

    // Подписка на пользователя
    @POST("/follow")
    fun follow(
        @Body body: TwoEmailRequest
    ): Call<UserToken>

    // Получение своих подписок
    @POST("/followersFromMe")
    fun followersFromMe(
        @Body body: OneEmailRequest
    ): Call<ArrayList<FriendsInfo>>

    // Удаление подписки
    @POST("/deleteFollowers")
    fun deleteFollowers(
        @Body body: TwoEmailRequest
    ): Call<UserToken>

    // Информация о друге
    @POST("/friendinfo")
    fun friendInfo(
        @Body body: OneEmailRequest
    ): Call<FriendInfoResponse>
}