package com.example.kodeshell;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {

    RecyclerView commentRecyclerView;
    CommentAdapter commentAdapter;

    ImageView postComment;

    EditText comment;

    private List<CommentDetails> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        postComment = view.findViewById(R.id.post_comment_button);
        comment = view.findViewById(R.id.comment_text);

        commentRecyclerView = view.findViewById(R.id.commentRecyclerView);

        commentAdapter = new CommentAdapter(list, getContext());

        commentRecyclerView.setAdapter(commentAdapter);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postComment.setOnClickListener(view1 -> postNewComment());

        return view;
    }

    private void postNewComment() {
        String text = comment.getText().toString().trim();

        if(text.isEmpty()) {
            Toast.makeText(getContext(), "Type a comment to post", Toast.LENGTH_SHORT).show();
        }
        else {
            CommentDetails obj = new CommentDetails();
            obj.setAvatar(R.drawable.avatar6);
            obj.setUsername("Sophia Charlotte");
            obj.setTime("Just now");
            obj.setComment(text);
            list.add(obj);

            commentAdapter.notifyItemInserted(list.size() - 1);
            commentRecyclerView.scrollToPosition(list.size() - 1);

            comment.setText("");
        }
    }

    public List<CommentDetails> getList() {
        return list;
    }

    public void setList(List<CommentDetails> list) {
        this.list = list;
    }
}