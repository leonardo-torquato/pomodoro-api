import React, { useState } from "react";
import usePomodoroTimer from "./usePomodoroTimer";
import axios from 'axios';
import "./PomodoroTimer.css";

// Adicione currentUser como prop na definição do componente
const PomodoroTimer = ({ currentUser }) => {
  // Estados para as configurações do Pomodoro
  const [sprintDuration, setSprintDuration] = useState(25 * 60); // 25 min
  const [shortBreakDuration, setShortBreakDuration] = useState(5 * 60); // 5 min
  const [longBreakDuration, setLongBreakDuration] = useState(15 * 60); // 15 min
  const [sprintsPerPomodoro, setSprintsPerPomodoro] = useState(4);
  const [sprintCount, setSprintCount] = useState(0);
  const [category, setCategory] = useState('');

  const { timeLeft, start, pause, reset } = usePomodoroTimer(sprintDuration, false, handleComplete);

  function handleComplete() {
    if (sprintCount < sprintsPerPomodoro - 1) {
      alert("Tempo de pausa curta!");
      reset(shortBreakDuration);
      setSprintCount((prev) => prev + 1);
    } else {
      alert("Pausa longa iniciada!");
      reset(longBreakDuration);
      setSprintCount(0);
    }
  }

  const formatTime = (seconds) => {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:00`;
  };

  const savePomodoro = async () => {
    try {
      await axios.post('/api/pomodoro/start', {
        duracaoSprint: formatTime(sprintDuration),
        sprintsPorPomodoro: sprintsPerPomodoro,
        duracaoPausaCurta: formatTime(shortBreakDuration),
        duracaoPausaLonga: formatTime(longBreakDuration),
        categoria: category,
        usuarioId: currentUser?.id || null
      }, { withCredentials: true });
    } catch (error) {
      console.error('Erro ao salvar pomodoro:', error);
    }
  };

  const handleStart = () => {
    reset(sprintDuration);
    start();
    savePomodoro();
  };

  return (
    <div className="container">
      <div className="timer-box">
        <h1>Pomodoro Timer</h1>
        <h2 className="time-display">
          {Math.floor(timeLeft / 60)}:{String(timeLeft % 60).padStart(2, "0")}
        </h2>

        <div className="button-group">
          <button className="start-button" onClick={handleStart}>Iniciar</button>
          <button className="pause-button" onClick={pause}>Pausar</button>
          <button className="reset-button" onClick={() => reset(sprintDuration)}>Reiniciar</button>
        </div>

        <div className="settings">
          <label>
            Duração do Sprint (min): 
            <input
              type="number"
              value={sprintDuration / 60}
              onChange={(e) => setSprintDuration(e.target.value * 60)}
            />
          </label>

          <label>
            Duração da Pausa Curta (min): 
            <input
              type="number"
              value={shortBreakDuration / 60}
              onChange={(e) => setShortBreakDuration(e.target.value * 60)}
            />
          </label>

          <label>
            Duração da Pausa Longa (min): 
            <input
              type="number"
              value={longBreakDuration / 60}
              onChange={(e) => setLongBreakDuration(e.target.value * 60)}
            />
          </label>

          <label>
            Sprints por Pomodoro:
            <input
              type="number"
              value={sprintsPerPomodoro}
              onChange={(e) => setSprintsPerPomodoro(parseInt(e.target.value))}
            />
          </label>
        </div>

        <div className="category-input">
          <input
            type="text"
            placeholder="Categoria (opcional)"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            disabled={!currentUser} // Desabilita se não estiver logado
          />
        </div>
      </div>
    </div>
  );
};

export default PomodoroTimer;