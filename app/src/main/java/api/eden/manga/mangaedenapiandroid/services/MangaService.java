package api.eden.manga.mangaedenapiandroid.services;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

import api.eden.manga.mangaedenapiandroid.MangaEden;
import api.eden.manga.mangaedenapiandroid.models.Manga;
import api.eden.manga.mangaedenapiandroid.models.Response;
import retrofit.RestAdapter;

public class MangaService {

    public List<Manga> getMangaList() {
        MangaEden mangaEden = new RestAdapter.Builder().setEndpoint(MangaEden.ENDPOINT)
                .build().create(MangaEden.class);
        Response listMangaResponse = mangaEden.listManga();
        List<Manga> mangaList = listMangaResponse.getManga();

        for (Integer i = 0; i < mangaList.size() -1; i++) {
            Log.d("MANGA", mangaList.get(i).getT());
        }

        return mangaList;
    }
}
