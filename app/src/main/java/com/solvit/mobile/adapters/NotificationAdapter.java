package com.solvit.mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solvit.mobile.R;
import com.solvit.mobile.model.NotificationModel;

import java.util.List;

/**
 * A general purpose adapter for notifications
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    List<NotificationModel> notifications;
    OnItemClickListener listener;
    public NotificationAdapter(Context context, List<NotificationModel> notifications, OnItemClickListener listener) {
        this.context = context;
        this.notifications = notifications;
        this.listener = listener;
    }

    public void update(List<NotificationModel> newNotifications) {
        this.notifications.clear();
        this.notifications = newNotifications;
        notifyDataSetChanged();
    }

    // inflate the layout and giving the look of each row
    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_notification_item, parent, false);

        return new NotificationAdapter.ViewHolder(view);
    }

    // assign values of each row as they come back on the screen and depends of the position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvBuilding.setText(String.valueOf(notifications.get(position).getBuilding()));
        holder.tvTitle.setText(notifications.get(position).getTitle());
        holder.tvRoom.setText(notifications.get(position).getRoom());
        holder.bind(notifications.get(position), listener);
        holder.imgNotification.setImageResource(notifications.get(position).getStatus().equals("PENDING") ? R.drawable.ic_baseline_pending_actions_24 : R.drawable.ic_baseline_done_outline_24);
    }

    // the number of items you want to display
    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public interface OnItemClickListener {
        void onItemClick(NotificationModel notification);
    }

    // grabbing the views from our layout recycle_view_item.xml, kinda of create method
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBuilding, tvTitle, tvRoom;
        ImageView imgNotification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBuilding = itemView.findViewById(R.id.tvItemBuilding);
            tvTitle = itemView.findViewById(R.id.tvItemTitle);
            tvRoom = itemView.findViewById(R.id.tvItemRoom);
            imgNotification = itemView.findViewById(R.id.imgNotification);

        }

        public void bind(final NotificationModel notificationModel, final OnItemClickListener listener) {
            itemView.setOnClickListener(view -> listener.onItemClick(notificationModel));
        }
    }
}
