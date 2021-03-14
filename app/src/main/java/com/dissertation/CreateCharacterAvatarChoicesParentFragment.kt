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

class CreateCharacterAvatarChoicesParentFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_character_avatar_choices_parent_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.createCharacterAvatarChoicesParentTextView)
        val nextButton = view.findViewById<ImageButton>(R.id.createCharacterAvatarChoicesParentNextButton)

        nextButton.setOnClickListener {
            onNextButtonPressed(textView)
        }
        return view
    }

    override fun onViewCreated(
        view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        val textView = view.findViewById<TextView>(R.id.createCharacterAvatarChoicesParentTextView)
        textView.setText(R.string.createCharacterAvatarChoicesText1)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    private fun onNextButtonPressed(textView: TextView) {
        val transaction = childFragmentManager.beginTransaction()
        if (textView.text.toString() == getString(R.string.createCharacterAvatarChoicesText1)) {
            val createCharacterAvatarChoicesColoursFragment = CreateCharacterAvatarChoicesColoursFragment()
            transaction.replace(R.id.createCharacterAvatarChoicesParentFragment, createCharacterAvatarChoicesColoursFragment)
            transaction.commit();
        }
    }
}