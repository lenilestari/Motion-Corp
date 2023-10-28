package com.example.motioncorp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.motioncorp.databinding.ActivityStreamVideoBinding

class StreamVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStreamVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStreamVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myWebView: WebView = binding.WebViewStreamVideo

        myWebView.loadUrl("https://radio.motioncorpbymmtc.id/index.php/stream-video/")
    }


}