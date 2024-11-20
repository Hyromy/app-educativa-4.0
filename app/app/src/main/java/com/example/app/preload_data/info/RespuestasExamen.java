package com.example.app.preload_data.info;

import android.content.Context;

import com.example.app.db.models.RecursoModel;
import com.example.app.db.models.RespuestaExamenModel;
import com.example.app.db.utils.crud.Recurso;
import com.example.app.db.utils.crud.RespuestaExamen;

public class RespuestasExamen {
    private Context context;
    private RespuestaExamen crud;
    private Recurso crudRecurso;
    private RespuestaExamenModel[] respuestas;

    public RespuestasExamen(Context context) {
        this.context = context;
        this.crud = new RespuestaExamen(this.context);
        this.crudRecurso = new Recurso(this.context);

        run();
    }

    public void run() {
        System.out.println("Creando respuestas de examen...");
        crud.open();
        crudRecurso.open();

        String img = "[IMAGEN]";
        String p = "[Pendiente]";

        respuestas = new RespuestaExamenModel[] {
                new RespuestaExamenModel(1, 1, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(2, 1, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(3, 1, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(4, 1, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(5, 2, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(6, 2, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(7, 2, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(8, 2, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(9, 3, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(10, 3, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(11, 3, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(12, 3, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(13, 4, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(14, 4, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(15, 4, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(16, 4, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(17, 5, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(18, 5, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(19, 5, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(20, 5, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(21, 6, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(22, 6, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(23, 6, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(24, 6, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(25, 7, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(26, 7, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(27, 7, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(28, 7, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(29, 8, 0, "X, D, C", 3),
                new RespuestaExamenModel(30, 8, 0, "C, X, D", 2),
                new RespuestaExamenModel(31, 8, 0, "D, X, C", 1),
                new RespuestaExamenModel(32, 8, 0, "C, D, X", 0),

                new RespuestaExamenModel(33, 9, 0, "IIS", 3),
                new RespuestaExamenModel(34, 9, 0, "XIIIA", 2),
                new RespuestaExamenModel(35, 9, 0, "IIA", 1),
                new RespuestaExamenModel(36, 9, 0, "IIIW", 0),

                new RespuestaExamenModel(37, 10, 0, "187", 3),
                new RespuestaExamenModel(38, 10, 0, "176", 2),
                new RespuestaExamenModel(39, 10, 0, "121", 1),
                new RespuestaExamenModel(40, 10, 0, "198", 0),

                new RespuestaExamenModel(41, 11, 0, "128", 3),
                new RespuestaExamenModel(42, 11, 0, "144", 2),
                new RespuestaExamenModel(43, 11, 0, "126", 1),
                new RespuestaExamenModel(44, 11, 0, "127", 0),

                new RespuestaExamenModel(45, 12, 0, "B", 3),
                new RespuestaExamenModel(46, 12, 0, "b", 2),
                new RespuestaExamenModel(47, 12, 0, "S", 1),
                new RespuestaExamenModel(48, 12, 0, "c", 0),

                new RespuestaExamenModel(49, 13, 0, "13", 3),
                new RespuestaExamenModel(50, 13, 0, "15", 2),
                new RespuestaExamenModel(51, 13, 0, "14", 1),
                new RespuestaExamenModel(52, 13, 0, "17", 0),

                new RespuestaExamenModel(53, 14, 0, "62", 3),
                new RespuestaExamenModel(54, 14, 0, "63", 2),
                new RespuestaExamenModel(55, 14, 0, "61", 1),
                new RespuestaExamenModel(56, 14, 0, "59", 0),

                new RespuestaExamenModel(57, 15, 0, "4", 3),
                new RespuestaExamenModel(58, 15, 0, "2", 2),
                new RespuestaExamenModel(59, 15, 0, "0", 1),
                new RespuestaExamenModel(60, 15, 0, "8", 0),

                new RespuestaExamenModel(61, 16, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(62, 16, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(63, 16, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(64, 16, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(65, 17, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(66, 17, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(67, 17, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(68, 17, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(69, 18, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(70, 18, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(71, 18, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(72, 18, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(73, 19, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(74, 19, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(75, 19, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(76, 19, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(77, 20, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(78, 20, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(79, 20, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(80, 20, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(81, 21, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(82, 21, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(83, 21, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(84, 21, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(85, 22, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 3),
                new RespuestaExamenModel(86, 22, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 2),
                new RespuestaExamenModel(87, 22, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 1),
                new RespuestaExamenModel(88, 22, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), img, 0),

                new RespuestaExamenModel(89, 23, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), "Escuchar", 3),
                new RespuestaExamenModel(90, 23, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), "Ruido", 2),
                new RespuestaExamenModel(91, 23, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), "Percatarse", 1),
                new RespuestaExamenModel(92, 23, crudRecurso.getIdBy(RecursoModel.nombre, "cat"), "Oreja", 0),

                new RespuestaExamenModel(93, 24, 0, "Calzado", 3),
                new RespuestaExamenModel(94, 24, 0, "Pelota", 2),
                new RespuestaExamenModel(95, 24, 0, "Rebota", 1),
                new RespuestaExamenModel(96, 24, 0, "Voto", 0),

                new RespuestaExamenModel(97, 25, 0, "Cría", 3),
                new RespuestaExamenModel(98, 25, 0, "Vientre", 2),
                new RespuestaExamenModel(99, 25, 0, "Panza", 1),
                new RespuestaExamenModel(100, 25, 0, "Vertebrado", 0),

                new RespuestaExamenModel(101, 26, 0, "Políglota", 3),
                new RespuestaExamenModel(102, 26, 0, "Multilingüe", 2),
                new RespuestaExamenModel(103, 26, 0, "Septilingüe", 1),
                new RespuestaExamenModel(104, 26, 0, "Traductor", 0),

                new RespuestaExamenModel(105, 27, 0, "Resistencia", 3),
                new RespuestaExamenModel(106, 27, 0, "Oposición", 2),
                new RespuestaExamenModel(107, 27, 0, "Destreza", 1),
                new RespuestaExamenModel(108, 27, 0, "Apolomo", 0),

                new RespuestaExamenModel(109, 28, 0, "Phi", 3),
                new RespuestaExamenModel(110, 28, 0, "Cinco", 2),
                new RespuestaExamenModel(111, 28, 0, "Tau", 1),
                new RespuestaExamenModel(112, 28, 0, "Euler", 0),

                new RespuestaExamenModel(113, 29, 0, "16", 3),
                new RespuestaExamenModel(114, 29, 0, "32", 2),
                new RespuestaExamenModel(115, 29, 0, "24", 1),
                new RespuestaExamenModel(116, 29, 0, "60", 0),

                new RespuestaExamenModel(117, 30, 0, "Olas", 3),
                new RespuestaExamenModel(118, 30, 0, "Aire", 2),
                new RespuestaExamenModel(119, 30, 0, "Pasto", 1),
                new RespuestaExamenModel(120, 30, 0, "Ventilador", 0),

                new RespuestaExamenModel(121, 31, 0, p, 3),
                new RespuestaExamenModel(122, 31, 0, p, 2),
                new RespuestaExamenModel(123, 31, 0, p, 1),
                new RespuestaExamenModel(124, 31, 0, p, 0),

                new RespuestaExamenModel(125, 32, 0, "D, I, D, I, D, I, I, I, D, D, D, I", 3),
                new RespuestaExamenModel(126, 32, 0, "D, I, D, I, I, I, I, I, D, D, D, I", 2),
                new RespuestaExamenModel(127, 32, 0, "D, I, D, I, D, I, I, D, I, D, D, I", 1),
                new RespuestaExamenModel(128, 32, 0, "D, I, I, I, I, I, I, I, D, D, I, I", 0),

                new RespuestaExamenModel(129, 33, 0, p, 3),
                new RespuestaExamenModel(130, 33, 0, p, 2),
                new RespuestaExamenModel(131, 33, 0, p, 1),
                new RespuestaExamenModel(132, 33, 0, p, 0),

                new RespuestaExamenModel(133, 34, 0, p, 3),
                new RespuestaExamenModel(134, 34, 0, p, 2),
                new RespuestaExamenModel(135, 34, 0, p, 1),
                new RespuestaExamenModel(136, 34, 0, p, 0),

                new RespuestaExamenModel(137, 35, 0, p, 3),
                new RespuestaExamenModel(138, 35, 0, p, 2),
                new RespuestaExamenModel(139, 35, 0, p, 1),
                new RespuestaExamenModel(140, 35, 0, p, 0),

                new RespuestaExamenModel(141, 36, 0, p, 3),
                new RespuestaExamenModel(142, 36, 0, p, 2),
                new RespuestaExamenModel(143, 36, 0, p, 1),
                new RespuestaExamenModel(144, 36, 0, p, 0),

                new RespuestaExamenModel(145, 37, 0, p, 3),
                new RespuestaExamenModel(146, 37, 0, p, 2),
                new RespuestaExamenModel(147, 37, 0, p, 1),
                new RespuestaExamenModel(148, 37, 0, p, 0),

                new RespuestaExamenModel(149, 38, 0, p, 3),
                new RespuestaExamenModel(150, 38, 0, p, 2),
                new RespuestaExamenModel(151, 38, 0, p, 1),
                new RespuestaExamenModel(152, 38, 0, p, 0),

                new RespuestaExamenModel(153, 39, 0, p, 3),
                new RespuestaExamenModel(154, 39, 0, p, 2),
                new RespuestaExamenModel(155, 39, 0, p, 1),
                new RespuestaExamenModel(156, 39, 0, p, 0),

                new RespuestaExamenModel(157, 40, 0, p, 3),
                new RespuestaExamenModel(158, 40, 0, p, 2),
                new RespuestaExamenModel(159, 40, 0, p, 1),
                new RespuestaExamenModel(160, 40, 0, p, 0),

                new RespuestaExamenModel(161, 41, 0, p, 3),
                new RespuestaExamenModel(162, 41, 0, p, 2),
                new RespuestaExamenModel(163, 41, 0, p, 1),
                new RespuestaExamenModel(164, 41, 0, p, 0),

                new RespuestaExamenModel(165, 42, 0, p, 3),
                new RespuestaExamenModel(166, 42, 0, p, 2),
                new RespuestaExamenModel(167, 42, 0, p, 1),
                new RespuestaExamenModel(168, 42, 0, p, 0),

                new RespuestaExamenModel(169, 43, 0, p, 3),
                new RespuestaExamenModel(170, 43, 0, p, 2),
                new RespuestaExamenModel(171, 43, 0, p, 1),
                new RespuestaExamenModel(172, 43, 0, p, 0),

                new RespuestaExamenModel(173, 44, 0, p, 3),
                new RespuestaExamenModel(174, 44, 0, p, 2),
                new RespuestaExamenModel(175, 44, 0, p, 1),
                new RespuestaExamenModel(176, 44, 0, p, 0),

                new RespuestaExamenModel(177, 45, 0, p, 3),
                new RespuestaExamenModel(178, 45, 0, p, 2),
                new RespuestaExamenModel(179, 45, 0, p, 1),
                new RespuestaExamenModel(180, 45, 0, p, 0)
        };

        createRespuestas(respuestas);

        crud.close();
        crudRecurso.close();
        System.out.println("Respuestas de examen creadas.");
    }

    private void createRespuestas(RespuestaExamenModel[] respuestas) {
        RespuestaExamenModel[] respuestasExistentes = crud.readAll();
        if (respuestasExistentes.length > 0) {
            return;
        }

        for (RespuestaExamenModel respuesta : respuestas) {
            for (RespuestaExamenModel respuestaExistente : respuestasExistentes) {
                if (respuesta.equals(respuestaExistente)) {
                    break;
                }
            }

            System.out.println("Insertando respuesta de examen...");
            try {
                crud.insert(respuesta);
            } catch (Exception e) {
                System.out.println("Error al insertar respuesta de examen: " + e.getMessage());
            }
        }
    }
}