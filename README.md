# MyAlbums App

This is a sample project for review subjects like MVVM and Repository pattern with Kotlin.

## Deployment

Download the code and run it. No additional configurations are needed.

## Architecture

Using MVVM and reactive programming we can handle background tasks in a cleaner way, and be notified about results whenever they are ready. Also, classes were organized depending on their responsibility and interaction.

### Data
For models and sources (Repository)

### DI
For Dependencies Injection Module

### Provider
For data providers like cloud (APIs), local (DBs & preferences)

### UI
For Activities, fragments, view models, and use cases

### Utils
For functions and constants that could be called multiple times from several project sections

## Future improvements
- Compose
- Animations between fragments
- Pagination for listing albums
- Blur effect in list screen title
- Split into several DI modules
- Support more exceptions (Error's handling)

## Screenshots & Videos

List:
https://user-images.githubusercontent.com/6877923/123006036-64e2e780-d3b7-11eb-922e-018994b32da5.mov

## License
[MIT](https://choosealicense.com/licenses/mit/)