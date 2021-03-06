package api.eden.manga.mangaedenapiandroid.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.google.gson.annotations.Expose;

import java.util.Date;

public class Chapter extends Model{


    @Column(name = "num_chapter")
    @Expose
    public Integer num_chapter;

    @Column(name = "date_chapter")
    @Expose
    public Long date_chapter;
    @Column(name = "name_chapter")
    @Expose
    public String name_chapter;
    @Column(name = "id_chapter")
    @Expose
    public String id_chapter;

    public Chapter() {

    }

    public Integer getNum_chapter() {
        return num_chapter;
    }

    public void setNum_chapter(Integer num_chapter) {
        this.num_chapter = num_chapter;
    }

    public Chapter(Integer num_chapter, Long date_chapter, String name_chapter, String id_chapter) {
        this.num_chapter = num_chapter;
        this.date_chapter = date_chapter;
        this.name_chapter = name_chapter;
        this.id_chapter = id_chapter;
    }

    public Long getDate_chapter() {
        return date_chapter;

    }

    public void setDate_chapter(Long date_chapter) {
        this.date_chapter = date_chapter;
    }

    public String getName_chapter() {
        return name_chapter;
    }

    public void setName_chapter(String name_chapter) {
        this.name_chapter = name_chapter;
    }

    public String getId_chapter() {
        return id_chapter;
    }

    public void setId_chapter(String id_chapter) {
        this.id_chapter = id_chapter;
    }
}
