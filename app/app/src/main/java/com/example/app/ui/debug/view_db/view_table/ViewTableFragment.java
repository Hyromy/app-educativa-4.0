package com.example.app.ui.debug.view_db.view_table;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.db.utils.crud.AbstractCRUD;
import com.example.app.utils.drawer.ViewTableDrawer;

public class ViewTableFragment extends Fragment {
    private AbstractCRUD crud;
    private String table;

    private ViewTableModel model = new ViewTableModel();
    private ViewTableDrawer drawer;

    private View v;
    private GridLayout gridLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view;
        gridLayout = v.findViewById(R.id.gridLayout);
        drawer = new ViewTableDrawer(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            table = bundle.getString("table");

            crud = model.getCRUD(table, getContext());
            crud.open();

            try {
                drawData();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Ocurrió un problema al recuperar la información", Toast.LENGTH_SHORT).show();
            }

            crud.close();
        }
    }

    public void drawData() {
        TextView tv;

        String[][] data = crud.getStringLogs(table);

        gridLayout.setColumnCount(data[0].length);
        gridLayout.setRowCount(data.length + 1);

        String[] cols = crud.colums(table);
        for (String col : cols) {
            tv = drawer.textView(col);
            gridLayout.addView(tv);
        }

        for (String[] rows : data) {
            for (String col : rows) {
                tv = drawer.textView(col);
                gridLayout.addView(tv);
            }
        }
    }
}