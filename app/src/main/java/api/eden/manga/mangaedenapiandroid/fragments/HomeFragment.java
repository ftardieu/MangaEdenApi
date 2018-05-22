package api.eden.manga.mangaedenapiandroid.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        setHasOptionsMenu(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        BottomNavigationView navigation =  getActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);
        getActivity().setTitle("MangaEdenApiAndroid") ;

        Profile profile = app.getProfile();
        setRetainInstance(false);
        DialogClassFragment myDialog = new DialogClassFragment();

        if (profile == null) {
            myDialog.show(getFragmentManager() , "Pseudosubmit");
        }else{
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
        ChapterFragment chapterFragment = new ChapterFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("favorites_chapter_id"  ,favoritesManga.getNext_chapter_id());
        bundle.putString("manga_id"  ,favoritesManga.getManga_id());
        chapterFragment.setArguments(bundle);
        transaction.replace(R.id.content, chapterFragment);

        transaction.addToBackStack(null);

        transaction.commit();
    }


}