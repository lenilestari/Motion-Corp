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
import android.widget.ProgressBar
import android.widget.TextView
import com.example.motioncorp.R
import com.example.motioncorp.databinding.FragmentNewsBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null

    private lateinit var progressBar: ProgressBar
    private lateinit var loadingMessage: TextView

    private val binding get() = _binding!!
    private val url1 = "https://news.motioncorpbymmtc.id/"
    private val url2 = "https://news.motioncorpbymmtc.id/tren/"
    private val url3 = "https://news.motioncorpbymmtc.id/viral/"
    private val url4 = "https://news.motioncorpbymmtc.id/nasional/"
    private val url5 = "https://news.motioncorpbymmtc.id/internasional/"
    private val url6 = "https://news.motioncorpbymmtc.id/lifestyle/"
    private val url7 = "https://news.motioncorpbymmtc.id/sport/"
    private val url8 = "https://news.motioncorpbymmtc.id/technology/"
    private val url9 = "https://news.motioncorpbymmtc.id/celebrity/"
    private val url10 = "https://news.motioncorpbymmtc.id/editorial/"
    private val url11 = "https://news.motioncorpbymmtc.id/infografis/"
    private val url12 = "https://news.motioncorpbymmtc.id/video/"
    private val url13 = "https://news.motioncorpbymmtc.id/cek-fakta/"
    private val url14 = "https://news.motioncorpbymmtc.id/fakta-patung-pancoran-karya-edhi-sunarso/"
    private val url15 =
        "https://news.motioncorpbymmtc.id/papeda-mendunia-google-doodle-menampilkan-tema-merayakan-papeda/"
    private val url16 =
        "https://news.motioncorpbymmtc.id/prediksi-bacawapres-pendamping-prabowo-di-pilpres-2024/"
    private val url17 =
        "https://news.motioncorpbymmtc.id/mengenal-batik-durian-yang-berhasil-eksis-di-milan-fashion-week/"
    private val url18 =
        "https://news.motioncorpbymmtc.id/johnny-plate-akui-menyesal-proyek-bts-4g-tak-rampung/"
    private val url19 =
        "https://news.motioncorpbymmtc.id/capai-work-life-balance-untuk-tingkatkan-produktivitas-kerja/"
    private var currentUrl: String = url1 // Menyimpan URL saat ini

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressBar = root.findViewById(R.id.Progressbar_Home)
        loadingMessage = root.findViewById(R.id.loadingMessage)
        loadingMessage.text = "Tunggu sebentar..."

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myWebView: WebView = view.findViewById(R.id.WebView4)
        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                if (url == "https://news.motioncorpbymmtc.id/tren/") {
                    MyAsyncTask(myWebView).execute(url2)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/viral/") {
                    MyAsyncTask(myWebView).execute(url3)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/nasional/") {
                    MyAsyncTask(myWebView).execute(url4)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/internasional/") {
                    MyAsyncTask(myWebView).execute(url5)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/lifestyle/") {
                    MyAsyncTask(myWebView).execute(url6)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/sport/") {
                    MyAsyncTask(myWebView).execute(url7)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/technology/") {
                    MyAsyncTask(myWebView).execute(url8)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/celebrity/") {
                    MyAsyncTask(myWebView).execute(url9)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/editorial/") {
                    MyAsyncTask(myWebView).execute(url10)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/infografis/") {
                    MyAsyncTask(myWebView).execute(url11)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/video/") {
                    MyAsyncTask(myWebView).execute(url12)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/cek-fakta/") {
                    MyAsyncTask(myWebView).execute(url13)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/fakta-patung-pancoran-karya-edhi-sunarso/") {
                    MyAsyncTask(myWebView).execute(url14)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/papeda-mendunia-google-doodle-menampilkan-tema-merayakan-papeda/") {
                    MyAsyncTask(myWebView).execute(url15)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/prediksi-bacawapres-pendamping-prabowo-di-pilpres-2024/") {
                    MyAsyncTask(myWebView).execute(url16)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/mengenal-batik-durian-yang-berhasil-eksis-di-milan-fashion-week/") {
                    MyAsyncTask(myWebView).execute(url17)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/johnny-plate-akui-menyesal-proyek-bts-4g-tak-rampung/") {
                    MyAsyncTask(myWebView).execute(url18)
                    return true
                } else if (url == "https://news.motioncorpbymmtc.id/capai-work-life-balance-untuk-tingkatkan-produktivitas-kerja/") {
                    MyAsyncTask(myWebView).execute(url19)
                    return true
                }
                return false
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
                document.getElementsByClass("elementor elementor-490 elementor-location-footer")
                    .remove()
                document.getElementsByClass("elementor-button-wrapper").remove()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
