package com.example.pomodoroApp.pomodoro_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pomodoros")
public class Pomodoro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Durações em segundos (exemplo: 25 minutos = 1500 segundos)
    private int sprintDurationSec; 
    private int shortBreakDurationSec;
    private int longBreakDurationSec;
    
    private int sprintsPerCycle; 
    
    private LocalDate startDate;
    private LocalDate finalDate; // Preenchido apenas ao finalizar

    private String category;
    private Long usuarioId; // Null se for anônimo
}