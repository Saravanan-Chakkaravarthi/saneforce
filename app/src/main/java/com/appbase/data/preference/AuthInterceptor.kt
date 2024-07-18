package com.appbase.data.preference

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val preferenceManager: PreferenceManager
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = preferenceManager.jwtToken
        val request = chain.request().newBuilder()
            .addHeader(name = "Authorization", value = "Bearer $jwt")
            .build()
        return  chain.proceed(request)
    }
}