package api.eden.manga.mangaedenapiandroid;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import api.eden.manga.mangaedenapiandroid.models.Manga;
import api.eden.manga.mangaedenapiandroid.models.Response;
import retrofit.RestAdapter;


public class HomeFragment extends Fragment {

    TextView count;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}