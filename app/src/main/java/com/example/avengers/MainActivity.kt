package com.example.avengers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.core.content.ContextCompat
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.app.DatePickerDialog
import java.util.Calendar
import android.widget.TextView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import android.view.animation.AnimationUtils


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var ticketCalculator: TicketCalculator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ticketCalculator = TicketCalculator(15, 10)
        viewModel = MainViewModel(this, ticketCalculator)

        // Set up the ViewFlipper with images
        val viewFlipper = findViewById<ViewFlipper>(R.id.imageSlider)
        val images = listOf(
            R.drawable.avengers_endgame_poster,
            R.drawable.ironman,
            R.drawable.captainamerica,
            R.drawable.thor,
            R.drawable.blackwidow,
            R.drawable.thanos
        )
        setupViewFlipper(viewFlipper, images)
        setupTheaterSpinner()

        // Set up the DatePickerDialog
        val datePicketText = findViewById<TextView>(R.id.datePickerText)
        datePicketText.setOnClickListener {
            showDatePickerDialog(datePicketText)
        }

        // Set up the ticket calculation
        val adultTicketsCount = findViewById<EditText>(R.id.adultTicketsCount)
        val childTicketsCount = findViewById<EditText>(R.id.childTicketsCount)
        val adultPrice = findViewById<TextView>(R.id.adultPrice)
        val childPrice = findViewById<TextView>(R.id.childPrice)
        val totalPrice = findViewById<TextView>(R.id.totalPrice)

        // Using text watcher to update prices synchronously
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val adultCount = adultTicketsCount.text.toString().toIntOrNull() ?: 0
                val childCount = childTicketsCount.text.toString().toIntOrNull() ?: 0

                adultPrice.text = viewModel.getFormattedAdultPrice(adultCount)
                childPrice.text = viewModel.getFormattedChildPrice(childCount)
                totalPrice.text = viewModel.getFormattedTotalPrice(adultCount, childCount)
            }
        }
        adultTicketsCount.addTextChangedListener(textWatcher)
        childTicketsCount.addTextChangedListener(textWatcher)

        // Set up the button to show the custom dialog
        val getTicketsButton = findViewById<Button>(R.id.getTicketsButton)
        getTicketsButton.setOnClickListener {
            val chosenTheater = findViewById<Spinner>(R.id.theaterSpinner).selectedItem.toString()
            val chosenDate = datePicketText.text.toString()
            val adultCount = adultTicketsCount.text.toString().toIntOrNull() ?: 0
            val childCount = childTicketsCount.text.toString().toIntOrNull() ?: 0

            if(!viewModel.isValidInput(adultCount, childCount, chosenTheater, chosenDate)){
                val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
                getTicketsButton.startAnimation(shake)
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            } else {
                val bounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
                getTicketsButton.startAnimation(bounce)
                TicketConfirmationDialog(this) { name, email ->
                    Toast.makeText(this, getString(R.string.confirmation_message), Toast.LENGTH_SHORT).show()
                }.show(adultCount, childCount, ticketCalculator.calculateTotalPrice(adultCount, childCount), chosenTheater, chosenDate)
            }
        }
    }

    // Function to set up the ViewFlipper with images
    private fun setupViewFlipper(viewFlipper: ViewFlipper, images: List<Int>) {
        for (image in images) {
            val imageView = ImageView(this)
            imageView.setImageResource(image)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.background = ContextCompat.getDrawable(this, R.drawable.image_rounded_corners)
            imageView.clipToOutline = true
            viewFlipper.addView(imageView)
        }
    }

    // Function to set up the theater spinner
    private fun setupTheaterSpinner(){
        val theaterSpinner = findViewById<Spinner>(R.id.theaterSpinner)
        val theaterAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.theater_options,
            android.R.layout.simple_spinner_item
        )
        theaterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        theaterSpinner.adapter = theaterAdapter
    }

    // Function to show the DatePickerDialog
    private fun showDatePickerDialog(dateTextView: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                dateTextView.text = getString(R.string.date_picker_chose, selectedDay, selectedMonth + 1, selectedYear)
                dateTextView.setTextColor(ContextCompat.getColor(this, R.color.black))
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}