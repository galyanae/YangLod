package com.goodthinking.younglod.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodthinking.younglod.user.model.Course;

/**
 * Created by Owner on 03/09/2016.
 */
public class CourseRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;

    public CourseRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item, parent, false);
        SimpleItemViewHolder pvh = new SimpleItemViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleItemViewHolder viewHolder = (SimpleItemViewHolder) holder;
        viewHolder.position = position;
        Course course = CourseArraydata.getInstance().getCourses().get(position);
        ((SimpleItemViewHolder) holder).ItemCourseHeadLine.setText(course.getCourseName());
        ((SimpleItemViewHolder) holder).ItemCourseSynopsys.setText(course.getCourseSynopsys());
        ((SimpleItemViewHolder) holder).ItemCourseDate.setText("ON: " + course.getCourseDate()+ " AT: "+course.getCourseTime());

    }

    @Override
    public int getItemCount() {
        return CourseArraydata.getInstance().getCourses().size();
    }

    public final class SimpleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ItemCourseHeadLine, ItemCourseSynopsys, ItemCourseDate;
        public int position;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
            ItemCourseHeadLine = (TextView) itemView.findViewById(R.id.ItemCourseHeadLine);
            ItemCourseSynopsys = (TextView) itemView.findViewById(R.id.ItemCourseSynopsys);
            ItemCourseDate = (TextView) itemView.findViewById(R.id.ItemCoursedate);

        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,CourseInformationActivity_Firebase.class);
            intent.putExtra("Coursekey",CourseArraydata.getInstance().getCourses().get(position).getKey());
            intent.putExtra("position",position );
            context.startActivity(intent);

        }
    }
}

