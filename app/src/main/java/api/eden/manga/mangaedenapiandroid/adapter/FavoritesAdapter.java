package api.eden.manga.mangaedenapiandroid.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.model.FavoritesManga;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder>  {


    private FavoritesAdapterListener listener;
    private List<FavoritesManga> favoritesList;


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mangaName;
        private TextView mangaNum;
        private TextView mangaTitle;
        private TextView time;

        MyViewHolder(View view) {
            super(view);

            mangaName = view.findViewById(R.id.mangaName);
            mangaNum = view.findViewById(R.id.mangaNum);
            mangaTitle = view.findViewById(R.id.mangaTitle);
            time = view.findViewById(R.id.time);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected manga in callback
                    listener.onFavoritesSelected(favoritesList.get(getAdapterPosition()));
                }
            });
        }
    }

    public FavoritesAdapter(List<FavoritesManga> favoritesList, FavoritesAdapter.FavoritesAdapterListener listener) {
        this.listener = listener;
        this.favoritesList = favoritesList;
    }

    @NonNull
    @Override
    public FavoritesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoritesAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.MyViewHolder holder, final int position) {
        final FavoritesManga favoritesManga = favoritesList.get(position);
        DecimalFormat format = new DecimalFormat();
        format.setDecimalSeparatorAlwaysShown(false);


        holder.mangaName.setText(favoritesManga.getManga_alias());
        if (favoritesManga.getNext_chapter_title() != null) {
            holder.mangaTitle.setText((favoritesManga.getNext_chapter_title()));
        }
        if (favoritesManga.getNext_chapter_num() != null){
            holder.mangaNum.setText(format.format(favoritesManga.getNext_chapter_num()));
        }
        if (favoritesManga.getNext_chapter_time() != null){
            String result = getTimeEllasped(favoritesManga);
            holder.time.setText(result);
        }

    }


    private String getTimeEllasped(FavoritesManga favoritesManga){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long ts = timestamp.getTime();

        long millis = ts - favoritesManga.getNext_chapter_time() * 1000 ;

        if(millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }





        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long weeks = days / 7;
        StringBuilder sb = new StringBuilder(64);
        if (weeks > 4 ){
            sb.append("Long time ago");
        }else{
            days = days  % 7 ;
            long hours = TimeUnit.MILLISECONDS.toHours(millis);
            millis -= TimeUnit.HOURS.toMillis(hours);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);

            sb.append(weeks);
            sb.append(" weeks ");
            sb.append(days);
            sb.append(" Days ");
            sb.append(hours);
            sb.append(" Hours ");
            sb.append(minutes);
            sb.append(" Minutes ");

        }


        return(sb.toString());
    }

    public interface FavoritesAdapterListener {
        void onFavoritesSelected(FavoritesManga favoritesManga);
    }
}

