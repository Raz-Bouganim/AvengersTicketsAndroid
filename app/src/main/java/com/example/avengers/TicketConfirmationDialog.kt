package com.example.avengers

import android.content.Context
import android.view.LayoutInflater
import android.app.AlertDialog
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class TicketConfirmationDialog(private val context: Context, private val onConfirm: (name: String, email: String) -> Unit) {

    fun show(adultCount: Int, childCount: Int, totalPrice: Int, theater: String, date: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)
        val dialogBuilder = AlertDialog.Builder(context).setView(dialogView).setCancelable(false)
        val dialog = dialogBuilder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.show()

        val nameInput = dialogView.findViewById<EditText>(R.id.nameInput)
        val emailInput = dialogView.findViewById<EditText>(R.id.emailInput)

        // Access dialog views
        val movieDateTextView = dialogView.findViewById<TextView>(R.id.movieDate)
        val ticketCountTextView = dialogView.findViewById<TextView>(R.id.ticketCount)
        val theaterNameTextView = dialogView.findViewById<TextView>(R.id.theaterName)
        val totalPriceTextView = dialogView.findViewById<TextView>(R.id.totalPrice)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
        val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)

        // Populate order details
        movieDateTextView.text = date
        ticketCountTextView.text = context.getString(R.string.ticket_count, adultCount, childCount)
        theaterNameTextView.text = theater
        totalPriceTextView.text = context.getString(R.string.total_tickets_price, totalPrice)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        // Confirm button action
        confirmButton.setOnClickListener {
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()

            if (name.isEmpty()) {
                nameInput.error = context.getString(R.string.name_required)
                confirmButton.startAnimation(shake)
            } else if (email.isEmpty() || !isValidEmail(email)) {
                emailInput.error = context.getString(R.string.valid_email_required)
                confirmButton.startAnimation(shake)
            } else {
                val bounce = AnimationUtils.loadAnimation(context, R.anim.bounce)
                confirmButton.startAnimation(bounce)
                Toast.makeText(
                    context,
                    context.getString(R.string.confirmation_message),
                    Toast.LENGTH_SHORT
                ).show()
                onConfirm(name, email)
                dialog.dismiss()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}