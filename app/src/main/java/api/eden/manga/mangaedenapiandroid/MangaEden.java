package api.eden.manga.mangaedenapiandroid;
import java.util.List;

import api.eden.manga.mangaedenapiandroid.models.Response;
import retrofit.Endpoint;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import api.eden.manga.mangaedenapiandroid.models.Manga;

public interface MangaEden {
    public static final String ENDPOINT = "https://www.mangaeden.com/api";

    @GET("/list/0/")
    Response listManga();

    MangaEden mangaEden = new RestAdapter.Builder().setEndpoint(MangaEden.ENDPOINT)
            .build().create(MangaEden.class);

}