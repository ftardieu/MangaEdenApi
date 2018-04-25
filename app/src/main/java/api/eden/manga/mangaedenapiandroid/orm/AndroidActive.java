package api.eden.manga.mangaedenapiandroid.orm;

import android.app.Application;

import com.activeandroid.ActiveAndroid;


public class AndroidActive extends com.activeandroid.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}

