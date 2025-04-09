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
        // Validação do usuário
        if (pomodoro.getUsuarioId() != null && !userRepository.existsById(pomodoro.getUsuarioId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado!");     
        }
        
        // Define a data de início (não usa mais 'status')
        pomodoro.setStartDate(LocalDate.now());
        
        return pomodoroRepository.save(pomodoro);
    }

    public Optional<Pomodoro> update(Long id, Pomodoro novoPomodoro) {
        return pomodoroRepository.findById(id).map(pomodoroExistente -> {
            // Atualiza apenas campos editáveis
            pomodoroExistente.setSprintDurationSec(novoPomodoro.getSprintDurationSec());
            pomodoroExistente.setShortBreakDurationSec(novoPomodoro.getShortBreakDurationSec());
            pomodoroExistente.setLongBreakDurationSec(novoPomodoro.getLongBreakDurationSec());
            pomodoroExistente.setSprintsPerCycle(novoPomodoro.getSprintsPerCycle());
            pomodoroExistente.setCategory(novoPomodoro.getCategory());
            
            return pomodoroRepository.save(pomodoroExistente);
        });
    }

    public void complete(Long id) {
        pomodoroRepository.findById(id).ifPresent(pomodoro -> {
            pomodoro.setFinalDate(LocalDate.now()); // Marca a data de finalização
            pomodoroRepository.save(pomodoro);
        });
    }

    public List<Pomodoro> getCompletedPomodoros(Long userId) {
        return pomodoroRepository.findByUsuarioId(userId).stream()
                .filter(p -> p.getFinalDate() != null) // Substitui 'isFinalizado' por checagem direta
                .collect(Collectors.toList());
    }

    public List<Pomodoro> getAllCompleted() {
        return pomodoroRepository.findByFinalDateIsNotNull();
    }
}

