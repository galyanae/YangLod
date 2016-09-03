package com.goodthinking.younglod.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodthinking.younglod.user.model.User;

import java.util.ArrayList;

/**
 * Created by user on 30/08/2016.
 */
public class Applicant_List_Adapter extends RecyclerView.Adapter{
    private Context context;
    private ArrayList<User> applicantList;

    public Applicant_List_Adapter(Context context) {
        this.context = context;
        applicantList=new ArrayList<>();
    }
    public ArrayList<User> getApplicantList() {
        return applicantList;
    }


    public Applicant_List_Adapter(ArrayList<User> applicantList) {
        this.applicantList = applicantList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicant_list_item, parent, false);
        SimpleItemViewHolder pvh = new SimpleItemViewHolder(v);
        return pvh;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleItemViewHolder viewHolder = (SimpleItemViewHolder) holder;
        viewHolder.position = position;
        User user = applicantList.get(position);
        ((SimpleItemViewHolder) holder).applicantName.setText(context.getString(R.string.applicant_name)+" "+user.getUserName());
        ((SimpleItemViewHolder) holder).applicantPhone.setText(context.getString(R.string.applicant_phone)+" "+user.getUserPhone());
        ((SimpleItemViewHolder) holder).applicantMail.setText(context.getString(R.string.applicant_mail)+" " +user.getUserEmail());
        ((SimpleItemViewHolder) holder).applicantparticipatorsnum.setText(context.getString(R.string.applicat_participtors_num)+" "+
                String.valueOf(user.getUserNoOfParticipators()));


    }

    @Override
    public int getItemCount() {
        return applicantList.size();
    }

    private class SimpleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView applicantName, applicantPhone, applicantMail,applicantparticipatorsnum;
        public int position;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
            applicantName = (TextView) itemView.findViewById(R.id.ItemAppicantName);
            applicantPhone = (TextView) itemView.findViewById(R.id.ItemAppicantPhone);
            applicantMail = (TextView) itemView.findViewById(R.id.ItemAppicantEmail);
            applicantparticipatorsnum = (TextView) itemView.findViewById(R.id.ItemAppicantnoofparticipators);


        }
        @Override
        public void onClick(View v) {

        }
    }
}
