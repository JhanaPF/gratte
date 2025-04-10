# Pix Kotlin Clean Architecture

## Overview

This repository provides a clear and concise example of implementing Modular Clean Architecture with MVVM, adhering to S.O.L.I.D. principles, and utilizing the State Holder pattern in the UI. It leverages the latest Jetpack Compose best practices (at least tries to) and libraries.

The UI is entirely built with Jetpack Compose, following essential Compose lint guidelines detailed [here](https://slackhq.github.io/compose-lints/rules/).

Key technologies integrated into the project include:

- **Room** for locale storage.
- **Compose** navigation
- **Retrofit** for seamless networking.
- **Hilt** for robust dependency injection.
- **Kotlin Coroutines and Flow** for efficient asynchronous operations.
- **Coil** for streamlined image loading.
- **GPUImage** for optimized real-time image filtering.
- **Lottie** for engaging animations.

Only the Domain is tested (100% coverage) because it:

- Ensures business logic correctness, crucial for scalability.
- Keeps tests quick and maintainable.
- Reduces dependency on external frameworks and minimizes UI-related disruptions.

> **Note:** The project intentionally includes varied approaches to state handling within ViewModels and error handling to illustrate multiple practices that i like. Also it have been conceived for DARKMODE.<br /><br />
> Also i lost some performances encoding and decoding into Bitmap instead of passing Bitmap in the domain to apply the filter before converting it one time but i didn't wanted to break the android agnostic Domain Layer...

## Preview

![Demo GIF](app/src/main/assets/Pixelise_it.gif)

## Project Structure

- **app** – Application-level setup including dependencies, Hilt DI, and configuration.
- **Common** – Shared utilities, extensions, constants, and helper functions used across modules.
- **Data** – Manages repositories interfacing with Network and Persistence layers.
- **Domain** – Core business logic, defining use cases, entities, and interfaces driving app functionality.
- **Navigation** – Navigation logic and definitions for smooth transitions between screens.
- **Network** – Retrofit-based API services and networking configurations.
- **Persistence** – Local storage management using Room, including Database, DAOs, and Entities.
- **Presentation** – UI components built with Compose, ViewModels, state management logic, and interaction handling.

## MVVM Architecture Flow

### View (Jetpack Compose)

- Observes and reacts to UI state changes provided by the ViewModel.
- Dispatches user interaction events back to the ViewModel.

### ViewModel

- Maintains and manages UI state through observable data holders (Flow or StateFlow).
- Fetches and updates data by interacting with repositories.

### Repository

- Abstracts and manages data sources such as network services or local databases.
- Supplies data to ViewModels.

## Key Libraries

- [Hilt](https://dagger.dev/hilt/) – Dependency Injection
- [Retrofit](https://github.com/square/retrofit) – Networking
- [Kotlin Coroutines and Flow](https://github.com/Kotlin/kotlinx.coroutines) – Asynchronous operations
- [Coil](https://github.com/coil-kt/coil) – Efficient image loading
- [GPUImage](https://github.com/cats-oss/android-gpuimage) – Real-time image filtering:
  - Optimized specifically for Android using OpenGL ES.
  - Flexible API for easy filter chaining and customization.
  - GPU acceleration for enhanced performance.
- [Lottie](https://github.com/airbnb/lottie-android) – Animations
- [Jetpack Compose](https://developer.android.com/jetpack/compose) – Modern, declarative UI development
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) – Essential tools including ViewModel, and Room

## Testing Libraries

- [JUnit5](https://junit.org/junit5/) – Unit testing
- [MockK](https://mockk.io/) – Mocking library
- [Turbine](https://github.com/cashapp/turbine) – Testing Kotlin Flows

### Installation

If you're not a developer, simply install the `.apk` file available in the "Releases" section on the right.
