package com.example.myfoodplannerapp.Auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myfoodplannerapp.HomeActivity.HomeActivity
import com.example.myfoodplannerapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class loginActivity : AppCompatActivity() {
    companion object {
        private const val RC_SIGN_IN = 9001
    }
    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        val email: EditText =findViewById(R.id.lgnEmail)
        val password: EditText =findViewById(R.id.lgnPassword)
        val btnRgstr: Button = findViewById(R.id.lgnBtn)
        btnRgstr.setOnClickListener {
            if(email.text.toString().isNotEmpty()&&password.text.toString().isNotEmpty()){
                mAuth.signInWithEmailAndPassword(email.text.toString(),password.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful)
                            startActivity(Intent(this, HomeActivity::class.java))
                        }
            }
            else
                Toast.makeText(this,"Empty", Toast.LENGTH_SHORT).show()
        }
        //val currentUser = mAuth.currentUser

//        if (currentUser != null) {
//            // The user is already signed in, navigate to MainActivity
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
//        }




        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
