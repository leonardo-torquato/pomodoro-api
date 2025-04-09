import { useState, useEffect, useRef } from "react";

const usePomodoroTimer = (duration, onComplete) => {
  const [timeLeft, setTimeLeft] = useState(duration);
  const [isRunning, setIsRunning] = useState(false);
  const timerRef = useRef(null);
  const hasCompleted = useRef(false); //ref para controlar a flag de estado de conclusão


  // Resetar o timer quando a duração mudar
  useEffect(() => {
    setTimeLeft(duration);
    hasCompleted.current = false;
  }, [duration]);

  useEffect(() => {
    if (isRunning) {
      timerRef.current = setInterval(() => {
        setTimeLeft((prev) => {
          if (prev <= 1 && !hasCompleted.current) {
            hasCompleted.current = true;
            clearInterval(timerRef.current);
            setIsRunning(false);
            onComplete?.();
            return 0;
          }
          return prev - 1;
        });
      }, 1000);
    }

    return () => {
      clearInterval(timerRef.current);
      hasCompleted.current = false;
    }
  }, [isRunning, onComplete]);

  return {
    timeLeft,
    isRunning,
    start: () => setIsRunning(true),
    pause: () => setIsRunning(false),
    reset: (newDuration) => {
      setIsRunning(false);
      setTimeLeft(newDuration);
    }
  };
};

export default usePomodoroTimer;  