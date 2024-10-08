# Movie App 🤖

 ![Language](https://img.shields.io/github/languages/top/cortinico/kotlin-android-template?color=blue&logo=kotlin)

The movie app helps you to find good information about movies such as the most popular, the top related movies and why not the person most popular in the environment. Also, has te ability to see and save your location each 5 minutes sent you notifications when is saved. Its not all! Also you can save all your photos here.

## How to use 👣

1. Download or clone the project.
2. Add the missing TheMovieDb API into `gradle.properties`. For example:
   ```MOVIE_DB_API_KEY=YOUR KEY HERE```
4. Add the Google Api Key into `AndroidManifest.xml`:
```
<meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="YOUR API KEY HERE" />
```
5. Run the project.

## Features 🎨

- Kotlin.
- Room.
- Clean Architecture.
- MVVM.
- Clean code.
- JUnit 4.
- Coroutines.
- Flow.
- Dagger-Hilt.
- GitFlow.
- Retrofit
- OkHttp3.
- Google Maps Api.
- The Movie Db Api.
- View Binding.
- Glide.
- Mockk.
- Firebase.

## Gradle Setup 🐘

This project is using [**Gradle Kotlin DSL**](https://docs.gradle.org/current/userguide/kotlin_dsl.html) as well as the [Plugin DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block) to setup the build.

Dependencies are centralized inside the Gradle Version Catalog in the [libs.versions.toml](gradle/libs.versions.toml) file in the `root` folder.


## How it works ⚙️

the movie app was made with a single activity `MainActivity.kt`; Inside, host 4 fragments with navigation component that define the final view:

1. ProfileFragment or Profile screen: Show information about the most popular person in the movie environment, also, show the latest review made by him. The information is loaded from `https://api.themoviedb.org/3/person/popular` and saved in local database using room. In the first load of the screen, gets the saved data from room, next, update the data from remote.
2. HomeFragment or Home screen: Show three horizontal recyclerviews. The first is a list of the most popular movies. The second one is a list of the top rated movies. The last one is a recommendation list based on the most popular movie. The data is loaded from:
- Popular movies: `https://api.themoviedb.org/3/movie/popular`
- Top rated movies: `https://api.themoviedb.org/3/movie/top_rated`
- Recommendation movies: `https://api.themoviedb.org/3/movie/{movie_id}/recommendations` (The recommendation movies is based on the most popular movie)
  Same as profile screen, the data is saved in local database using room. In the first load of the screen, gets the saved data from room, next, update the data from remote.
3. MapFragment or Map screen: Show in the entire screen a map where can show the last position saved on firestore. This screen manage the location permission and is able to start the save remote location service.
4. GalleryFragment or Gallery screen: Show a recyclerview in a grid mode with all the photos saved on firebase storage. Also, contains a Floating Action Button to upload new photos or images to the cloud. It has the capacity of request and manage external permission based on the Android API level.

## Architecture 🚀

Modules:
1. App: Contains the entire presentation side of the app. Has the activity, viewmodels, fragments, adapter and all the resources to draw the screens. All the presentations layer is here!
2. Firebase: Contains 2 packages: Firestore and Storage. Each of them contains the base of clean architecture: data, di and domain.
3. Moviedb: Contains the call service to moviedb api, repositories and use cases to retrieve information about movies 
4. Storage: Contains calls to get and set data from Room.

All the modules contains a XXXDependencyProvider. It has the base objects to work well when its required for around the nodule such as database implementations, for example.
All modules is based on clean architecture. It means that the primary package could be data, domain or presentation.


## Flows

1. Save image: 

https://github.com/user-attachments/assets/2cc89fe6-c819-43db-a114-815350060b64

2. Get movies:

![Example](https://github.com/user-attachments/assets/69188540-55ae-4b02-a1f7-d656de7da3d8)

3. Dialogs (FragmentDialog):

![fargmentdialog_Screenshot_20240807_222318](https://github.com/user-attachments/assets/923e5cf8-c632-43bf-bae9-112fce8f6d25)
![error_Screenshot_20240808_013105](https://github.com/user-attachments/assets/31bdb129-f8e2-4bc4-8fbe-edaef89d54c7)

4. Map screen:

![mapScreenshot_20240808_085542](https://github.com/user-attachments/assets/3f9820a9-b461-4b09-a5e6-1d9732c57329)

5. Profile screen:

![profileScreenshot_20240808_090051](https://github.com/user-attachments/assets/a97e83db-aeec-471f-84d8-9512eacc3d35)

## To improve

- Manage loaders its friendly for end users.
- Improve the way how GenericDialogFragment is created with more customisable attributes.
- Manage flows in a base strategy for each usecase received into viewModels. This avoid boilerplate.


## Work in progress

- Save location into firestore
- Call service every 5 minutes to show Notification and save location
- Show location dots into map

