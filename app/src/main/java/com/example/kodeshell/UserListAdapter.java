package com.example.kodeshell;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
                intent.putExtra("Name", contactList.get(position).getFirstName());
                intent.putExtra("ID", contactList.get(position).getUserId());
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
        // TextView phoneNumberTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.contactName);
            // phoneNumberTextView = itemView.findViewById(R.id.contactPhone);
        }

        void bind(User contact) {
            nameTextView.setText(contact.getFirstName() + " " + contact.getLastName());
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
}
