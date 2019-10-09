package com.incapp.onlinelearning.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.incapp.onlinelearning.CourseDetailActivity;
import com.incapp.onlinelearning.CourseListAdapter;
import com.incapp.onlinelearning.CourseModel;
import com.incapp.onlinelearning.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerView_courses);

        final List<CourseModel> courseList = new ArrayList<>();

        FirebaseFirestore.getInstance()
                .collection("courses")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            CourseModel courseModel = snapshot.toObject(CourseModel.class);

                            courseList.add(courseModel);
                        }

                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                        CourseListAdapter adapter = new CourseListAdapter(getActivity(), courseList, new CourseListAdapter.CourseClickListener() {
                            @Override
                            public void onCourseClicked(CourseModel courseModel) {
                                Intent intent = new Intent(getActivity(), CourseDetailActivity.class);

                                intent.putExtra("title", courseModel.getTitle());
                                intent.putExtra("image", courseModel.getImage());
                                intent.putExtra("detail", courseModel.getDetail());
                                intent.putExtra("video", courseModel.getVideo());

                                startActivity(intent);
                            }
                        });

                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        return root;
    }
}