package com.goodthinking.younglod.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodthinking.younglod.user.model.Course;

public class MyAdapter extends RecyclerView.Adapter {
    private Context context;

    public MyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_gread_view_adapter, parent, false);
        return new SimpleItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleItemViewHolder viewHolder = (SimpleItemViewHolder) holder;
        viewHolder.position = position;
        Course course = CourseArrayData.getInstance().getCourses().get(position);
        ((SimpleItemViewHolder) holder).ItemCourseHeadLine.setText(course.getCourseName());
        ((SimpleItemViewHolder) holder).ItemCourseSynopsys.setText(course.getCourseSynopsys());
        ((SimpleItemViewHolder) holder).ItemCourseStartDate.setText(context.getString(R.string.fromdate) + course.getCourseStartdate()+ " AT: "+course.getCoursetime());

        ((SimpleItemViewHolder) holder).ItemCoursesEndDate.setText(context.getString(R.string.todate) 
                + course.getCourseEndDate());
    }

    @Override
    public int getItemCount() {
        return CourseArrayData.getInstance().getCourses().size();
    }

    public final class SimpleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ItemCourseHeadLine, ItemCourseSynopsys, ItemCourseStartDate, ItemCoursesEndDate;
        public int position;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
            ItemCourseHeadLine = (TextView) itemView.findViewById(R.id.ItemCourseHeadLine);
            ItemCourseSynopsys = (TextView) itemView.findViewById(R.id.ItemCourseSynopsys);
            ItemCourseStartDate = (TextView) itemView.findViewById(R.id.ItemCourseStartDate);
            ItemCoursesEndDate = (TextView) itemView.findViewById(R.id.ItemCourseEndDate);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,OneCourseMainActivity.class);
            System.out.println("position=" + position);
            System.out.println(CourseArrayData.getInstance().getCourses().get(position).getKey());
            //
            intent.putExtra("oneCourse", CourseArrayData.getInstance().getCourses().get(position)); // using the (String name, Parcelable value) overload!

            context.startActivity(intent);

        }
    }
}