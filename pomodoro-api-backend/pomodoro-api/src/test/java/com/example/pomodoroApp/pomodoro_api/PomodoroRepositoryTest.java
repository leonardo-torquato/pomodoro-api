package com.example.pomodoroApp.pomodoro_api;

import com.example.pomodoroApp.pomodoro_api.model.Pomodoro;
import com.example.pomodoroApp.pomodoro_api.repository.PomodoroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
//import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Usa o banco de dados configurado
class PomodoroRepositoryTest {

    @Autowired
    private PomodoroRepository pomodoroRepository;

    @Test
    @Rollback(false) // Mantém as alterações no banco de dados para verificar a persistência real
    public void testCreatePomodoro() {
        // Criando um novo objeto Pomodoro para teste
        Pomodoro pomodoro = new Pomodoro();
        //pomodoro.setDuracao(LocalTime.of(0, 25));
        pomodoro.setCategory("Trabalho");
        pomodoro.setFinalDate(LocalDate.now());
    

        // Salvando o Pomodoro no banco de dados
        Pomodoro savedPomodoro = pomodoroRepository.save(pomodoro);

        // Verificando se o Pomodoro foi salvo corretamente
        assertThat(savedPomodoro).isNotNull();
        assertThat(savedPomodoro.getId()).isGreaterThan(0);
    }

    @Test
    public void testTableCreation() {
        // Checa se a tabela 'pomodoro' está acessível ao buscar todos os registros
        Iterable<Pomodoro> pomodoros = pomodoroRepository.findAll();
        assertThat(pomodoros).isNotNull(); // A tabela existe e pode ser acessada
    }
}
