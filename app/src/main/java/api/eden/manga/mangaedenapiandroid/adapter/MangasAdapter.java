package api.eden.manga.mangaedenapiandroid.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.fragments.SearchFragment;
import api.eden.manga.mangaedenapiandroid.model.FavoritesManga;
import api.eden.manga.mangaedenapiandroid.model.Manga;
import api.eden.manga.mangaedenapiandroid.model.Profile;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class MangasAdapter extends RecyclerView.Adapter<MangasAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private RecyclerView rView;
    private List<Manga> mangaList;
    private List<Manga> mangaListFiltered;
    private MangasAdapterListener listener;
    private Profile profile;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView thumbnail;
        public ToggleButton myfavoritesButton  ;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            thumbnail = view.findViewById(R.id.thumbnail);
            myfavoritesButton = view.findViewById(R.id.myfavoritesButton);

            myfavoritesButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Manga manga = mangaListFiltered.get(getAdapterPosition());
                    SearchFragment searchFragment  = new SearchFragment();
                    searchFragment.addFarovitesManga(isChecked ,manga );
                    if (isChecked) {
                        myfavoritesButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_full));
                    } else {
                        myfavoritesButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_empty));
                    }
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // send selected manga in callback
                    listener.onMangaSelected(mangaListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public MangasAdapter(Context context, List<Manga> contactList,  List<Manga> contactListFiltered,  MangasAdapterListener listener , Profile profile , RecyclerView rView) {
        this.context = context;
        this.rView = rView;
        this.listener = listener;
        this.mangaList = contactList;
        this.mangaListFiltered = contactListFiltered;
        this.profile = profile;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manga_row_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Manga manga = mangaListFiltered.get(position);
        holder.name.setText(manga.getT());

        Boolean isFavorite = false ;
        FavoritesManga favoriteManga = new FavoritesManga();
        if (favoriteManga.getIsFavoriteManga(manga, profile) != null ){
            favoriteManga = favoriteManga.getIsFavoriteManga(manga, profile);
            isFavorite = favoriteManga.getFavorite();
        }

        holder.myfavoritesButton.setChecked(isFavorite);
        if (isFavorite){
            holder.myfavoritesButton.setBackgroundDrawable(ContextCompat.getDrawable(context , R.drawable.ic_star_full));
        }else{
            holder.myfavoritesButton.setBackgroundDrawable(ContextCompat.getDrawable(context , R.drawable.ic_star_empty));
        }

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.error)
                .error(R.drawable.error);

        String image = "https://cdn.mangaeden.com/mangasimg/" + manga.getIm();
        if (manga.getIm() == null ){
            image = "https://cdn.mangaeden.com/images/no_image.svg";
        }

        Glide.with(context)
                .load(image)
                .apply(options)
                .into(holder.thumbnail);

        }

    @Override
    public int getItemCount() {
        return mangaListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mangaListFiltered = mangaList;
                } else {
                    List<Manga> filteredList = new ArrayList<>();
                    for (Manga row : mangaList) {

                        if (row.getT().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mangaListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mangaListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mangaListFiltered = (ArrayList<Manga>) filterResults.values;
                rView.getRecycledViewPool().clear();
                notifyDataSetChanged();

            }
        };
    }

    public List<Manga> getMangaListFiltered(){
        return mangaListFiltered;
    }

    public interface MangasAdapterListener {
        void onMangaSelected(Manga manga);
    }


}