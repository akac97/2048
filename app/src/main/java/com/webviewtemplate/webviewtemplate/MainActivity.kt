package com.webviewtemplate.webviewtemplate

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import android.content.pm.ActivityInfo
import android.webkit.WebResourceRequest
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.webviewtemplate.webviewtemplate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val applicationUrl = "file:///android_asset/index.html"
    private lateinit var binding: ActivityMainBinding
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        webView = binding.webView

        // Set screen orientation to vertical mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Enable hardware acceleration
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        // Allow only HTTPS
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return !request.url.toString().startsWith("https://")
            }
        }

        // Disable page reload swipes
        webView.setOnTouchListener { _, _ -> true }

        // Load local HTML file
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        } else {
            webView.loadUrl(applicationUrl)
        }

        with(webView.settings) {
            domStorageEnabled = true
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            blockNetworkImage = false
            loadsImagesAutomatically = true
            mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }
    }

    // Save the state of the WebView when the activity is paused
    override fun onPause() {
        super.onPause()
        webView.saveState(Bundle())
    }

    // Disable back button
    override fun onBackPressed() {
        // Do nothing
    }
}
