package it.hw.catapushdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.catapush.library.Catapush;
import com.catapush.library.interfaces.RecoverableErrorCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Catapush.getInstance()
                .setUser("lusu888", "Zz150317")
                .start(new RecoverableErrorCallback() {
                    @Override
                    public void success(Object aBoolean) {//修改
                        Log.d("MyApp", "Catapush has been successfully started");
                    }
                    @Override
                    public void warning(@NonNull Throwable throwable) {
                        Log.d("MyApp", "Catapush warning: " + throwable.getMessage());
                    }
                    @Override
                    public void failure(@NonNull Throwable throwable) {
                        Log.d("MyApp", "Catapush can't be started: " + throwable.getMessage());
                    }
                });
    }
}