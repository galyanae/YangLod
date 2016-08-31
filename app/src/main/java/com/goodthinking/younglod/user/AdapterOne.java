package com.goodthinking.younglod.user;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goodthinking.younglod.user.model.Icon;

import java.util.ArrayList;

/**
 * Created by Owner on 23/07/2016.
 */
public class AdapterOne  extends BaseAdapter {

    public ArrayList<Icon> icons;
    public ArrayList<Icon> getItems() { return icons;    }
    private Context context;

    public AdapterOne (Context context){

        this.context = context;
        icons=new ArrayList<>();
        icons.add(new Icon(1, R.drawable.newstext,"news"));
        icons.add(new Icon(2,R.drawable.eventstext,"events"));
        icons.add(new Icon(3, R.drawable.coursestext,"courses"));
        icons.add(new Icon(4, R.drawable.career,"career"));
        icons.add(new Icon(5, R.drawable.hayalim,"soldiers"));
        icons.add(new Icon(6, R.drawable.itnadvit,"itnadvut"));
        icons.add(new Icon(7, R.drawable.business,"business"));
        icons.add(new Icon(8, R.drawable.parents,"parents"));
        icons.add(new Icon(9, R.drawable.milgottext,"scholarship"));
        icons.add(new Icon(10,R.drawable.contactustext,"contacts"));

    }

    @Override
    public int getCount() {
        return icons.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout linearLayout = (LinearLayout) View.inflate(context, R.layout.gread1, null);
            ImageView imageView = (ImageView) linearLayout.findViewById(R.id.iconImage);
           imageView.setBackgroundResource(icons.get(position).getImage());
            return linearLayout;

    }}
