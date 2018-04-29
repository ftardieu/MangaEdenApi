package api.eden.manga.mangaedenapiandroid.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import api.eden.manga.mangaedenapiandroid.MainActivity;
import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.model.Manga;

public class MangaFragment extends Fragment {

    private TextView test;

    public MangaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manga, container, false);
        test = (TextView) view.findViewById(R.id.textView2);
        getActivity().setTitle(getArguments().getString("manga_title")) ;
        test.setText(getArguments().getString("manga_id"));

        return view;
    }



}