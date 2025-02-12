package com.survey.hrms_chat_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.survey.hrms_chat_app.model.LoginResponse
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(getSavedLoginResponse()!=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        usernameEditText = findViewById(R.id.inputUsername)
        passwordEditText = findViewById(R.id.inputPassword)
        loginButton = findViewById(R.id.button4)

        loginButton.setOnClickListener {

            if (usernameEditText.text.toString().isEmpty() && passwordEditText.text.toString()
                    .isEmpty()
            ) {
                usernameEditText.error = "Username cannot be empty"
                passwordEditText.error = "Password cannot be empty"
            } else if (usernameEditText.text.toString().isEmpty()) {
                usernameEditText.error = "Username cannot be empty"
            } else if (passwordEditText.text.toString().isEmpty()) {
                passwordEditText.error = "Password cannot be empty"
            }
            else {
                loginApiCall()
            }
        }


    }


    private fun loginApiCall() {
        val url = "http://192.168.88.214:8082/api/v1/authenticate"

        val username = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        val params = JSONObject()
        params.put("username", username)
        params.put("password", password)
        params.put("status", "WEB")

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, params,
            { response ->
                Log.d("API Response", response.toString())

                val loginResponse = Gson().fromJson(response.toString(), LoginResponse::class.java)

                setSharedPreferenced(loginResponse)

                val token = loginResponse.token
                Log.d("User Token", token)
                val userFullName = loginResponse.data.userFullInfo.userFullName
                Toast.makeText(this, "Welcome, $userFullName!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            },
            { error ->
                Log.e("API Error", error.toString())
                Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }


    private fun setSharedPreferenced(loginResponse: LoginResponse) {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(loginResponse)
        editor.putString("LOGIN_RESPONSE", json)
        editor.apply()
    }

    private fun getSavedLoginResponse(): LoginResponse? {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("LOGIN_RESPONSE", null)
        return json?.let { gson.fromJson(it, LoginResponse::class.java) }
    }

}