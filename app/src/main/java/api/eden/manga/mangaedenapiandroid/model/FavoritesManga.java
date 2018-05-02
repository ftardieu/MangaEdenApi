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
    public Long last_read_at;


    @Column(name = "last_chapter_read")
    public Double last_chapter_read;

    @Column(name = "last_chapter_started")
    public String last_chapter_started;


    @Column(name = "last_page_read")
    public Integer last_page_read;

    @Column(name = "manga_id")
    public String manga_id;

    @Column(name = "profile_pseudo")
    public String profile_pseudo;

    @Column(name = "is_favorite")
    public Boolean is_favorite;


    @Column(name = "manga_alias")
    public String manga_alias;

    @Column(name = "next_chapter_num")
    public Double next_chapter_num = 0.0 ;

    public Double getNext_chapter_num() {
        return next_chapter_num;
    }

    public void setNext_chapter_num(Double next_chapter_num) {
        this.next_chapter_num = next_chapter_num;
    }

    @Column(name = "next_chapter_id")
    public String next_chapter_id;

    @Column(name = "next_chapter_title")
    public String next_chapter_title;

    @Column(name = "next_chapter_time")
    public Long next_chapter_time;

    public Boolean getIs_favorite() {
        return is_favorite;
    }



    public String getManga_alias() {
        return manga_alias;
    }

    public void setManga_alias(String manga_alias) {
        this.manga_alias = manga_alias;
    }

    public String getNext_chapter_id() {
        return next_chapter_id;
    }

    public void setNext_chapter_id(String next_chapter_id) {
        this.next_chapter_id = next_chapter_id;
    }

    public String getNext_chapter_title() {
        return next_chapter_title;
    }

    public void setNext_chapter_title(String next_chapter_title) {
        this.next_chapter_title = next_chapter_title;
    }

    public Long getNext_chapter_time() {
        return next_chapter_time;
    }

    public void setNext_chapter_time(Long next_chapter_time) {
        this.next_chapter_time = next_chapter_time;
    }

    public Long getLast_read_at() {
        return last_read_at;
    }

    public void setLast_read_at(Long last_read_at) {
        this.last_read_at = last_read_at;
    }

    public Double getLast_chapter_read() {
        return last_chapter_read;
    }

    public void setLast_chapter_read(Double last_chapter_read) {
        this.last_chapter_read = last_chapter_read;
    }

    public String getLast_chapter_started() {
        return last_chapter_started;
    }

    public void setLast_chapter_started(String last_chapter_started) {
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

    public FavoritesManga( Long last_read_at , Double last_chapter_read , String last_chapter_started , Integer last_page_read , String manga_id , String profile_pseudo , Boolean is_favorite) {
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