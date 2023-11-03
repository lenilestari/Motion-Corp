package com.example.motioncorp.Fragment

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.motioncorp.R
import com.example.motioncorp.databinding.FragmentHomeBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var progressBar: ProgressBar
    private lateinit var loadingMessage: TextView

    private val binding get() = _binding!!
    private val url1 = "https://motioncorpbymmtc.id/"
    private val url2 = "https://tv.motioncorpbymmtc.id/"
    private val url3 = "https://tv.motioncorpbymmtc.id/motion-tv-live/"
    private val url4 = "https://radio.motioncorpbymmtc.id/motion-audio-live/"
    private var currentUrl: String = url1 // Menyimpan URL saat ini

    private var fullScreenUrl: String? = null // Tidak perlu diinisialisasi dengan url2
    private var isExitingFullScreen = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressBar = root.findViewById(R.id.Progressbar_Home)
        loadingMessage = root.findViewById(R.id.loadingMessage)
        loadingMessage.text = "Tunggu sebentar..."

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myWebView: WebView = view.findViewById(R.id.WebView1)
        val webSetting: WebSettings = myWebView.settings
        webSetting.javaScriptEnabled = true
        webSetting.setDomStorageEnabled(true)
        webSetting.allowFileAccess = true
        webSetting.allowContentAccess = true
        webSetting.mediaPlaybackRequiresUserGesture = false

        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH)
        myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

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
                if (isExternalLink(url)) {
                    openExternalLink(url)
                    return true
                } else {
                    val result = MyAsyncTask(myWebView).execute(url).get()
                    when (result) {
                        url2 -> {
                            removeHeaderStyleTv(myWebView)
                            return true
                        }
                        url3 -> {
                            removeHeaderStyleTv(myWebView)
                            return true
                        }
                        url4 -> {
                            removeHeaderStyleTv(myWebView)
                            return true
                        }
                        else -> return true
                    }
                }
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

//                        // Ganti kembali ke orientasi semula
//                        requireActivity().requestedOrientation = originalOrientation

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

//                // Ganti orientasi ke landscape
//                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

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

    private inner class MyAsyncTask(private val webView: WebView) : AsyncTask<String, Void, String>() {
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
                document.getElementsByClass("elementor elementor-867 elementor-location-header").remove()
                document.getElementsByClass("elementor elementor-880 elementor-location-footer").remove()
                document.getElementsByClass("elementor elementor-24 elementor-location-header").remove()
                document.getElementsByClass("elementor elementor-40 elementor-location-footer").remove()
                document.getElementsByClass("elementor-element elementor-element-3232a414 e-con-full e-flex e-con e-child").remove()
                document.getElementsByClass("elementor elementor-40 elementor-location-footer").remove()
                document.getElementsByClass("elementor-element elementor-element-1f524a1 elementor-widget elementor-widget-button").remove()
                document.getElementsByClass("elementor-element elementor-element-5338298 elementor-mobile-align-right elementor-widget elementor-widget-button").remove()

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

    private fun isExternalLink(url: String?): Boolean {
        val isExternal = url != null && (
                url.startsWith("https://www.facebook.com/") ||
                        url.startsWith("https://twitter.com/") || url.contains("twitter.com") ||
                        url.startsWith("https://whatsapp.com/") || url.contains("whatsapp.com")
                )
        Log.d("ExternalLinkCheck", "URL: $url isExternal: $isExternal")
        return isExternal
    }

    private fun openExternalLink(url: String?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
