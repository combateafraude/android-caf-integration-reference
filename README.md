# CAF SDK Integration: Liveness and Document Detector

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Project Structure](#project-structure)

## Overview

This repository provides an example of integrating **CAF's** proprietary SDKs for **Facial Liveness** and **Document Detection** into an Android application. It serves as a reference for developers looking to implement identity verification functionalities using the advanced technologies offered by CAF.

## Features

- **Facial Liveness**: Verifies facial vitality to ensure the interaction is performed by a human and not a digital representation.
- **Document Detection**: Captures and analyzes identification documents, for identity validation.

## Technologies Used

- **Kotlin**: Primary programming language used in the project.
- **CafFaceLivenessSDK**: CAF's proprietary SDK for facial liveness verification.
- **DocumentDetector**: CAF's proprietary SDK for document detection and analysis.

## Prerequisites

Before getting started, ensure you have the following:

- **Android Studio**: Recommended IDE for Android development.
- **API Keys**: Tokens and identifiers provided by CAF for using the SDKs.
- **Android Device or Emulator**: To run and test the application.

## Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/combateafraude/android-caf-integration-reference
   ```

2. **Open the Project in Android Studio**

   - Launch Android Studio.
   - Click on **"Open an existing Android Studio project"**.
   - Navigate to the cloned project directory and select it.

3. **Sync Dependencies**

   Android Studio will automatically sync the dependencies defined in the `build.gradle` files. Ensure you have an active internet connection.

## Configuration

1. **Add SDK Credentials**

   - In the `MainActivity.kt` file, locate the `companion` object:

     ```Kotlin
     companion object {
         private const val REQUEST_CODE = 1000
         private const val PERSON_ID = "YOUR_PERSON_ID"
         private const val MOBILE_TOKEN = "YOUR_MOBILE_TOKEN"
     }
     ```

   - Replace `"YOUR_PERSON_ID"`
   - Replace `"YOUR_MOBILE_TOKEN"` with the values provided by CAF.

2. **SDK Configuration**

   - **Caf Face Liveness**: Configured in the `setupCafFaceLiveness()` function.
   - **Caf Document Detection**: Configured in the `setupCafDocumentDetector()` function.

   Verify that all configurations align with your project requirements and the specifications of CAF's SDKs.

## Usage

1. **Run the Application**

   - Connect an Android device or start an emulator.
   - In Android Studio, click **"Run"** or press `Shift + F10`.

2. **Interact with the Buttons**

   - **Facial Liveness**: Click the **"Caf FaceLiveness"** button to initiate the facial vitality verification process.
   - **Document Detection**: Click the **"Caf Document Detection"** button to capture and analyze identification documents.

3. **View Logs and Results**

   The application will display log messages and Toast notifications indicating the status of operations performed by the SDKs.

## Project Structure

```
caf-sdk-integration-example/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/yourusername/cafsdk/
│   │   │   │   └── MainActivity.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/activity_main.xml
│   │   │   │   └── values/strings.xml
│   │   │   └── AndroidManifest.xml
│   ├── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

- **MainActivity.kt**: Main class managing the integration of the SDKs.
- **activity_main.xml**: User interface layout with buttons to start the processes.
- **build.gradle**: Gradle configuration files for managing project dependencies and build settings.
- **settings.gradle**: All required repositories.

---

*This project is a reference integration of CAF's proprietary SDKs for Caf Face Liveness and Caf Document Detection. All rights to the SDKs belong to CAF.*