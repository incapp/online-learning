package com.incapp.onlinelearning.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.incapp.onlinelearning.R;

public class ProfileFragment extends Fragment {

    ImageView imageView;
    TextView textViewName;
    TextView textViewEmail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        imageView = (ImageView) root.findViewById(R.id.imageView);
        textViewName = (TextView) root.findViewById(R.id.textView_name);
        textViewEmail = (TextView) root.findViewById(R.id.textView_email);

        String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Uri uri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();

        textViewEmail.setText(email);
        textViewName.setText(name);

        Glide.with(getActivity()).load(uri).into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("image/png");

                startActivityForResult(intent, 0);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0 && resultCode == -1) {
            Uri imageUri = data.getData();

            imageView.setImageURI(imageUri);

            final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

            //Upload to FirebaseStorage.
            FirebaseStorage.getInstance()
                    .getReference("userpics")
                    .child(email + ".png")
                    .putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            FirebaseStorage.getInstance()
                                    .getReference("userpics")
                                    .child(email + ".png")
                                    .getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                                    .setPhotoUri(uri)
                                                    .build();

                                            FirebaseAuth.getInstance().getCurrentUser()
                                                    .updateProfile(request);


                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}