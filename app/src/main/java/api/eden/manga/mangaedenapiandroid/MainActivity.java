package api.eden.manga.mangaedenapiandroid;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.activeandroid.ActiveAndroid;

import api.eden.manga.mangaedenapiandroid.model.Profile;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Integer view ;
    private String pseudo;

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


        Profile profile = new Profile();


        if (profile.getProfile() == null) {
            DialogClassFragment myDialog = new DialogClassFragment();


            myDialog.show(getFragmentManager(),"PseudoSubmit");
        }



        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        showFragment(new HomeFragment() , false);
    }

    private void showFragment(Fragment fragment , Boolean addToBackStack ) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        transaction.replace(R.id.content, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}