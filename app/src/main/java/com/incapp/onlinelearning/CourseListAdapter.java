package com.incapp.onlinelearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.MyViewHolder> {

    public interface CourseClickListener {
        void onCourseClicked(CourseModel courseModel);
    }

    private List<CourseModel> list;
    private CourseClickListener listener;
    private Context context;

    public CourseListAdapter(Context context, List<CourseModel> list, CourseClickListener listener) {
        this.list = list;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_for_course_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        CourseModel courseModel = list.get(i);

        Glide.with(context).load(courseModel.getImage()).into(myViewHolder.imageView);
        myViewHolder.textViewTitle.setText(courseModel.getTitle());

        myViewHolder.itemView.setTag(courseModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textViewTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textView_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            CourseModel courseModel = (CourseModel) itemView.getTag();

            listener.onCourseClicked(courseModel);
        }
    }
}
