package ru.donolaktys.translator.model.data.api

import okhttp3.Interceptor
import java.io.IOException

class BaseInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        return chain.proceed(chain.request())
    }

    companion object {
        val interceptor: BaseInterceptor
            get() = BaseInterceptor()
    }
}