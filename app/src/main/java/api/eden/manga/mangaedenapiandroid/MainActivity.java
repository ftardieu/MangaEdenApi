package api.eden.manga.mangaedenapiandroid;


import android.content.Intent;
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

    private TextView mTextMessage;
    private Integer view ;
    private String pseudo;
    private MangaEden mEden;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(new HomeFragment() , false);
                    return true;
                case R.id.navigation_search:
                    showFragment(new SearchFragment() , false );
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        mEden = ApiUtils.getService();
        loadAnswers(linlaHeaderProgress);




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

    public void loadAnswers(final LinearLayout linlaHeaderProgress) {
        mEden.getResponse().enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if(response.isSuccessful()) {

                    List<Manga> myList = response.body().getManga();
                    ActiveAndroid.beginTransaction();
                    int i = 0 ;
                    for (Manga manga : myList ){
                        manga.save();
                        i++;
                    }
                    ActiveAndroid.setTransactionSuccessful();
                    ActiveAndroid.endTransaction();
                    Manga manga = new Manga();
                    List<Manga> mangas = manga.getMangas();
                    linlaHeaderProgress.setVisibility(View.GONE);
                    showFragment(new HomeFragment() , false);

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