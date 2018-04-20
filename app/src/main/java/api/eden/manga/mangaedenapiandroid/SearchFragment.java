package api.eden.manga.mangaedenapiandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import api.eden.manga.mangaedenapiandroid.models.Manga;
import api.eden.manga.mangaedenapiandroid.models.Response;
import api.eden.manga.mangaedenapiandroid.services.MangaService;
import retrofit.RestAdapter;


public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new ListMangaTask().execute();

        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    public void afficherListManga(List<Manga> listManga) {
        Toast.makeText(getContext(), Integer.toString(listManga.size()), Toast.LENGTH_SHORT).show();
    }

    class ListMangaTask extends AsyncTask<String,Void,List<Manga>> {

        @Override
        protected List<Manga> doInBackground(String...params) {
            return new MangaService().getMangaList();
        }

        @Override
        protected void onPostExecute(List<Manga> listManga) {
            super.onPostExecute(listManga);
            afficherListManga(listManga);
        }
    }

}