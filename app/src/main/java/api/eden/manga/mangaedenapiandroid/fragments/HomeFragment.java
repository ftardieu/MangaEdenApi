package api.eden.manga.mangaedenapiandroid.fragments;


import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;

import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.model.Profile;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Profile profile = new Profile();
        DialogClassFragment myDialog = new DialogClassFragment();
        if (profile.getProfile() == null) {
            myDialog.show(getFragmentManager() , "Pseudosubmit");
        }



        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}