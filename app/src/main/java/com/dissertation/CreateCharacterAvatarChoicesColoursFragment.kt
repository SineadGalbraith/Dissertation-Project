package com.dissertation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class CreateCharacterAvatarChoicesColoursFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.create_character_avatar_choices_colours_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.createCharacterAvatarChoicesColoursTextView)
        textView.setText(R.string.createCharacterAvatarChoicesText2)
        return view
    }
}