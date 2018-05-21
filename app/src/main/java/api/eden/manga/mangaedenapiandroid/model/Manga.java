package api.eden.manga.mangaedenapiandroid.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;

import java.util.List;


@Table(name = "Manga")
public class Manga extends Model
{
    @Expose
    @Column(name = "a")
    private String a;

    public String getA() { return this.a; }

    public void setA(String a) { this.a = a; }


    private List<String> c;

    public List<String> getC() { return this.c; }

    public void setC(List<String> c) { this.c = c; }

    @Expose
    @Column(name = "h")
    private int h;

    public int getH() { return this.h; }

    public void setH(int h) { this.h = h; }

    @Expose
    @Column(name = "i" , unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String i;

    public String getI() { return this.i; }

    public void setI(String i) { this.i = i; }

    @Expose
    @Column(name = "im")
    private String im;

    public String getIm() { return this.im; }

    public void setIm(String im) { this.im = im; }

    @Expose
    @Column(name = "ld")
    private double ld;

    public double getLd() { return this.ld; }

    public void setLd(double ld) { this.ld = ld; }

    @Expose
    @Column(name = "s")
    private int s;

    public int getS() { return this.s; }

    public void setS(int s) { this.s = s; }

    @Expose
    @Column(name = "t")
    private String t;

    public String getT() { return this.t; }

    public void setT(String t) { this.t = t; }

    public Manga(){
        super();
    }

    public Manga (String title ,  Integer status , double ld , String im , String i , Integer h , String a){
        super ();
        this.t = title;
        this.s = status;
        this.ld = ld;
        this.im = im;
        this.i = i ;
        this.h = h;
        this.a = a;
    }

    public List<Manga> getMangas() {
        return new Select()
                .from(Manga.class)
                .orderBy("h DESC")
                .execute();
    }

    public Manga getMangaById(String id) {
        return new Select()
                .from(Manga.class)
                .where("I = ?", id)
                .executeSingle();
    }

    public Manga updateManga(Manga newValuedManga) {
        String updateSet =
                "i = ? , " +
                "t = ? , " +
                "s = ? , " +
                "a = ? , " +
                "ld = ? ," +
                "im = ? ," +
                "h = ? ,";
        new Update(Manga.class)
                .set(updateSet, newValuedManga.getI(), newValuedManga.getT(), newValuedManga.getS(), newValuedManga.getA(), newValuedManga.getLd(), newValuedManga.getIm(), newValuedManga.getH())
                .where("i = ? ", newValuedManga.getI())
                .execute();
        return this;
    }
}
