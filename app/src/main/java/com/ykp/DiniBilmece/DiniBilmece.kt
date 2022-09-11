package com.ykp.DiniBilmece

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


//the following annotation triggers hilt's code generation,
// including a base class for the application that serves as the application-level dependency container.
@HiltAndroidApp
class DiniBilmece: Application()