package com.example.motioncorp.Fragment

import android.content.Intent
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
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import com.example.motioncorp.R
import com.example.motioncorp.databinding.FragmentInfoBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null

    private lateinit var progressBar: ProgressBar
    private lateinit var loadingMessage: TextView

    private val binding get() = _binding!!
    private val url1 = "https://motioncorpbymmtc.id/info-perusahaan/"
    private var currentUrl: String = url1 // Menyimpan URL saat ini

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressBar = root.findViewById(R.id.Progressbar_Home)
        loadingMessage = root.findViewById(R.id.loadingMessage)
        loadingMessage.text = "Tunggu sebentar..."

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myWebView: WebView = view.findViewById(R.id.WebView5)
        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {

                currentUrl = url1
                view?.loadUrl(url1)
                return true
            }
        }

        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                if (isExternalLink(url)) {
                    openExternalLink(url)
                    return true
                } else {
                    currentUrl = url1
                    view?.loadUrl(url1)
                    return true
                }
            }
        }

        val webSetting: WebSettings = myWebView.settings
        webSetting.javaScriptEnabled = true
        webSetting.setDomStorageEnabled(true)
        webSetting.allowFileAccess = true
        webSetting.allowContentAccess = true
        webSetting.mediaPlaybackRequiresUserGesture = false

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
                document.getElementsByClass("elementor-element elementor-element-456e12b e-flex e-con-boxed wpr-particle-no wpr-jarallax-no wpr-parallax-no wpr-sticky-section-no e-con e-parent").remove()
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
        }
    }

    private fun isExternalLink(url: String?): Boolean {
        val isExternal = url != null && (
                url.startsWith("https://www.linkedin.com/") ||
                        url.startsWith("https://www.youtube.com/") || url.contains("youtube.com") ||
                        url.startsWith("https://www.tiktok.com/") ||
                        url.startsWith("https://www.instagram.com/") || url.startsWith("https://instagram.com/")

                )
        Log.d("ExternalLinkCheck", "URL: $url isExternal: $isExternal")
        return isExternal
    }




    private fun openExternalLink(url: String?) {
        if (url != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
