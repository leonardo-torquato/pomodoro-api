package com.example.pomodoroApp.pomodoro_api.service;

import com.example.pomodoroApp.pomodoro_api.model.Pomodoro;
import com.example.pomodoroApp.pomodoro_api.repository.PomodoroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PomodoroService {

    private final PomodoroRepository pomodoroRepository;

    @Autowired
    public PomodoroService(PomodoroRepository pomodoroRepository) {
        this.pomodoroRepository = pomodoroRepository;
    }

    public List<Pomodoro> getAllPomodoros() {
        return pomodoroRepository.findAll();
    }

    public Optional<Pomodoro> getPomodoroById(Long id) {
        return pomodoroRepository.findById(id);
    }

    public Pomodoro createPomodoro(Pomodoro pomodoro) {
        return pomodoroRepository.save(pomodoro);
    }

    public Pomodoro updatePomodoro(Long id, Pomodoro updatedPomodoro) {
        return pomodoroRepository.findById(id)
                .map(pomodoro -> {
                    pomodoro.setSprintsPorPomodoro(updatedPomodoro.getSprintsPorPomodoro());
                    pomodoro.setDuracaoSprint(updatedPomodoro.getDuracaoSprint());
                    pomodoro.setDuracaoPausaCurta(updatedPomodoro.getDuracaoPausaCurta());
                    pomodoro.setDuracaoPausaCurta(updatedPomodoro.getDuracaoPausaLonga());
                    pomodoro.setCategoria(updatedPomodoro.getCategoria());
                    pomodoro.setDataInicio(updatedPomodoro.getDataInicio());
                    pomodoro.setDataFinal(updatedPomodoro.getDataFinal());
                    pomodoro.setStatus(updatedPomodoro.isStatus());
                    return pomodoroRepository.save(pomodoro);
                })
                .orElseThrow(() -> new RuntimeException("Pomodoro not found"));
    }

    public void deletePomodoro(Long id) {
        pomodoroRepository.deleteById(id);
    }
}
