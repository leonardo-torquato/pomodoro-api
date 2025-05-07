import React, { useState, useEffect, useRef } from "react";
import usePomodoroTimer from "../../hooks/usePomodoroTimer";
import axios from 'axios';
import "./PomodoroTimer.css";
import tickingSound from '../../assets/clock-ticking.mp3';
import alarmSound from '../../assets/clock-alarm.mp3';

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
  const [category, setCategory] = useState('');
  const [isPaused, setIsPaused] = useState(true);
  const [activeTab, setActiveTab] = useState('sprint');
  const [editValue, setEditValue] = useState('');
  const [isEditing, setIsEditing] = useState(false);
  const [hasStarted, setHasStarted] = useState(false);
  const [pomodoroId, setPomodoroId] = useState(null);
  const [sprintCount, setSprintCount] = useState(0);
  

  const tickingAudio = useRef(new Audio(tickingSound));
  const alarmAudio = useRef(new Audio(alarmSound));

  // Atualiza a aba ativa quando a fase muda
  useEffect(() => {
    if (phase === 'sprint') setActiveTab('sprint');
    else if (phase === 'short_break') setActiveTab('shortBreak');
    else if (phase === 'long_break') setActiveTab('longBreak');
  }, [phase]);

  // Atualiza o tempo quando a aba muda em estado idle
  useEffect(() => {
    if (phase === 'idle') {
      const duration = activeTab === 'sprint' ? settings.sprint :
                      activeTab === 'shortBreak' ? settings.shortBreak :
                      settings.longBreak;
      reset(duration);
    }
  }, [activeTab, phase]);

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
    phase === 'idle' ? settings.sprint : getPhaseDuration(),
    handleCycleComplete
  );

  // Efeito para reiniciar e iniciar o timer quando a fase mudar
  useEffect(() => {
    if (phase !== 'idle') {
      reset(getPhaseDuration());
      start(); // Inicia automaticamente o novo timer
    } else {
      reset(settings.sprint); // Reseta para o tempo de sprint quando em idle
    }
  }, [phase]); // Executa sempre que a fase muda

  // Efeito para tocar o alarme quando o tempo acabar
  useEffect(() => {
    if (timeLeft === 0 && phase !== 'idle' && !isPaused && hasStarted) {
      alarmAudio.current.play().catch(error => console.log('Erro ao reproduzir alarme:', error));
    }
  }, [timeLeft, phase, isPaused, hasStarted]);

  // Efeito para controlar o som do tique-taque
  useEffect(() => {
    if (phase !== 'idle' && !isPaused) {
      tickingAudio.current.loop = true;
      tickingAudio.current.play().catch(error => console.log('Erro ao reproduzir som:', error));
      setHasStarted(true);
    } else {
      tickingAudio.current.pause();
      tickingAudio.current.currentTime = 0;
      if (phase === 'idle') {
        setHasStarted(false);
      }
    }

    return () => {
      tickingAudio.current.pause();
      tickingAudio.current.currentTime = 0;
    };
  }, [phase, isPaused]);

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
        setHasStarted(false);
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
      setIsPaused(false);
      start();
    } else if (isPaused) {
      setIsPaused(false);
      start();
    } else {
      setIsPaused(true);
      pause();
    }
  };

  const handleReset = () => {
    setPhase('idle');
    setSprintCount(0);
    setIsPaused(true);
    setHasStarted(false);
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
      setEditValue(Math.floor(settings[activeTab] / 60));
    }
  };

  const handleEditChange = (e) => {
    const value = e.target.value === '' ? '' : Math.max(1, Math.min(60, Number(e.target.value)));
    setEditValue(value);
  };

  const handleEditSubmit = () => {
    if (editValue === '') {
      setEditValue(Math.floor(settings[activeTab] / 60));
      return;
    }
    const newDuration = editValue * 60;
    setSettings(prev => ({
      ...prev,
      [activeTab]: newDuration
    }));
    reset(newDuration);
    setIsEditing(false);
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleEditSubmit();
    } else if (e.key === 'Escape') {
      setIsEditing(false);
      setEditValue(Math.floor(settings[activeTab] / 60));
    }
  };

  return (
    <div className="container">
      <div className="timer-box">
        <h1>Pomodoro Timer</h1>
        
        <div className="tabs">
          <button 
            className={`tab ${activeTab === 'sprint' ? 'active' : ''}`}
            onClick={() => phase === 'idle' && setActiveTab('sprint')}
            data-phase="sprint"
            disabled={phase !== 'idle'}
          >
            Sprint
          </button>
          <button 
            className={`tab ${activeTab === 'shortBreak' ? 'active' : ''}`}
            onClick={() => phase === 'idle' && setActiveTab('shortBreak')}
            data-phase="short_break"
            disabled={phase !== 'idle'}
          >
            Pausa Curta
          </button>
          <button 
            className={`tab ${activeTab === 'longBreak' ? 'active' : ''}`}
            onClick={() => phase === 'idle' && setActiveTab('longBreak')}
            data-phase="long_break"
            disabled={phase !== 'idle'}
          >
            Pausa Longa
          </button>
        </div>

        <div className="sprints-input">
          <h2 className="phase-display">
            {PHASES[phase].name} ({sprintCount + 1}/
            {phase === 'idle' ? (
              <input
                type="number"
                value={settings.sprintsPerCycle}
                onChange={(e) => setSettings(prev => ({
                  ...prev,
                  sprintsPerCycle: Math.max(1, Math.min(10, Number(e.target.value)))
                }))}
                min="1"
                max="10"
                className="sprints-input"
              />
            ) : (
              settings.sprintsPerCycle
            )})
          </h2>
        </div>

        <div 
          className={`time-display ${phase}`}
          onClick={handleTimerClick}
        >
          {isEditing ? (
            <div className="time-edit-container">
              <input
                type="number"
                value={editValue}
                onChange={handleEditChange}
                onBlur={handleEditSubmit}
                onKeyDown={handleKeyPress}
                autoFocus
                min="1"
                max="60"
                className="time-edit-input"
              />
              <span className="time-edit-suffix">:00</span>
            </div>
          ) : (
            formatTime(timeLeft)
          )}
        </div>

        <div className="button-group">
          <button className="control-button" onClick={handleStart}>
            {phase === 'idle' ? 'Iniciar Ciclo' : (isPaused ? 'Continuar' : 'Pausar')}
          </button>
          <button className="reset-button" onClick={handleReset}>
            Reiniciar
          </button>
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

export default PomodoroTimer;