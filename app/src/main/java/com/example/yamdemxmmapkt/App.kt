package com.example.yamdemxmmapkt

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Reading API key from BuildConfig.
        // Do not forget to add your MAPKIT_API_KEY property to local.properties file.
        MapKitFactory.setApiKey("YOUR_API_KEY")
    }
}
