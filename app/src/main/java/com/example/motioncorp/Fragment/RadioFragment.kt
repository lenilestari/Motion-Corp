package com.example.motioncorp.Fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.AsyncTask
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.motioncorp.R
import com.example.motioncorp.StreamVideoActivity
import com.example.motioncorp.databinding.FragmentRadioBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class RadioFragment : Fragment() {
    private var _binding: FragmentRadioBinding? = null
    private val binding get() = _binding!!
    private val url1 = "https://radio.motioncorpbymmtc.id/"
    private val url2 = "https://radio.motioncorpbymmtc.id/index.php/stream-video/"
    private val url3 = "https://radio.motioncorpbymmtc.id/index.php/stream-audio/"
    private val url4 = "https://radio.motioncorpbymmtc.id/index.php/damkar/"
    private val url5 = "https://radio.motioncorpbymmtc.id/index.php/fyi/"
    private var currentUrl: String = url1 // Menyimpan URL saat ini

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRadioBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myWebView: WebView = view.findViewById(R.id.WebView3)
        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                if (url == "https://radio.motioncorpbymmtc.id/index.php/stream-video/") {
                    MyAsyncTask(myWebView).execute(url2)
                    val intent = Intent(view?.context, StreamVideoActivity::class.java)
                    startActivity(intent)
                } else if (url == "https://radio.motioncorpbymmtc.id/index.php/stream-audio/") {
                    MyAsyncTask(myWebView).execute(url3)
                    return true
                } else if (url == "https://radio.motioncorpbymmtc.id/index.php/damkar/") {
                    MyAsyncTask(myWebView).execute(url4)
                } else if (url == "https://radio.motioncorpbymmtc.id/index.php/fyi/") {
                    MyAsyncTask(myWebView).execute(url5)
                }
                return false
            }
        }


        myWebView.webChromeClient = object : WebChromeClient() {
            override fun onShowCustomView(
                view: View?,
                requestedOrientation: Int,
                callback: CustomViewCallback?
            ) {
                // Ganti orientasi layar ke landscape
                activity?.requestedOrientation = requestedOrientation
                super.onShowCustomView(view, requestedOrientation, callback)
                // Di sini, tambahkan kode JavaScript untuk mengontrol fullscreen
                activity?.requestedOrientation = requestedOrientation
                binding.WebView3.visibility = View.GONE
//                binding.customView3.visibility = View.VISIBLE
//                binding.customView3.addView(view)
                val javascriptCode = """
             
                    var iframe = document.getElementById("widget2");
                    if (iframe && (iframe.requestFullscreen || iframe.mozRequestFullScreen || iframe.webkitRequestFullscreen)) {
                        if (iframe.requestFullscreen) {
                            iframe.requestFullscreen();
                        } else if (iframe.mozRequestFullScreen) {
                            iframe.mozRequestFullScreen();
                        } else if (iframe.webkitRequestFullscreen) {
                            iframe.webkitRequestFullscreen();
                        }
                    }
                """
                myWebView.evaluateJavascript(javascriptCode, null)
            }

            override fun onHideCustomView() {
                // Kembali ke orientasi potret
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                super.onHideCustomView()

                binding.WebView3.visibility = View.VISIBLE
//                binding.customView3.visibility = View.GONE
            }
        }

        val webSetting: WebSettings = myWebView.settings
        webSetting.javaScriptEnabled = true
        webSetting.setDomStorageEnabled(true)
        webSetting.allowFileAccess = true
        webSetting.allowContentAccess = true
        webSetting.mediaPlaybackRequiresUserGesture = false

        myWebView.canGoBack()
        myWebView.settings.javaScriptEnabled = true

        myWebView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                && event.action == MotionEvent.ACTION_UP
                && myWebView.canGoBack()
            ) {
                myWebView.goBack()
                return@OnKeyListener true
            }
            false
        })

        // Menggunakan AsyncTask untuk mengambil dan memproses HTML
        MyAsyncTask(myWebView).execute(url1)
    }

    private inner class MyAsyncTask(private val webView: WebView) :
        AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg urls: String): String? {
            val url = urls[0]
            var document: Document? = null
            try {
                document = Jsoup.connect(url).get()
                document.getElementsByClass("skip-link screen-reader-text").remove()
                document.getElementsByClass("elementor-element elementor-element-19762840 elementor-widget elementor-widget-theme-site-logo elementor-widget-image")
                    .remove()
                document.getElementsByClass("elementor elementor-2069 elementor-location-footer")
                    .remove()
                document.getElementsByClass("elementor elementor-2572 elementor-location-header")
                    .remove()
                document.getElementsByClass("skip-link screen-reader-text").remove()
                document.getElementsByClass("elementor elementor-2156 elementor-location-header")
                    .remove()
                document.getElementsByClass("elementor-section elementor-top-section elementor-element elementor-element-2ff5023f elementor-section-height-min-height elementor-section-boxed elementor-section-height-default elementor-section-items-middle")
                    .remove()
                document.getElementsByClass("elementor-section elementor-top-section elementor-element elementor-element-1667493 elementor-section-boxed elementor-section-height-default elementor-section-height-default")
                    .remove()
                document.getElementsByClass("elementor elementor-2156 elementor-location-header")
                    .remove()
                document.getElementsByClass("elementor-button elementor-button-link elementor-size-sm")
                    .remove()
                document.getElementsByClass("elementor elementor-2069 elementor-location-footer")
                    .remove()
                document.getElementsByClass("elementor-background-slideshow__slide__image").remove()
                document.getElementsByClass("elementor-section elementor-top-section elementor-element elementor-element-5051ca45 elementor-section-boxed elementor-section-height-default elementor-section-height-default")
                    .remove()
                document.getElementsByClass("elementor-menu-toggle__icon--open eicon-menu-bar")
                    .remove()
                document.getElementsByClass("attachment-full size-full wp-image-2474").remove()

            } catch (e: IOException) {
                e.printStackTrace()
            }
            return document?.toString()
        }

        override fun onPostExecute(html: String?) {
            super.onPostExecute(html)
            if (html != null) {
                val modifiedHtml = "<html>$html</html>"
                webView.loadDataWithBaseURL(currentUrl, modifiedHtml, "text/html", "utf-8", "")
                webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
