package com.example.practica_2_dsm.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practica_2_dsm.R;
import com.example.practica_2_dsm.databinding.FragmentDashboardBinding;
import com.example.practica_2_dsm.ui.dashboard.DashboardViewModel;

public class DashboardFragment extends Fragment {

    private EditText editTextRut, editTextNombre, editTextEdad;
    private Button btnSave, searchBtn;

    private SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);


        FragmentDashboardBinding binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);


        editTextRut = binding.editTextRut;
        editTextNombre = binding.editTextNombre;
        editTextEdad = binding.editTextEdad;
        btnSave = binding.btnSave;


        load();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Toast.makeText(requireContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarDatos();
            }
        });

    }

    private void save() {

        String rut = editTextRut.getText().toString();
        String nombre = editTextNombre.getText().toString();
        String edad = editTextEdad.getText().toString();


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("rut", rut);
        editor.putString("nombre", nombre);
        editor.putString("edad", edad);
        editor.apply();
    }

    private void load() {

        String rut = sharedPreferences.getString("rut", "");
        String nombre = sharedPreferences.getString("nombre", "");
        String edad = sharedPreferences.getString("edad", "");


        editTextRut.setText(rut);
        editTextNombre.setText(nombre);
        editTextEdad.setText(edad);
    }
    private void buscarDatos() {
        String rut = editTextRut.getText().toString();
        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        String info = preferences.getString(rut, "");

        if (info.length() == 0) {
            Toast.makeText(getActivity(), "No se encontro ningun registro", Toast.LENGTH_LONG).show();
        } else {
            editTextNombre.setText(info);
            editTextEdad.setText(info);
        }
    }
}
