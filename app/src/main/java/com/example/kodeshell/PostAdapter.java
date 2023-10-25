package com.example.kodeshell;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {

    ArrayList<PostDetails> list;
    Context context;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FragmentManager fragmentManager;

    public PostAdapter(ArrayList<PostDetails> list, Context context, FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }
    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post_card, parent, false);
        return new PostHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Picasso.get().load(list.get(position).getAvatar()).fit().centerInside().into(holder.profilePic);

        Picasso.get().load(list.get(position).getUpVoteIcon()).into(holder.upVoteIcon);
        Picasso.get().load(list.get(position).getDownVoteIcon()).into(holder.downVoteIcon);

        holder.username.setText(list.get(position).getUsername());
        holder.time.setText(list.get(position).getTime());
        holder.post.setText(list.get(position).getPost());

        int upVoteCount = list.get(position).getUpVote();
        int downVoteCount = list.get(position).getDownVote();
        holder.upvoteCount.setText("+" + Integer.toString(upVoteCount));
        holder.downvoteCount.setText("-" + Integer.toString(downVoteCount));
        database = FirebaseDatabase.getInstance();
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = database.getReference().child("post").child(list.get(position).getId()).child("upvoters");
        final boolean[] upvoted = {false};
        final boolean[] downvoted = {false};// default value if voters map or currentUser is not found
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Boolean Voted = dataSnapshot.child(currentUser).getValue(Boolean.class);
                    if (Voted != null) {
                        if (Voted)
                            upvoted[0] = true;
                        else upvoted[0] = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Failed to read vote status", databaseError.toException());
            }
        });
        reference = database.getReference().child("post").child(list.get(position).getId()).child("downvoters");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Boolean Voted = dataSnapshot.child(currentUser).getValue(Boolean.class);
                    if (Voted != null) {
                        if (Voted)
                            downvoted[0] = true;
                        else downvoted[0] = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Failed to read vote status", databaseError.toException());
            }
        });

        holder.upVoteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(upvoted[0]){
                    int curr = list.get(position).getUpVote();
                    DatabaseReference reference = database.getReference().child("post").child(list.get(position).getId()).child("upvoters").child(currentUser);
                    reference.setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                upvoted[0] = false;
                                DatabaseReference upVoteReference = database.getReference().child("post").child(list.get(position).getId()).child("upVote");
                                upVoteReference.setValue(curr - 1);
                                notifyItemChanged(position);
                            } else {
                                Toast.makeText(view.getContext(), "Error in updating vote status", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    return;
                }
                if(downvoted[0]){
                    return;
                }
                int curr = list.get(position).getUpVote();
                DatabaseReference reference = database.getReference().child("post").child(list.get(position).getId()).child("upvoters").child(currentUser);
                reference.setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Successfully updated the vote status in the database
                            upvoted[0] = true;
                            DatabaseReference upVoteReference = database.getReference().child("post").child(list.get(position).getId()).child("upVote");
                            upVoteReference.setValue(curr + 1);

                            // Notify the adapter that the item at the given position has changed
                            notifyItemChanged(position);
                        } else {
                            Toast.makeText(view.getContext(), "Error in updating vote status", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        holder.downVoteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(downvoted[0]){
                    int curr = list.get(position).getDownVote();
                    DatabaseReference reference = database.getReference().child("post").child(list.get(position).getId()).child("downvoters").child(currentUser);
                    reference.setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                downvoted[0] = false;
                                DatabaseReference downVoteReference = database.getReference().child("post").child(list.get(position).getId()).child("downVote");
                                downVoteReference.setValue(curr - 1);
                                notifyItemChanged(position);
                            } else {
                                Toast.makeText(view.getContext(), "Error in updating vote status", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    return;
                }
                if(upvoted[0]){
                    return;
                }
                int curr = list.get(position).getDownVote();
                DatabaseReference reference = database.getReference().child("post").child(list.get(position).getId()).child("downvoters").child(currentUser);
                reference.setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Successfully updated the vote status in the database
                            downvoted[0] = true;
                            DatabaseReference downVoteReference = database.getReference().child("post").child(list.get(position).getId()).child("downVote");
                            downVoteReference.setValue(curr + 1);

                            // Notify the adapter that the item at the given position has changed
                            notifyItemChanged(position);
                        } else {
                            Toast.makeText(view.getContext(), "Error in updating vote status", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        if(list.get(position).getComments() == null) {
            holder.commentCount.setText("0");
        }
        else {
            holder.commentCount.setText(Integer.toString(list.get(position).getComments().size()));
        }

        holder.commentCount.setOnClickListener(view -> openCommentFragment(list.get(position).getId(), list.get(position).getComments()));
        holder.commentIcon.setOnClickListener(view -> openCommentFragment(list.get(position).getId(), list.get(position).getComments()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void openCommentFragment(String id, List<CommentDetails> list) {
        CommentFragment commentFragment = new CommentFragment();

        commentFragment.setList(list);
        commentFragment.setPostID(id);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment_container, commentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
