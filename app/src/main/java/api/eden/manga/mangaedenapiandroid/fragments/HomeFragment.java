package api.eden.manga.mangaedenapiandroid.fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import api.eden.manga.mangaedenapiandroid.Manager.MangaManager;
import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.adapter.FavoritesAdapter;
import api.eden.manga.mangaedenapiandroid.application.Application;
import api.eden.manga.mangaedenapiandroid.interfaces.MangaEden;
import api.eden.manga.mangaedenapiandroid.model.FavoritesManga;
import api.eden.manga.mangaedenapiandroid.model.Profile;


public class HomeFragment extends Fragment implements FavoritesAdapter.FavoritesAdapterListener {


    private FavoritesAdapter fAdapter;
    private List<FavoritesManga> favoritesManga;
    private RecyclerView recyclerView;
    private MangaEden mEden;
    private Application app = new Application();
    private MangaManager mangaManager = new MangaManager();

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

        getActivity().setTitle("MangaEdenApiAndroid") ;

        Profile profile = app.getProfile();
        setRetainInstance(false);
        DialogClassFragment myDialog = new DialogClassFragment();

        if (profile == null) {
            myDialog.show(getFragmentManager() , "Pseudosubmit");
        }else{

            //favoritesManga = mangaManager.getFavoritesManga(profile);
            favoritesManga = new ArrayList<>();
            fAdapter = new FavoritesAdapter(favoritesManga , this);
            recyclerView.setAdapter(fAdapter);

            mangaManager.getFavoritesManga(profile, fAdapter, favoritesManga);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //fAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFavoritesSelected(FavoritesManga favoritesManga) {
        Toast.makeText(getActivity(), favoritesManga.getNext_chapter_id(), Toast.LENGTH_SHORT).show();
        ChapterFragment chapterFragment = new ChapterFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("favorites_chapter_id"  ,favoritesManga.getNext_chapter_id());
        chapterFragment.setArguments(bundle);
        transaction.replace(R.id.content, chapterFragment);

        transaction.addToBackStack(null);

        transaction.commit();
    }


}