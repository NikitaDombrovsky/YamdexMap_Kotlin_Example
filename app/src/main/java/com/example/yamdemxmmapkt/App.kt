package com.example.yamdemxmmapkt

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Reading API key from BuildConfig.
        // Do not forget to add your MAPKIT_API_KEY property to local.properties file.
        MapKitFactory.setApiKey("52eb0455-d7e5-4526-a6e4-ca5699d8a4bc")
    }
}