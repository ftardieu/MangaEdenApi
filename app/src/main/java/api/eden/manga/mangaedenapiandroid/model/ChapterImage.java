package api.eden.manga.mangaedenapiandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChapterImage
{
    @SerializedName("images")
    @Expose
    private  List<List<String>> images = null;

    public List<List<String>> getImages() {
        return images;
    }

    public void setImages( List<List<String>> images) {
        this.images = images;
    }

    public ChapterImage( List<List<String>>images) {
        this.images = images;
    }
}
