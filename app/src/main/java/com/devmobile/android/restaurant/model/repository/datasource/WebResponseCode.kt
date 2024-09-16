package com.devmobile.android.restaurant.model.datasource

/**
 * REST server response codes
 */
enum class WebResponseCode {

    OK {

        override fun getMessage() = "It's Okay"
        override fun getCode() = 200
    },
    NOT_FOUND {

        override fun getMessage() = "Not was possible find the page"
        override fun getCode() = 404
    },
    REQUEST_TIMEOUT {

        override fun getMessage() = "Time for request delayed"
        override fun getCode() = 408
    },
    INTERNAL_Web_ERROR {

        override fun getMessage() = "Server isn't works"
        override fun getCode() = 500
    },
    SERVICE_UNAVAILABLE {

        override fun getMessage() = "Service Unavailable"
        override fun getCode() = 503
    };

    abstract fun getMessage() : String
    abstract fun getCode() : Int
}