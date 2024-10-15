package com.example.app.ui.admin.query;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryFragment extends Fragment {

    private QueryViewModel mViewModel;

    public static QueryFragment newInstance() {
        return new QueryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_query, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            setToolbarTitle(bundle.getString("queryName"));
        }

        // consultar la base de datos
        generateAutoItems(view);

        // btnlimpiar la lista
        view.findViewById(R.id.btn_clear).setOnClickListener(v -> clearList());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(QueryViewModel.class);
        // TODO: Use the ViewModel
    }

    private void generateAutoItems(View view) {
        int dp8 = dpToPx(8);
        int dp16 = dp8 * 2;
        LinearLayout linearLayout = view.findViewById(R.id.linear_layout);
        Context context = getContext();
        for (int i = 1; i <= Math.random() * 10 + 5; i++) {
            TextView textID = textView(context, "id", String.valueOf(i));
            TextView textName = textView(context, "tx", "{itemName} [PRUEBA]");

            LinearLayout linearInfo = linearInfo(context);
            linearInfo.addView(textID);
            linearInfo.addView(textName);

            ImageView trash = imageView(context, i, R.drawable.ic_trash);
            trash.setOnClickListener(setDelListener(trash.getTag().toString()));

            LinearLayout linearContainer = linearContainer(context, dp16, dp8, i);
            linearContainer.addView(linearInfo);
            linearContainer.addView(trash);

            linearLayout.addView(linearContainer);
        }
    }

    private void setToolbarTitle(String title) {
        if (requireActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) requireActivity();
            String currentTitle = Objects.requireNonNull(activity.getSupportActionBar()).getTitle().toString();
            activity.getSupportActionBar().setTitle(currentTitle + " " + title);
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private LinearLayout linearContainer(Context context, int paddingDP, int marginDP, int tagID) {
        LinearLayout linearContainer = new LinearLayout(context);
        linearContainer.setId(View.generateViewId());
        linearContainer.setTag("item_" + String.valueOf(tagID));
        linearContainer.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(marginDP, marginDP, marginDP, marginDP);
        linearContainer.setLayoutParams(params);
        linearContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_box));
        linearContainer.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.Greeuttec));
        linearContainer.setPadding(paddingDP, paddingDP, paddingDP, paddingDP);

        return linearContainer;
    }

    private LinearLayout linearInfo(Context context) {
        LinearLayout linearInfo = new LinearLayout(context);
        linearInfo.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;

        return linearInfo;
    }

    private TextView textView(Context context, String type, String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        if (type.toLowerCase().equals("id")) {
            int dp8 = dpToPx(8);
            int dp32 = dp8 * 2;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            textView.setTextSize(dp32);
            textView.setPadding(0, 0, dp8, 0);

        } else if (type.toLowerCase().equals("tx")) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER_VERTICAL);
        }

        return textView;
    }

    private ImageView imageView(Context context, int tagID, int resID) {
        ImageView imageView = new ImageView(context);
        imageView.setId(View.generateViewId());
        imageView.setTag("trash_item_" + String.valueOf(tagID));
        imageView.setImageResource(resID);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);

        return imageView;
    }

    private View.OnClickListener setDelListener(String tag) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(QueryFragment.this.getContext());
                alert.setTitle("Eliminar");
                alert.setMessage("¿Estás seguro de que deseas eliminar {" + tag + "}?");
                alert.setPositiveButton(android.R.string.yes, (dialog, which) -> delItem(tag));
                alert.setNegativeButton(android.R.string.no, null);
                alert.show();
            }
        };
    }

    private void delItem(String tag) {
        // encontrar contenedor padre
        Pattern pattern = Pattern.compile("item_[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        tag = matcher.group();

        // eliminar de la vista
        LinearLayout linearLayout = requireView().findViewById(R.id.linear_layout);
        View view = linearLayout.findViewWithTag(tag);
        linearLayout.removeView(view);

        // eliminar de la base de datos

        // mensaje
        messageToast("Elemento eliminado [db incompleta]");
    }

    private void clearList() {
        LinearLayout linearLayout = requireView().findViewById(R.id.linear_layout);
        linearLayout.removeAllViews();

        messageToast("Lista limpiada [funcion experimental]");
    }

    private void messageToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}