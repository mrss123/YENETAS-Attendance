# Production ProGuard / R8 Rules for Offline App with R8 Full Mode

# 1. Attributes and Metadata
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# 2. Android Core & Lifecycle Components
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends androidx.lifecycle.ViewModel {
    public <init>(...);
}

# 3. Room Database & SQLite Persistence
-keep class androidx.room.RoomDatabase
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class * { *; }
-keepclassmembers @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao interface * { *; }
-keepclassmembers @androidx.room.Dao interface * { *; }
-keep class * extends androidx.room.migration.Migration
-dontwarn androidx.room.paging.**

# 4. App Data Models & Entities
-keepclassmembers class com.example.data.** {
    <fields>;
    <init>(...);
}

# 5. Jetpack Compose
-keepclassmembers class * extends androidx.compose.ui.Modifier$Element { *; }
-keepclassmembers class * extends androidx.compose.runtime.State { *; }
-dontwarn androidx.compose.**

# 6. Kotlin Coroutines & Flow
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# 7. General Optimization & Warnings
-dontwarn java.lang.invoke.**
-dontwarn javax.annotation.**


