package com.webviewtemplate.webviewtemplate

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import android.content.pm.ActivityInfo
import android.webkit.WebResourceRequest
import androidx.webkit.WebViewAssetLoader
import com.webviewtemplate.webviewtemplate.databinding.ActivityMainBinding
import android.view.View

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
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        // Allow only HTTPS
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return !request.url.toString().startsWith("https://")
            }
        }

        // Load local HTML file
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        } else {
            val assetLoader = WebViewAssetLoader.Builder()
                .addPathHandler("/android_asset/", WebViewAssetLoader.AssetsPathHandler(this))
                .build()
            webView.loadUrl(assetLoader.createAssetUrl(applicationUrl).toString())
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
