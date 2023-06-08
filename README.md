# Catch Android SDK

![Catch](https://user-images.githubusercontent.com/74115740/207220638-ef31c835-9a06-49d3-a8e5-d4e49acaae10.png)

[![Maven Central](https://img.shields.io/maven-central/v/com.getcatch/catch-android-sdk.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.getcatch%22%20AND%20a:%22catch-android-sdk%22)
[![License](https://img.shields.io/badge/license-MIT-lightgray)](https://github.com/getcatch/catch-android-sdk/blob/main/LICENSE)

The Catch Android SDK allows you to integrate [Catch](https://www.getcatch.com) as a payment option in your native Android apps.

Table of contents
=================

<!--ts-->
* [Documentation](#documentation)
* [Installation](#installation)
* [Example](#example)
<!--te-->

## Documentation

Read the [integration guide](https://catch.readme.io/reference/catch-android-sdk) or [browse the API reference](https://getcatch.github.io/catch-android-sdk/).

## Installation

### Requirements

The Catch Android SDK is compatible with the following versions:

- Android 5.0 (API level 21) and above
- [Android Gradle Plugin](https://developer.android.com/build/releases/gradle-plugin) 4.1.3+
- [Gradle](https://gradle.org/releases/) 6.8.3+

### Configuration

Add the `catch-android-sdk` to your `build.gradle` dependencies.

```
dependencies {
  implementation 'com.getcatch:catch-android-sdk:1.0.1'
}
```

## Getting Started

### Example

A demo app is included in this project. To run it you will need to do the following:
- Clone this repository
- Open this project in Android Studio
- Run the `sample` app
