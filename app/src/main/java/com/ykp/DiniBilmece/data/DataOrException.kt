package com.ykp.DiniBilmece.data

data class DataOrException<T, Boolean, E : Exception>(
    var data: T? = null,
    var connection: Boolean? = null,
    var loading: Boolean? = null,
    var e: E? = null

)