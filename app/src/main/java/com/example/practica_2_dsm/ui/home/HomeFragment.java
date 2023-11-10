package com.example.practica_2_dsm.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.practica_2_dsm.R;
import com.example.practica_2_dsm.databinding.FragmentDashboardBinding;
import com.example.practica_2_dsm.databinding.FragmentHomeBinding;
import com.example.practica_2_dsm.ui.dashboard.DashboardFragment;

import java.util.ArrayList;


class Consola{
    private String nombre;
    private String descripcion;

    public Consola(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String toString() {
        return nombre;
    }

}
public class HomeFragment extends Fragment {

    private ListView listView;
    private EditText txt_name, txt_description;
    public ArrayList<Consola> almacenamiento = new ArrayList<Consola>();
    private ArrayAdapter<Consola> adapter;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Object blinding;
        txt_name = binding.nameTxt;
        txt_description = binding.descriptionTxt;
        listView = binding.listView;

        // Crear el adaptador personalizado
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, almacenamiento);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {  Consola consola = almacenamiento.get(position);

                // Crea un AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle(consola.getNombre())
                        .setMessage("Descripción: " + consola.getDescripcion())
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Puedes realizar acciones adicionales si es necesario
                                dialog.dismiss();
                            }
                        });

                // Muestra el AlertDialog
                builder.create().show();
            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardFragment dashboardFragment = new DashboardFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, dashboardFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        binding.addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                save();
            }
            public void setOnClickListener(View.OnClickListener onClickListener) {


            }
        });

    }

    // Metodo para guardar las consolas
    public void save () {
        String name = binding.nameTxt.getText().toString();
        String description = binding.descriptionTxt.getText().toString();
        if (!name.isEmpty() && !description.isEmpty()) {
            Consola consola = new Consola(name, description);
            almacenamiento.add(consola);
            adapter.notifyDataSetChanged();
            cleanForm();
            Toast.makeText(this.getActivity(), "Registro Exitoso", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this.getActivity(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    public void cleanForm() {
        txt_name.setText("");
        txt_description.setText("");
    }
}

