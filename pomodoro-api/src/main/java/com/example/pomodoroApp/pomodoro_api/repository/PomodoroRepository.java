package com.example.pomodoroApp.pomodoro_api.repository;

import com.example.pomodoroApp.pomodoro_api.model.Pomodoro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PomodoroRepository extends JpaRepository<Pomodoro, Long> {
}
