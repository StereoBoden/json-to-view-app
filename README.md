# Json to View App

Consumes a json file and programatically generates views based on the config

## Installation

Simply run master and it should compile and run within Android Studio

## Known Issues
- Blue view has a whitespace area when the location (miles TextView) is updated
- FusedLocationClient inside LocationClientFactory sometimes doesnt update its coordinates

## Libraries Used
- androidx.fragment:fragment-ktx:1.3.1 
  - Because it makes declaring a ViewModel delegate a 1 liner
- com.google.android.gms:play-services-location:18.0.0
  - To use its FusedLocationClient to get the device's location

## Libraries Would have Used
- Dagger 2 or Koin for Dependency Injection
- OkHttp and Retrofit2 for the API calls
- GSON (Built into Retrofit2) for json parsing
- Glide for image loading