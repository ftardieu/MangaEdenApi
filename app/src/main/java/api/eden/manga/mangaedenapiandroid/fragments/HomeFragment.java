package api.eden.manga.mangaedenapiandroid.fragments;


import android.os.Bundle;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;
import android.widget.Toast;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.adapter.FavoritesAdapter;
import api.eden.manga.mangaedenapiandroid.adapter.MangasAdapter;
import api.eden.manga.mangaedenapiandroid.interfaces.MangaEden;
import api.eden.manga.mangaedenapiandroid.model.Chapter;
import api.eden.manga.mangaedenapiandroid.model.FavoritesManga;
import api.eden.manga.mangaedenapiandroid.model.Manga;
import api.eden.manga.mangaedenapiandroid.model.MangaDetail;
import api.eden.manga.mangaedenapiandroid.model.Profile;
import api.eden.manga.mangaedenapiandroid.model.Response;
import api.eden.manga.mangaedenapiandroid.utils.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;


public class HomeFragment extends Fragment implements FavoritesAdapter.FavoritesAdapterListener {


    private FavoritesAdapter fAdapter;
    private List<FavoritesManga> favoritesManga;
    private RecyclerView recyclerView;
    private MangaEden mEden;
    private List<Chapter> listChapters;
    private FavoritesAdapter.FavoritesAdapterListener fListener;
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
            mEden = ApiUtils.getService();

            fAdapter = new FavoritesAdapter(getActivity(), favoritesManga ,fListener );

            if (favoritesManga.size() > 0 ){
                for (FavoritesManga fmanga : favoritesManga ) {
                    loadMangaDetail(fmanga , fAdapter, fListener);
                }
            }


            fAdapter.notifyDataSetChanged();



            recyclerView.setAdapter(fAdapter);

            //Todo set le titre pour chaque page.
            getActivity().setTitle("MangaEdenApiAndroid") ;

        }

        return view;
    }


    @Override
    public void onFavoritesSelected(FavoritesManga favoritesManga) {

    }

    public void loadMangaDetail(final FavoritesManga fManga , final FavoritesAdapter fAdapter  , final FavoritesAdapter.FavoritesAdapterListener fListener){

        mEden.getMangaDetail(fManga.getManga_id()).enqueue(new Callback<MangaDetail>() {

            @Override
            public void onResponse(Call<MangaDetail> call, retrofit2.Response<MangaDetail> response) {
                if (response.isSuccessful()){
                    List<List<String>> listChapter = response.body().getChapters();
                    listChapters = new ArrayList<>();
                    for (List<String> chapter : listChapter){
                        Chapter c = new Chapter(Double.parseDouble(chapter.get(0)) , chapter.get(1), chapter.get(2) , chapter.get(3) );
                        listChapters.add(c);
                    }
                    Chapter nextChapter  = new Chapter();
                    for (Chapter chap : listChapters){

                        if (Double.compare(chap.getNum_chapter() , fManga.getLast_chapter_read()) == 0){
                            if (nextChapter.getNum_chapter() != null){
                                fManga.setNext_chapter_id(nextChapter.getId_chapter());
                                fManga.setNext_chapter_num(nextChapter.getNum_chapter());
                                //fManga.setNext_chapter_time( nextChapter.getDate_chapter());
                                fManga.setNext_chapter_title(nextChapter.getName_chapter());
                            }
                            fManga.setLast_chapter_read(chap.getNum_chapter());
                            fManga.save();
                        }
                        nextChapter = chap;
                    }
                }else {
                    int statusCode  = response.code();
                    Log.d("MainActivity", Integer.toString(statusCode));
                    Log.d("MainActivity", response.toString());
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<MangaDetail> call, Throwable t) {
                Log.d("HomeFragment", "Error loading datas home");
                Log.d("HomeFragment", t.getMessage());
            }
        });



    }
}