package api.eden.manga.mangaedenapiandroid.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import api.eden.manga.mangaedenapiandroid.customClass.TouchImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import api.eden.manga.mangaedenapiandroid.R;

public class PagerAdapter extends android.support.v4.view.PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    ArrayList<String> arrayList;




    public PagerAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        if(arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.view_pager_item, container, false);

        TouchImageView img = (TouchImageView) itemView.findViewById(R.id.viewPagerItem_image1);
        String image = arrayList.get(position);
        String url = "https://cdn.mangaeden.com/mangasimg/" + image;
        Glide.with(context).load(url).into(img);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
