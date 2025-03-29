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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime duracaoSprint;
    private int sprintsPorPomodoro;
    private LocalTime duracaoPausaCurta;
    private LocalTime duracaoPausaLonga;
    private String categoria;

    private LocalDate dataInicio = LocalDate.now();;
    private LocalDate dataFinal;

    private boolean status;
    private Long usuarioId; // Associa um usuário ao pomodoro (null se for anônimo)

    public boolean isFinalizado() {
        return dataFinal != null;
    }
}

