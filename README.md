# FRVR Business Case

## Mobile Technical Challenge - Android
The goal was to develop an application that integrates native code and javascript code through a Javascript Interface. 
The application is divided in two parts: 
 - The first is a library (SDK) layer that can be instantiated in any application so the developer can call the methods and interact with the library activities.
 - The second is the app layer that implements the SDK, instantiating and calling its methods.
___
 ## The webinterface library
 The library should be instantiated at the onCreate lifecycle of the app's activity, like so:

 ``` kotlin
private lateinit var webInterfaceLib: WebInterfaceLib

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    ... // stuff

    webInterfaceLib = WebInterfaceLib(this)
}
 ```

There were three main features to be covered:
- Native Logs
- Local Notifications
- Application Lifecycle Events 

You can interact with the library through three different methods to call it's activities. Each is responsible for one of the three main features. So:
``` kotlin
fun openNativeLogs()
fun openLocalNotifications()
fun openLifecycleEvents()
```

All of the three activities use the same html page, which is loaded locally from the library assets. To access the different features, the app will use a URLParam to designate the desired feature. Since we have three features, we have three different URL parameter options:
``` kotlin
const val NATIVE_LOGS_PARAM = "native_logs"
const val LOCAL_NOTIFICATIONS_PARAM = "local_notifications"
const val LIFECYCLE_EVENTS_PARAM = "lifecycle_events"
```
___
## Native logs
- [x] **As user I can print native logs (use logcat)**

#### To achieve this goal, the following Javascript Interface was created.
``` kotlin
fun showMessageFromWebView(message: String)
```


___
## Local Notifications
- [x] **As user I want to register notifications**
- [x] **As user I can check all registered notifications**
- [x] **As user I can delete a notification**
- [x] **As user I receive schedule notifications**

#### For this, the room database was used to persist the notification data so the user can keep track of the added notifications and delete them. Three separated Javascript Interfaces are responsible for handling with the user interactions in the webview, those being: 
``` kotlin
fun getNotifications(): String
fun addNotification(
    displayTime: String,
    id: Int,
    title: String,
    message: String
)
fun deleteNotification(notification: String)
```

___
## Application Lifecycle Events
- [x] **As a user I can visualize the application lifecycle logs in the html page.**
#### For this feature, an ActivityLifecycleCallback class is registered on the application's onCreate lifecycle. Through this class, we are able to receive all the events from all the activities, store it in our room database and eventually, send the data to the webview when required (onload). The javascript interface responsible for handling this is:
``` kotlin
fun getLifecycleEvents(): String
```
___

## Technical details
Besides that, the app is running on minSdk 26. We're additionally using room library for local storage and gson library to transition JSON data between the web and native layers. This was developed with the MVVM architecture in mind, so all the responsibilities are properly separated in individual modules.