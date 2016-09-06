package com.goodthinking.younglod.user;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodthinking.younglod.user.model.newsItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.TreeMap;

public class NewsRecyclerAdapter extends RecyclerView.Adapter {
    private static final String IMAGES_BUCKET = "gs://hadashot-9bbf1.appspot.com";
    private TreeMap<String, newsItem> newsArray;
    private Context context;
    private final Handler handler = new Handler();
    AlertDialog show;
    int currentPosition;
    boolean isManager;
    TextView Date;
    TextView Time;
    TextView tvHead;
    TextView tvYedia;
    TextView tvPages;
    ImageView tvImg;
    ImageView ivBackward;
    ImageView ivForward;
    newsItem newsItem;

    private DatabaseReference root;
    DatabaseReference newsRef;

    public interface RefreshMeCallback {
        void refreshMe();
    }

    private RefreshMeCallback callerActivity;

    public NewsRecyclerAdapter(TreeMap<String, newsItem> newsArray, Activity caller, boolean isManager) {
        this.callerActivity = (RefreshMeCallback) caller;
        this.newsArray = newsArray;
        this.isManager = isManager;
        root = FirebaseDatabase.getInstance().getReference();
        newsRef = root.child("Tables").child("news");
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
        newsItem = new newsItem();

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
            if (isManager) {
                ((SimpleItemViewHolder) holder).ivDelete.setVisibility(View.VISIBLE);

            } else ((SimpleItemViewHolder) holder).ivDelete.setVisibility(View.GONE);

        }
    }

    public void deleteNews(View v, int position) {
        System.out.println("position=" + position);
        newsItem = new newsItem();

        // loop until getting the right news
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

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            // set title
            alertDialogBuilder.setTitle("Delete are you sure?");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click yes to exit!")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity
                            System.out.println("delete confirmed" + newsItem.toString());
                    newsRef.child(newsItem.getKey()).removeValue(); // remove from DB
                    newsArray.remove(newsItem.getDate()+newsItem.getTime()+newsItem.getKey());  //remove from array
                            ((NewsActivity) context).refreshMe();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            System.out.println("delete canceled");
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    }

    @Override
    public int getItemCount() {
        return newsArray.size();
    }

    public final class SimpleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvHeadline, tvYedia;
        ImageView ivYedia, ivDelete;
        public int position;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            tvYedia = (TextView) itemView.findViewById(R.id.tvYedia);
            ivYedia = (ImageView) itemView.findViewById(R.id.ivYedia);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
            ivDelete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int field = v.getId();
            int pos = getAdapterPosition();

            if (field == R.id.ivDelete) {
                System.out.println("ivDelete called");
                deleteNews(v, pos);
            } else {
                System.out.println("holder called");
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
            System.out.println("iName="+newsItem.getiName());

            if (newsItem.getiName() != null && newsItem.getiName().length()>0 && !newsItem.getiName().equals("nada"))
            {
                System.out.println("go display my image");
                //there is picture go get it
                tvImg.setVisibility(View.VISIBLE);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                // https://firebase.google.com/docs/storage/android/create-reference
                // Create a storage reference from our app
                StorageReference storageRef = storage.getReference();

                // Create a reference to "mountains.jpg"
/*
 */
                storageRef.child(newsItem.getiName()).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bmpNew;
                        ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
                        bmpNew = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                        tvImg.setImageBitmap(bmpNew);
                        // Use the bytes to display the image
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });

                // tvImg.setImageBitmap();
            } else tvImg.setVisibility(View.GONE);

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


                if (newPosition == 0)
                    ivBackward.setVisibility(View.INVISIBLE);
                else ivBackward.setVisibility(View.VISIBLE);

                if (newPosition == (newsArray.size() - 1))
                    ivForward.setVisibility(View.INVISIBLE);
                else ivForward.setVisibility(View.VISIBLE);

                tvPages.setText((newPosition + 1) + "/" + newsArray.size());

                currentPosition = newPosition;
            }
            System.out.println("iName="+newsItem.getiName());

            if (newsItem.getiName() != null && newsItem.getiName().length()>0 && !newsItem.getiName().equals("nada"))
            {
                System.out.println("go display my image");
                //there is picture go get it
                tvImg.setVisibility(View.VISIBLE);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                // https://firebase.google.com/docs/storage/android/create-reference
                // Create a storage reference from our app
                StorageReference storageRef = storage.getReference();

                // Create a reference to "mountains.jpg"
/*
 */
                storageRef.child(newsItem.getiName()).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bmpNew;
                        ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
                        bmpNew = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                        tvImg.setImageBitmap(bmpNew);
                        // Use the bytes to display the image
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });

                // tvImg.setImageBitmap();
            } else tvImg.setVisibility(View.GONE);
            show.setView(v);
        }
    }

}