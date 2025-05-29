package com.ujjwal.medicamp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.ujjwal.medicamp.R
import com.ujjwal.medicamp.model.Event

object EmailUtils {

    fun createEmailIntent(context: Context, event: Event): Intent? {
        if (event.title.isBlank() || event.location.isBlank()) {
            Toast.makeText(context, R.string.email_error_message, Toast.LENGTH_SHORT).show()
            return null
        }

        val subject = context.getString(R.string.email_subject, event.title)
        val body = context.getString(R.string.email_body, event.title, event.location)

        val uri = Uri.parse("mailto:")
            .buildUpon()
            .appendQueryParameter("subject", subject)
            .appendQueryParameter("body", body)
            .build()

        return Intent(Intent.ACTION_SENDTO).apply {
            data = uri
        }
    }
}
