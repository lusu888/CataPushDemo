# CataPushDemo with HMS push
This guideline is from https://github.com/Catapush/catapush-docs/blob/master/AndroidSDK/DOCUMENTATION_ANDROID_SDK.md#Huawei-Push-Kit-prerequisites
## Pay attention. You MUST change the code like this:
+ Do NOT write the class "MyApplication", write a class as your Application Name, Such My Name is "CataPushDemo"
such as
```Java
public class CataPushDemo extends MultiDexApplication {
```

+ The Application Name Class import is like this
 ```Java
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.multidex.MultiDexApplication;

import com.catapush.library.Catapush;

import com.catapush.library.hms.CatapushHms;
import com.catapush.library.interfaces.Callback;
import com.catapush.library.notifications.NotificationTemplate;

import java.io.IOException;
import java.util.Collections;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;
```
+ in The Application Name Class, change code Boolean to Object like this
```Java
public void success(Object response) {
```

+ in App/build.gradle add this in dependencies
```Java
implementation "androidx.multidex:multidex:2.0.1"//這個沒有寫
```
