package com.example.pomodoroApp.pomodoro_api.service;

import com.example.pomodoroApp.pomodoro_api.model.Pomodoro;
import com.example.pomodoroApp.pomodoro_api.repository.PomodoroRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class PomodoroService {

    private final PomodoroRepository pomodoroRepository;

    public PomodoroService(PomodoroRepository pomodoroRepository) {
        this.pomodoroRepository = pomodoroRepository;
    }

    public Pomodoro save(Pomodoro pomodoro) {
        pomodoro.setStatus(true); // Define como ativo ao iniciar
        return pomodoroRepository.save(pomodoro);
    }

    public Optional<Pomodoro> update(Long id, Pomodoro novoPomodoro) {
        return pomodoroRepository.findById(id).map(pomodoroExistente -> {
            pomodoroExistente.setDuracaoSprint(novoPomodoro.getDuracaoSprint());
            pomodoroExistente.setSprintsPorPomodoro(novoPomodoro.getSprintsPorPomodoro());
            pomodoroExistente.setDuracaoPausaCurta(novoPomodoro.getDuracaoPausaCurta());
            pomodoroExistente.setDuracaoPausaLonga(novoPomodoro.getDuracaoPausaLonga());
            pomodoroExistente.setCategoria(novoPomodoro.getCategoria());
            return pomodoroRepository.save(pomodoroExistente);
        });
    }

    public void complete(Long id) {
        pomodoroRepository.findById(id).ifPresent(pomodoro -> {
            pomodoro.setStatus(false);
            pomodoro.setDataFinal(LocalDate.now()); // Marca o tempo de finalização
            pomodoroRepository.save(pomodoro);
        });
    }

    public List<Pomodoro> getCompletedPomodoros(Long usuarioId) {
        return pomodoroRepository.findByUsuarioId(usuarioId).stream()
                .filter(Pomodoro::isFinalizado)
                .collect(Collectors.toList());
    }
}

