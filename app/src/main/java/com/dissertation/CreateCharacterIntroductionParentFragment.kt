/* This class contains the code used for displaying the Dialog Fragment in the Create Character
Screen. */
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
    /*
    When this class is called, the Dialog Fragment will be displayed on the current screen. Elements
    from the screen are also found and stored within variables to be used elsewhere with the class.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.dialogFragmentTextView)
        val nextButton = view.findViewById<ImageButton>(R.id.dialogFragmentNextButton)

        nextButton.setOnClickListener {
            onNextButtonPressed(textView)
        }
        return view
    }

    /*
    When the class is called, show the first of the text passages on the Dialog Fragment.
     */
    override fun onViewCreated(
        view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        val textView = view.findViewById<TextView>(R.id.dialogFragmentTextView)
        textView.setText(R.string.createCharacterIntroduction1)
    }

    /*
    Store the created Dialog Fragment as a variable to be used elsewhere in the class.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    /*
    When the "Next" button is pressed, depending on the current textView content, change the text
    passage that is displayed or display the Enter Name user input fragment.
     */
    private fun onNextButtonPressed(textView: TextView) {
        if (textView.text.toString() == getString(R.string.createCharacterIntroduction1)) {
            textView.setText(R.string.createCharacterIntroduction2)
        } else if (textView.text.toString() == getString(R.string.createCharacterIntroduction2)) {
            textView.setText(R.string.createCharacterIntroduction3)
        } else if (textView.text.toString() == getString(R.string.createCharacterIntroduction3)) {
            val transaction = childFragmentManager.beginTransaction()
            val createCharacterEnterNameFragment = CreateCharacterEnterNameFragment()
            transaction.replace(R.id.dialogFragment, createCharacterEnterNameFragment)
            transaction.commit();
            textView.text = null
        } else {
            handleInput()
        }
    }

    /*
    Detect when the Dialog Fragment has been dismissed.
     */
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity: Activity? = activity
        if (activity is DialogInterface.OnDismissListener) {
            (activity as DialogInterface.OnDismissListener).onDismiss(dialog)
        }
    }

    /*
    When the "Next" button on the Enter Name fragment is pressed, if the user has entered a name,
    extract the name and pass it to the saveName function to be saved. If the user has not entered
    a name, display an alert message reminding them to enter a name.
     */
    private fun handleInput() {
        val editText = view?.findViewById<EditText>(R.id.createCharacterEnterNameEditText)
        val nameInput = editText?.text?.toString()
        if (nameInput != "") {
            saveName(nameInput)

        } else {
            showAlert()
        }
    }

    /*
    Add the entered name to the Shared Preferences. The name will be stored with the key "username"
    and the entered name as the value. The dialog fragment will then be dismissed.
     */
    override fun saveName(name: String?) {
        val sharedPreferences = this.activity!!.getSharedPreferences("application", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("username", name).apply()
        dialog?.dismiss()
    }

    /*
    If the user has not entered a name into the box, display an alert requesting that they enter
    a name.
     */
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