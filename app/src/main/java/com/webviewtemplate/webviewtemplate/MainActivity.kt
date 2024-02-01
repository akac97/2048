package com.webviewtemplate.webviewtemplate

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.webkit.WebView
import com.webviewtemplate.webviewtemplate.databinding.ActivityMainBinding

class MainActivity : Activity() {
    // Load local HTML file from the 'assets' directory
    private val applicationUrl = "file:///android_asset/index.html"
    private lateinit var binding: ActivityMainBinding
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webView = binding.webView

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    finish()
                }
            }
        }

        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true

        webView.loadUrl(applicationUrl)
    }
}
