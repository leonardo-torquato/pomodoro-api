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

    // Endpoint renomeado para "/create" (mais descritivo)
    @PostMapping("/create")
    public ResponseEntity<Pomodoro> createPomodoro(@RequestBody Pomodoro pomodoro) {
        Pomodoro savedPomodoro = pomodoroService.save(pomodoro);
        return ResponseEntity.ok(savedPomodoro);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Pomodoro> updatePomodoro(
            @PathVariable Long id, 
            @RequestBody Pomodoro pomodoro
    ) {
        return pomodoroService.update(id, pomodoro)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint renomeado para "/finish/{id}" (semântica mais clara)
    @PostMapping("/finish/{id}")
    public ResponseEntity<String> finishPomodoro(@PathVariable Long id) {
        pomodoroService.complete(id);
        return ResponseEntity.ok("Pomodoro finalizado com sucesso!");
    }

    // Endpoint ajustado para usar query param (melhor prática para filtros)
    @GetMapping("/completed")
    public ResponseEntity<List<Pomodoro>> getCompletedPomodoros(
            @RequestParam(required = false) Long userId // Filtro opcional
    ) {
        List<Pomodoro> completedPomodoros = 
            (userId != null) 
                ? pomodoroService.getCompletedPomodoros(userId) 
                : pomodoroService.getAllCompleted(userId);
        return ResponseEntity.ok(completedPomodoros);
    }
}