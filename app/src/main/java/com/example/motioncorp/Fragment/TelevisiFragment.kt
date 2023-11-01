package com.example.motioncorp.Fragment

import android.os.AsyncTask
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.widget.ProgressBar
import android.widget.TextView
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.ViewGroup.LayoutParams
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.widget.FrameLayout
import com.example.motioncorp.R
import com.example.motioncorp.databinding.FragmentTelevisiBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class TelevisiFragment : Fragment() {
    private var _binding: FragmentTelevisiBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingMessage: TextView

    private val url1 = "https://tv.motioncorpbymmtc.id/"
    private val url2 = "https://tv.motioncorpbymmtc.id/motion-tv-live"
    private var currentUrl: String = url1

    private var fullScreenUrl: String? = null
    private var isExitingFullScreen = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTelevisiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressBar = root.findViewById(R.id.Progressbar_Home)
        loadingMessage = root.findViewById(R.id.loadingMessage)
        loadingMessage.text = "Tunggu sebentar..."

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myWebView: WebView = view.findViewById(R.id.WebView2)
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

        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
               when (MyAsyncTask(myWebView).execute(url).toString()) {
                   url2 -> removeHeaderStyleTv(myWebView)
                   else -> return true
               }
                return false
            }

            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                // Mengecek URL permintaan
                val url = request?.url.toString()

                // URL yang ingin Anda blokir
                val blockedUrl = "https://organizations.minnit.chat/js/logjserror.js?mid=1693053978"

                if (url == blockedUrl) {
                    // Blokir permintaan ke URL yang Anda tentukan
                    return WebResourceResponse("text/javascript", "utf-8", null)
                }

                // Izinkan permintaan sumber eksternal lainnya
                return super.shouldInterceptRequest(view, request)
                Log.d("Minnit chat", "Blokir TTS")
            }
        }

        myWebView.webChromeClient = object : WebChromeClient() {
            var originalOrientation = 0
            var originalSystemUiVisibility = 0
            var customView: View? = null
            var customViewCallback: WebChromeClient.CustomViewCallback? = null

            override fun getDefaultVideoPoster(): Bitmap? {
                return customView?.let {
                    BitmapFactory.decodeResource(resources, 2130837573)
                } ?: null
            }

            override fun onHideCustomView() {
                if (customView != null) {
                    if (isExitingFullScreen) {
                        if (fullScreenUrl != null) {
                            MyAsyncTask(myWebView).execute(fullScreenUrl)
                            fullScreenUrl = null
                        }
                    } else {
                        (requireActivity().window.decorView as FrameLayout).removeView(customView)
                        customView = null
                        requireActivity().window.decorView.systemUiVisibility = originalSystemUiVisibility
                        requireActivity().requestedOrientation = originalOrientation
                        customViewCallback?.onCustomViewHidden()
                        customViewCallback = null
                    }
                    isExitingFullScreen = false
                }
            }

            override fun onShowCustomView(paramView: View, paramCustomViewCallback: WebChromeClient.CustomViewCallback) {
                if (customView != null) {
                    onHideCustomView()
                    return
                }

                customView = paramView
                originalSystemUiVisibility = requireActivity().window.decorView.systemUiVisibility
                originalOrientation = requireActivity().requestedOrientation
                customViewCallback = paramCustomViewCallback

                (requireActivity().window.decorView as FrameLayout).addView(customView,
                    ViewGroup.LayoutParams(-1, -1)
                )
                requireActivity().window.decorView.systemUiVisibility = 3846 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                fullScreenUrl = myWebView.url
            }
        }

        MyAsyncTask(myWebView).execute(url1)
    }


    private fun removeHeaderStyleTv(myWebView: WebView) {
        myWebView.loadUrl(
            "javascript:(function() { " + "var head = document.getElementsByTagName('footer')[0];" + "head.parentNode.removeChild(head);" + "})()"
        );
    }

    private inner class MyAsyncTask(private val webView: WebView) :
        AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
            loadingMessage.text = "Tunggu sebentar..."
        }

        override fun doInBackground(vararg urls: String): String? {
            val url = urls[0]
            var document: Document? = null
            try {
                document = Jsoup.connect(url).get()
                document.getElementsByClass("elementor elementor-24 elementor-location-header")
                    .remove()
                document.getElementsByClass("elementor elementor-40 elementor-location-footer")
                    .remove()
                document.getElementsByClass("elementor elementor-132 elementor-location-header")
                    .remove()
                document.getElementsByClass("elementor elementor-40 elementor-location-footer")
                    .remove()
                document.getElementsByClass("elementor-element elementor-element-5338298 elementor-mobile-align-right elementor-widget elementor-widget-button")
                    .remove()
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

            progressBar.visibility = View.GONE
            loadingMessage.text = ""

            val javascriptCode = """
                var iframes = document.getElementsByTagName('iframe');
                for (var i = 0; i < iframes.length; i++) {
                var iframe = iframes[i];
                iframe.setAttribute('allowfullscreen', 'true');
                }
                
                if (window.speechSynthesis) {
                // Hanya jika window.speechSynthesis tersedia
                window.speechSynthesis.cancel(); // Menonaktifkan TTS
                if (window.speechSynthesis.getVoices) {
                window.speechSynthesis.getVoices = function() { return []; }; // Mengembalikan daftar suara kosong 
                } 
            }

            """

            webView.post {
                webView.evaluateJavascript(javascriptCode, null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
