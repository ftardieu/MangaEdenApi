package api.eden.manga.mangaedenapiandroid.Manager;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import api.eden.manga.mangaedenapiandroid.adapter.FavoritesAdapter;
import api.eden.manga.mangaedenapiandroid.adapter.MangasAdapter;
import api.eden.manga.mangaedenapiandroid.interfaces.MangaEden;
import api.eden.manga.mangaedenapiandroid.model.Chapter;
import api.eden.manga.mangaedenapiandroid.model.FavoritesManga;
import api.eden.manga.mangaedenapiandroid.model.MangaDetail;
import api.eden.manga.mangaedenapiandroid.model.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import api.eden.manga.mangaedenapiandroid.application.Application;

public class MangaManager {

    private List<Chapter> listChapters;
    private Application app = new Application();
    public void loadMangaDetail(final FavoritesManga fManga , final FavoritesAdapter adapter){
        MangaEden mEden = app.getMangaEden();
        mEden.getMangaDetail(fManga.getManga_id()).enqueue(new Callback<MangaDetail>() {

            @Override
            public void onResponse(Call<MangaDetail> call, retrofit2.Response<MangaDetail> response) {
                if (response.isSuccessful()){
                    List<List<String>> listChapter = response.body().getChapters();
                    listChapters = new ArrayList<>();
                    for (List<String> chapter : listChapter){
                        String dc = chapter.get(1).replaceAll("[\\D]","");
                        dc = dc.substring(0, dc.length() -1 );
                        Long dateChapter = Long.parseLong(dc);
                        Chapter c = new Chapter(Double.parseDouble(chapter.get(0)) , dateChapter, chapter.get(2) , chapter.get(3) );
                        listChapters.add(c);
                    }
                    Chapter nextChapter  = new Chapter();

                    for (Chapter chap : listChapters){
                        if (  fManga.getLast_chapter_read() != null && Double.compare(chap.getNum_chapter() , fManga.getLast_chapter_read()) == 0){
                            fManga.setNext_chapter_id(nextChapter.getId_chapter());
                            fManga.setNext_chapter_num(nextChapter.getNum_chapter());
                            fManga.setNext_chapter_time(nextChapter.getDate_chapter());
                            fManga.setNext_chapter_title(nextChapter.getName_chapter());
                            fManga.save();
                            break;
                        }
                        nextChapter = chap;
                    }

                    if (fManga.getNext_chapter_time() == null){
                        fManga.setNext_chapter_id(nextChapter.getId_chapter());
                        fManga.setNext_chapter_num(nextChapter.getNum_chapter());
                        fManga.setNext_chapter_time(nextChapter.getDate_chapter());
                        fManga.setNext_chapter_title(nextChapter.getName_chapter());
                        fManga.save();
                    }
                    //adapter.notifyItemInserted(adapter.getItemCount());
                    adapter.notifyDataSetChanged();
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
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        private FavoritesManga fmanga;
        private FavoritesAdapter adapter;

        AsyncCaller (FavoritesManga fmanga, FavoritesAdapter adapter){
            super();
            this.fmanga = fmanga;
            this.adapter = adapter;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            loadMangaDetail(fmanga ,adapter);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
        }
    }

    public void getFavoritesManga(Profile profile, FavoritesAdapter favoritesAdapter, List<FavoritesManga> favoritesMangas){
        FavoritesManga favorite = new FavoritesManga();
        favoritesMangas.addAll(favorite.getFavoritesMangas(profile));
        if (favoritesMangas.size() > 0 ){
            for (FavoritesManga fmanga : favoritesMangas) {
                new AsyncCaller(fmanga, favoritesAdapter).execute();
            }
        }
    }

    // il faut faire des events / listener
}
