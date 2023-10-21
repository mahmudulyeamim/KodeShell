package com.example.kodeshell;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class NewPostFragment extends Fragment {

    ImageView profilePic;
    TextView username;

    EditText postContent;

    Button postButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_post, container, false);

        profilePic = view.findViewById(R.id.new_post_user_img);
        username = view.findViewById(R.id.new_post_username);
        postContent = view.findViewById(R.id.new_post_content);
        postButton = view.findViewById(R.id.upload_new_post_button);

        Picasso.get().load(R.drawable.avatar10).fit().centerInside().into(profilePic);
        username.setText("Istahak Islam");

        postButton.setOnClickListener(view1 -> createNewPost());

        return view;
    }

    private void createNewPost() {
        String post = postContent.getText().toString().trim();
        if(!post.isEmpty()) {
            Toast.makeText(getContext(), "Post uploaded successfully", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }
}