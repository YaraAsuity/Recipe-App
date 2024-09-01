package com.yara.recipeapp.ui.user_data.login

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
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.yara.recipeapp.R
import com.yara.recipeapp.ui.user_data.UserViewModel
import kotlinx.coroutines.launch

class ForgotPasswordFragment : Fragment() {

    private lateinit var edtEmail: EditText
    private lateinit var btnSubmit: Button
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)
        edtEmail = view.findViewById(R.id.edtEmail)
        btnSubmit = view.findViewById(R.id.btnSubmit)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        btnSubmit.setOnClickListener {
            val email = edtEmail.text.toString()

            if (TextUtils.isEmpty(email)) {
                Snackbar.make(view, "Please enter your email", Snackbar.LENGTH_LONG).show()
            } else {
                lifecycleScope.launch {
                    val user = userViewModel.getUser(email)
                    if (user != null) {
                        val action = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToResetPasswordFragment(user.id)
                        findNavController().navigate(action)
                    } else {
                        Snackbar.make(view, "No account found with this email", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        return view
    }
}