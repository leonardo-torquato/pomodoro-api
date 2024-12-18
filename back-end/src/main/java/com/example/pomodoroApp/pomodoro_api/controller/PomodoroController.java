package com.example.pomodoroApp.pomodoro_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pomodoroApp.pomodoro_api.model.Pomodoro;
import com.example.pomodoroApp.pomodoro_api.service.PomodoroService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pomodoros")
public class PomodoroController {

    private final PomodoroService pomodoroService;

    @Autowired
    public PomodoroController(PomodoroService pomodoroService) {
        this.pomodoroService = pomodoroService;
    }

    @GetMapping
    public List<Pomodoro> getAllPomodoros() {
        return pomodoroService.getAllPomodoros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pomodoro> getPomodoroById(@PathVariable Long id) {
        return pomodoroService.getPomodoroById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pomodoro createPomodoro(@RequestBody Pomodoro pomodoro) {
        return pomodoroService.createPomodoro(pomodoro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pomodoro> updatePomodoro(@PathVariable Long id, @RequestBody Pomodoro pomodoro) {
        try {
            return ResponseEntity.ok(pomodoroService.updatePomodoro(id, pomodoro));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePomodoro(@PathVariable Long id) {
        pomodoroService.deletePomodoro(id);
        return ResponseEntity.noContent().build();
    }
}
