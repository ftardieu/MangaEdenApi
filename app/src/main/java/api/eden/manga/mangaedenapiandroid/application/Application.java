package api.eden.manga.mangaedenapiandroid.application;

import api.eden.manga.mangaedenapiandroid.interfaces.MangaEden;
import api.eden.manga.mangaedenapiandroid.model.Profile;
import api.eden.manga.mangaedenapiandroid.utils.ApiUtils;

public class Application  {

    public MangaEden getMangaEden(){
        MangaEden mEden = ApiUtils.getService();
        return mEden;
    }
    public Profile getProfile(){
        Profile profile = new Profile();
        profile = profile.getProfile();
        return profile;
    }


}
