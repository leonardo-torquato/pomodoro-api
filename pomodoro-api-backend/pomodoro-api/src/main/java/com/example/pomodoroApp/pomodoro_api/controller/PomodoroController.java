package com.example.pomodoroApp.pomodoro_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pomodoroApp.pomodoro_api.model.Pomodoro;
import com.example.pomodoroApp.pomodoro_api.service.PomodoroService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pomodoro")
public class PomodoroController {

    private final PomodoroService pomodoroService;

    public PomodoroController(PomodoroService pomodoroService) {
        this.pomodoroService = pomodoroService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startPomodoro(@RequestBody Pomodoro pomodoro) {
        pomodoroService.save(pomodoro);
        return ResponseEntity.ok("Pomodoro iniciado!");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updatePomodoro(@RequestBody Pomodoro pomodoro) {
        pomodoroService.update(pomodoro);
        return ResponseEntity.ok("Pomodoro atualizado!");
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completePomodoro(@RequestBody Pomodoro pomodoro) {
        pomodoroService.complete(pomodoro);
        return ResponseEntity.ok("Pomodoro finalizado!");
    }
}

