package api.eden.manga.mangaedenapiandroid.utils;

import api.eden.manga.mangaedenapiandroid.interfaces.MangaEden;
import api.eden.manga.mangaedenapiandroid.retrofit.RetrofitClient;

public class ApiUtils {

    public static final String BASE_URL = "https://www.mangaeden.com/api/";

    public static MangaEden getService() {
        return RetrofitClient.getClient(BASE_URL).create(MangaEden.class);
    }
}
