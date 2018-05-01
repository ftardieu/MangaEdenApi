package api.eden.manga.mangaedenapiandroid.interfaces;


import api.eden.manga.mangaedenapiandroid.model.MangaDetail;
import api.eden.manga.mangaedenapiandroid.model.Response;
import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Path;


public interface MangaEden {


    @GET("list/0/")
    Call<Response> getResponse();


    @GET("manga/{manga_id}/")
    Call<MangaDetail> getMangaDetail(@Path("manga_id") String manga_id);



}