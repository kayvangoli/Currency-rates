# Currency Rates

Currency Rates is a sample Android application built using Kotlin, Compose, MVVM. The application displays a list of Currency rates, including their name, photo and price.

## Features

- Display a list of Currency rates.
- Refresh data screen every 2 minutes.

## Architecture

The application follows the Clean Architecture principles, which means that it is divided into layers:

- Presentation: The UI layer built using Compose and the ViewModel architecture component.
- Domain: The business logic layer that contains the use cases.
- Data: The data layer that provides the required data to the use cases using the Repository pattern. 

## Libraries

- Kotlin: A modern programming language that offers concise syntax and powerful features.
- Compose: A modern toolkit for building native Android UI.
- ViewModel: A lifecycle-aware component used to store and manage UI-related data in a lifecycle-conscious way.
- Hilt: A dependency injection library for Android that reduces boilerplate code and makes testing easier.
- Coroutines: A lightweight concurrency library for Kotlin that simplifies the code for long-running tasks and asynchronous operations.
- JUnit: A unit testing framework for Java and Kotlin.
- MockK: A mocking library for Kotlin used for testing.
- Robolectric: A testing framework for Android that allows running unit tests on the JVM.

## Requirements

- Android Studio Arctic Fox or newer
- Gradle 7.0 or newer
- Android SDK 30 or newer

## Getting Started

1. Clone this repository using `git clone https://github.com/kayvangoli/Currency-rates.git.
2. Open Android Studio and select "Open an existing project".
3. Navigate to the cloned repository and click "OK".
4. Wait for the project to sync and build.
5. Run the application on a device or emulator.

## Author

Kayvan Goli - @kayvangoli

