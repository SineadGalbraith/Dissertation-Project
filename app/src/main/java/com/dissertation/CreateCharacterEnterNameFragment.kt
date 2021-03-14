package com.dissertation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment


class CreateCharacterEnterNameFragment : Fragment() {

    internal interface CreateCharacterEnterNameFragmentListener {
        fun showAlert()
        fun saveName(name: String?)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.create_character_enter_name_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.createCharacterEnterNameTextView)
        textView.setText(R.string.enterNameText1)

        val editText = view.findViewById<EditText>(R.id.createCharacterEnterNameEditText)
        editText.imeOptions = EditorInfo.IME_ACTION_DONE;

        return view
    }
}