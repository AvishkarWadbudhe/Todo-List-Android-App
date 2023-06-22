# Todo-List-Android-App
Title: Todo List Android App

## Overview
This repository contains a Todo List Android app developed using Java, MVVM architecture, and Room Database. The app provides various functionalities such as creating tasks, marking tasks as complete, viewing task history, and deleting tasks and history.

## Features
- Create Task: Users can create new tasks by providing a task title and description.
- Mark as Complete: Tasks can be marked as complete to indicate their status.
- View History: Users can view the history of completed tasks, providing a record of their progress.
- Delete Task and History: Tasks and their corresponding history can be deleted from the app.

## Architecture and Technologies Used
The app is built using the following technologies and architectural patterns:
- Java: The primary programming language used for developing the Android app.
- MVVM (Model-View-ViewModel) Architecture: The app follows the MVVM architectural pattern to separate concerns and improve code organization.
- Room Database: The Room Persistence Library is used to provide an abstraction layer over SQLite, making it easier to work with local data storage.
- LiveData: LiveData is utilized to observe and react to data changes, ensuring a smooth and efficient user experience.
- RecyclerView: The RecyclerView component is used to efficiently display lists of tasks and history items.

## Getting Started
To run the app locally or contribute to its development, please follow the steps below:

1. Clone the repository:
```
git clone https://github.com/AvishkarWadbudhe/Todo-List-Android-App.git
```
2. Open the project in Android Studio:
- Launch Android Studio and select "Open an existing Android Studio project."
- Browse to the cloned repository directory and select it.

3. Build and run the app:
- Connect an Android device or use an emulator.
- Click on the "Run" button in Android Studio to build and run the app on the selected device.

## Contributions
Contributions to the Todo List Android app are welcome. If you have any suggestions, bug fixes, or additional features to propose, please submit a pull request. Make sure to follow the existing code style and guidelines.

If you encounter any issues or have questions, feel free to open an issue in the repository. We appreciate your feedback and contributions to make the app better.

## License
The Todo List Android app is released under the [MIT License](https://opensource.org/licenses/MIT). Feel free to modify and distribute the app according to the terms of the license.
