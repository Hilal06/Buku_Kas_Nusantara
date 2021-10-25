package org.test.bukukasnusantara.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.test.bukukasnusantara.R
import org.test.bukukasnusantara.fragment.LoginFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportFragmentManager.beginTransaction()
            .replace(R.id.login_activity, LoginFragment())
            .addToBackStack(null)
            .commit()

    }
}