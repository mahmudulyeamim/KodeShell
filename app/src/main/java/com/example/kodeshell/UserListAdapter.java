package com.example.kodeshell;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> implements Filterable {

    private List<User> contactList, filteredList;
    private Context mContext;

    public UserListAdapter(Context context, List<User> contactList) {
        this.contactList = contactList;
        filteredList = new ArrayList<>(contactList);
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);
        return new ViewHolder(itemView);
    }

        @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User contact = contactList.get(position);
        holder.bind(contact);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("Name", contactList.get(position).getFirstName() + " " + contactList.get(position).getLastName());
                intent.putExtra("ID", contactList.get(position).getUserId());
                intent.putExtra("AvatarId", contactList.get(position).getAvatarid());
                mContext.startActivity(intent);
            }
        });
    }
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        User contact = filteredList.get(position); // Use filteredList instead of contactList
//        holder.bind(contact);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, MainActivity.class);
//                intent.putExtra("publisherId", contactList.get(position).getUserId());
//                mContext.startActivity(intent);
//            }
//        });
//    }
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        User contact = filteredList.get(position); // Use the filteredList instead of contactList
//        holder.bind(contact);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, MainActivity.class);
//                intent.putExtra("publisherId", filteredList.get(position).getUserId()); // Use filteredList
//                mContext.startActivity(intent);
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        if (contactList != null) return contactList.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView profilePic;
        // TextView phoneNumberTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.contactName);
            profilePic = itemView.findViewById(R.id.contactImage);
            // phoneNumberTextView = itemView.findViewById(R.id.contactPhone);
        }

        void bind(User contact) {
            nameTextView.setText(contact.getFirstName() + " " + contact.getLastName());
            Picasso.get().load(loadImage(contact.avatarid)).fit().centerInside().into(profilePic);

            // phoneNumberTextView.setText(contact.getMail());
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
                newList.addAll(filteredList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (User item : filteredList) {
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
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactList.clear();
            contactList.addAll((List) results.values);
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
}
