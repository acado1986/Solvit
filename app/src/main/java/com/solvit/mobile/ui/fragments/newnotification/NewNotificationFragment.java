package com.solvit.mobile.ui.fragments.newnotification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.type.DateTime;
import com.solvit.mobile.R;
import com.solvit.mobile.databinding.FragmentNewnotificationBinding;
import com.solvit.mobile.model.NotificationModel;
import com.solvit.mobile.model.NotificationType;
import com.solvit.mobile.model.RevisedBy;
import com.solvit.mobile.model.Status;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NewNotificationFragment extends Fragment {

    private FragmentNewnotificationBinding binding;
    private NewNotificationViewModel newNotificationViewModel;
    private EditText building;
    private EditText room;
    private EditText title;
    private EditText description;
    private Spinner spNotificationType;
    private Spinner spPcNumber;
    private Button btnSend;
    private Button btnClear;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newNotificationViewModel =
                new ViewModelProvider(this).get(NewNotificationViewModel.class);

        binding = FragmentNewnotificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // bind fields
        TextView etUserNotification = binding.tvUserName;
        Log.d("New Notifiaction" ,"onCreateView: " + newNotificationViewModel.getUserName());
        etUserNotification.setText(newNotificationViewModel.getUserName());

        building = binding.etBuildingNotification;
        room = binding.etNotificationRoom;
        title = binding.etNotificationTitle;
        description = binding.etNotificationDescription;
        spNotificationType = binding.spNotificationType;
        spPcNumber = binding.spPcNumber;
        btnSend = binding.btnSendNotification;
        btnClear = binding.btnResetNotification;

        // set the spinners data
        spNotificationType.setAdapter(new ArrayAdapter<NotificationType>(getContext(), android.R.layout.simple_spinner_dropdown_item, NotificationType.values()));
        spPcNumber.setAdapter(new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_dropdown_item, IntStream.iterate(1, i -> i + 1).limit(50).boxed().collect(Collectors.toList())));

        btnSend.setOnClickListener(view1 -> {
            NotificationModel newNotification = new NotificationModel(
                    RevisedBy.WORKER.toString(), Status.PENDING.toString(),
                    title.getText().toString(),
                    room.getText().toString(), building.getText().toString(),
                    description.getText().toString(),
                    newNotificationViewModel.getUserName(),
                    Long.valueOf(spPcNumber.getSelectedItem().toString()),
                    new ArrayList<String>()
            );

            String collectioPath = "";
            NotificationType notificationType = NotificationType.values()[spNotificationType.getSelectedItemPosition()];
            switch (notificationType){
                case TIC:
                    collectioPath = getResources().getString(R.string.collectionIt);
                    break;
                case MAINTENANCE:
                    collectioPath = getResources().getString(R.string.collectionMaintenance);
                    break;
                case RECEPTION:
                    collectioPath = getResources().getString(R.string.collectionReception);
                    break;
            }

            newNotificationViewModel.writeNotification(newNotification, collectioPath);
            Toast.makeText(getContext(), "Se ha mandado la nueva notification", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        });

        btnClear.setOnClickListener(view12 -> {
            limpiarCampos();
        });
    }

    private void limpiarCampos() {
        // limpiar los campos
        title.setText("");
        room.setText("");
        building.setText("");
        description.setText("");
        spPcNumber.setSelection(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}