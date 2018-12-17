# fligths-map-app
An app to display flights from [Lufthansa API](https://developer.lufthansa.com/docs) and show the route on a map

## Usage

To retrieve data from API, you need to create an account and replace the api key in *NetworkConfig.kt* with your credentials
```kotlin
    const val API_CLIENT_ID = "your_client_id"
    const val API_CLIENT_SECRET = "your_client_secret"
```

Also you need to create a project on Google Cloud and add your maps key in *google_maps_key.xml*
```xml
    <string name="google_maps_key" translatable="false" templateMergeStrategy="preserve">your_maps_key</string>
```
## Summary

* Kotlin app based on Clean Architecture (MVP pattern)
* Interactors connected to the different layers using Repository pattern.
* Dependency injection handled with Dagger2
* Asynchronous events with RxKotlin
* Unit tests using Mockito
* _(To do)_ UI tests with Espresso
* Used [Lufthansa API](https://developer.lufthansa.com/docs) as network provider. Endpoints used **oauth/token**, **references/airports** and **operations/schedules**


## Libraries

* [Dagger](https://google.github.io/dagger/)
* [RxKotlin](https://github.com/ReactiveX/RxKotlin)
* [Retrofit](https://square.github.io/retrofit/)
* [Lottie](https://airbnb.design/lottie)
* [Mockito](https://github.com/nhaarman/mockito-kotlin/)