package api.eden.manga.mangaedenapiandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import api.eden.manga.mangaedenapiandroid.model.Manga;

public class Response
{
    @SerializedName("end")
    @Expose
    private int end;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("start")
    @Expose
    private int start;
    @SerializedName("manga")
    @Expose
    private ArrayList<Manga> manga;


    public Response(){super();}
    public int getEnd() { return this.end; }

    public void setEnd(int end) { this.end = end; }


    public ArrayList<Manga> getManga() { return this.manga; }

    public void setManga(ArrayList<Manga> manga) { this.manga = manga; }

    public int getPage() { return this.page; }

    public void setPage(int page) { this.page = page; }

    public int getStart() { return this.start; }

    public void setStart(int start) { this.start = start; }

    private int total;

    public int getTotal() { return this.total; }

    public void setTotal(int total) { this.total = total; }
}
