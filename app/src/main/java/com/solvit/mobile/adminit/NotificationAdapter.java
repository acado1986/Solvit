package com.solvit.mobile.adminit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solvit.mobile.R;
import com.solvit.mobile.model.NotificationModelIT;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(NotificationModelIT notification);
    }

    Context context;
    List<NotificationModelIT> notifications;
    OnItemClickListener listener;

    public NotificationAdapter(Context context, List<NotificationModelIT> notifications, OnItemClickListener listener) {
        this.context = context;
        this.notifications = notifications;
        this.listener = listener;
    }

    public void update(List<NotificationModelIT> newNotifications) {
        this.notifications.clear();
        this.notifications = newNotifications;
        notifyDataSetChanged();
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
        holder.tvBuilding.setText(String.valueOf(notifications.get(position).getBuilding()));
        holder.tvDescription.setText(notifications.get(position).getDescription());
        holder.tvRoom.setText(notifications.get(position).getRoom());
        holder.bind(notifications.get(position), listener);
    }

    // the number of items you want to display
    @Override
    public int getItemCount() {
        return notifications.size();
    }

    // grabbing the views from our layout recycle_view_item.xml, kinda of create method
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBuilding, tvDescription, tvRoom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBuilding = itemView.findViewById(R.id.tvBuilding);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvRoom = itemView.findViewById(R.id.tvRoom);

        }

        public void bind(final NotificationModelIT notificationModelIT, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(notificationModelIT);
                }
            });
        }
    }
}
