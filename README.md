# Rick and Morty Characters App

## Overview

A single-screen Android app built with Jetpack Compose that showcases characters from **Rick and Morty**. The app utilizes a **Clean Architecture** approach with the **MVI** pattern, providing a robust and maintainable codebase.

## Features

- **Character List**: Displays a list of characters with images and names.
- **Favorites Toggle**: Users can toggle between all characters and their favorited ones.
- **Endless Scrolling**: Implements endless scroll for all character list.
- **Favorite Icon**: Each character item features a favorite icon indicating its state.
- **Click Interaction**: Clicking on a character toggles its favorite state.
- **Database Integration**: Favorited characters are saved to a Room database.
- **Error Handling**: Catches errors from the database, network, server, and unknown issues, with a retry button on the error screen for network issues.
- **Dark Mode Toggle**: Users can switch between light and dark modes.

## Tech Stack

- **Architecture**: MVI with Clean Architecture
- **Database**: Room
- **Dependency Injection**: Hilt
- **Networking**: Retrofit and OkHttp
- **UI**: Jetpack Compose
- **Image Loading**: Coil
- **Unit Tests**: Junit and Mockk

## Acknowledgments

- [Rick and Morty API](https://rickandmortyapi.com/) for character data.

