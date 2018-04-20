package api.eden.manga.mangaedenapiandroid.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Profile")
public class Profile extends Model {
    // If name is omitted, then the field name is used.

    @Column(name = "pseudo")
    public String pseudo;


    public List<FavoritesManga> FavoritesManga() {
        return getMany(FavoritesManga.class, "FavoritesManga");
    }

    public Profile() {
        super();
    }

    public Profile( String pseudo) {
        super();
        this.pseudo = pseudo;
    }

    public static Profile getProfile() {
        return new Select()
                .from(Profile.class)
                .executeSingle();
    }
}