package com.webviewtemplate.webviewtemplate

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import android.content.pm.ActivityInfo
import com.webviewtemplate.webviewtemplate.databinding.ActivityMainBinding

class MainActivity : Activity() {
    private val applicationUrl = "file:///android_asset/index.html"
    private lateinit var binding: ActivityMainBinding
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webView = binding.webView

        // Set screen orientation to vertical mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Enable hardware acceleration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }

        // Allow only HTTPS
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return !url.startsWith("https://")
            }
        }

        // Load local HTML file
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        } else {
            webView.loadUrl(applicationUrl)
        }

        with(webView.settings) {
            domStorageEnabled = true
            javaScriptEnabled = true
            setRenderPriority(WebSettings.RenderPriority.HIGH)
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
