package api.eden.manga.mangaedenapiandroid.interfaces;


import api.eden.manga.mangaedenapiandroid.model.MangaDetail;
import api.eden.manga.mangaedenapiandroid.model.Response;
import retrofit2.Call;

import retrofit2.http.GET;


public interface MangaEden {


    @GET("list/0/")
    Call<Response> getResponse();


    @GET("{manga_id}/")
    Call<MangaDetail> getMangaDetail();



}