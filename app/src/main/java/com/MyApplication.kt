package com

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * MyApplication actúa como el punto de entrada de la inyección de dependencias. Al estar anotada con @HiltAndroidApp, habilita Hilt en toda la app.
 * */
@HiltAndroidApp
class MyApplication: Application(){}