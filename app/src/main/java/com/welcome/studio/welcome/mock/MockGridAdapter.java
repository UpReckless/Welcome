package com.welcome.studio.welcome.mock;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;

import java.io.File;

/**
 * Created by Royal on 21.11.2016.
 */

public class MockGridAdapter extends BaseAdapter {

    private String[] images=new String[]{};
    private Context context;
    private File dir;

    public MockGridAdapter(Context context){
        dir=new File(Environment.getExternalStorageDirectory()+"/Pictures/Instagram");
        images=dir.list();
        this.context=context;
    }

    @Override
    public int getCount() {
        if (images!=null) {
            return images.length;
        }return 0;
    }

    @Override
    public Object getItem(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null){
            view=LayoutInflater.from(context).inflate(R.layout.grid_img_item,viewGroup,false);
            viewHolder=new ViewHolder();
            viewHolder.gridImgView=(ImageView)view.findViewById(R.id.grid_img_view);
            view.setTag(viewHolder);
        }else viewHolder=(ViewHolder)view.getTag();
        Picasso.with(context).load(new File(dir.getPath())).into(viewHolder.gridImgView);
        return view;
    }
    private static class ViewHolder{
        ImageView gridImgView;
    }
}
