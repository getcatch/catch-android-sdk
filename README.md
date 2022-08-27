# Catch Android SDK

The project contains 3 modules:

- `catch-android-sdk`: This is the library module containing all of the SDK source code.
- `sample`: This is a sample Android app (it depends directly on `:catch-android-sdk`), meant for
  prototyping, testing, and demonstration.
- `buildSrc`: This module contains shared gradle configuration files.

# Versions and Specifications

- The project depends on gradle 7.4.1.
- You need JDK Version 11 (>=) to build and use this project.
- It targets java version 8.

# Setup

Import the project in Android Studio and let it sync and build.

We have [a custom pre-commit hook](hooks/pre-commit) that will run detekt (read more below) to help
you be catch linting and formatting errors before pushing up code.

In order for you to enable that git hook, run the following command:

```shell
git config core.hooksPath hooks/
```

# Detekt

We use [detekt](https://detekt.dev) for static code analysis.

You can run detekt from the command line with the following command:

```shell
./gradlew detekt
```

You can also pass it the flag `--auto-correct` and it will fix any simple formatting issues that it
can for you.

```shell
./gradlew detekt --auto-correct
```

**Note:** Even if all the issues are auto-fixable, it will let you know what errors you came across.
Run it twice to see if errors remain after auto-fixing.

It is recommended to run `dependencyUpdates` to check what is updated and what not (Gradle version,
plugin, kotlin version and dependencies).
