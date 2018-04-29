package api.eden.manga.mangaedenapiandroid.adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.fragments.SearchFragment;
import api.eden.manga.mangaedenapiandroid.model.FavoritesManga;
import api.eden.manga.mangaedenapiandroid.model.Manga;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder>  {


    private FavoritesAdapter.FavoritesAdapterListener listener;
    private List<FavoritesManga> favoritesList;
    private Context context;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mangaName;
        public TextView lastChapter;
        public TextView time;

        public MyViewHolder(View view) {
            super(view);
            mangaName = (TextView) view.findViewById(R.id.mangaName);
            lastChapter = (TextView) view.findViewById(R.id.lastChapter);
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
        holder.mangaName.setText(favoritesManga.getManga_id());

    }

    public interface FavoritesAdapterListener {
        void onFavoritesSelected(FavoritesManga favoritesManga);
    }
}

