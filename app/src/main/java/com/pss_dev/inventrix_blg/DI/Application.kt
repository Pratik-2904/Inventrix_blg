package com.pss_dev.inventrix_blg.DI

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    // This class will automatically initialize Hilt
}