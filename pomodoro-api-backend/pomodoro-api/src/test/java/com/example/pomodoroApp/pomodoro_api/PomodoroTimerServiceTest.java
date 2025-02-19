package com.example.pomodoroApp.pomodoro_api;

import com.example.pomodoroApp.pomodoro_api.model.Pomodoro;
import com.example.pomodoroApp.pomodoro_api.service.PomodoroTimerService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class PomodoroTimerServiceTest {

    @Autowired
    private PomodoroTimerService pomodoroTimerService;

    @Test
    public void testPomodoroTimer() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        pomodoroTimerService.setCompletionListener(v -> latch.countDown());

        Pomodoro pomodoro = new Pomodoro();
        pomodoro.setDuracaoSprint(LocalTime.of(0, 0, 10)); // Sprint de 10 segundos
        pomodoro.setDuracaoPausaCurta(LocalTime.of(0, 0, 5)); // Pausa curta de 5 segundos
        pomodoro.setDuracaoPausaLonga(LocalTime.of(0, 0, 8)); // Pausa longa de 8 segundos
        pomodoro.setSprintsPorPomodoro(2); // Dois sprints para o teste

        System.out.println("Iniciando o Pomodoro...");
        pomodoroTimerService.startPomodoro(pomodoro);

        if (!latch.await(50, TimeUnit.SECONDS)) {
            System.out.println("Teste de pomodoro finalizado antes do ciclo terminar.");
        } else {
            System.out.println("Pomodoro completo.");
        }
    }
}