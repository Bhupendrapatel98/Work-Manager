# Overview of WorkManager 

**Key Features of WorkManager:** <br>
WorkManager is a Android architecture component that allows you to run tasks asynchronously in the background, even when the app is not running or 
the device is in Doze mode. It's a part of the Android Jetpack, a set of libraries that help you build robust, scalable, and maintainable apps.<br>

**1. Background Task Management:** <br>
WorkManager ensures that tasks are executed even if the app is terminated or the device restarts. This reliability makes it an excellent 
choice for managing background operations.

**2. Wide Compatibility:** <br>
WorkManager is compatible with Android API level 14 and higher, allowing consistent behavior across a broad range of devices.

**3. Flexibility in Task Management:** <br>
It supports various types of work requests, including one-time and periodic tasks, and allows chaining of tasks for more complex workflows.

**4. Observability:** <br>
You can use LiveData or Flow to monitor the status of background tasks, enabling you to update the UI or manage task results effectively.

**5. Task Persistence:** <br>
WorkManager persists tasks in a database. This means that even if the app is closed or the device reboots, pending tasks will be rescheduled and executed.

# Why WorkManager over Coroutines + APIs?
While coroutines and APIs can handle background tasks, WorkManager offers several additional features that make it preferable for specific scenarios:

**Guaranteed Execution:**
Unlike coroutines, which are tied to the app's lifecycle, WorkManager ensures task completion even if the app is killed or the device restarts.

**Task Persistence:**
Tasks managed by WorkManager are stored in a database, so they survive app restarts or device reboots, and are executed when conditions are met.

**Automatic Retry Mechanism:**
WorkManager has a built-in retry feature that retries tasks if they fail, which needs to be manually implemented when using coroutines and APIs.

**Constraints and Scheduling:**
You can specify conditions such as network type, battery level, or charging state to control when tasks are executed. Tasks can also be scheduled to run at specific intervals or times.

**Chaining and Parallel Execution:**
WorkManager supports chaining multiple tasks for sequential execution and running tasks in parallel, enhancing performance for complex workflows.

**Integration with Android Architecture:**
As a part of Android Jetpack, WorkManager integrates seamlessly with other architecture components like Room, LiveData, and ViewModel.

# Choosing the Right Tool:

- **Use WorkManager** when:
  1. Tasks are critical to app functionality and must be completed regardless of app state (e.g., data synchronization, image uploads).
  2. Long-running tasks that shouldn't affect the app's performance (e.g., video processing, large file uploads).
  3. You need to control task execution conditions (e.g., network constraints, battery level).
  4. Automatic retry logic for tasks that may fail is required.

- **Use Coroutines + APIs** for:
  1. Simple background tasks like fetching data or performing lightweight computations.
  2. Real-time data processing needs, such as live updates or streaming data.
 
# Creating Work Requests
We can create both one-time and periodic tasks using WorkManager.

**One-Time WorkRequest**
To schedule a one-time task:

```
// Create a OneTimeWorkRequest to run one time
val workRequest = OneTimeWorkRequest.Builder(MyWork::class.java)
    .setInitialDelay(1, TimeUnit.MINUTES) // Set a delay before the task starts
    .addTag("my_unique_tag") // Add a unique tag for identifying this task
    .build()

// Schedule the OneTimeWorkRequest
WorkManager.getInstance(this).enqueue(workRequest)
```

**Periodic WorkRequest**
To schedule a periodic task:

```
// Create a PeriodicWorkRequest to run every 15 minutes
val periodicWorkRequest = PeriodicWorkRequest.Builder(
    MyWork::class.java,
    15,
    TimeUnit.MINUTES
).addTag("my_unique_tag") // Add a unique tag for identifying this task
 .build()

// Schedule the PeriodicWorkRequest
WorkManager.getInstance(this).enqueue(periodicWorkRequest)
```

**Canceling Work Requests**
To cancel a work request by its unique tag:
```
// Cancel the work request with the specified tag
WorkManager.getInstance(this).cancelUniqueWork("my_unique_tag")
```

**Implementing the Worker Class**
The core of our background task is the MyWork class:

```
class MyWork(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        // Perform your background task here
        
        // Return Result.SUCCESS if the task completed successfully
        return Result.success()
    }
}
```



