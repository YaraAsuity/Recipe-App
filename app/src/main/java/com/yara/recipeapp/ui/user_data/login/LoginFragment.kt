package com.yara.recipeapp.ui.user_data.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.yara.recipeapp.Constants
import com.yara.recipeapp.R
import com.yara.recipeapp.ui.user_data.UserViewModel
import com.yara.recipeapp.data.SharedPrefs
import com.yara.recipeapp.data.db.User
import com.yara.recipeapp.ui.activities.MainActivity2
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var btnlogin : Button
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var EDTemail: EditText
    private lateinit var EDTpass : EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        view.findViewById<TextView>(R.id.txtSignUP).setOnClickListener {
            goToRegister(view)
        }
        view.findViewById<TextView>(R.id.forgotPass).setOnClickListener {
            // errorDialog("Forgot Password"")
        }

        btnlogin = view.findViewById(R.id.btnLogin)
        EDTemail = view.findViewById(R.id.logMail)
        EDTpass = view.findViewById(R.id.logPass)
        EDTemail.compoundDrawablePadding = 30
        EDTpass.compoundDrawablePadding = 30

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        btnlogin.setOnClickListener{

            val email = EDTemail.text.toString()
            val pass = EDTpass.text.toString()

            if( TextUtils.isEmpty(email) ){
                errorSnackBar(view,Constants.EMAIL_FIELD_EMPTY,pass)
            }else if(TextUtils.isEmpty(pass)){
                errorSnackBar(view,Constants.PASSWORD_FIELD_EMPTY,pass)
            }
            else{
                lifecycleScope.launch {
                    val user : User? = mUserViewModel.getUser(email)
                    if (user != null){
                        if(pass == user.password){
                            SharedPrefs.signIn(user.id)
                            val intent = Intent(activity, MainActivity2::class.java)
                            startActivity(intent)
                        }else{
                            errorSnackBar(view,Constants.PASSWORD_NOT_VALID,user.password)

                        }

                    }else {
                        errorDialogToRegister(view)

                    }

                }

            }

        }

        return view
    }

    private fun goToRegister(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun errorDialog(title:String,message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("OK") { _, _ -> }
        builder.setTitle(title)
        builder.setMessage(message)
        builder.create().show()
    }
    private fun errorSnackBar(view: View,errorMessage:String,pass:String) {
        Snackbar.make(view,errorMessage, Snackbar.LENGTH_LONG)
            .setAction(Constants.OK){
                errorDialog("password","your password is \" $pass \" \nBe honest don't use it to find out users' passwords ")
            }
            .show()
    }
    private fun errorDialogToRegister( view: View) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(Constants.SIGN_UP){_,_->
            goToRegister(view)
            EDTemail.text.clear()
            EDTpass.text.clear()
        }
        builder.setNegativeButton(Constants.STAY){_,_ -> }
        builder.setTitle(Constants.ERROR_TITLE)
        builder.setMessage(Constants.NOT_FOUND_EMAIL)
        builder.create().show()
    }
}
