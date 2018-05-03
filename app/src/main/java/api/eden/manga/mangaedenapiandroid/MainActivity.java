package api.eden.manga.mangaedenapiandroid;


import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;

import java.util.List;
import java.util.concurrent.ExecutionException;

import api.eden.manga.mangaedenapiandroid.adapter.MangasAdapter;
import api.eden.manga.mangaedenapiandroid.fragments.DialogClassFragment;
import api.eden.manga.mangaedenapiandroid.fragments.HomeFragment;
import api.eden.manga.mangaedenapiandroid.fragments.MangaFragment;
import api.eden.manga.mangaedenapiandroid.fragments.SearchFragment;
import api.eden.manga.mangaedenapiandroid.interfaces.MangaEden;
import api.eden.manga.mangaedenapiandroid.model.Manga;
import api.eden.manga.mangaedenapiandroid.model.Profile;
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
                    Log.d("test", "test");
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


        Log.d("mainActivity", "mainAcivity");
        LinearLayout linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        linlaHeaderProgress.setVisibility(View.VISIBLE);


        loadDatas(linlaHeaderProgress);
       // AsyncTask asyncCaller =  new AsyncCaller(linlaHeaderProgress).execute();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult" , "test");
    }

    private void showFragment(Fragment fragment , Boolean addToBackStack ) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        public List<Manga> myList ;
        public LinearLayout linlaHeaderProgress ;
        public AsyncCaller (List <Manga> myList , LinearLayout linlaHeaderProgress){
            super();
            this.myList = myList ;
            this.linlaHeaderProgress = linlaHeaderProgress;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("jeremy", "insert");
            ActiveAndroid.beginTransaction();
            int i = 0 ;
            for (Manga manga : myList ){
                manga.save();

                if (i == 100 ) {
                    ActiveAndroid.setTransactionSuccessful();
                    ActiveAndroid.endTransaction();
                    i = 0;
                    ActiveAndroid.beginTransaction();
                }
                i++;

            }

            ActiveAndroid.setTransactionSuccessful();
            ActiveAndroid.endTransaction();
            return null;
        }


        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            linlaHeaderProgress.setVisibility(View.GONE);
            showFragment(new HomeFragment() , false);
        }
    }

    public void loadDatas(final LinearLayout linlaHeaderProgress){
        Log.d("LoadDatas" , "LoadDatas");
        mEden = ApiUtils.getService();
        mEden.getResponse().enqueue(new Callback<Response>() {



            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if(response.isSuccessful()) {

                    List<Manga> myList = response.body().getManga();

                     AsyncTask asyncCaller =  new AsyncCaller(myList, linlaHeaderProgress).execute();

                }else {
                    int statusCode  = response.code();
                    Log.d("MainActivity", Integer.toString(statusCode));
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