package api.eden.manga.mangaedenapiandroid.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.SearchView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import api.eden.manga.mangaedenapiandroid.MainActivity;
import api.eden.manga.mangaedenapiandroid.Manager.MangaManager;
import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.application.Application;
import api.eden.manga.mangaedenapiandroid.interfaces.MangaEden;
import api.eden.manga.mangaedenapiandroid.model.Chapter;
import api.eden.manga.mangaedenapiandroid.model.FavoritesManga;
import api.eden.manga.mangaedenapiandroid.model.Manga;
import api.eden.manga.mangaedenapiandroid.model.MangaDetail;
import api.eden.manga.mangaedenapiandroid.model.Profile;
import retrofit2.Call;
import retrofit2.Callback;

public class MangaFragment extends Fragment {

    private TextView manga_title;
    private TextView manga_author;
    private TextView manga_status;
    private TextView manga_description;
    private TextView manga_lastUpdate;
    private TextView manga_category;
    private ImageView manga_img;

    private Spinner spinner;
    private String manga_id;
    private Menu menu;
    private View view;
    private Integer check = 0;
    private Application app = new Application();
    private MangaManager mangaManager = new MangaManager();
    private FavoritesManga fManga = new FavoritesManga();
    private Profile profile = new Profile();
    private MangaDetail mangaDetail;
    private boolean isSpinnerTouched = false;




