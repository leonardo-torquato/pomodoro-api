package com.example.pomodoroApp.pomodoro_api.service;

import com.example.pomodoroApp.pomodoro_api.model.Pomodoro;
import com.example.pomodoroApp.pomodoro_api.repository.PomodoroRepository;
import com.example.pomodoroApp.pomodoro_api.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class PomodoroService {

    private final PomodoroRepository pomodoroRepository;
    private final UserRepository userRepository;

    public Pomodoro save(Pomodoro pomodoro) {

        if (pomodoro.getUsuarioId() != null && !userRepository.existsById(pomodoro.getUsuarioId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado!");     
        }
        
        pomodoro.setDataInicio(LocalDate.now()); // Define a data de início
        pomodoro.setStatus(true);
        
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

