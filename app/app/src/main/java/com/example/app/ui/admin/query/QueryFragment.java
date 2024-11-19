package com.example.app.ui.admin.query;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.PreguntaActividadModel;
import com.example.app.db.models.PreguntaExamenModel;
import com.example.app.db.models.RespuestaActividadModel;
import com.example.app.db.models.RespuestaExamenModel;
import com.example.app.db.models.TemaModel;
import com.example.app.db.utils.crud.Contenido;
import com.example.app.db.utils.crud.ExamenDiagnostico;
import com.example.app.db.utils.crud.PreguntaActividad;
import com.example.app.db.utils.crud.PreguntaExamen;
import com.example.app.db.utils.crud.RespuestaActividad;
import com.example.app.db.utils.crud.RespuestaExamen;
import com.example.app.db.utils.crud.Tema;
import com.example.app.utils.drawer.QueryDrawer;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryFragment extends Fragment {
    private String itemsType = null;
    private String table = null;

    private QueryDrawer drawer = new QueryDrawer();
    private QueryModel model = new QueryModel();

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
            itemsType = bundle.getString("queryName");
            setToolbarTitle(itemsType);
        }

        view.findViewById(R.id.btn_agregar).setOnClickListener(saveListener());
        setSearchView(view);

        Context context = getContext();
        table = model.titleToTable(itemsType);
        getLogs(context, view);
    }

    private void getLogs(Context context, View view) {
        if (table.equals("tema")) {
            setTemaLogs(context, view);

        } else if (table.equals("examen_diagnostico")) {
            setExamenLogs(context, view);

        } else if (table.equals("contenido")) {
            setContenidoLogs(context, view);

        } else if (table.equals("pregunta_")) {
            setPreguntaLogs(context, view);

        } else if (table.equals("respuesta_")) {
            setRespuestaLogs(context, view);

        } else if (table.equals("apoyo")) {
            // setApoyoLogs(context, view);

        } else if (table.equals("recurso")) {
            setRecursoLogs(context, view);
        }
    }

    private void setTemaLogs(Context context, View view) {
        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel[] temas = crudTema.readAll();
        crudTema.close();

        for (TemaModel tema : temas) {
            generateItem(view, tema.idValue, tema.tituloValue, table);
        }
    }

    private void setExamenLogs(Context context, View view) {
        ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
        crudExamen.open();
        ExamenDiagnosticoModel[] examenes = crudExamen.readAll();
        crudExamen.close();

        for (ExamenDiagnosticoModel examen : examenes) {
            generateItem(view, examen.idValue, examen.tituloValue, table);
        }
    }

    private void setContenidoLogs(Context context, View view) {
        Contenido crudContenido = new Contenido(context);
        crudContenido.open();
        ContenidoModel[] contenidos = crudContenido.readAll();
        
        String title = null;
        for (ContenidoModel contenido : contenidos) {
            title = crudContenido.getTitleFromTheme(contenido.idValue);
            generateItem(view, contenido.idValue, contenido.tituloValue + " (" + title + ")", table);
        }
        crudContenido.close();
    }

    private void setPreguntaLogs(Context context, View view) {
        PreguntaExamen crudPregunta = new PreguntaExamen(context);
        crudPregunta.open();
        PreguntaExamenModel[] preguntas = crudPregunta.readAll();
        crudPregunta.close();
        String exam = null;
        for (PreguntaExamenModel pregunta : preguntas) {
            exam = crudPregunta.getTitleFromExam(pregunta);
            generateItem(view, pregunta.idValue, "D: " + exam + " -> " + pregunta.textoValue, table + "examen");
        }

        PreguntaActividad crudActividad = new PreguntaActividad(context);
        crudActividad.open();
        PreguntaActividadModel[] actividades = crudActividad.readAll();
        crudActividad.close();
        String activity = null;
        for (PreguntaActividadModel actividad : actividades) {
            activity = crudActividad.getTitleFromTheme(actividad);
            generateItem(view, actividad.idValue, "A: " + activity + " -> " + actividad.textoValue, table + "actividad");
        }
    }

    private void setRespuestaLogs(Context context, View view) {
        RespuestaExamen crudRespuesta = new RespuestaExamen(context);
        crudRespuesta.open();
        RespuestaExamenModel[] respuestas = crudRespuesta.readAll();
        crudRespuesta.close();
        String pregunta = null;
        for (RespuestaExamenModel respuesta : respuestas) {
            pregunta = crudRespuesta.getTextFromQuestion(respuesta);
            generateItem(view, respuesta.idValue, "E: " + pregunta + " -> " + respuesta.textoValue, table + "examen");
        }

        RespuestaActividad crudActividad = new RespuestaActividad(context);
        crudActividad.open();
        RespuestaActividadModel[] respuestasA = crudActividad.readAll();
        crudActividad.close();
        String actividad = null;
        for (RespuestaActividadModel respuesta : respuestasA) {
            actividad = crudActividad.getTextFromQuestion(respuesta);
            generateItem(view, respuesta.idValue, "A: " + actividad + " -> " + respuesta.textoValue, table + "actividad");
        }
    }

    private void setRecursoLogs(Context context, View view) {
        Toast.makeText(context, "Este es el query", Toast.LENGTH_SHORT).show();
    }

    private void setSearchView(View view) {
        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnClickListener(v -> searchView.setIconified(false));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void generateItem(View view, int id, String name, String table) {
        int dp8 = 24;
        int dp16 = dp8 * 2;
        String tag = table + "_" + id;
        LinearLayout linearLayout = view.findViewById(R.id.linear_layout);
        Context context = getContext();

        TextView textID = drawer.textView(context, "id", String.valueOf(id));
        TextView textName = drawer.textView(context, "tx", name);

        LinearLayout linearInfo = drawer.linearInfo(context);
        linearInfo.addView(textID);
        linearInfo.addView(textName);

        ImageView trash = drawer.imageView(context, tag, R.drawable.ic_trash);
        trash.setOnClickListener(setDelListener(trash.getTag().toString()));

        LinearLayout linearContainer = drawer.linearContainer(context, dp16, dp8, tag);
        linearContainer.addView(linearInfo);
        linearContainer.addView(trash);
        linearContainer.setOnClickListener(editListener(linearContainer.getTag().toString()));

        linearLayout.addView(linearContainer);
    }

    private void setToolbarTitle(String title) {
        if (requireActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) requireActivity();
            String currentTitle = Objects.requireNonNull(activity.getSupportActionBar()).getTitle().toString();
            activity.getSupportActionBar().setTitle(currentTitle + " " + title);
        }
    }

    private View.OnClickListener setDelListener(String tag) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(QueryFragment.this.getContext());
                alert.setTitle("Eliminar");
                alert.setMessage("¿Estás seguro de que deseas eliminar {" + tag + "}?");
                alert.setPositiveButton(android.R.string.yes, (dialog, which) -> delItem(tag, getContext()));
                alert.setNegativeButton(android.R.string.no, null);
                alert.show();
            }
        };
    }

    private void delItem(String tag, Context context) {
        Pattern pattern = Pattern.compile("(?<=_).*_[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        tag = matcher.group();

        LinearLayout linearLayout = requireView().findViewById(R.id.linear_layout);
        View view = linearLayout.findViewWithTag(tag);
        linearLayout.removeView(view);

        delFromDB(tag, context);
    }

    private void delFromDB(String tag, Context context) {
        if (tag.contains("tema")) {
            delTema(tag, context);

        } else if (tag.contains("examen_diagnostico")) {
            delExamen(tag, context);

        } else if (tag.contains("contenido")) {
            delContenido(tag, context);

        } else if (tag.contains("pregunta")) {
            if (tag.contains("examen")) {
                delPreguntaExamen(tag, context);
            } else if (tag.contains("actividad")) {
                delPreguntaActividad(tag, context);
            }

        } else if (tag.contains("respuesta")) {
            if (tag.contains("examen")) {
                delRespuestaExamen(tag, context);
            } else if (tag.contains("actividad")) {
                delRespuestaActividad(tag, context);
            }
        }
    }

    private void delTema(String tag, Context context) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        int id = Integer.parseInt(matcher.group());

        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel tema = crudTema.read(id);
        crudTema.delete(tema);
        crudTema.close();
    }

    private void delExamen(String tag, Context context) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        int id = Integer.parseInt(matcher.group());

        ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
        crudExamen.open();
        ExamenDiagnosticoModel examen = crudExamen.read(id);
        crudExamen.delete(examen);
        crudExamen.close();
    }

    private void delContenido(String tag, Context context) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        int id = Integer.parseInt(matcher.group());

        Contenido crudContenido = new Contenido(context);
        crudContenido.open();
        ContenidoModel contenido = crudContenido.read(id);
        crudContenido.delete(contenido);
        crudContenido.close();
    }

    private void delPreguntaExamen(String tag, Context context) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        int id = Integer.parseInt(matcher.group());

        PreguntaExamen crudPregunta = new PreguntaExamen(context);
        crudPregunta.open();
        PreguntaExamenModel pregunta = crudPregunta.read(id);
        crudPregunta.delete(pregunta);
        crudPregunta.close();
    }

    private void delPreguntaActividad(String tag, Context context) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        int id = Integer.parseInt(matcher.group());

        PreguntaActividad crudActividad = new PreguntaActividad(context);
        crudActividad.open();
        PreguntaActividadModel actividad = crudActividad.read(id);
        crudActividad.delete(actividad);
        crudActividad.close();
    }

    private void delRespuestaExamen(String tag, Context context) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        int id = Integer.parseInt(matcher.group());

        RespuestaExamen crudRespuesta = new RespuestaExamen(context);
        crudRespuesta.open();
        RespuestaExamenModel respuesta = crudRespuesta.read(id);
        crudRespuesta.delete(respuesta);
        crudRespuesta.close();
    }

    private void delRespuestaActividad(String tag, Context context) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(tag);
        matcher.find();
        int id = Integer.parseInt(matcher.group());

        RespuestaActividad crudRespuesta = new RespuestaActividad(context);
        crudRespuesta.open();
        RespuestaActividadModel respuesta = crudRespuesta.read(id);
        crudRespuesta.delete(respuesta);
        crudRespuesta.close();
    }

    private void clearList() {
        LinearLayout linearLayout = requireView().findViewById(R.id.linear_layout);
        linearLayout.removeAllViews();
    }

    private View.OnClickListener editListener(String tag) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putChar("action", 'e');
                bundle.putString("table", model.titleToTable(itemsType));

                Pattern pattern = Pattern.compile("_[0-9]+");
                Matcher matcher = pattern.matcher(tag);
                matcher.find();
                bundle.putInt("id", Integer.parseInt(matcher.group().substring(1)));

                bundle.putString("itemTag", tag);
                Navigation.findNavController(view).navigate(R.id.action_nav_query_to_nav_item, bundle);
            }
        };
    }

    private View.OnClickListener saveListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putChar("action", 'c');
                bundle.putString("table", model.titleToTable(itemsType));
                bundle.putInt("id", 0);

                Navigation.findNavController(view).navigate(R.id.action_nav_query_to_nav_item, bundle);
            }
        };
    }
}