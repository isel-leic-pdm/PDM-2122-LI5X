
## Week 1
### 06/10/2021 - Course introduction
* Syllabus, teaching methodology and bibliography.
  * Evaluation
  * Resources

For reference:
  * [Lecture video (in Portuguese)](https://www.youtube.com/watch?v=4902iEuQSuI&list=PL8XxoCaL3dBj-9DhstfK_krmviLwfN7mX&index=1)
  
## Week 2
### 13/10/2021 - Hello Android: Activities
* Android application development: introduction
  * [Inversion of Control](https://martinfowler.com/bliki/InversionOfControl.html)
  * [Activity](https://developer.android.com/guide/components/activities/intro-activities)
    * Specifying its UI through a resource [layout](https://developer.android.com/guide/topics/resources/layout-resource)
    * The Legacy Android View hierarchy (overview)
      * [View](https://developer.android.com/reference/android/view/View)
      * [Layouts](https://developer.android.com/guide/topics/ui/declaring-layout)
      * [Constraint Layout](https://developer.android.com/training/constraint-layout/)
    * [View Binding](https://developer.android.com/topic/libraries/view-binding)
* [The Kotlin programming language](https://kotlinlang.org/docs/reference/)
  

For reference:
  * [Lecture video](https://www.youtube.com/watch?v=2o3p_yZyYv4&list=PL8XxoCaL3dBj-9DhstfK_krmviLwfN7mX&index=2)
  * Kotlin:
    * [Null safety](https://kotlinlang.org/docs/null-safety.html)
    * [Delegated properties](https://kotlinlang.org/docs/delegated-properties.html)
    * [Lazy properties](https://kotlinlang.org/docs/delegated-properties.html#lazy-properties)

### 15/10/2020 - Activity lifecycle 
* The Activity as an execution host
  * [Lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)
  * [Threading model](https://developer.android.com/guide/components/processes-and-threads#Threads)
* Building a UI using the legacy Android View hierarchy: introduction
  * [Custom Views](https://developer.android.com/guide/topics/ui/custom-components)

For reference:
  * [Lecture video (in Portuguese)](https://www.youtube.com/watch?v=kYFLKZwwBWI&list=PL8XxoCaL3dBj-9DhstfK_krmviLwfN7mX&index=3)

## Week 3
### 20/10/2021 - Building the Quote Of Day Demo
* [Application resources](https://developer.android.com/guide/topics/resources/providing-resources)
  * Language dependent: [localization](https://developer.android.com/guide/topics/resources/localization)
* Building a UI using the legacy Android View hierarchy: continued
  * [Custom Views](https://developer.android.com/guide/topics/ui/custom-components)
  * [Rendering](https://developer.android.com/guide/topics/ui/how-android-draws)
* Connectivity
  * [Permissions](https://developer.android.com/training/basics/network-ops/connecting)
  * Performing network operations using [Retrofit](https://square.github.io/retrofit/) and [GSON](https://github.com/google/gson)
    * An alternative: [Volley](https://developer.android.com/training/volley)
* The Activity as an execution host, continued
  * Consequences of performing asynchronous operations (e.g. network requests)
  * _View model_ as an alternative execution host
    * [Overview](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * Dependencies:
      * [Lifecycle components](https://developer.android.com/jetpack/androidx/releases/lifecycle#groovy)
      * [Activity extensions](https://developer.android.com/jetpack/androidx/releases/activity)

For reference:
  * [Lecture video (in Portuguese)](https://www.youtube.com/watch?v=nWJyvz70AZ4&list=PL8XxoCaL3dBj-9DhstfK_krmviLwfN7mX&index=4)
  * [Ngrok - Getting started](https://dashboard.ngrok.com/get-started/setup)

### 22/10/2021 - Laboratory
* Practical class dedicated to the course's project

## Week 4
### 27/10/2021 - Building the Quote Of Day Demo, continued
* [Android application reference architecture](https://developer.android.com/jetpack/guide)
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), revisited
  * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* Android application components: [Application](https://developer.android.com/reference/android/app/Application)
  * Motivation
  * Lifecycle
* Building Android applications:
  * Navigation between Activities
    * [Intents (explicit and implicit) and intent filters](https://developer.android.com/guide/components/intents-filters)
    * [User task and back stack](https://developer.android.com/guide/components/activities/tasks-and-back-stack)
  * [Options Menu](https://developer.android.com/guide/topics/ui/menus#options-menu) and [menu resource](https://developer.android.com/guide/topics/resources/menu-resource)   
  
For reference:
  * [Lecture video (in Portuguese)](https://www.youtube.com/watch?v=zIQU0lzO3I8&list=PL8XxoCaL3dBj-9DhstfK_krmviLwfN7mX&index=5)

### 29/10/2021 - Laboratory
* Practical class dedicated to the course's project

## Week 5
### 03/11/2021 - State management: view state
* Characterizing state
  * UI state (a.k.a view state) and application state
  * [Preserving view state across system initiated process terminations](https://developer.android.com/topic/libraries/architecture/saving-states)
* Activity lifecycle, revisited
  * onCreate(), onSaveInstanceState() and onRestoreInstanceState()
* [Parcelable contract](https://developer.android.com/reference/android/os/Parcelable)
  * Motivation
  * Manual implementation
  * Implementation with @Parcelize ([a Parcelable implementation generator](https://developer.android.com/kotlin/parcelize))
* [Saved State module for ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate)

For reference:
  * [Lecture video (in Portuguese)](https://www.youtube.com/watch?v=G-H-I-0ZP_o&list=PL8XxoCaL3dBj-9DhstfK_krmviLwfN7mX&index=6)
  * [Android Debug Bridge (adb) documentation](https://developer.android.com/studio/command-line/adb)

### 05/11/2021 - Laboratory
* Practical class dedicated to the course's project

## Week 6
### 10/11/2021 - Laboratory
* Practical class dedicated to the course's project

### 12/11/2021 - Laboratory
* Practical class dedicated to the course's project

## Week 7
### 17/11/2021 - Laboratory
* Practical class dedicated to the course's project

### 19/11/2021 - Laboratory
* Practical class dedicated to the course's project

## Week 8
### 24/11/2021 - Android Application Architecture
* State management: persistent application state
  * Persisting application state in a local relational DB using [Room](https://developer.android.com/training/data-storage/room#kotlin)
  * Architectural elements: 
    * [Entities](https://developer.android.com/training/data-storage/room/defining-data);
    * [DAOs](https://developer.android.com/training/data-storage/room/accessing-data), and;
    * [Database](https://developer.android.com/training/data-storage/room#database)
* Building the UI: Displaying lists of items
  * Adapting data to views (Adapter pattern)
  * [Using Recycler View](https://developer.android.com/guide/topics/ui/layout/recyclerview)
    * View Holder pattern
    * View recycling
For reference:
  * [Lecture video (in Portuguese)](https://youtu.be/U48MutMWbFs)
  * [Saving data using SQLite](https://developer.android.com/training/data-storage/sqlite)

### 26/11/2021 - Android Application Architecture, continued
* State management: persistent application state, continued
  * Persisting application state in a local relational DB using [Room](https://developer.android.com/training/data-storage/room#kotlin), continued
  * Android threading model, revisited
    * [Handlers and HandlerThreads](https://developer.android.com/guide/background/threading)
* Considerations on the design of android applications
  * [Android application reference architecture](https://developer.android.com/jetpack/guide), revisited
For reference:
  * [Lecture video (in Portuguese)](https://youtu.be/8RlFCRV5hJA)

## Week 9
### 01/12/2021 - National Holliday

### 03/12/2021 - Android Application Architecture, continued
* Considerations on the design of Android applications, continued
  * [Android application reference architecture](https://developer.android.com/jetpack/guide), revisited
* State management: persistent application state, continued
  * Persisting application state in a local relational DB using [Room](https://developer.android.com/training/data-storage/room#kotlin), continued
    * [Type mappings in Room](https://developer.android.com/training/data-storage/room/referencing-data): TypeConverters
  * Android threading model, revisited
    * LiveData as a means to signal asynchronous operations completion   
* For reference:
  * [Lecture video (in Portuguese)](https://youtu.be/u7HnbJ-T7RU)
  * [Property Animations](https://developer.android.com/guide/topics/graphics/prop-animation)
  * [Write asynchronous DAO queries](https://developer.android.com/training/data-storage/room/async-queries)

## Week 10
### 08/12/2021 - National Holliday

### 10/12/2021 - Android Application Architecture, continued
* Considerations on the design of Android applications, continued
  * [Android application reference architecture](https://developer.android.com/jetpack/guide), revisited
* [Performing background work](https://developer.android.com/guide/background)
  * Motivation
  * Definition of background work (i.e. non user facing)
  * Resource management, revisited
    * [Processes](https://developer.android.com/guide/components/processes-and-threads#Processes)
* Introduction to the [Work Manager API](https://developer.android.com/topic/libraries/architecture/workmanager/)
  * Purpose and motivation
* For reference:
  * [Lecture video (in Portuguese)](https://youtu.be/aWWlVl57DUs)

## Week 11
### 15/12/2021 - Performing Background Work
* [Performing background work](https://developer.android.com/guide/background)
  * Motivation
  * Definition of background work (i.e. non user facing)
  * Resource management, revisited
    * [Processes](https://developer.android.com/guide/components/processes-and-threads#Processes)
    * [Processes and application lifecycle](https://developer.android.com/guide/components/activities/process-lifecycle)
  * Tradicional Android application architecture using BroadcastReceiver and Service components
* Introduction to the Work Manager API, continued
  * Purpose and motivation
  * Worker definitions and corresponding threading models
    * [Worker](https://developer.android.com/topic/libraries/architecture/workmanager/advanced/worker)
    * [ListenableWorker](https://developer.android.com/topic/libraries/architecture/workmanager/advanced/listenableworker)
* For reference:
  * Lecture video (in Portuguese) (__coming soon__)

### 17/12/2021 - TBD