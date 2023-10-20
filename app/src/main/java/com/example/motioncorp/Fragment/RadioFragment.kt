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
import com.example.motioncorp.R
import com.example.motioncorp.databinding.FragmentHomeBinding
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
                    return true
                } else if (url == "https://radio.motioncorpbymmtc.id/index.php/stream-audio/") {
                    MyAsyncTask(myWebView).execute(url3)
                    return true
                }
                return false
            }
        }

        val webSetting: WebSettings = myWebView.settings
        webSetting.javaScriptEnabled = true

        myWebView.canGoBack()
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

    private inner class MyAsyncTask(private val webView: WebView) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg urls: String): String? {
            val url = urls[0]
            var document: Document? = null
            try {
                document = Jsoup.connect(url).get()
                document.getElementsByClass("skip-link screen-reader-text").remove()
                document.getElementsByClass("elementor elementor-2069 elementor-location-footer").remove()
                document.getElementsByClass("elementor elementor-2156 elementor-location-header").remove()
                document.getElementsByClass("elementor-button elementor-button-link elementor-size-sm").remove()
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
