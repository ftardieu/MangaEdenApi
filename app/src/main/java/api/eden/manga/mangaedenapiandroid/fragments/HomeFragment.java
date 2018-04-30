package api.eden.manga.mangaedenapiandroid.fragments;


import android.os.Bundle;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;

import java.util.List;

import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.adapter.FavoritesAdapter;
import api.eden.manga.mangaedenapiandroid.model.FavoritesManga;
import api.eden.manga.mangaedenapiandroid.model.Manga;
import api.eden.manga.mangaedenapiandroid.model.Profile;


public class HomeFragment extends Fragment implements FavoritesAdapter.FavoritesAdapterListener {


    private FavoritesAdapter fAdapter;
    private List<FavoritesManga> favoritesManga;
    private RecyclerView recyclerView;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.listFavorites);
        setHasOptionsMenu(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        Profile profile = new Profile();
        setRetainInstance(false);
        DialogClassFragment myDialog = new DialogClassFragment();
        if (profile.getProfile() == null) {
            myDialog.show(getFragmentManager() , "Pseudosubmit");
        }else{
            profile = profile.getProfile();
            FavoritesManga favorites = new FavoritesManga();
            favoritesManga = favorites.getFavoritesMangas(profile);

            fAdapter = new FavoritesAdapter(getActivity(), favoritesManga  ,this);
            fAdapter .notifyDataSetChanged();

            //Todo set le titre pour chaque page.
            getActivity().setTitle("MangaEdenApiAndroid") ;
        }

        recyclerView.setAdapter(fAdapter);
        return view;
    }


    @Override
    public void onFavoritesSelected(FavoritesManga favoritesManga) {

    }
}