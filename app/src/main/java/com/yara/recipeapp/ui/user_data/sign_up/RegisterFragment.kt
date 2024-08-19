package com.yara.recipeapp.ui.user_data.sign_up

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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.yara.recipeapp.Constants
import com.yara.recipeapp.R
import com.yara.recipeapp.ViewModel.UserViewModel
import com.yara.recipeapp.data.SharedPrefs
import com.yara.recipeapp.data.db.User
import com.yara.recipeapp.ui.activities.MainActivity2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var btnSignUP: Button
    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var edtPhone: EditText
    private lateinit var user_view_model: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user_view_model = ViewModelProvider(this)[UserViewModel::class.java]

        btnSignUP = view.findViewById(R.id.btnsignUP)
        edtEmail = view.findViewById(R.id.edtmail)
        edtPass = view.findViewById(R.id.edtpass)
        edtPhone = view.findViewById(R.id.edtphone)
        edtEmail.compoundDrawablePadding = 30
        edtPass.compoundDrawablePadding = 30
        edtPhone.compoundDrawablePadding = 30

        var email: String
        var pass: String
        var phone: String



        btnSignUP.setOnClickListener {
            email = edtEmail.text.toString()
            pass = edtPass.text.toString()
            phone = edtPhone.text.toString()

            when {
                TextUtils.isEmpty(email) -> errorSnackBar(view, Constants.EMAIL_FIELD_EMPTY)
                TextUtils.isEmpty(pass) -> errorSnackBar(view, Constants.PASSWORD_FIELD_EMPTY)
                TextUtils.isEmpty(phone) -> errorSnackBar(view, Constants.PHONE_FIELD_EMPTY)
                !validateEmail(email) -> errorSnackBar(view, Constants.EMAIL_NOT_VALID)
                !validatePassword(pass) -> errorSnackBar(view, Constants.PASSWORD_NOT_VALID)
                !validatePhone(phone) -> errorSnackBar(view, Constants.PHONE_NOT_VALID)
                else -> {
                    lifecycleScope.launch {
                        val user: User? = user_view_model.getUser(email)
                        if (user == null) {
                            val currentUser = User(0, email, pass, phone)
                            user_view_model.addUser(currentUser)

                            delay(10)
                            lifecycleScope.launch {
                                val testID: User? = user_view_model.getUser(email)
                                if (testID != null) {
                                    SharedPrefs.signIn(testID.id)
                                    val intent = Intent(activity, MainActivity2::class.java)
                                    startActivity(intent)
                                } else {
                                    errorSnackBar(view, Constants.ERROR_SIGNUP)
                                }
                            }
                        } else {
                            errorDialog(Constants.USER_EMAIL_EXISTS)
                        }
                    }
                }
            }
            edtEmail.text.clear()
            edtPass.text.clear()
            edtPhone.text.clear()
        }
    }

    private fun errorDialog(errorMessage:String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("OK"){_,_->}
        builder.setTitle("EROR")
        builder.setMessage(errorMessage)
        builder.create().show()
    }
    private fun errorSnackBar(view: View,errorMessage:String) {
        Snackbar.make(view,errorMessage, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun validateEmail(email: String): Boolean
    {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}"
        val regex = Regex(emailPattern)
        return regex.matches(email)
    }

    private fun validatePassword(password: String): Boolean
    {
        val strongPasswordPattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&_#])[A-Za-z\\d@\$!%*?&_#]{8,}"
        val regex = Regex(strongPasswordPattern)
        return regex.matches(password)
    }

    private fun validatePhone(phone: String): Boolean
    {
        val phonePattern = "01[\\d]{9,}"
        val regex = Regex(phonePattern)
        return regex.matches(phone)
    }


}