package com.example.pomodoroApp.pomodoro_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pomodoros")
public class Pomodoro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    private Long id;

    private LocalTime duracaoSprint;
    private int SprintsPorPomodoro;
    private LocalTime duracaoPausaCurta;
    private LocalTime duracaoPausaLonga;
    private String categoria;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private boolean status;
}

