package api.eden.manga.mangaedenapiandroid.fragments;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import api.eden.manga.mangaedenapiandroid.MainActivity;
import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.adapter.MangasAdapter;
import api.eden.manga.mangaedenapiandroid.model.Manga;


public class SearchFragment extends Fragment  implements MangasAdapter.MangasAdapterListener{

    private RecyclerView recyclerView;
    private List<Manga> mangaList;
    private MangasAdapter mAdapter;
    private SearchView searchView;
    private MangasAdapter.MangasAdapterListener mListener ;
    private Boolean mAlreadyLoaded = false;
    private String oldSearch = "";

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);



        if (savedInstanceState == null && !mAlreadyLoaded) {
            mAlreadyLoaded = true;
            Manga manga = new Manga();
            mangaList = manga.getMangas();

        }


        setHasOptionsMenu(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MangasAdapter(getActivity(), mangaList ,this);
        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQuery( oldSearch , true);

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
                return false;
            }
        });

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onMangaSelected(Manga manga) {

        MangaFragment mangaFragment = new MangaFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("manga_title"  ,manga.getT());
        bundle.putString("manga_search"  , searchView.getQuery().toString());
        mangaFragment.setArguments(bundle);
        transaction.replace(R.id.content, mangaFragment);

        transaction.addToBackStack(null);

        transaction.commit();
    }
}