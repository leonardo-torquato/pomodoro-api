import { useState, useEffect, useRef } from "react";

const usePomodoroTimer = (initialTime, autoStart = false, onComplete) => {
  const [timeLeft, setTimeLeft] = useState(initialTime);
  const [isRunning, setIsRunning] = useState(autoStart);
  const timerRef = useRef(null);

  useEffect(() => {
    if (isRunning) {
      timerRef.current = setInterval(() => {
        setTimeLeft((prevTime) => {
          if (prevTime <= 1) {
            clearInterval(timerRef.current);
            setIsRunning(false);
            if (onComplete) onComplete(); // Chama a função ao terminar
            return 0;
          }
          return prevTime - 1;
        });
      }, 1000);
    } else {
      clearInterval(timerRef.current);
    }

    return () => clearInterval(timerRef.current);
  }, [isRunning, onComplete]);

  const start = () => setIsRunning(true);
  const pause = () => setIsRunning(false);
  const reset = (newTime) => {
    setIsRunning(false);
    setTimeLeft(newTime);
  };

  return { timeLeft, isRunning, start, pause, reset };
};

export default usePomodoroTimer;
