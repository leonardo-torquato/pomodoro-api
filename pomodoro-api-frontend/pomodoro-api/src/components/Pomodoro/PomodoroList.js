import React, { useEffect, useState } from 'react';
import axios from 'axios';

const PomodoroList = ({ currentUser }) => {
    const [pomodoros, setPomodoros] = useState([]);

    useEffect(() => {
        if (currentUser) {
            axios.get(`/api/pomodoro/completed/${currentUser.id}`, { withCredentials: true })
                .then(response => {
                    setPomodoros(response.data);
                })
                .catch(error => {
                    console.error('Erro ao buscar Pomodoros:', error);
                });
        }
    }, [currentUser]);

    return (
        <div className="pomodoro-list">
            <h2>Meus Pomodoros Concluídos</h2>
            <ul>
                {pomodoros.map(pomodoro => (
                    <li key={pomodoro.id}>
                        <strong>{pomodoro.categoria || 'Sem categoria'}</strong> - 
                        {new Date(pomodoro.dataInicio).toLocaleDateString()} - 
                        {pomodoro.sprintsPorPomodoro} sprints
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default PomodoroList;