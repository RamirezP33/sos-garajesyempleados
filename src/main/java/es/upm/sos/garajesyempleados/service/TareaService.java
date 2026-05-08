package es.upm.sos.garajesyempleados.service;

import es.upm.sos.garajesyempleados.model.EstadoTarea;
import es.upm.sos.garajesyempleados.model.TareaRevision;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TareaService {

    private final Map<Integer, TareaRevision> tareas = new ConcurrentHashMap<>();
    private final AtomicInteger secuencia = new AtomicInteger(0);

    public TareaRevision crearTareaRevision(Integer garajeId) {
        Integer taskId = secuencia.incrementAndGet();

        TareaRevision tarea = new TareaRevision(
                taskId,
                garajeId,
                EstadoTarea.PENDIENTE,
                "Tarea aceptada. Pendiente de ejecución.",
                null);

        tareas.put(taskId, tarea);
        return tarea;
    }

    public Optional<TareaRevision> buscarPorId(Integer taskId) {
        return Optional.ofNullable(tareas.get(taskId));
    }

    public void marcarEnProceso(Integer taskId) {
        TareaRevision tarea = tareas.get(taskId);
        if (tarea != null) {
            tarea.setEstado(EstadoTarea.EN_PROCESO);
            tarea.setMensaje("Revisión en proceso.");
        }
    }

    public void marcarCompletada(Integer taskId, String resultadoUri) {
        TareaRevision tarea = tareas.get(taskId);
        if (tarea != null) {
            tarea.setEstado(EstadoTarea.COMPLETADA);
            tarea.setMensaje("Revisión completada correctamente.");
            tarea.setResultadoUri(resultadoUri);
        }
    }

    public void marcarFallida(Integer taskId) {
        TareaRevision tarea = tareas.get(taskId);
        if (tarea != null) {
            tarea.setEstado(EstadoTarea.FALLIDA);
            tarea.setMensaje("La revisión ha fallado.");
        }
    }
}
