package api.eden.manga.mangaedenapiandroid;


import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import api.eden.manga.mangaedenapiandroid.fragments.HomeFragment;
import api.eden.manga.mangaedenapiandroid.fragments.LoadingFragment;
import api.eden.manga.mangaedenapiandroid.fragments.SearchFragment;
import api.eden.manga.mangaedenapiandroid.interfaces.MangaEden;
import api.eden.manga.mangaedenapiandroid.model.Manga;
import api.eden.manga.mangaedenapiandroid.model.Response;
import api.eden.manga.mangaedenapiandroid.utils.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {


    private MangaEden mEden;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(new HomeFragment() , false );
                    return true;
                case R.id.navigation_search:
                    showFragment(new SearchFragment() , false );
                    return true;
            }
            return false;
        }

    };

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        showFragment(new HomeFragment() , false);
        //linlaHeaderProgress.setVisibility(View.VISIBLE);

        //loadDatas(linlaHeaderProgress);
       // AsyncTask asyncCaller =  new AsyncCaller(linlaHeaderProgress).execute();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showFragment(Fragment fragment , Boolean addToBackStack ) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public class AsyncCaller extends AsyncTask<Void, Integer, Void>
    {
         List<Manga> myList ;
         LinearLayout linlaHeaderProgress ;
         ProgressBar progressBar;
         AsyncCaller (List <Manga> myList , LinearLayout linlaHeaderProgress){
            super();
            this.myList = myList;
            this.linlaHeaderProgress = linlaHeaderProgress;
        }

        public AsyncCaller setProgressBar(ProgressBar progressBar) {
            this.progressBar = progressBar;
            return this;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Manga mangaDB = new Manga();
            List<Manga> mangaList = mangaDB.getMangas();
            List<String> mangaIds = new ArrayList<>();

            for(Manga dbManga: mangaList) {
                mangaIds.add(dbManga.getI());
            }

            showFragment(new LoadingFragment() , false);

            int i = 0;
            int j = 0;
            int percent = 0;
            int listSize = myList.size();

            Calendar c = new GregorianCalendar();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            publishProgress((int) 0);
            ActiveAndroid.beginTransaction();
            for (Manga newManga : myList ){
                if(!mangaIds.contains(newManga.getI())) {
                    newManga.save();
                    i++;
                } else {

                    if(newManga.getLd() >= c.getTimeInMillis()) {
                        mangaDB.updateManga(newManga);
                        i++;
                    }

                }

                if (i == 100 ) {
                    percent = (j * 100 / listSize);
                    publishProgress((int) (percent));
                    ActiveAndroid.setTransactionSuccessful();
                    ActiveAndroid.endTransaction();
                    i = 0;
                    ActiveAndroid.beginTransaction();
                }
                j++;
            }

            ActiveAndroid.setTransactionSuccessful();
            ActiveAndroid.endTransaction();
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            this.progressBar.setProgress(values[0]);
        }


        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            linlaHeaderProgress.setVisibility(View.GONE);
            showFragment(new HomeFragment() , false);
        }
    }

    public void loadDatas(final LinearLayout linlaHeaderProgress){
        mEden = ApiUtils.getService();
        mEden.getResponse().enqueue(new Callback<Response>() {



            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if(response.isSuccessful()) {

                    List<Manga> myList = response.body().getManga();

                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.loadingMangaBar);
                    AsyncTask asyncCaller =  new AsyncCaller(myList, linlaHeaderProgress).setProgressBar(progressBar).execute();
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }




        });
    }


}