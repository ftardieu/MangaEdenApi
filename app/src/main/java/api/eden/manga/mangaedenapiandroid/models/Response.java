package api.eden.manga.mangaedenapiandroid.models;

import java.util.ArrayList;

public class Response
{
    private int end;
    private int page;
    private int start;
    private ArrayList<Manga> manga;



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
