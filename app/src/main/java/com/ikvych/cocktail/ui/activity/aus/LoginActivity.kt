package com.ikvych.cocktail.ui.activity.aus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.ikvych.cocktail.R
import com.ikvych.cocktail.ui.activity.MainActivity

class LoginActivity : AppCompatActivity() {

    val login: String = "l"
    val password: String = "1"

    lateinit var loginView: EditText
    lateinit var passwordView: EditText
    lateinit var button: Button
    var filter: InputFilter = object :
        InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence {
            if (source != null) {
                var text = source.toString()
                text = text.replace(" ", "")
                text = text.replace("\t", "")
                return text
            }
            return ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginView = findViewById(R.id.login)
        loginView.filters = arrayOf(filter)
        passwordView = findViewById(R.id.password)
        passwordView.filters = arrayOf(filter)
        button = findViewById(R.id.button)

        button.setOnClickListener(onLoginButtonListener())


        val textWatcher: TextWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                invalidateAuthData()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }

        loginView.addTextChangedListener(textWatcher)
        passwordView.addTextChangedListener(textWatcher)
        invalidateAuthData()
    }

    private fun onLoginButtonListener(): (v: View) -> Unit {
        return {
            val intent = Intent(this, MainActivity::class.java)
            closeKeyboard()
            startActivity(intent)
        }
    }

    private fun closeKeyboard() {
        val view: View = this.currentFocus ?: return
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

    private fun invalidateAuthData() {
        val login: String = loginView.text.toString()
        val password = passwordView.text.toString()

        button.isEnabled = login == this@LoginActivity.login && password == this@LoginActivity.password
    }





}
