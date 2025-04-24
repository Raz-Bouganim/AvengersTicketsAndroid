package com.example.avengers

class TicketCalculator(private val adultTicketPrice: Int, private val childTicketPrice: Int) {

    fun calculateAdultPrice(adultTickets: Int): Int {
        return adultTickets * adultTicketPrice
    }

    fun calculateChildPrice(childTickets: Int): Int {
        return childTickets * childTicketPrice
    }

    fun calculateTotalPrice(adultTickets: Int, childTickets: Int): Int {
        return calculateAdultPrice(adultTickets) + calculateChildPrice(childTickets)
    }
}