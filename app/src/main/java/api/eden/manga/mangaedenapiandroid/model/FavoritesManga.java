package api.eden.manga.mangaedenapiandroid.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;


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

    @Column(name = "profile_pseudo")
    public String profile_pseudo;

    @Column(name = "is_favorite")
    public Boolean is_favorite;

    public Date getLast_read_at() {
        return last_read_at;
    }

    public void setLast_read_at(Date last_read_at) {
        this.last_read_at = last_read_at;
    }

    public Integer getLast_chapter_read() {
        return last_chapter_read;
    }

    public void setLast_chapter_read(Integer last_chapter_read) {
        this.last_chapter_read = last_chapter_read;
    }

    public Integer getLast_chapter_started() {
        return last_chapter_started;
    }

    public void setLast_chapter_started(Integer last_chapter_started) {
        this.last_chapter_started = last_chapter_started;
    }

    public Integer getLast_page_read() {
        return last_page_read;
    }

    public void setLast_page_read(Integer last_page_read) {
        this.last_page_read = last_page_read;
    }

    public String getManga_id() {
        return manga_id;
    }

    public void setManga_id(String manga_id) {
        this.manga_id = manga_id;
    }

    public String getProfile_pseudo() {
        return profile_pseudo;
    }

    public void setProfile_pseudo(String profile_pseudo) {
        this.profile_pseudo = profile_pseudo;
    }

    public FavoritesManga() {
        super();
    }

    public FavoritesManga( Date last_read_at , Integer last_chapter_read , Integer last_chapter_started , Integer last_page_read , String manga_id , String profile_pseudo , Boolean is_favorite) {
        super();
        this.last_chapter_read = last_chapter_read;
        this.last_chapter_started = last_chapter_started;
        this.last_page_read = last_page_read;
        this.manga_id = manga_id;
        this.profile_pseudo = profile_pseudo;
        this.last_read_at = last_read_at;
        this.is_favorite = is_favorite;
    }


    public Boolean getFavorite() {
        return is_favorite;
    }

    public void setFavorite(Boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

    public static FavoritesManga getFavoriteManga(Manga manga , Profile profile) {
        return new Select()
                .from(FavoritesManga.class)
                .where("manga_id = ?", manga.getI())
                .and("profile_pseudo = ?" , profile.getPseudo())
                .executeSingle();
    }

    public List<FavoritesManga> getFavoritesMangas(Profile profile) {
        return new Select()
                .from(FavoritesManga.class)
                .where("profile_pseudo = ?" , profile.getPseudo())
                .and("is_favorite = ?" , 1)
                .execute();
    }

    public static FavoritesManga getIsFavoriteManga(Manga manga , Profile profile){
        return new Select()
                .from(FavoritesManga.class)
                .where("manga_id = ?", manga.getI())
                .and("profile_pseudo = ?" , profile.getPseudo())
                .executeSingle();
    }
}