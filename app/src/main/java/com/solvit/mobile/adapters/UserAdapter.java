package com.solvit.mobile.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solvit.mobile.R;
import com.solvit.mobile.model.Role;
import com.solvit.mobile.model.UserInfo;

import java.util.List;

/**
 * User adapter for the recycle list
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context context;
    List<UserInfo> users;
    OnCheckedChangeListener listener;
    public UserAdapter(Context context, List<UserInfo> users, OnCheckedChangeListener listener) {
        this.context = context;
        this.users = users;
        this.listener = listener;
    }

    // inflate the layout and giving the look of each row
    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_user_item, parent, false);

        return new UserAdapter.ViewHolder(view);
    }

    // assign values of each row as they come back on the screen and depends of the position
    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        // options for spinner
        holder.spFillRole.setAdapter(new ArrayAdapter<Role>(context, android.R.layout.simple_dropdown_item_1line, Role.values()));
        holder.tvFillUserMail.setText(String.valueOf(users.get(position).getEmail()));
        holder.tvFillUserName.setText(users.get(position).getDisplayName());
        holder.spFillRole.setSelection(users.get(position).getRole().ordinal());
        holder.swUserActivate.setChecked(users.get(position).getActive());
        holder.bind(users.get(position), listener);
    }

    // the number of items you want to display
    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);

        holder.swUserActivate.setOnCheckedChangeListener(null);
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(CompoundButton compoundButton, boolean b, View view, UserInfo userInfo);
    }

    // grabbing the views from our layout recycle_view_item.xml, kinda of create method
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFillUserMail, tvFillUserName;
        Spinner spFillRole;
        Switch swUserActivate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFillUserMail = itemView.findViewById(R.id.tvFillUserEmail);
            tvFillUserName = itemView.findViewById(R.id.tvFillUserName);
            spFillRole = itemView.findViewById(R.id.spFillRole);
            swUserActivate = itemView.findViewById(R.id.swUserActivate);

        }

        public void bind(final UserInfo userInfo, final OnCheckedChangeListener listener) {
            swUserActivate.setOnCheckedChangeListener((compoundButton, b) -> {
                listener.onCheckedChanged(compoundButton, b, itemView, userInfo);
                Log.d(TAG, "onCheckedChanged: " + userInfo.getUid());
            });
        }
    }
}
