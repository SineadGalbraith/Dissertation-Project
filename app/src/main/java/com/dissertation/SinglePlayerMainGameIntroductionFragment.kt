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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.single_player_main_game_introduction_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.singlePlayerMainGameIntroductionTextView)
        val nextButton = view.findViewById<ImageButton>(R.id.singlePlayerMainGameIntroductionNextButton)
        val backButton = view.findViewById<ImageButton>(R.id.singlePlayerMainGameIntroductionBackButton)
        val finishedButton = view.findViewById<ImageButton>(R.id.singlePlayerMainGameIntroductionFinishedButton)
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

    override fun onViewCreated(
        view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        val textView = view.findViewById<TextView>(R.id.singlePlayerMainGameIntroductionTextView)
        textView.setText(R.string.singlePlayerMainGameIntroductionText1)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity: Activity? = activity
        if (activity is DialogInterface.OnDismissListener) {
            (activity as DialogInterface.OnDismissListener).onDismiss(dialog)
        }
    }

    private fun onNextButtonPressed(textView: TextView, nextButton: ImageButton, finishedButton: ImageButton) {
        if (textView.text.toString() == getString(R.string.singlePlayerMainGameIntroductionText1)) {
            textView.setText(R.string.singlePlayerMainGameIntroductionText2)
        } else if (textView.text.toString() == getString(R.string.singlePlayerMainGameIntroductionText2)) {
            textView.setText(R.string.singlePlayerMainGameIntroductionText3)
            nextButton.visibility = View.GONE
            finishedButton.visibility = View.VISIBLE
        }
    }

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