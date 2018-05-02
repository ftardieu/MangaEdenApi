package api.eden.manga.mangaedenapiandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.model.FavoritesManga;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder>  {


    private FavoritesAdapterListener listener;
    private List<FavoritesManga> favoritesList;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mangaName;
        public TextView mangaNum;
        public TextView mangaTitle;
        public TextView time;

        public MyViewHolder(View view) {
            super(view);
            mangaName = (TextView) view.findViewById(R.id.mangaName);
            mangaNum = (TextView) view.findViewById(R.id.mangaNum);
            mangaTitle = (TextView) view.findViewById(R.id.mangaTitle);
            time = (TextView) view.findViewById(R.id.time);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected manga in callback
                    listener.onFavoritesSelected(favoritesList.get(getAdapterPosition()));
                }
            });
        }
    }

    public FavoritesAdapter(Context context, List<FavoritesManga> favoritesList, FavoritesAdapter.FavoritesAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.favoritesList = favoritesList;
    }

    @Override
    public FavoritesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_row_item, parent, false);
        return new FavoritesAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.MyViewHolder holder, final int position) {
        final FavoritesManga favoritesManga = favoritesList.get(position);
        DecimalFormat format = new DecimalFormat();
        format.setDecimalSeparatorAlwaysShown(false);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()/1000);
        Long ts = timestamp.getTime();

        Log.d("currentTime" , Long.toString(ts));
        Log.d("currentTIme" , Long.toString(favoritesManga.getNext_chapter_time()));
        holder.mangaName.setText(favoritesManga.getManga_alias());
        holder.mangaTitle.setText((favoritesManga.getNext_chapter_title()));
        holder.mangaNum.setText(format.format(favoritesManga.getNext_chapter_num()));

        long timeEllasped = ts - favoritesManga.getNext_chapter_time() ;
        Log.d("timeEllapsed" , Long.toString(timeEllasped));
        long getMinuts = timeEllasped / 60 ;
        long getHours = 0 ;
        long getDays = 0 ;
        long getMonths = 0;
        long getYears = 0;
        long getRestMonths = 0 ;
        long getRestDays = 0 ;
        long getRestHours = 0 ;
        long getRestMinuts = 0 ;
        String result = "" ;

        if (getMinuts > 1 ){
            getHours = getMinuts / 60 ;
            if (getHours > 1 ) {
                getDays = getHours / 24 ;
                if (getDays > 1 ) {
                    getMonths = getDays / 30 ;
                    if (getMonths > 1) {
                        getYears =  getMonths / 12 ;
                    }
                }
            }
        }


        if (getYears != 0){
            getRestMonths = getDays - getYears * 365 ;
            result += Long.toString(getYears) + " year(s) ";
        }

        if (getMonths != 0){
            getRestDays = getDays - getYears * 365 ;
            getRestMonths = getRestDays / 30;
            result += Long.toString(getRestMonths) + " month(s) ";
        }

        if (getDays != 0){
            getRestDays = getDays - getYears * 365 - getRestMonths * 30;
            result += Long.toString(getRestDays) + " day(s) ";
        }

        Long time = timeEllasped - (getYears * 365*24*3600) - (getRestMonths * 30*24*3600) - ( getRestDays * 24*3600) ;
        if (getHours != 0){
            getRestHours = time / 3600  ;
            result += Long.toString(getRestHours) + " hour(s) ";
        }

        if (getMinuts != 0){
            getRestMinuts = (time / 3600  - getRestHours) * 60 ;
            result += Long.toString(getRestMinuts) + " m ";
        }

        Long getRest = (time / 3600  - getRestDays * 24 - getRestHours ) * 60 - getRestMinuts ;
        //result += Long.toString(getRest) + " s" ;

        Log.d("result" , result);
        holder.time.setText(result);

        //Todo faire le holder time # différence entre currentTime et time du champ

    }


    public interface FavoritesAdapterListener {
        void onFavoritesSelected(FavoritesManga favoritesManga);
    }
}

