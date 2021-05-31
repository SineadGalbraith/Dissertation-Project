/* This class contains the code used for displaying the Single Player Main Game Screen Dialog Fragment. */
package com.dissertation

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment

class SinglePlayerMainGameIntroductionFragment : DialogFragment() {
    
    /*
    When this class is called, display the Dialog Fragment and store a reference to the fragment
    components for use elsewhere in the class.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.dialogFragmentTextView)
        val nextButton = view.findViewById<ImageButton>(R.id.dialogFragmentNextButton)
        val backButton = view.findViewById<ImageButton>(R.id.dialogFragmentBackButton)
        val finishedButton = view.findViewById<ImageButton>(R.id.dialogFragmentFinishedButton)

        backButton.visibility = View.VISIBLE

        nextButton.setOnClickListener {
            onNextButtonPressed(textView, nextButton, finishedButton)
        }

        backButton.setOnClickListener {
            onBackButtonPressed(textView, nextButton, finishedButton)
        }

        finishedButton.setOnClickListener {
            dialog?.dismiss()
        }
        return view
    }

    /*
    When this class is called, display the first of the text passages in the Dialog Fragment.
    */
    override fun onViewCreated(
        view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        val textView = view.findViewById<TextView>(R.id.dialogFragmentTextView)
        textView.setText(R.string.singlePlayerMainGameIntroductionText1)
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
    Control the text passages displayed when the "Next" button is pressed.
     */
    private fun onNextButtonPressed(textView: TextView, nextButton: ImageButton, finishedButton: ImageButton) {
        if (textView.text.toString() == getString(R.string.singlePlayerMainGameIntroductionText1)) {
            textView.setText(R.string.singlePlayerMainGameIntroductionText2)
        } else if (textView.text.toString() == getString(R.string.singlePlayerMainGameIntroductionText2)) {
            textView.setText(R.string.singlePlayerMainGameIntroductionText3)
            nextButton.visibility = View.GONE
            finishedButton.visibility = View.VISIBLE
        }
    }

    /*
    Control the text passages displayed when the "Back" button is pressed or return to the Game
    Style Choice Screen.
     */
    private fun onBackButtonPressed(textView: TextView, nextButton: ImageButton, finishedButton: ImageButton) {
        if (textView.text.toString() == getString(R.string.singlePlayerMainGameIntroductionText3)) {
            textView.setText(R.string.singlePlayerMainGameIntroductionText2)
            nextButton.visibility = View.VISIBLE
            finishedButton.visibility = View.GONE
        } else if (textView.text.toString() == getString(R.string.singlePlayerMainGameIntroductionText2)) {
            textView.setText(R.string.singlePlayerMainGameIntroductionText1)
        } else if (textView.text.toString() == getString(R.string.singlePlayerMainGameIntroductionText1)) {
            val intent = Intent(activity, GameStyleChoiceScreen::class.java)
            startActivity(intent)
        }
    }


}