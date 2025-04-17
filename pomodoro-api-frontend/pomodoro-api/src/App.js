import React, { useState } from 'react';
import PomodoroTimer from './components/Pomodoro/PomodoroTimer';
import PomodoroList from './components/Pomodoro/PomodoroList';
import AuthSection from './components/Auth/AuthSection.js';
import './styles/App.css';

function App() {
  const [currentUser, setCurrentUser] = useState(null);

  return (
    <div className="app-container">
      <AuthSection currentUser={currentUser} setCurrentUser={setCurrentUser} />
      <PomodoroTimer currentUser={currentUser} />
    </div>
  );
}

export default App;