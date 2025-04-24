package com.example.avengers

import android.content.Context

class MainViewModel(private val context: Context, private val ticketCalculator: TicketCalculator) {

    fun getFormattedAdultPrice(adultCount: Int): String {
        val price = ticketCalculator.calculateAdultPrice(adultCount)
        return context.getString(R.string.adult_total_tickets, price)
    }

    fun getFormattedChildPrice(childCount: Int): String {
        val price = ticketCalculator.calculateChildPrice(childCount)
        return context.getString(R.string.child_total_tickets, price)
    }

    fun getFormattedTotalPrice(adultCount: Int, childCount: Int): String {
        val totalPrice = ticketCalculator.calculateTotalPrice(adultCount, childCount)
        return context.getString(R.string.total_tickets_price, totalPrice)
    }

    fun isValidInput(adultCount: Int?, childCount: Int?, theater: String, date: String): Boolean {
        return (adultCount != null || childCount != null) && theater.isNotEmpty() && date.isNotEmpty()
    }

}