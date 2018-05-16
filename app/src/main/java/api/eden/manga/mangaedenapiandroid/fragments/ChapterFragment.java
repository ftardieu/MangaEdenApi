package api.eden.manga.mangaedenapiandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.adapter.PagerAdapter;
import api.eden.manga.mangaedenapiandroid.application.Application;
import api.eden.manga.mangaedenapiandroid.interfaces.MangaEden;
import api.eden.manga.mangaedenapiandroid.model.Chapter;
import api.eden.manga.mangaedenapiandroid.model.ChapterImage;
import api.eden.manga.mangaedenapiandroid.model.FavoritesManga;
import api.eden.manga.mangaedenapiandroid.model.MangaDetail;
import api.eden.manga.mangaedenapiandroid.model.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterFragment extends Fragment {


    private PagerAdapter pAdapter;
    private Menu menu ;
    private ViewPager mViewPager;
    private ArrayList<String> array = new ArrayList<>();
    private MangaEden mEden;
    private Application app = new Application();
    private List<Chapter> listChapters;
    private String chapter_id;
    private String manga_id;
    private Profile profile;
    private FavoritesManga fManga;
    private Integer actualPage ;

    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        this.menu = menu;
        inflater.inflate(R.menu.manga_btn , menu);
        if (fManga.getLast_chapter_read() != null ){
            menu.findItem(R.id.action_back).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_next:
                loadMangaDetail(fManga ,true );
                return true;


            case R.id.action_back:
                loadMangaDetail(fManga ,false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_chapter, container, false);


        chapter_id = getArguments().getString("favorites_chapter_id");
        manga_id = getArguments().getString("manga_id");
        mEden = app.getMangaEden();
        profile = profile.getProfile();
        fManga = fManga.getFavoriteManga(manga_id ,profile);
        actualPage = fManga.getLast_page_read();
        BottomNavigationView navigation =  getActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);


        pAdapter = new PagerAdapter(getActivity() , array );
        mViewPager = view.findViewById(R.id.manga_chapter);
        mViewPager.setAdapter(pAdapter);

        getArrayChaptersId(chapter_id , array ,  pAdapter , false);
        setHasOptionsMenu(true);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (actualPage < position){
                    fManga.setLast_page_read(position);
                    fManga.save();
                    actualPage = position;
                }else if (position == array.size() -1){
                    loadMangaDetail(fManga ,true );
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    public void getArrayChaptersId(String chapter_id , final  ArrayList<String> array , final PagerAdapter pAdapter , final Boolean isNew) {


        mEden.getChapters(chapter_id).enqueue(new Callback<ChapterImage>() {


            ArrayList<String> arrayTemp = new ArrayList<>();
            @Override
            public void onResponse(Call<ChapterImage> call, Response<ChapterImage> response) {
                if (response.isSuccessful()) {
                    List<List<String>> listChapter = response.body().getImages();

                    for (List<String> listImages : listChapter) {
                        arrayTemp.add(listImages.get(1));
                    }
                    array.clear();
                    array.addAll(arrayTemp);
                    Collections.reverse(array);

                    pAdapter.notifyDataSetChanged();
                    if (isNew) {
                        actualPage = 0;
                    }

                    mViewPager.setCurrentItem(actualPage);
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

    public String loadMangaDetail(final FavoritesManga fManga , final Boolean isNext ){


        mEden.getMangaDetail(fManga.getManga_id()).enqueue(new Callback<MangaDetail>() {

            @Override
            public void onResponse(Call<MangaDetail> call, retrofit2.Response<MangaDetail> response) {
                if (response.isSuccessful()){
                    List<List<String>> listChapter = response.body().getChapters();
                    listChapters = new ArrayList<>();
                    Integer min = null;
                    Integer max = null;

                    for (List<String> chapter : listChapter){

                        String dc = chapter.get(1).replaceAll("[\\D]","");
                        dc = dc.substring(0, dc.length() -1 );
                        Long dateChapter = Long.parseLong(dc);

                        Double d =  Double.parseDouble(chapter.get(0));
                        if (min == null && max == null){
                            min = d.intValue();
                            max = d.intValue();
                        }

                        if (d.intValue() < min ){
                            min = d.intValue();
                        }

                        if (!chapter.get(0).contains(".")){
                            Chapter c = new Chapter(d.intValue() , dateChapter, chapter.get(2) , chapter.get(3) );
                            listChapters.add(c);
                        }
                    }

                    Chapter nextChapter  = new Chapter();

                    if ( (!max.equals(fManga.getNext_chapter_num()) && isNext) || !isNext ){


                        for (Chapter chap : listChapters) {
                            if (isNext) {
                                menu.findItem(R.id.action_back).setVisible(true);
                                if (fManga.getLast_chapter_read() == null && fManga.getLast_page_read() == null) {

                                    fManga.setLast_chapter_read(fManga.getNext_chapter_num());
                                    fManga.save();
                                    break;
                                } else if (nextChapter != null && Double.compare(chap.getNum_chapter(), fManga.getNext_chapter_num()) == 0) {

                                    fManga.setLast_chapter_read(chap.getNum_chapter());
                                    fManga.setLast_page_read(0);
                                    fManga.setNext_chapter_id(nextChapter.getId_chapter());
                                    fManga.setNext_chapter_num(nextChapter.getNum_chapter());
                                    fManga.setNext_chapter_time(nextChapter.getDate_chapter());
                                    fManga.setNext_chapter_title(nextChapter.getName_chapter());
                                    fManga.save();
                                    getArrayChaptersId(nextChapter.getId_chapter(), array, pAdapter, true);
                                    break;
                                } else if (nextChapter == null && Double.compare(chap.getNum_chapter(), fManga.getNext_chapter_num()) == 0) {

                                    menu.findItem(R.id.action_next).setVisible(false);
                                }
                                nextChapter = chap;
                            } else if (!isNext && Double.compare(chap.getNum_chapter(), fManga.getLast_chapter_read()) == 0) {

                                if (fManga.getLast_chapter_read() > min) {
                                    fManga.setLast_chapter_read(fManga.getLast_chapter_read() - 1);

                                    fManga.setLast_page_read(0);
                                    fManga.setNext_chapter_id(chap.getId_chapter());
                                    fManga.setNext_chapter_num(chap.getNum_chapter());
                                    fManga.setNext_chapter_time(chap.getDate_chapter());
                                    fManga.setNext_chapter_title(chap.getName_chapter());
                                    fManga.save();

                                    menu.findItem(R.id.action_next).setVisible(true);
                                    getArrayChaptersId(chap.getId_chapter(), array, pAdapter, true);
                                    break;
                                } else {
                                    menu.findItem(R.id.action_back).setVisible(false);
                                }
                            }
                        }
                    }else{
                        fManga.setLast_page_read(0);
                        fManga.save();
                        menu.findItem(R.id.action_next).setVisible(false);
                    }

                }else {
                    int statusCode  = response.code();
                    Log.d("MainActivity", Integer.toString(statusCode));
                    Log.d("MainActivity", response.toString());

                }

            }

            @Override
            public void onFailure(Call<MangaDetail> call, Throwable t) {
                Log.d("HomeFragment", "Error loading datas home");
                Log.d("HomeFragment", t.getMessage());
            }
        });
        return fManga.getNext_chapter_id();
    }
}