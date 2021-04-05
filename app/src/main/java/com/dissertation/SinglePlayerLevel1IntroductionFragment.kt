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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.single_player_levels_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.singlePlayerLevelsTextView)
        val nextButton = view.findViewById<ImageButton>(R.id.singlePlayerLevelsNextButton)
        val finishedButton = view.findViewById<ImageButton>(R.id.singlePlayerLevelsFinishedButton)

        nextButton.setOnClickListener {
            onNextButtonPressed(textView, nextButton, finishedButton)
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
        val textView = view.findViewById<TextView>(R.id.singlePlayerLevelsTextView)
        textView.setText(R.string.singlePlayerLevel1Introduction1)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

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