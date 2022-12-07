package com.solvit.mobile.ui.newnotification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.solvit.mobile.databinding.FragmentNewnotificationBinding;

public class NewNotificationFragment extends Fragment {

    private FragmentNewnotificationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewNotificationViewModel newNotificationViewModel =
                new ViewModelProvider(this).get(NewNotificationViewModel.class);

        binding = FragmentNewnotificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        TextView etUserNotification = binding.tvUserName;
        Log.d("New Notifiaction" ,"onCreateView: " + newNotificationViewModel.getUserName());
        etUserNotification.setText(newNotificationViewModel.getUserName());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}