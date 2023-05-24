package br.com.fgomes.cgd.views

import android.R
import android.os.Build
import br.com.fgomes.cgd.views.ToastSnack
import android.widget.Toast
import android.app.Activity
import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar

object ToastSnack {
    @JvmStatic
    fun show(p_context: Context, p_message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            showSnack( p_context, p_message)
        else
            Toast.makeText(p_context, p_message, Toast.LENGTH_LONG).show()
    }

    private fun showSnack(p_context: Context, p_message: String) {
        val activity = p_context as Activity
        val rootView = activity.window.decorView.findViewById<View>(R.id.content)
        val snackbar = Snackbar.make(rootView, p_message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }
}