package com.dissertation

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.dissertation.CreateCharacterEnterNameFragment.CreateCharacterEnterNameFragmentListener


class CreateCharacterIntroductionParentFragment : DialogFragment(), CreateCharacterEnterNameFragmentListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_character_introduction_parent_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.createCharacterIntroductionParentTextView)
        val nextButton = view.findViewById<ImageButton>(R.id.createCharacterIntroductionParentNextButton)

        nextButton.setOnClickListener {
            onNextButtonPressed(textView)
        }
        return view
    }

    override fun onViewCreated(
        view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        val textView = view.findViewById<TextView>(R.id.createCharacterIntroductionParentTextView)
        textView.setText(R.string.createCharacterIntroduction1)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    private fun onNextButtonPressed(textView: TextView) {
        if (textView.text.toString() == getString(R.string.createCharacterIntroduction1)) {
            textView.setText(R.string.createCharacterIntroduction2)
        } else if (textView.text.toString() == getString(R.string.createCharacterIntroduction2)) {
            textView.setText(R.string.createCharacterIntroduction3)
        } else if (textView.text.toString() == getString(R.string.createCharacterIntroduction3)) {
            val transaction = childFragmentManager.beginTransaction()
            val createCharacterEnterNameFragment = CreateCharacterEnterNameFragment()
            transaction.replace(R.id.createCharacterIntroductionParentFragment, createCharacterEnterNameFragment)
            transaction.commit();
            textView.text = null
        } else {
            handleInput()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity: Activity? = activity
        if (activity is DialogInterface.OnDismissListener) {
            (activity as DialogInterface.OnDismissListener).onDismiss(dialog)
        }
    }

    private fun handleInput() {
        val editText = view?.findViewById<EditText>(R.id.createCharacterEnterNameEditText)
        val nameInput = editText?.text?.toString()
        if (nameInput != "") {
            saveName(nameInput)

        } else {
            showAlert()
        }
    }

    override fun saveName(name: String?) {
        val sharedPreferences = this.activity!!.getSharedPreferences("application", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("username", name).apply()
        dialog?.dismiss()
    }

    override fun showAlert() {
        val alertBoxBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        alertBoxBuilder.setMessage("Please enter a name.")
        alertBoxBuilder.setCancelable(true)

        alertBoxBuilder.setPositiveButton(
            "Okay"
        ) { dialog, id -> dialog.cancel() }

        val enterNameAlert: AlertDialog = alertBoxBuilder.create()
        enterNameAlert.show()
    }
}