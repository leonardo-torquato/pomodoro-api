package com.example.pomodoroApp.pomodoro_api;

import com.example.pomodoroApp.pomodoro_api.model.Pomodoro;
import com.example.pomodoroApp.pomodoro_api.service.PomodoroTimerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class PomodoroTimerServiceTest {

    @Autowired
    private PomodoroTimerService pomodoroTimerService;

    @Test
    public void testPomodoroTimer() throws InterruptedException {
        // Configuração do Pomodoro
        Pomodoro pomodoro = new Pomodoro();
        pomodoro.setDuracaoSprint(LocalTime.of(0, 0, 10)); // Sprint de 10 segundos
        pomodoro.setDuracaoPausaCurta(LocalTime.of(0, 0, 5)); // Pausa curta de 5 segundos
        pomodoro.setDuracaoPausaLonga(LocalTime.of(0, 0, 8)); // Pausa longa de 8 segundos
        pomodoro.setSprintsPorPomodoro(2); // Dois sprints por pomodoro para o teste

        // Início do Pomodoro
        System.out.println("Iniciando o Pomodoro...");
        pomodoroTimerService.startPomodoro(pomodoro);

        // Espera por alguns segundos antes de pausar
        TimeUnit.SECONDS.sleep(6);
        pomodoroTimerService.pausePomodoro(); // Pausa o cronômetro
        System.out.println("Pomodoro pausado.");

        // Espera um pouco para simular o tempo em pausa
        TimeUnit.SECONDS.sleep(3);

        // Retoma o Pomodoro
        System.out.println("Retomando o Pomodoro...");
        pomodoroTimerService.resumePomodoro();

        // Espera até o Pomodoro finalizar
        TimeUnit.SECONDS.sleep(20); // Tempo adicional para ver o ciclo completo
        System.out.println("Fim do teste do Pomodoro.");
    }
}
