package com.op.movies.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.op.movies.R

class GenericDialogFragment(
    private val title: Int,
    private val message: Int,
    private val positiveButtonTitle: Int? = null,
    private val negativeButtonTitle: Int? = null,
    private val onPositiveButtonPressed: () -> Unit,
    private val onNegativeButtonPressed: () -> Unit,
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title).setMessage(message)
            if (positiveButtonTitle != null)
                builder.setPositiveButton(positiveButtonTitle) { dialog, id ->
                    onPositiveButtonPressed()
                }
            if (negativeButtonTitle != null) {
                builder.setNegativeButton(negativeButtonTitle) { dialog, id ->
                    onNegativeButtonPressed()
                }
            }
            builder.create()
        } ?: throw IllegalStateException(getString(R.string.activity_cannot_be_null))
    }
}