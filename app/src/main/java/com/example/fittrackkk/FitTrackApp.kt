package com.example.fittrackkk

import android.app.Application
import com.example.fittrackkk.data.local.FitTrackDatabase

class FitTrackApp : Application() {
    val database: FitTrackDatabase by lazy {
        FitTrackDatabase.getDatabase(this)
    }
}
