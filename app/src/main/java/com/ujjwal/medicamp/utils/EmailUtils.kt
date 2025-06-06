package com.ujjwal.medicamp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.ujjwal.medicamp.R
import com.ujjwal.medicamp.model.Event

object EmailUtils {

    fun createEmailIntent(context: Context, event: Event): Intent? {
        // Step 1: Get email from SharedPreferences
        val prefs = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val userEmail = prefs.getString("email", "") ?: ""

        // Step 2: Validate email presence
        if (userEmail.isBlank()) {
            Toast.makeText(
                context,
                "Please visit your profile to provide an email address.",
                Toast.LENGTH_LONG
            ).show()
            return null
        }

        // Step 3: Validate event title and location
        if (event.title.isBlank() || event.location.isBlank()) {
            Toast.makeText(context, R.string.email_error_message, Toast.LENGTH_SHORT).show()
            return null
        }

        // Step 4: Prepare subject and body
        val subject = context.getString(R.string.email_subject, event.title)
        val body = context.getString(R.string.email_body, event.title, event.location)

        // Step 5: Create email URI and intent
        val uri = Uri.parse("mailto:$userEmail")
            .buildUpon()
            .appendQueryParameter("subject", subject)
            .appendQueryParameter("body", body)
            .build()

        return Intent(Intent.ACTION_SENDTO).apply {
            data = uri
        }
    }
}
