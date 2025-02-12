package com.survey.hrms_chat_app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.survey.hrms_chat_app.model.LoginResponse

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var noDataTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        noDataTextView = findViewById(R.id.no_data_text)

        val response = getSavedLoginResponse()

        if (response == null) {
            Log.d("API Response", "No stored response. WebView will not be displayed.")
            webView.visibility = View.GONE
            noDataTextView.visibility = View.VISIBLE
        } else {
            Log.d("API Response", response.toString())
            webView.visibility = View.VISIBLE
            noDataTextView.visibility = View.GONE

            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.allowFileAccess = true
            webView.settings.allowContentAccess = true
            webView.settings.databaseEnabled = true

            webView.settings.setSupportZoom(true)
            webView.webViewClient=object:WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.d("WebView", "Page finished loading: $url")

                    //Set Login Response to local storage
                    saveLoginResponseToWebView(webView, response)

                    //Retrive Data from local storage
                    getLoginResponseFromWebView(webView) { response ->
                        if (response != null) {
                            Log.d("LoginResponse", "User: ${response.data.userFullInfo.userFullName}, Token: ${response.token}")
                        } else {
                            Log.d("LoginResponse", "No login response found in localStorage")
                        }
                    }
                }
            }

            webView.loadUrl("http://192.168.88.119:3000/")

            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                }
            })
        }
    }

    private fun getSavedLoginResponse(): LoginResponse? {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("LOGIN_RESPONSE", null)
        return json?.let { gson.fromJson(it, LoginResponse::class.java) }
    }

    fun saveLoginResponseToWebView(webView: WebView, loginResponse: LoginResponse) {
        val gson = Gson()
        val jsonResponse = gson.toJson(loginResponse)
        val safeJson = jsonResponse.replace("'", "\\'")
        val script = "localStorage.setItem('loginResponse', '$safeJson');"
        webView.evaluateJavascript(script, null)
    }

    fun getLoginResponseFromWebView(webView: WebView, callback: (LoginResponse?) -> Unit) {
        val script = "localStorage.getItem('loginResponse');"
        webView.evaluateJavascript(script) { jsonString ->
            if (!jsonString.isNullOrEmpty() && jsonString != "null") {
                val cleanJson = jsonString.replace("\\\"", "\"").trim('"')
                Log.d("WebView Local Storage", "Retrieved JSON: $cleanJson")

                val gson = Gson()
                val response = gson.fromJson(cleanJson, LoginResponse::class.java)
                callback(response)
            } else {
                callback(null)
            }
        }
    }
}
