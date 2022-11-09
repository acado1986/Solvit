package com.solvit.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    ArrayList<NotificationModel> notifications;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    // inflate the layout and giving the look of each row
    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_item, parent, false);

        return new NotificationAdapter.ViewHolder(view);
    }

    // assign values of each row as they come back on the screen and depends of the position
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.tvId.setText(String.valueOf(notifications.get(position).getId()));
        holder.tvDescription.setText(notifications.get(position).getDescription());
    }

    // the number of items you want to display
    @Override
    public int getItemCount() {
        return notifications.size();
    }

    // grabbing the views from our layout recycle_view_item.xml, kinda of create method
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
