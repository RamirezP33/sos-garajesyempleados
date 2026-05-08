package es.upm.sos.garajesyempleados.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RevisionAsyncService {

    private final TareaService tareaService;

    @Async
    public void ejecutarRevision(Integer taskId, Integer garajeId) {
        try {
            tareaService.marcarEnProceso(taskId);

            // Simulación de tarea larga
            Thread.sleep(20000);

            tareaService.marcarCompletada(taskId, "/api/v1/garajes/" + garajeId);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            tareaService.marcarFallida(taskId);
        } catch (Exception e) {
            tareaService.marcarFallida(taskId);
        }
    }
}