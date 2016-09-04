package com.goodthinking.younglod.user;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goodthinking.younglod.user.model.MenuIcon;

import java.util.ArrayList;

/**
 * Created by Owner on 23/07/2016.
 */
public class AdapterOne  extends BaseAdapter {

    public ArrayList<MenuIcon> menuIcons;
    public ArrayList<MenuIcon> getItems() { return menuIcons;    }
    private Context context;

    public AdapterOne (Context context){

        this.context = context;
        menuIcons=new ArrayList<>();
        menuIcons.add(new MenuIcon(1, R.drawable.newstext, "news"));
        menuIcons.add(new MenuIcon(2,R.drawable.eventstext,"events"));
        menuIcons.add(new MenuIcon(3, R.drawable.coursestext,"courses"));
        menuIcons.add(new MenuIcon(4, R.drawable.career,"career"));
        menuIcons.add(new MenuIcon(5, R.drawable.hayalim,"soldiers"));
        menuIcons.add(new MenuIcon(6, R.drawable.itnadvit,"itnadvut"));
        menuIcons.add(new MenuIcon(7, R.drawable.business,"business"));
        menuIcons.add(new MenuIcon(8, R.drawable.parents,"parents"));
        menuIcons.add(new MenuIcon(9, R.drawable.milgottext,"scholarship"));
        menuIcons.add(new MenuIcon(10,R.drawable.contactustext,"contacts"));

    }

    @Override
    public int getCount() {
        return menuIcons.size();
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
           imageView.setBackgroundResource(menuIcons.get(position).getImage());
            return linearLayout;

    }}
