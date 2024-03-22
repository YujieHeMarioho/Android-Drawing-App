/**
 * Matthew Goh, Yujie He, Eric Rapp
 * This class just sets up splash screen logic and holds the layout for fragment views.
 */
package com.example.finaldraw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.finaldraw.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        // Any additional setup after loading the view.
    }
}