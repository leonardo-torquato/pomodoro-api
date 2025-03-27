package com.example.pomodoroApp.pomodoro_api.controller;

import com.example.pomodoroApp.pomodoro_api.model.Pomodoro;
import com.example.pomodoroApp.pomodoro_api.service.PomodoroService;

import java.util.List;

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

    @PutMapping("/update/{id}")
    public ResponseEntity<Pomodoro> updatePomodoro(@PathVariable Long id, @RequestBody Pomodoro pomodoro) {
        return pomodoroService.update(id, pomodoro)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/complete/{id}")
    public ResponseEntity<String> completePomodoro(@PathVariable Long id) {
        pomodoroService.complete(id);
        return ResponseEntity.ok("Pomodoro finalizado!");
    }

    @GetMapping("/completed/{userId}")
    public ResponseEntity<List<Pomodoro>> getCompletedPomodoros(@PathVariable Long userId) {
        List<Pomodoro> completedPomodoros = pomodoroService.getCompletedPomodoros(userId);
        return ResponseEntity.ok(completedPomodoros);
    }
}