    public MangaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        this.menu = menu;
        inflater.inflate(R.menu.favorites , menu);
        menu.findItem(R.id.action_favorite).setVisible(true);
        changeFavoriteIcon();
    }

    public void changeFavoriteIcon(){
        if (  fManga != null && fManga.getFavorite()){
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_star_full);
        }else{
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_star_empty);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_favorite:
                if (fManga != null){
                    fManga.setFavorite(!fManga.getFavorite());
                }else{
                    fManga = new FavoritesManga();
                    fManga.setFavorite(true);
                    fManga.setLast_chapter_read(null);
                    fManga.setNext_chapter_num(0);
                    fManga.setLast_chapter_started(null);
                    fManga.setLast_page_read(0);
                    fManga.setLast_read_at(null);
                    fManga.setManga_id(manga_id);
                    fManga.setProfile_pseudo(profile.getPseudo());
                    fManga.setManga_alias(mangaDetail.getTitle());
                    fManga.setNext_chapter_num(0);
                }

                fManga.save();
                changeFavoriteIcon();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_manga, container, false);
        manga_id = getArguments().getString("manga_id");
        profile = profile.getProfile();
        fManga = fManga.getFavoriteManga(manga_id , profile);
        BottomNavigationView navigation =  getActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);


        LoadMangaDetails(manga_id);



        setHasOptionsMenu(true);
        return view;
    }



    public void LoadMangaDetails (final String manga_id){

        MangaEden mEden = app.getMangaEden();
        mEden.getMangaDetail(manga_id).enqueue(new Callback<MangaDetail>() {

            @Override
            public void onResponse(Call<MangaDetail> call, retrofit2.Response<MangaDetail> response) {
                if (response.isSuccessful()){
                    manga_title =  view.findViewById(R.id.mangaTitle);
                    manga_author = view.findViewById(R.id.mangaAuthor);
                    spinner = view.findViewById(R.id.spinner1);
                    manga_status = view.findViewById(R.id.mangaStatus);
                    manga_category = view.findViewById(R.id.mangaCategory);
                    manga_description = view.findViewById(R.id.mangaDescription);
                    manga_img = view.findViewById(R.id.mangaImg);
                    manga_lastUpdate = view.findViewById(R.id.mangaLastUpdate);
                    mangaDetail = response.body();
                    String url = "https://cdn.mangaeden.com/mangasimg/" + mangaDetail.getImage();
                    Glide.with(getActivity()).load(url).into(manga_img);

                    String status = "In progress";
                    if (mangaDetail.getStatus()==2){
                        status = "Completed";
                    }
                    manga_title.setText(mangaDetail.getTitle());
                    manga_author.setText(mangaDetail.getAuthor());
                    Document doc = Jsoup.parse(mangaDetail.getDescription());
                    manga_description.setText(doc.text());
                    manga_status.setText(status);
                    String category  = mangaDetail.getCategories().toString();
                    category = category.replace("[" , "");
                    category = category.replace("]" , "");

                    manga_category.setText(category);
;
;
                    String dc = mangaDetail.getLastChapterDate().replaceAll("[\\D]","");
                    dc = dc.substring(0, dc.length() -1 );
                    Long dateChapter = Long.parseLong(dc);
                    String date = DateFormat.format("yyyy-MM-dd", dateChapter * 1000L ).toString();
                    manga_lastUpdate.setText(date);

                    ArrayList<String> listNumChapter = new ArrayList<>();
                    final List<List<String>> listChapter = response.body().getChapters();
                    final List<Chapter> listChapters = new ArrayList<>();

                    Integer min = null;

                    for (List<String> chapter : listChapter){
                        if (!chapter.get(0).contains(".")){
                            Double d =  Double.parseDouble(chapter.get(0));
                            if (min == null ){
                                min = d.intValue();
                            }

                            if (d.intValue() < min ){
                                min = d.intValue();
                            }


                            String datec = chapter.get(1).replaceAll("[\\D]","");
                            datec = datec.substring(0, datec.length() -1 );
                            Long dateChap= Long.parseLong(datec);

                            listNumChapter.add(chapter.get(0));
                            Chapter c = new Chapter(d.intValue() , dateChap , chapter.get(2) , chapter.get(3) );
                            listChapters.add(c);
                        }
                    }

                    ArrayAdapter<String>adapter = new ArrayAdapter<>(getActivity(),
                            R.layout.my_spinner_custom, listNumChapter);
                    spinner.setAdapter(adapter);
                    if (fManga != null && fManga.getNext_chapter_num() >= 0) {

                        int spinnerPosition = adapter.getPosition(String.valueOf(fManga.getNext_chapter_num()));
                        if (spinnerPosition > min){
                            spinner.setSelection(spinnerPosition);
                        }
                    }
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // Only called when the user changes the selection
                            if (check < 1){
                                check++;
                                return;
                            }
                            check = 0;
                            Chapter chap = listChapters.get(position);

                           if (fManga == null ) {
                               fManga = new FavoritesManga();
                               fManga.setManga_id(manga_id);
                               fManga.setManga_alias(mangaDetail.getTitle());
                               fManga.setProfile_pseudo(profile.getPseudo());
                               fManga.setFavorite(false);
                           }

                            if (!fManga.getNext_chapter_num().equals(chap.getNum_chapter())){
                                fManga.setLast_page_read(0);
                                fManga.setLast_chapter_read(fManga.getNext_chapter_num());
                                fManga.setNext_chapter_id(chap.getId_chapter());
                                fManga.setNext_chapter_num(chap.getNum_chapter());
                                fManga.setNext_chapter_time(chap.getDate_chapter());
                                fManga.setNext_chapter_title(chap.getName_chapter());
                                fManga.save();
                            }

                            ChapterFragment chapterFragment = new ChapterFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putString("favorites_chapter_id"  , chap.id_chapter);
                            bundle.putString("manga_id"  , manga_id);
                            chapterFragment.setArguments(bundle);
                            transaction.replace(R.id.content, chapterFragment);

                            transaction.addToBackStack(null);

                            transaction.commit();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                }else {
                    int statusCode  = response.code();
                    Log.d("mangaDetail", String.valueOf(statusCode));
                }
            }

            @Override
            public void onFailure(Call<MangaDetail> call, Throwable t) {
                Log.d("mangaDetail", t.getMessage());
            }
        });

    }

}