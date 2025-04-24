# Avengers Tickets App

This project is part of the **Native Android Development with Kotlin** course. It is a UI-focused drill designed to introduce the basics of Android app development, including working with layouts, strings, and user interaction.

## Overview

The **Avengers Tickets App** is a simple application that allows users to book tickets for the movie *Avengers: Endgame*. The app demonstrates the following key concepts:

- **UI Design**: Using XML layouts to create a user-friendly interface.
- **String Resources**: Managing text in the `strings.xml` file for localization and reusability.
- **Dynamic Content**: Displaying dynamic values such as ticket prices and user input.
- **Basic User Interaction**: Handling user input and validating form fields.
- **Localization**: Supporting both Hebrew and English languages.
- **Responsive Design**: Providing a dedicated landscape layout for better usability on rotated screens.

## Features

- Display movie details, including title, description, cast, and director.
- Allow users to select ticket preferences (adult/child tickets, theater, and date).
- Calculate and display the total price dynamically.
- Provide a confirmation dialog for ticket booking.
- Support for Hebrew and English languages, with automatic language switching based on the device's locale.
- Optimized landscape layout for improved user experience when the device is rotated.

## Technologies Used

- **Kotlin**: Primary programming language for the app.
- **Gradle**: Build system for managing dependencies.
- **Android Studio**: IDE used for development.

## Project Structure

- **`res/layout`**: Contains XML files for the app's UI.
- **res/layout-land**: Contains XML files for the app's landscape UI.
- **`res/values/strings.xml`**: Stores all string resources used in the app.
- **res/values-iw/strings.xml**: Stores all string resources in Hebrew.
- **`src/main/java`**: Contains Kotlin code for app logic and functionality.

## How to Run the App

1. Clone the repository:
   ```bash
   git clone https://github.com/Raz-Bouganim/AvengersTicketsAndroid.git
   ```
2. Open the project in Android Studio.
3. Sync the Gradle files.
4. Run the app on an emulator or a physical device.
