package api.eden.manga.mangaedenapiandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.adapter.PagerAdapter;
import api.eden.manga.mangaedenapiandroid.application.Application;
import api.eden.manga.mangaedenapiandroid.interfaces.MangaEden;
import api.eden.manga.mangaedenapiandroid.model.ChapterImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterFragment extends Fragment {


    private PagerAdapter pAdapter;
    private ViewPager mViewPager;
    private ArrayList<String> array = new ArrayList<>();
    private MangaEden mEden;

    private Application app = new Application();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(
                R.layout.fragment_chapter, container, false);

        String chapter_id = getArguments().getString("favorites_chapter_id");
        Log.d("Chapter" , "Chapter");
        mEden = app.getMangaEden();


        pAdapter = new PagerAdapter(getActivity() , array );

        mViewPager = view.findViewById(R.id.manga_chapter);
        mViewPager.setAdapter(pAdapter);
         getArrayChaptersId(chapter_id , array ,  pAdapter);
        pAdapter.notifyDataSetChanged();
        return view;
    }

    public void getArrayChaptersId(String chapter_id ,final  ArrayList<String> array , final PagerAdapter pAdapter) {


        mEden.getChapters(chapter_id).enqueue(new Callback<ChapterImage>() {


            ArrayList<String> arrayTemp = new ArrayList<>();
            @Override
            public void onResponse(Call<ChapterImage> call, Response<ChapterImage> response) {
                Log.d("test", "test");
                if (response.isSuccessful()) {
                    List<List<String>> listChapter = response.body().getImages();

                    for (List<String> listImages : listChapter) {
                        arrayTemp.add(listImages.get(1));
                        Log.d("Array", "Add");
                    }
                    Log.d("arrayTemp" , arrayTemp.toString());
                    array.addAll(arrayTemp);
                    Collections.reverse(array);
                    pAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    Log.d("MainActivity", Integer.toString(statusCode));
                    Log.d("MainActivity", response.toString());
                }
            }

            @Override
            public void onFailure(Call<ChapterImage> call, Throwable t) {
                Log.d("chapterFragment", t.getMessage());
            }
        });
    }
}