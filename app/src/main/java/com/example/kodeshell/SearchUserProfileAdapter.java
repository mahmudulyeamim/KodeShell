package com.example.kodeshell;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchUserProfileAdapter extends RecyclerView.Adapter<SearchUserProfileAdapter.ViewHolder> implements Filterable {

    private List<User> userList, filteredList;

    FragmentManager fragmentManager;

    public SearchUserProfileAdapter(List<User> userList, FragmentManager fragmentManager) {
        this.userList = userList;
        filteredList = new ArrayList<>();
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_profile_item, parent, false);
        return new  SearchUserProfileAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = filteredList.get(position);
        holder.bind(user);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference().child("user");

                DatabaseReference currentUserRef = reference.child(filteredList.get(position).getUserId());
                currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User currUser = dataSnapshot.getValue(User.class);
                            openUserProfileFragment(currUser, filteredList.get(position).getUserId());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView profilePic;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.search_user_profile_image);
            nameTextView = itemView.findViewById(R.id.search_user_name);
        }

        void bind(User user) {
            nameTextView.setText(user.getFirstName() + " " + user.getLastName());
            Picasso.get().load(loadImage(user.avatarid)).fit().centerInside().into(profilePic);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> newList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                newList.addAll(userList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (User item : userList) {
                    if ((item.getFirstName().toLowerCase().trim() + item.getLastName().toLowerCase().trim()).contains(filterPattern)) {
                        newList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = newList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            filteredList.clear();
            filteredList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    private int loadImage(int i){
        if(i == 0) {
            return R.drawable.avatar1;
        }
        else if(i == 1) {
            return R.drawable.avatar2;
        }
        else if(i == 2) {
            return R.drawable.avatar3;
        }
        else if(i == 3) {
            return R.drawable.avatar4;
        }
        else if(i == 4) {
            return R.drawable.avatar5;
        }
        else if(i == 5) {
            return R.drawable.avatar6;
        }
        else if(i == 6) {
            return R.drawable.avatar7;
        }
        else if(i == 7) {
            return R.drawable.avatar8;
        }
        else if(i == 8) {
            return R.drawable.avatar9;
        }
        else if(i == 9) {
            return R.drawable.avatar10;
        }
        else if(i == 10) {
            return R.drawable.avatar11;
        }
        return R.drawable.avatar12;
    }

    private void openUserProfileFragment(User user, String userId) {
        UserProfileFragment userProfileFragment = new UserProfileFragment();

        userProfileFragment.setUser(user);
        userProfileFragment.setCurrentUserId(userId);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment_container, userProfileFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
