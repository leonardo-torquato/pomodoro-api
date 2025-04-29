import React, { useState, useEffect } from "react";
import usePomodoroTimer from "../../hooks/usePomodoroTimer";
import axios from 'axios';
import "./PomodoroTimer.css";

const PHASES = {
  idle: { name: 'Pronto para começar', duration: null },
  sprint: { name: 'Sprint' },
  short_break: { name: 'Pausa Curta' },
  long_break: { name: 'Pausa Longa' }
};

const PomodoroTimer = ({ currentUser }) => {

  // valores padrão do Pomodoro para place holder na aplicação.
  const [settings, setSettings] = useState({
    sprint: 25 * 60, // Convertendo minutos para segundos
    shortBreak: 5 * 60, // Convertendo minutos para segundos
    longBreak: 15 * 60, // Convertendo minutos para segundos
    sprintsPerCycle: 3
  });

  // Estado do ciclo
  const [phase, setPhase] = useState('idle');
  const [sprintCount, setSprintCount] = useState(0);
  const [category, setCategory] = useState('');
  const [pomodoroId, setPomodoroId] = useState(null);
  const [activeTab, setActiveTab] = useState('sprint');
  const [isEditing, setIsEditing] = useState(false);
  const [editValue, setEditValue] = useState('');

  // Calcula a duração atual baseada na fase
  const getPhaseDuration = () => {
    switch(phase) {
      case 'sprint': return settings.sprint;
      case 'short_break': return settings.shortBreak;
      case 'long_break': return settings.longBreak;
      default: return 0;
    }
  };

  // Hook do timer
  const { timeLeft, start, pause, reset } = usePomodoroTimer(
    getPhaseDuration(),
    handleCycleComplete
  );

  // Efeito para reiniciar e iniciar o timer quando a fase mudar
  useEffect(() => {
    if (phase !== 'idle' && timeLeft !== getPhaseDuration()) {
      reset(getPhaseDuration());
      start(); // Inicia automaticamente o novo timer
    }
  }, [phase]); // Executa sempre que a fase muda

function handleCycleComplete() {

  switch(phase) {
    case 'sprint':
      if(sprintCount < settings.sprintsPerCycle - 1) {
        setPhase('short_break');
      } else {
        setPhase('long_break');
      }
      break;

    case 'short_break':
      setSprintCount(prev => {
        const newCount = prev + 1;
        return newCount;
      });
      setPhase('sprint');   
      break;

    case 'long_break':
      completePomodoro();
      setPhase('idle');
      setSprintCount(0);
      break;
  }

} 

  const createPomodoro = async () => {
    try {
      const response = await axios.post('/api/pomodoro/create', {
        sprintDurationSec: settings.sprint,
        shortBreakDurationSec: settings.shortBreak,
        longBreakDurationSec: settings.longBreak,
        sprintsPerCycle: settings.sprintsPerCycle,
        category,
        usuarioId: currentUser?.id || null
      }, { withCredentials: true });
      
      setPomodoroId(response.data.id);
    } catch (error) {
      console.error('Erro ao criar pomodoro:', error);
    }
  };

  const completePomodoro = async () => {
    if(pomodoroId) {
      await axios.post(`/api/pomodoro/finish/${pomodoroId}`);
    }
  };

  const handleStart = () => {
    if(phase === 'idle') {
      createPomodoro();
      setPhase('sprint');
      setSprintCount(0);
    }
    start();
  };

  const handleReset = () => {
    setPhase('idle');
    setSprintCount(0);
    reset(settings.sprint);
  };

  const formatTime = (seconds) => {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;

    return `${String(mins).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
  };

  const handleTimerClick = () => {
    if (phase === 'idle') {
      setIsEditing(true);
      setEditValue(settings[activeTab] / 60);
    }
  };

  const handleEditChange = (e) => {
    const value = Math.max(1, Math.min(60, Number(e.target.value)));
    setEditValue(value);
  };

  const handleEditSubmit = () => {
    setSettings(prev => ({
      ...prev,
      [activeTab]: editValue * 60
    }));
    setIsEditing(false);
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleEditSubmit();
    }
  };

  return (
    <div className="container">
      <div className="timer-box">
        <h1>Pomodoro Timer</h1>
        
        <div className="tabs">
          <button 
            className={`tab ${activeTab === 'sprint' ? 'active' : ''}`}
            onClick={() => setActiveTab('sprint')}
          >
            Sprint
          </button>
          <button 
            className={`tab ${activeTab === 'shortBreak' ? 'active' : ''}`}
            onClick={() => setActiveTab('shortBreak')}
          >
            Pausa Curta
          </button>
          <button 
            className={`tab ${activeTab === 'longBreak' ? 'active' : ''}`}
            onClick={() => setActiveTab('longBreak')}
          >
            Pausa Longa
          </button>
        </div>

        <h2 className="phase-display">
          {PHASES[phase].name} ({sprintCount + 1}/{settings.sprintsPerCycle})
        </h2>

        <div 
          className={`time-display ${phase}`}
          onClick={handleTimerClick}
        >
          {isEditing ? (
            <input
              type="number"
              value={editValue}
              onChange={handleEditChange}
              onBlur={handleEditSubmit}
              onKeyPress={handleKeyPress}
              autoFocus
              min="1"
              max="60"
            />
          ) : (
            formatTime(timeLeft)
          )}
        </div>

        <div className="button-group">
          <button className="start-button" onClick={handleStart}>
            {phase === 'idle' ? 'Iniciar Ciclo' : 'Continuar'}
          </button>
          <button className="pause-button" onClick={pause}>
            Pausar
          </button>
          <button className="reset-button" onClick={handleReset}>
            Reiniciar
          </button>
        </div>

        <div className="sprints-input">
          <label>
            Sprints por Ciclo:
            <input
              type="number"
              value={settings.sprintsPerCycle}
              onChange={(e) => setSettings(prev => ({
                ...prev,
                sprintsPerCycle: Math.max(1, Math.min(10, Number(e.target.value)))
              }))}
              disabled={phase !== 'idle'}
              min="1"
              max="10"
            />
          </label>
        </div>

        <div className="category-input">
          <input
            type="text"
            placeholder="Categoria (opcional)"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            disabled={!currentUser || phase !== 'idle'}
          />
        </div>
      </div>
    </div>
  );
};

// Helper para formatar labels
const formatLabel = (key) => {
  const labels = {
    sprint: "Duração do Sprint (minutos)",
    shortBreak: "Pausa Curta (minutos)",
    longBreak: "Pausa Longa (minutos)",
    sprintsPerCycle: "Sprints por Ciclo"
  };
  return labels[key] || key;
};

export default PomodoroTimer;