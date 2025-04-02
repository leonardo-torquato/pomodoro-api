import React, { useState } from 'react';
import PomodoroTimer from './PomodoroTimer';
import PomodoroList from './PomodoroList';
import AuthSection from './Auth/AuthSection.js';
import './App.css';

function App() {
  const [currentUser, setCurrentUser] = useState(null);

  return (
    <div className="app-container">
      <AuthSection currentUser={currentUser} setCurrentUser={setCurrentUser} />
      <PomodoroTimer currentUser={currentUser} />
      {currentUser && <PomodoroList currentUser={currentUser} />}
    </div>
  );
}

export default App;