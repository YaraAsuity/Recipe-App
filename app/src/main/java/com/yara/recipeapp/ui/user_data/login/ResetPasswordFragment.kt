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

class ResetPasswordFragment : Fragment() {

    private lateinit var edtNewPass: EditText
    private lateinit var edtConfirmPass: EditText
    private lateinit var btnReset: Button
    private lateinit var userViewModel: UserViewModel
    private var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reset_password, container, false)
        edtNewPass = view.findViewById(R.id.edtNewPass)
        edtConfirmPass = view.findViewById(R.id.edtConfirmPass)
        btnReset = view.findViewById(R.id.btnReset)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        userId = arguments?.getInt("userId") ?: -1

        btnReset.setOnClickListener {
            val newPass = edtNewPass.text.toString()
            val confirmPass = edtConfirmPass.text.toString()

            if (TextUtils.isEmpty(newPass) || TextUtils.isEmpty(confirmPass)) {
                Snackbar.make(view, "Please fill all fields", Snackbar.LENGTH_LONG).show()
            } else if (newPass != confirmPass) {
                Snackbar.make(view, "Passwords do not match", Snackbar.LENGTH_LONG).show()
            } else {
                lifecycleScope.launch {
                    userViewModel.updatePassword(userId, newPass)
                    Snackbar.make(view, "Password reset successfully", Snackbar.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
                }
            }
        }

        return view
    }
}