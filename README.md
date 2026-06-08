# News App - Android Technical Test

A robust and modern Android application built to demonstrate clean architecture, reactive programming, and offline-first capabilities.

## 🚀 Technical Highlights
This project showcases high-level Android development standards, including:
* **Clean Architecture:** Separation of concerns using MVVM.
* **Offline-First Strategy:** Using **Room Database** as the Single Source of Truth, synchronized via **RemoteMediator**.
* **Modern UI:** Built entirely with **Jetpack Compose** and Material 3.
* **Performance:** Seamless list handling with **Paging 3** and **Shimmer Loading** animations.
* **Reliability:** Automated **Unit Testing** with JUnit and MockK.
* **Code Quality:** Enforced code style via **Ktlint**.

## 🛠 Tech Stack
* **Language:** Kotlin 2.0+
* **Architecture:** MVVM, Clean Architecture, Repository Pattern
* **Dependency Injection:** Dagger Hilt
* **Networking:** Retrofit2 + OkHttp3
* **Async:** Kotlin Coroutines & Flow
* **UI:** Jetpack Compose (Material 3)
* **Local Storage:** Room Database
* **Pagination:** Paging 3
* **Testing:** JUnit4, MockK, Turbine

## ✨ Key Features
* **Dynamic Browsing:** Filter news by category and search with debounced inputs.
* **Offline Support:** Read articles anytime. Data is cached locally, ensuring a smooth experience even without an internet connection.
* **Robust Error Handling:** User-friendly feedback for network/server exceptions.
* **Smooth UX:** Pull-to-refresh and skeleton loading states.

## ⚙️ How to Setup
1. Clone this repository.
2. Open the project in **Android Studio (Koala/Ladybug or newer)**.
3. Create a `local.properties` file in the root folder if it doesn't exist.
4. Add your API Key from [NewsAPI](https://newsapi.org/):
   ```properties
   NEWS_API_KEY="your_api_key_here"