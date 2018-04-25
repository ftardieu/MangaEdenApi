package api.eden.manga.mangaedenapiandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.model.Manga;

import com.bumptech.glide.Glide;


public class MangasAdapter extends RecyclerView.Adapter<MangasAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Manga> mangaList;
    private List<Manga> mangaListFiltered;
    private MangasAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onMangaSelected(mangaListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public MangasAdapter(Context context, List<Manga> contactList, MangasAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.mangaList = contactList;
        this.mangaListFiltered = contactList;
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
        Glide.with(context)
                .load("https://cdn.mangaeden.com/mangasimg/" + manga.getIm())
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

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
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

                notifyDataSetChanged();

            }
        };
    }

    public interface MangasAdapterListener {
        void onMangaSelected(Manga manga);
    }
}