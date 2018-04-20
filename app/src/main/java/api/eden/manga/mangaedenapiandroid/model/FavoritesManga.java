package api.eden.manga.mangaedenapiandroid.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;


@Table(name = "FavoritesManga")
public class FavoritesManga extends Model{
    // If name is omitted, then the field name is used.

    @Column(name = "last_read_at")
    public Date last_read_at;


    @Column(name = "last_chapter_read")
    public Integer last_chapter_read;

    @Column(name = "last_chapter_started")
    public Integer last_chapter_started;


    @Column(name = "last_page_read")
    public Integer last_page_read;

    @Column(name = "manga_id")
    public String manga_id;

    @Column(name = "profile_id")
    public Profile profile_id;






    public FavoritesManga() {
        super();
    }

    public FavoritesManga( Date last_read_at , Integer last_chapter_read , Integer last_chapter_started , Integer last_page_read , String manga_id , Profile profile_id) {
        super();
        this.last_chapter_read = last_chapter_read;
        this.last_chapter_started = last_chapter_started;
        this.last_page_read = last_page_read;
        this.manga_id = manga_id;
        this.profile_id = profile_id;
        this.last_read_at = last_read_at;
    }
}