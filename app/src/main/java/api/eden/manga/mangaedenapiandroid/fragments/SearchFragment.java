package api.eden.manga.mangaedenapiandroid.fragments;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import api.eden.manga.mangaedenapiandroid.MainActivity;
import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.adapter.MangasAdapter;
import api.eden.manga.mangaedenapiandroid.model.FavoritesManga;
import api.eden.manga.mangaedenapiandroid.model.Manga;
import api.eden.manga.mangaedenapiandroid.model.Profile;


public class SearchFragment extends Fragment  implements MangasAdapter.MangasAdapterListener{

    private RecyclerView recyclerView;
    private List<Manga> mangaList;
    private MangasAdapter mAdapter;
    private SearchView searchView;
    private ToggleButton myfavoritesButton;
    private MangasAdapter.MangasAdapterListener mListener ;
    private Boolean mAlreadyLoaded = false;
    private String oldSearch = "";

    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("test" , "changed");
        mAlreadyLoaded = true;
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Toast.makeText(getActivity(),"PORTRAIT",Toast.LENGTH_LONG).show();

            //add your code what you want to do when screen on PORTRAIT MODE
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Toast.makeText(getActivity(),"LANDSCAPE",Toast.LENGTH_LONG).show();
            //add your code what you want to do when screen on LANDSCAPE MODE
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        setHasOptionsMenu(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        myfavoritesButton = (ToggleButton) view.findViewById(R.id.myfavoritesButton);
        if (savedInstanceState == null && !mAlreadyLoaded) {
            mAlreadyLoaded = true;
            Manga manga = new Manga();
            mangaList = manga.getMangas();
            mAdapter = new MangasAdapter(getActivity(), mangaList , mangaList ,this);
            mAdapter.notifyDataSetChanged();
        }else{
            mAdapter = new MangasAdapter(getActivity(),mangaList ,mAdapter.getMangaListFiltered() ,this);
        }

        recyclerView.setAdapter(mAdapter);


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconified(true);

        if (oldSearch != "" ){
            searchView.setQuery( oldSearch , true);
            mAdapter.getFilter().filter(oldSearch);
            searchView.setIconified(false);
            searchView.clearFocus();
            //            //Todo set le titre pour chaque page
            getActivity().setTitle("MangaEden") ;

        }

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                oldSearch = searchView.getQuery().toString();
                return false;
            }
        });

    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("manga_search" , searchView.getQuery().toString());
        Log.d("State" , "saveInstance");


    }

    @Override
    public void onMangaSelected(Manga manga) {


        MangaFragment mangaFragment = new MangaFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("manga_title"  ,manga.getT());
        bundle.putString("manga_id"  ,manga.getI());
        mangaFragment.setArguments(bundle);
        transaction.replace(R.id.content, mangaFragment);

        transaction.addToBackStack(null);

        transaction.commit();
    }


    public FavoritesManga addFarovitesManga(Boolean isChecked , Manga manga) {
        Profile profile = new Profile();
        profile = profile.getProfile();
        Date currentTime = Calendar.getInstance().getTime();

        FavoritesManga favoritesManga = new FavoritesManga();
        favoritesManga = favoritesManga.getFavoriteManga(manga, profile);

        if (isChecked) {
            if (favoritesManga == null){
                favoritesManga = new FavoritesManga();
                favoritesManga.setFavorite(true);
                favoritesManga.setLast_chapter_read(0);
                favoritesManga.setLast_chapter_started(0);
                favoritesManga.setLast_page_read(0);
                favoritesManga.setLast_read_at(currentTime);
                favoritesManga.setManga_id(manga.getI());
                favoritesManga.setProfile_pseudo(profile.getPseudo());


            }else{
                favoritesManga.setFavorite(true);
            }
        } else {
            favoritesManga.setFavorite(false);
        }
        favoritesManga.save();
        return favoritesManga;
    }

}