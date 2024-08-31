package com.yara.recipeapp.ui.fragments.signout

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.yara.recipeapp.R
import com.yara.recipeapp.ui.activities.MainActivity2


class SignOutDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity()
            .layoutInflater.inflate(R.layout.fragment_sign_out_dialog, null)
        val builder = AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(false)
        val dialog = builder.create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val buttonCancel: Button = view.findViewById(R.id.cancel)
        val buttonConfirm: Button = view.findViewById(R.id.delete)
        buttonCancel.setOnClickListener {
            dismiss()
        }
        buttonConfirm.setOnClickListener {
            (activity as? MainActivity2)?.signOut()
            dismiss()
        }
        return dialog
    }
}



