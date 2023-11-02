package com.example.motioncorp.Fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.motioncorp.R
import com.example.motioncorp.databinding.FragmentRadioBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class RadioFragment : Fragment() {
    private var _binding: FragmentRadioBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingMessage: TextView

    private val url1 = "https://radio.motioncorpbymmtc.id/"
    private val url2 = "https://radio.motioncorpbymmtc.id/motion-video-live/"
    private val url10 = "https://radio.motioncorpbymmtc.id/video-on-demand/"
    private val url3 = "https://radio.motioncorpbymmtc.id/motion-audio-live/"
    private val url4 = "https://radio.motioncorpbymmtc.id/damkar/"
    private val url5 = "https://radio.motioncorpbymmtc.id/fyi/"
    private val url6 = "https://radio.motioncorpbymmtc.id/on-duta/"
    private val url7 = "https://radio.motioncorpbymmtc.id/gema-budaya/"
    private val url8 = "https://radio.motioncorpbymmtc.id/2pm-show/"
    private val url9 = "https://radio.motioncorpbymmtc.id/20vers/"

    private var currentUrl: String = url1
    private var fullScreenUrl: String? = null // Tidak perlu diinisialisasi dengan url2
    private var isExitingFullScreen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRadioBinding.inflate(inflater, container, false)
        val root: View = binding.root


        progressBar = root.findViewById(R.id.Progressbar_Home)
        loadingMessage = root.findViewById(R.id.loadingMessage)
        loadingMessage.text = "Tunggu sebentar..."

        return root
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myWebView: WebView = view.findViewById(R.id.WebView3)
        val webSetting: WebSettings = myWebView.settings
        webSetting.javaScriptEnabled = true
        webSetting.setDomStorageEnabled(true)
        webSetting.allowFileAccess = true
        webSetting.allowContentAccess = true
        webSetting.mediaPlaybackRequiresUserGesture = false

        myWebView.settings.javaScriptEnabled = true

        showSoftKeyboard(myWebView)

        myWebView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && myWebView.canGoBack()) {
                myWebView.goBack()
                return@OnKeyListener true
            }
            false
        })

        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?, url: String?
            ): Boolean {
                binding.rootView.fitsSystemWindows = true
                binding.rootView.setPadding(
                    binding.rootView.paddingLeft,
                    binding.rootView.paddingTop,
                    binding.rootView.paddingRight,
                    binding.rootView.paddingBottom
                )

                when (MyAsyncTask(myWebView).execute(url).toString()) {
                    url2 -> myWebView.evaluateJavascript(
                        "javascript:  " + "var inputText = document.getElementById('password'); " + "password.value = 'test'; " + "password.dispatchEvent(new Event('input')) ",
                        null
                    );

                    url10 -> removeHeaderStyleRadio(myWebView)

                    url3 -> removeHeaderStyleRadio(myWebView)

                    url4 -> removeHeaderStyleRadio(myWebView)

                    url5 -> removeHeaderStyleRadio(myWebView)

                    url6 -> removeHeaderStyleRadio(myWebView)

                    url7 -> removeHeaderStyleRadio(myWebView)

                    url8 -> removeHeaderStyleRadio(myWebView)

                    url9 -> removeHeaderStyleRadio(myWebView)

                    else -> return true
                }
                return false
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
                        requireActivity().window.decorView.systemUiVisibility =
                            originalSystemUiVisibility
                        requireActivity().requestedOrientation = originalOrientation
                        customViewCallback?.onCustomViewHidden()
                        customViewCallback = null
                    }
                    isExitingFullScreen = false
                }
            }

            override fun onShowCustomView(
                paramView: View, paramCustomViewCallback: WebChromeClient.CustomViewCallback
            ) {
                if (customView != null) {
                    onHideCustomView()
                    return
                }

                customView = paramView
                originalSystemUiVisibility = requireActivity().window.decorView.systemUiVisibility
                originalOrientation = requireActivity().requestedOrientation
                customViewCallback = paramCustomViewCallback

                (requireActivity().window.decorView as FrameLayout).addView(
                    customView, ViewGroup.LayoutParams(-1, -1)
                )
                requireActivity().window.decorView.systemUiVisibility =
                    3846 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                fullScreenUrl = myWebView.url
            }
        }

        MyAsyncTask(myWebView).execute(url1)
    }


    private fun removeHeaderStyleRadio(myWebView: WebView) {
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
                document.getElementsByClass("elementor-section elementor-top-section elementor-element elementor-element-1667493 elementor-section-boxed elementor-section-height-default elementor-section-height-default")
                    .remove()
                document.getElementsByClass("elementor elementor-2156 elementor-location-header")
                    .remove()
                document.getElementsByClass("elementor elementor-2069 elementor-location-footer")
                    .remove()
                document.getElementsByClass("elementor-background-slideshow_slide_image").remove()
                document.getElementsByClass("elementor-menu-toggle__icon--open eicon-menu-bar")
                    .remove()
                document.getElementsByClass("attachment-full size-full wp-image-2474").remove()
                document.getElementsByClass("elementor elementor-2069 elementor-location-footer")
                    .remove()
                document.getElementsByClass("elementor-section elementor-top-section elementor-element elementor-element-2ff5023f elementor-section-height-min-height elementor-section-boxed elementor-section-height-default elementor-section-items-middle")
                    .remove()

                document.getElementsByClass("has_eae_slider elementor-section elementor-inner-section elementor-element elementor-element-55a947c5 elementor-section-boxed elementor-section-height-default elementor-section-height-default elementor-sticky")
                    .remove()
                document.getElementsByClass("elementor-element elementor-element-c030feb elementor-widget elementor-widget-image")
                    .remove()
                document.getElementsByClass("elementor-element elementor-element-0a9d5e8 elementor-widget elementor-widget-button")
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
