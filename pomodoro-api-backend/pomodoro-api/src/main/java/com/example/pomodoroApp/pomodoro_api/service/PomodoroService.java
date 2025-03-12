package com.example.pomodoroApp.pomodoro_api.service;

import com.example.pomodoroApp.pomodoro_api.model.Pomodoro;
import com.example.pomodoroApp.pomodoro_api.repository.PomodoroRepository;

import org.springframework.stereotype.Service;

@Service
public class PomodoroService {

    private final PomodoroRepository pomodoroRepository;

    public PomodoroService(PomodoroRepository pomodoroRepository) {
        this.pomodoroRepository = pomodoroRepository;
    }

    public void save(Pomodoro pomodoro) {
        pomodoro.setStatus(true);
        pomodoroRepository.save(pomodoro);
    }

    public void update(Pomodoro pomodoro) {
        pomodoroRepository.save(pomodoro);
    }

    public void complete(Pomodoro pomodoro) {
        pomodoro.setStatus(false);
        pomodoroRepository.save(pomodoro);
    }
}
