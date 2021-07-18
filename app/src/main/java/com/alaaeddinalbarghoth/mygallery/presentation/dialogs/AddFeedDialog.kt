package com.alaaeddinalbarghoth.mygallery.presentation.dialogs

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.alaaeddinalbarghoth.mygallery.R
import com.alaaeddinalbarghoth.mygallery.databinding.DialogAddFeedBinding


class AddFeedDialog(
    private var optionSelected: ((idAdded: Boolean, title: String, description: String) -> Unit)
) : DialogFragment() {

    private lateinit var binding: DialogAddFeedBinding

    // region LifeCycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.DialogFragmentStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = let {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_add_feed,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener {
            if (binding.etTitle.text.toString().isNotEmpty() &&
                binding.etDescription.text.toString().isNotEmpty()
            )
                optionSelected.invoke(
                    true,
                    binding.etTitle.text.toString(),
                    binding.etDescription.text.toString()
                )
            else
                Toast.makeText(
                    requireContext(),
                    getString(R.string.fill_input_fields_message),
                    Toast.LENGTH_LONG
                ).show()
        }

        binding.btnCancel.setOnClickListener {
            optionSelected.invoke(false, "", "")
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }
    // endregion
}