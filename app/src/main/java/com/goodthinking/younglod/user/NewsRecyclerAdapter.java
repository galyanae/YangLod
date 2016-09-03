package com.goodthinking.younglod.user;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodthinking.younglod.user.model.newsItem;

import java.util.TreeMap;

public class NewsRecyclerAdapter extends RecyclerView.Adapter {
    private TreeMap<String, newsItem> newsArray;
    private Context context;
    private final Handler handler = new Handler();
    AlertDialog show;
    int currentPosition;

    TextView Date;
    TextView Time;
    TextView tvHead;
    TextView tvYedia;
    TextView tvPages;
    ImageView tvImg;
    ImageView ivBackward;
    ImageView ivForward;
    Object caller;

    public NewsRecyclerAdapter(TreeMap<String, newsItem> newsArray, Object caller) {
        this.caller = caller;
        this.newsArray = newsArray;
    }

    public NewsRecyclerAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_yedia_item, parent, false);
        return new SimpleItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleItemViewHolder viewHolder = (SimpleItemViewHolder) holder;
        viewHolder.position = position;
        System.out.println("position=" + position);
        newsItem newsItem = new newsItem();

        // loop until getting the right event
        int pos = 0;
        for (TreeMap.Entry<String, newsItem> entry : newsArray.entrySet()) {
            if (pos == position) {
                newsItem = entry.getValue();
                break;
            }
            System.out.println(entry.getKey() + " - " + entry.getValue().toString());
            pos++;
        }
        if (pos == position) {
            ((SimpleItemViewHolder) holder).tvHeadline.setText(newsItem.getHeadline());
            String str = newsItem.getDate();
            String tm = newsItem.getTime();
            str = str.substring(6) + "/" + str.substring(4, 6) + "/" + str.substring(0, 4) + " " + tm.substring(0, 2) + ":" +
                    tm.substring(2) + " ";
            ((SimpleItemViewHolder) holder).tvYedia.setText(str + newsItem.getInfo());
/*
            if (newsItem.getImage() != null && newsItem.getImage().length() > 0) {
                ((SimpleItemViewHolder) holder).ivYedia.setVisibility(View.VISIBLE);
                ((SimpleItemViewHolder) holder).ivYedia.setImageBitmap(newsItem.getImg());
            } else ((SimpleItemViewHolder) holder).ivYedia.setVisibility(View.GONE);
*/

        }
    }

    @Override
    public int getItemCount() {
        return newsArray.size();
    }

    public final class SimpleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvHeadline, tvYedia;
        ImageView ivYedia;
        public int position;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            tvYedia = (TextView) itemView.findViewById(R.id.tvYedia);
            ivYedia = (ImageView) itemView.findViewById(R.id.ivYedia);

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            fullNews(v, pos);
        }
    }

    private class ToastRunnable implements Runnable {
        String mText;

        public ToastRunnable(String text) {
            mText = text;
        }

        public void run() {
            Toast.makeText(context.getApplicationContext(), mText, Toast.LENGTH_SHORT).show();
        }
    }

    public void doToast(String msg) {
        handler.post(new ToastRunnable(msg));
    }

    protected void fullNews(View v, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.full_news, null, false);
        currentPosition = position;
        Date = (TextView) view.findViewById(R.id.tvDate);
        Time = (TextView) view.findViewById(R.id.tvTime);
        tvHead = (TextView) view.findViewById(R.id.tvHead);
        tvYedia = (TextView) view.findViewById(R.id.tvYedia);
        tvImg = (ImageView) view.findViewById(R.id.ivYedia);
        tvPages = (TextView) view.findViewById(R.id.tvPages);
        ivBackward = (ImageView) view.findViewById(R.id.ivBackward);
        ivForward = (ImageView) view.findViewById(R.id.ivForward);

        newsItem newsItem = new newsItem();
        int pos = 0;
        for (TreeMap.Entry<String, newsItem> entry : newsArray.entrySet()) {
            if (pos == position) {
                newsItem = entry.getValue();
                break;
            }
            pos++;
        }
        if (pos == position) {
            String str = newsItem.getDate();
            Date.setText(str.substring(6) + "/" + str.substring(4, 6) + "/" + str.substring(0, 4) + " ");
            str = newsItem.getTime();
            Time.setText(str.substring(0, 2) + ":" + str.substring(2) + " ");
            tvHead.setText(newsItem.getHeadline());
            tvYedia.setText(newsItem.getInfo());
/*
            if (newsItem.getImage() != null && newsItem.getImage().length() > 0) {
                tvImg.setVisibility(View.VISIBLE);
                tvImg.setImageBitmap(newsItem.getImg());
            } else tvImg.setVisibility(View.GONE);
*/

            if (position == 0)
                ivBackward.setVisibility(View.INVISIBLE);
            else ivBackward.setVisibility(View.VISIBLE);

            if (position == (newsArray.size() - 1))
                ivForward.setVisibility(View.INVISIBLE);
            else ivForward.setVisibility(View.VISIBLE);

            tvPages.setText((position + 1) + "/" + newsArray.size());
        }
        builder.setView(view);
        ivBackward.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                pageNews(view);
            }
        });

        ivForward.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                pageNews(view);

            }
        });
        show = builder.show();
    }

    public void pageNews(View v) {
        int newPosition = currentPosition;
        if (v.getId() == R.id.ivBackward) {
            if (currentPosition == 0) return;
            newPosition--;
        } else    // probably forward
        {
            if (currentPosition == (newsArray.size() - 1)) {
                return;
            }
            newPosition++;
        }

        newsItem newsItem = new newsItem();
        int pos = 0;
        for (TreeMap.Entry<String, newsItem> entry : newsArray.entrySet()) {
            if (pos == newPosition) {
                newsItem = entry.getValue();
                break;
            }
            pos++;
        }
        if (pos == newPosition) {
            String str = newsItem.getDate();
            Date.setText(str.substring(6) + "/" + str.substring(4, 6) + "/" + str.substring(0, 4) + " ");
            str = newsItem.getTime();
            Time.setText(str.substring(0, 2) + ":" + str.substring(2) + " ");
            tvHead.setText(newsItem.getHeadline());
            tvYedia.setText(newsItem.getInfo());
/*
            if (newsItem.getImage() != null && newsItem.getImage().length() > 0) {
                tvImg.setVisibility(View.VISIBLE);
                tvImg.setImageBitmap(newsItem.getImg());
            } else tvImg.setVisibility(View.GONE);
*/

            if (newPosition == 0)
                ivBackward.setVisibility(View.INVISIBLE);
            else ivBackward.setVisibility(View.VISIBLE);

            if (newPosition == (newsArray.size() - 1))
                ivForward.setVisibility(View.INVISIBLE);
            else ivForward.setVisibility(View.VISIBLE);

            tvPages.setText((newPosition + 1) + "/" + newsArray.size());

            currentPosition = newPosition;
        }
        show.setView(v);
    }
}

