import { useState, useEffect, useRef } from "react";

const usePomodoroTimer = (duration, onComplete) => {
  const [timeLeft, setTimeLeft] = useState(duration);
  const [isRunning, setIsRunning] = useState(false);
  const timerRef = useRef(null);

  // Resetar o timer quando a duração mudar
  useEffect(() => {
    setTimeLeft(duration);
  }, [duration]);

  useEffect(() => {
    if (isRunning) {
      timerRef.current = setInterval(() => {
        setTimeLeft((prev) => {
          if (prev <= 1) {
            clearInterval(timerRef.current);
            setIsRunning(false);
            onComplete?.();
            return 0;
          }
          return prev - 1;
        });
      }, 1000);
    }

    return () => clearInterval(timerRef.current);
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