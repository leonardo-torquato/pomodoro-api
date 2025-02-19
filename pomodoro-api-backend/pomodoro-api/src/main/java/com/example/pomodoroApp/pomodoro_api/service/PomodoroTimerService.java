package com.example.pomodoroApp.pomodoro_api.service;

import com.example.pomodoroApp.pomodoro_api.model.Pomodoro;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Service
public class PomodoroTimerService {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean isRunning;
    private volatile boolean isPaused;

    @Setter
    private Consumer<Void> completionListener;

    public void startPomodoro(Pomodoro pomodoro) {
        isRunning = true;
        isPaused = false;
        executor.submit(() -> runPomodoroCycle(pomodoro));
    }
//    private void runPomodoroCycle(Pomodoro pomodoro) {
//        try {
//            completedSprints = 0;
//            while (isRunning && completedSprints < pomodoro.getSprintsPorPomodoro()) {
//                runSprint(pomodoro.getDuracaoSprint());
//                completedSprints++;
//
//                runPause(pomodoro.getDuracaoPausaCurta(), "Pausa curta");
//            }
//            runPause(pomodoro.getDuracaoPausaLonga(), "Pausa longa");
//            endPomodoroCycle(pomodoro);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }

    private void runPomodoroCycle(Pomodoro pomodoro) {
        try {
            int completedSprints = 0;
            while (isRunning && completedSprints < pomodoro.getSprintsPorPomodoro()) {
                runSprint(pomodoro.getDuracaoSprint());
                completedSprints++;
                runPause(pomodoro.getDuracaoPausaCurta(), "Pausa curta");
            }
            runPause(pomodoro.getDuracaoPausaLonga(), "Pausa longa");
            endPomodoroCycle(pomodoro);
            if (completionListener != null) {
                // Chama o callback para permitir testes que dependem de temporização
                completionListener.accept(null);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void runSprint(LocalTime duracaoSprint) throws InterruptedException {
        System.out.println("Início do sprint: duração de " + duracaoSprint.toString());
        startTimer(duracaoSprint);
    }

    private void runPause(LocalTime duracaoPausa, String tipoPausa) throws InterruptedException {
        System.out.println(tipoPausa + " iniciada: duração de " + duracaoPausa.toString());
        startTimer(duracaoPausa);
    }

    private void startTimer(LocalTime duration) throws InterruptedException {
        long remainingTimeInSeconds = duration.toSecondOfDay(); // Converte LocalTime para segundos
        while (remainingTimeInSeconds > 0 && isRunning) {
            if (!isPaused) {
                System.out.println("Tempo restante: " + remainingTimeInSeconds + " segundos.");
                TimeUnit.SECONDS.sleep(1); // Conta um segundo
                remainingTimeInSeconds--;
            } else {
                synchronized (this) {
                    while (isPaused) {
                        wait(); // Espera enquanto o cronômetro está pausado
                    }
                }
            }
        }
    }

    private void endPomodoroCycle(Pomodoro pomodoro) {
        isRunning = false;
        pomodoro.setStatus(false);
        pomodoro.setDataFinal(LocalDate.now());
        System.out.println("Pomodoro finalizado.");
    }



    public void pausePomodoro() {
        if (isRunning) {
            isPaused = true;
            System.out.println("Pomodoro pausado.");
        }
    }

    public synchronized void resumePomodoro() {
        if (isRunning && isPaused) {
            isPaused = false;
            System.out.println("Pomodoro retomado.");
            notifyAll(); // Retoma o cronômetro
        }
    }
}
