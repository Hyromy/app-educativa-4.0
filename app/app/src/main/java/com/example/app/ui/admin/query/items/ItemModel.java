package com.example.app.ui.admin.query.items;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.PreguntaActividadModel;
import com.example.app.db.models.PreguntaExamenModel;
import com.example.app.db.models.RecursoModel;
import com.example.app.db.models.RespuestaActividadModel;
import com.example.app.db.models.RespuestaExamenModel;
import com.example.app.db.models.TemaModel;
import com.example.app.db.utils.crud.Contenido;
import com.example.app.db.utils.crud.ExamenDiagnostico;
import com.example.app.db.utils.crud.PreguntaActividad;
import com.example.app.db.utils.crud.PreguntaExamen;
import com.example.app.db.utils.crud.Recurso;
import com.example.app.db.utils.crud.RespuestaActividad;
import com.example.app.db.utils.crud.RespuestaExamen;
import com.example.app.db.utils.crud.Tema;
import com.example.app.utils.ImageHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemModel {
    private ImageHelper imageHelper;

    private int getIdSelectionFromSpinner(LinearLayout layout, String tag) {
        Spinner spinner = layout.findViewWithTag(tag);
        String selection = spinner.getSelectedItem().toString();
        Pattern pattern = Pattern.compile("\\(\\d+\\)");
        Matcher matcher = pattern.matcher(selection);
        matcher.find();
        return Integer.parseInt(matcher.group().substring(1, matcher.group().length() - 1));
    }

    private int getIdSelectionFromSpinner(Spinner spinner) {
        String selection = spinner.getSelectedItem().toString();
        Pattern pattern = Pattern.compile("\\(\\d+\\)");
        Matcher matcher = pattern.matcher(selection);
        matcher.find();
        return Integer.parseInt(matcher.group().substring(1, matcher.group().length() - 1));
    }

    public void insertTema(Context context, LinearLayout layout) {
        EditText tituloET = layout.findViewWithTag("titulo");
        EditText descripcionET = layout.findViewWithTag("descripcion");

        String titulo = tituloET.getText().toString().trim();
        String descripcion = descripcionET.getText().toString().trim();

        if (titulo.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            Tema crudTema = new Tema(context);
            TemaModel tema = new TemaModel(
                    titulo,
                    descripcion
            );

            crudTema.open();
            try {
                crudTema.insert(tema);
                Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show();

                tituloET.setText("");
                descripcionET.setText("");

            } catch (Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudTema.close();
            }
        }
    }

    public void updateTema(Context context, LinearLayout layout, int id) {
        String titulo = ((EditText) layout.findViewWithTag("titulo")).getText().toString().trim();
        String descripcion = ((EditText) layout.findViewWithTag("descripcion")).getText().toString().trim();

        if (titulo.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            Tema crudTema = new Tema(context);
            TemaModel tema = new TemaModel(
                    id,
                    titulo,
                    descripcion
            );

            crudTema.open();
            try {
                crudTema.update(tema);
                Toast.makeText(context, "Registro actualizado", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudTema.close();
            }
        }
    }

    public void insertExamenDiagnostico(Context context, LinearLayout layout) {
        Spinner spinner = layout.findViewWithTag("tema_spinner");
        int idItem = getIdSelectionFromSpinner(spinner);
        String titulo = ((EditText) layout.findViewWithTag("titulo")).getText().toString().trim();
        String nPreguntas = ((EditText) layout.findViewWithTag("n_preguntas")).getText().toString().trim();
        String nivelMaximo = ((EditText) layout.findViewWithTag("nivel_maximo")).getText().toString().trim();

        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel tema = crudTema.read(idItem);
        crudTema.close();

        if (idItem > 0 || !nPreguntas.isEmpty() || !nivelMaximo.isEmpty()) {
            if (titulo.isEmpty()) {
                titulo = tema.tituloValue;
            }

            ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
            ExamenDiagnosticoModel examen = new ExamenDiagnosticoModel(
                    idItem,
                    titulo,
                    Integer.parseInt(nPreguntas),
                    Integer.parseInt(nivelMaximo)
            );
            crudExamen.open();
            try {
                crudExamen.insert(examen);
                Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show();

                spinner.setSelection(0);
                ((EditText) layout.findViewWithTag("titulo")).setText("");
                ((EditText) layout.findViewWithTag("n_preguntas")).setText("");
                ((EditText) layout.findViewWithTag("nivel_maximo")).setText("");

            } catch (Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudExamen.close();
            }
        } else {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateExamenDiagnostico(Context context, LinearLayout layout, int id) {
        int idItem = getIdSelectionFromSpinner(layout, "tema_spinner");
        String titulo = ((EditText) layout.findViewWithTag("titulo")).getText().toString().trim();
        String nPreguntas = ((EditText) layout.findViewWithTag("n_preguntas")).getText().toString().trim();
        String nivelMaximo = ((EditText) layout.findViewWithTag("nivel_maximo")).getText().toString().trim();

        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel tema = crudTema.read(idItem);
        crudTema.close();

        if (idItem > 0 || !nPreguntas.isEmpty() || !nivelMaximo.isEmpty()) {
            if (titulo.isEmpty()) {
                titulo = tema.tituloValue;
            }

            ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
            ExamenDiagnosticoModel examen = new ExamenDiagnosticoModel(
                    id,
                    idItem,
                    titulo,
                    Integer.parseInt(nPreguntas),
                    Integer.parseInt(nivelMaximo)
            );
            crudExamen.open();
            try {
                crudExamen.update(examen);
                Toast.makeText(context, "Registro actualizado", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudExamen.close();
            }
        } else {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertContenido(Context context, LinearLayout layout) {
        Spinner spinnerT = layout.findViewWithTag("tema_spinner");
        int idItem = getIdSelectionFromSpinner(spinnerT);
        String titulo = ((EditText) layout.findViewWithTag("titulo")).getText().toString().trim();
        String descripcion = ((EditText) layout.findViewWithTag("descripcion")).getText().toString().trim();

        Spinner spinnerN = layout.findViewWithTag("nivel_contenido_spinner");
        int nivelContenido = getIdSelectionFromSpinner(spinnerN);

        int nPreguntas = Integer.parseInt(((EditText) layout.findViewWithTag("n_preguntas")).getText().toString().trim());

        if (idItem > 0 || nPreguntas > 0) {
            Contenido crudContenido = new Contenido(context);
            ContenidoModel contenido = new ContenidoModel(
                    idItem,
                    titulo,
                    descripcion,
                    nivelContenido,
                    nPreguntas
            );
            crudContenido.open();

            ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
            crudExamen.open();
            int idExamen = crudExamen.getIdBy(ExamenDiagnosticoModel.idTema, String.valueOf(idItem));
            ExamenDiagnosticoModel examen = crudExamen.read(idExamen);
            crudExamen.close();

            try {
                if (examen.nivelMaximoValue < nivelContenido) {
                    throw new IllegalArgumentException("El nivel de contenido maximo debe ser de " + examen.nivelMaximoValue + " o menor");
                }

                crudContenido.insert(contenido);
                Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show();

                spinnerT.setSelection(0);
                spinnerN.setSelection(0);
                ((EditText) layout.findViewWithTag("titulo")).setText("");
                ((EditText) layout.findViewWithTag("descripcion")).setText("");
                ((EditText) layout.findViewWithTag("n_preguntas")).setText("");

            } catch (IllegalArgumentException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudContenido.close();
            }
        } else {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateContenido(Context context, LinearLayout layout, int id) {
        int idItem = getIdSelectionFromSpinner(layout, "tema_spinner");
        String titulo = ((EditText) layout.findViewWithTag("titulo")).getText().toString().trim();
        String descripcion = ((EditText) layout.findViewWithTag("descripcion")).getText().toString().trim();

        Spinner spinner = layout.findViewWithTag("nivel_contenido_spinner");
        int nivelContenido = getIdSelectionFromSpinner(spinner);

        int nPreguntas = Integer.parseInt(((EditText) layout.findViewWithTag("n_preguntas")).getText().toString().trim());

        if (idItem > 0 || nPreguntas > 0) {
            Contenido crudContenido = new Contenido(context);
            ContenidoModel contenido = new ContenidoModel(
                    id,
                    idItem,
                    titulo,
                    descripcion,
                    nivelContenido,
                    nPreguntas
            );
            crudContenido.open();

            ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
            crudExamen.open();
            int idExamen = crudExamen.getIdBy(ExamenDiagnosticoModel.idTema, String.valueOf(idItem));
            ExamenDiagnosticoModel examen = crudExamen.read(idExamen);
            crudExamen.close();

            try {
                if (examen.nivelMaximoValue < nivelContenido) {
                    throw new IllegalArgumentException("El nivel de contenido maximo debe ser de " + examen.nivelMaximoValue + " o menor");
                }

                crudContenido.update(contenido);
                Toast.makeText(context, "Registro actualizado", Toast.LENGTH_SHORT).show();

            } catch (IllegalArgumentException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudContenido.close();
            }
        } else {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertPreguntaExamen(Context context, LinearLayout layout) {
        Spinner spinner = layout.findViewWithTag("examen_spinner");
        int idItem = getIdSelectionFromSpinner(spinner);
        String texto = ((EditText) layout.findViewWithTag("texto")).getText().toString().trim();
        PreguntaExamenModel pregunta = new PreguntaExamenModel(
                idItem,
                0,
                texto
        );

        PreguntaExamen crudPregunta = new PreguntaExamen(context);
        crudPregunta.open();
        try {
            crudPregunta.insert(pregunta);
            Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show();

            spinner.setSelection(0);
            ((EditText) layout.findViewWithTag("texto")).setText("");

        } catch (Exception e) {
            Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());

        } finally {
            crudPregunta.close();
        }
    }

    public void updatePreguntaExamen(Context context, LinearLayout layout, int id) {
        int idItem = getIdSelectionFromSpinner(layout, "examen_spinner");
        String texto = ((EditText) layout.findViewWithTag("texto")).getText().toString().trim();

        PreguntaExamenModel pregunta = new PreguntaExamenModel(
                id,
                idItem,
                0,
                texto
        );
        PreguntaExamen crudPregunta = new PreguntaExamen(context);
        crudPregunta.open();

        try {
            crudPregunta.update(pregunta);
            Toast.makeText(context, "Registro actualizado", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());

        } finally {
            crudPregunta.close();
        }
    }

    public void insertPreguntaActividad(Context context, LinearLayout layout) {
        Spinner spinner = layout.findViewWithTag("contenido_spinner");
        int idItem = getIdSelectionFromSpinner(spinner);
        String texto = ((EditText) layout.findViewWithTag("texto")).getText().toString().trim();

        PreguntaActividadModel pregunta = new PreguntaActividadModel(
                idItem,
                0,
                texto
        );

        PreguntaActividad crudPregunta = new PreguntaActividad(context);
        crudPregunta.open();

        try {
            crudPregunta.insert(pregunta);
            Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show();

            spinner.setSelection(0);
            ((EditText) layout.findViewWithTag("texto")).setText("");

        } catch (Exception e) {
            Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());

        } finally {
            crudPregunta.close();
        }
    }

    public void updatePreguntaActividad(Context context, LinearLayout layout, int id) {
        int idItem = getIdSelectionFromSpinner(layout, "contenido_spinner");
        String texto = ((EditText) layout.findViewWithTag("texto")).getText().toString().trim();

        PreguntaActividadModel pregunta = new PreguntaActividadModel(
                id,
                idItem,
                0,
                texto
        );

        PreguntaActividad crudPregunta = new PreguntaActividad(context);
        crudPregunta.open();

        try {
            crudPregunta.update(pregunta);
            Toast.makeText(context, "Registro actualizado", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());

        } finally {
            crudPregunta.close();
        }
    }

    public void insertRespuestaExamen(Context context, LinearLayout layout) {
        Spinner spinner = layout.findViewWithTag("pregunta_examen_spinner");
        int idItem = getIdSelectionFromSpinner(spinner);
        String texto = ((EditText) layout.findViewWithTag("texto")).getText().toString().trim();
        String puntaje = ((EditText) layout.findViewWithTag("puntaje")).getText().toString().trim();

        if (idItem > 0 || !puntaje.isEmpty()) {
            RespuestaExamenModel respuesta = new RespuestaExamenModel(
                    idItem,
                    0,
                    texto,
                    Integer.parseInt(puntaje)
            );

            RespuestaExamen crudRespuesta = new RespuestaExamen(context);
            crudRespuesta.open();
            try {
                crudRespuesta.insert(respuesta);
                Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show();

                spinner.setSelection(0);
                ((EditText) layout.findViewWithTag("texto")).setText("");
                ((EditText) layout.findViewWithTag("puntaje")).setText("");

            } catch (Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudRespuesta.close();
            }
        } else {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateRespuestaExamen(Context context, LinearLayout layout, int id) {
        int idItem = getIdSelectionFromSpinner(layout, "pregunta_examen_spinner");
        String texto = ((EditText) layout.findViewWithTag("texto")).getText().toString().trim();
        String puntaje = ((EditText) layout.findViewWithTag("puntaje")).getText().toString().trim();

        if (idItem > 0 || !puntaje.isEmpty()) {
            RespuestaExamenModel respuesta = new RespuestaExamenModel(
                    id,
                    idItem,
                    0,
                    texto,
                    Integer.parseInt(puntaje)
            );

            RespuestaExamen crudRespuesta = new RespuestaExamen(context);
            crudRespuesta.open();
            try {
                crudRespuesta.update(respuesta);
                Toast.makeText(context, "Registro actualizado", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudRespuesta.close();
            }
        } else {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertRespuestaActividad(Context context, LinearLayout layout) {
        Spinner spinner = layout.findViewWithTag("pregunta_actividad_spinner");
        int idItem = getIdSelectionFromSpinner(spinner);
        String texto = ((EditText) layout.findViewWithTag("texto")).getText().toString().trim();
        boolean correcta = ((CheckBox) layout.findViewWithTag("es_correcto")).isChecked();

        RespuestaActividadModel respuesta = new RespuestaActividadModel(
                idItem,
                0,
                texto,
                correcta
        );

        RespuestaActividad crudRespuesta = new RespuestaActividad(context);
        crudRespuesta.open();
        try {
            crudRespuesta.insert(respuesta);
            Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show();

            spinner.setSelection(0);
            ((EditText) layout.findViewWithTag("texto")).setText("");
            ((CheckBox) layout.findViewWithTag("es_correcto")).setChecked(false);

        } catch (Exception e) {
            Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());

        } finally {
            crudRespuesta.close();
        }
    }

    public void updateRespuestaActividad(Context context, LinearLayout layout, int id) {
        int idItem = getIdSelectionFromSpinner(layout, "pregunta_actividad_spinner");
        String texto = ((EditText) layout.findViewWithTag("texto")).getText().toString().trim();
        boolean correcta = ((CheckBox) layout.findViewWithTag("es_correcto")).isChecked();

        RespuestaActividadModel respuesta = new RespuestaActividadModel(
                id,
                idItem,
                0,
                texto,
                correcta
        );

        RespuestaActividad crudRespuesta = new RespuestaActividad(context);
        crudRespuesta.open();
        try {
            crudRespuesta.update(respuesta);
            Toast.makeText(context, "Registro actualizado", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());

        } finally {
            crudRespuesta.close();
        }
    }

    public void insertRecurso(Context context, LinearLayout layout, ImageHelper imageHelper) {
        ImageView imageView = layout.findViewWithTag("recurso_img");
        if (imageView.getDrawable() != null) {
            String fileName = imageHelper.getImageFileName();
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

            fileName = filenameSplitter(fileName)[0];

            try {
                insertImgInDB(context, fileName);
                insertImgInStorage(context, bitmap, fileName);

                Toast.makeText(context, "Imagen guardada", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Ocurrió un problema al guardar la imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Seleccione una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateRecurso(Context context, LinearLayout layout, int id, ImageHelper imageHelper) {
        ImageView imageView = layout.findViewWithTag("recurso_img");
        if (imageView.getDrawable() != null) {
            Recurso crudRecurso = new Recurso(context);
            crudRecurso.open();

            try {
                RecursoModel recurso = crudRecurso.read(id);
                deleteImgFromStorage(context, recurso);

                String fileName = imageHelper.getImageFileName();
                fileName = filenameSplitter(fileName)[0];
                recurso.nombreValue = fileName;
                crudRecurso.update(recurso);

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                insertImgInStorage(context, bitmap, fileName);

                Toast.makeText(context, "Imagen actualizada", Toast.LENGTH_SHORT).show();
                
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Ocurrió un problema al actualizar la imagen", Toast.LENGTH_SHORT).show();
            } finally {
                crudRecurso.close();
            }
        } else {
            Toast.makeText(context, "Seleccione una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private String[] filenameSplitter(String fileName) {
        Pattern pattern = Pattern.compile("^(.*?)(\\.[^.]*$|$)");
        Matcher matcher = pattern.matcher(fileName);
        matcher.find();
        return new String[] {matcher.group(1), matcher.group(2)};
    }

    private void insertImgInDB(Context context, String fileName) {
        RecursoModel recurso = new RecursoModel(fileName, "jpeg", "img");
        Recurso crudRecurso = new Recurso(context);
        crudRecurso.open();
        crudRecurso.insert(recurso);
        crudRecurso.close();
    }

    private void insertImgInStorage(Context context, Bitmap bitmap, String fileName) throws IOException {
        File storageDir = context.getFilesDir();
        File imageFile = new File(storageDir, fileName + ".jpeg");

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        }
    }

    public void deleteImgFromStorage(Context context, RecursoModel recurso) {
        File storageDir = context.getFilesDir();
        File imageFile = new File(storageDir, recurso.nombreValue + "." + recurso.extensionValue);

        if (imageFile.exists()) {
            imageFile.delete();
        }
    }
}