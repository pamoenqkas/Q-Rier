package com.example.q_rier.User

class UserApi {
    companion object {
        val BASE_URL = "http://192.168.1.16:8080/q-rier/ci4-apiserver/public/user"

        val GET_ALL_URL = BASE_URL + "user/"
        val GET_BY_ID_URL = BASE_URL + "user/"
        val ADD_URL = BASE_URL + "user/"
        val UPDATE_URL = BASE_URL + "user/"
        val DELETE_URL = BASE_URL + "user/"
    }
}