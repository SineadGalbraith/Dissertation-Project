/* This class contains the code used for displaying the Single Player Level 1 Introduction Fragment. */
package com.dissertation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment

class SinglePlayerLevel1IntroductionFragment :  DialogFragment() {

    /*
    When this class is called, display the Dialog Fragment and store the components of the
    fragment locally.

    If the finished button is pressed, close the Dialog Fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.dialogFragmentTextView)
        val nextButton = view.findViewById<ImageButton>(R.id.dialogFragmentNextButton)
        val finishedButton = view.findViewById<ImageButton>(R.id.dialogFragmentFinishedButton)

        nextButton.setOnClickListener {
            onNextButtonPressed(textView, nextButton, finishedButton)
        }

        finishedButton.setOnClickListener {
            dialog?.dismiss()
        }
        return view
    }

    /*
    When the class is called, display the first of the text passages for the Dialog Fragment.
     */
    override fun onViewCreated(
        view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        val textView = view.findViewById<TextView>(R.id.dialogFragmentTextView)
        textView.setText(R.string.singlePlayerLevel1Introduction1)
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
    Depending on the current displayed text passage, change the content of the textView when the
    "Next" button is pressed.
     */
    private fun onNextButtonPressed(textView: TextView, nextButton: ImageButton, finishedButton: ImageButton) {
        if (textView.text.toString() == getString(R.string.singlePlayerLevel1Introduction1)) {
            textView.setText(R.string.singlePlayerLevel1Introduction2)
        } else if (textView.text.toString() == getString(R.string.singlePlayerLevel1Introduction2)) {
            textView.setText(R.string.singlePlayerLevel1Introduction3)
        } else if (textView.text.toString() == getString(R.string.singlePlayerLevel1Introduction3)) {
            textView.setText(R.string.singlePlayerLevel1Introduction4)
        } else if (textView.text.toString() == getString(R.string.singlePlayerLevel1Introduction4)) {
            textView.setText(R.string.singlePlayerLevel1Introduction5)
        } else if (textView.text.toString() == getString(R.string.singlePlayerLevel1Introduction5)) {
            textView.setText(R.string.singlePlayerLevel1Introduction6)
            nextButton.visibility = View.GONE
            finishedButton.visibility = View.VISIBLE
        }
    }

}