import React, { useEffect, useState } from 'react';
import axios from 'axios';

const PomodoroList = () => {
    const [pomodoros, setPomodoros] = useState([]);

    useEffect(() => {
        axios.get('/api/pomodoros')
            .then(response => {
                setPomodoros(response.data);
            })
            .catch(error => {
                console.error('Erro ao buscar Pomodoros:', error);
            });
    }, []);

    return (
        <div>
            <h1>Lista de Pomodoros</h1>
            <ul>
                {pomodoros.map(pomodoro => (
                    <li key={pomodoro.id}>
                        {pomodoro.categoria} - {pomodoro.duracaoSprint}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default PomodoroList;