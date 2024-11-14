// components/Game.jsx
import React, { useState, useEffect } from 'react';
import GameBoard from './GameBoard';
import Mateman from './Mateman';
import Alien from './Alien';
import UI from './UI';
import Question from './Question';
import { BOARD_SIZE, BLOCK_SIZE, levelData } from '../data/boardData';

const initialMatemanPosition = { x: 7 * BLOCK_SIZE, y: 11 * BLOCK_SIZE };
const initialAliens = [
  { id: 1, position: { x: 4 * BLOCK_SIZE, y: 4 * BLOCK_SIZE }, direction: { x: 1, y: 0 }, type: 'alien1', isSleeping: false },
  { id: 2, position: { x: 10 * BLOCK_SIZE, y: 4 * BLOCK_SIZE }, direction: { x: -1, y: 0 }, type: 'alien2', isSleeping: false },
  // Agrega más aliens si lo deseas
];

const questions = [
  {
    text: '¿Cuál es el resultado de 9 + 9?',
    options: ['18', '19', '17'],
    correct: '18',
  },
  {
    text: '¿Cuál es el resultado de 27 - 18?',
    options: ['9', '8', '7'],
    correct: '9',
  },
  {
    text: '¿Cuál es el resultado de 2 x 5?',
    options: ['10', '15', '20'],
    correct: '10',
  },
  // Agrega más preguntas
];

const Game = () => {
  const [matemanPosition, setMatemanPosition] = useState(initialMatemanPosition);
  const [matemanDirection, setMatemanDirection] = useState('right');
  const [aliens, setAliens] = useState(initialAliens);
  const [score, setScore] = useState(0);
  const [lives, setLives] = useState(3);
  const [message, setMessage] = useState('');
  const [question, setQuestion] = useState(null);
  const [gameOver, setGameOver] = useState(false);
  const [dotsEaten, setDotsEaten] = useState(0);

  useEffect(() => {
    const handleKeyDown = (e) => {
      if (gameOver) return;
      switch (e.key) {
        case 'ArrowUp':
          setMatemanDirection('up');
          break;
        case 'ArrowDown':
          setMatemanDirection('down');
          break;
        case 'ArrowLeft':
          setMatemanDirection('left');
          break;
        case 'ArrowRight':
          setMatemanDirection('right');
          break;
        default:
          break;
      }
    };
    window.addEventListener('keydown', handleKeyDown);
    return () => window.removeEventListener('keydown', handleKeyDown);
  }, [gameOver]);

  useEffect(() => {
    if (gameOver) return;
    const interval = setInterval(() => {
      moveMateman();
      moveAliens();
      checkCollisions();
    }, 200);
    return () => clearInterval(interval);
  }, [matemanPosition, matemanDirection, aliens, gameOver]);

  const moveMateman = () => {
    let deltaX = 0;
    let deltaY = 0;
    switch (matemanDirection) {
      case 'up':
        deltaY = -BLOCK_SIZE;
        break;
      case 'down':
        deltaY = BLOCK_SIZE;
        break;
      case 'left':
        deltaX = -BLOCK_SIZE;
        break;
      case 'right':
        deltaX = BLOCK_SIZE;
        break;
      default:
        break;
    }
    const newPosition = {
      x: matemanPosition.x + deltaX,
      y: matemanPosition.y + deltaY,
    };
    if (canMoveTo(newPosition)) {
      setMatemanPosition(newPosition);
      eatDot(newPosition);
    }
  };

  const canMoveTo = (position) => {
    const x = position.x / BLOCK_SIZE;
    const y = position.y / BLOCK_SIZE;
    if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) {
      return false;
    }
    const index = x + y * BOARD_SIZE;
    const cell = levelData[index];
    return cell !== 0;
  };

  const eatDot = (position) => {
    const x = position.x / BLOCK_SIZE;
    const y = position.y / BLOCK_SIZE;
    const index = x + y * BOARD_SIZE;
    if (levelData[index] & 16) {
      levelData[index] &= ~16;
      setScore(score + 10);
      setDotsEaten(dotsEaten + 1);
      if (dotsEaten + 1 === totalDots()) {
        showQuestion();
      }
    }
  };

  const totalDots = () => {
    return levelData.filter((cell) => cell & 16).length;
  };

  const showQuestion = () => {
    const randomQuestion = questions[Math.floor(Math.random() * questions.length)];
    setQuestion(randomQuestion);
    const sleepingAliens = aliens.map((alien) => ({ ...alien, isSleeping: true }));
    setAliens(sleepingAliens);
  };

  const moveAliens = () => {
    const newAliens = aliens.map((alien) => {
      if (alien.isSleeping) return alien;
      const directions = [
        { x: -BLOCK_SIZE, y: 0 },
        { x: BLOCK_SIZE, y: 0 },
        { x: 0, y: -BLOCK_SIZE },
        { x: 0, y: BLOCK_SIZE },
      ];
      let { x: deltaX, y: deltaY } = alien.direction;
      const newPosition = {
        x: alien.position.x + deltaX,
        y: alien.position.y + deltaY,
      };
      if (canMoveTo(newPosition)) {
        return { ...alien, position: newPosition };
      } else {
        const randomDirection = directions[Math.floor(Math.random() * directions.length)];
        return { ...alien, direction: randomDirection };
      }
    });
    setAliens(newAliens);
  };

  const checkCollisions = () => {
    aliens.forEach((alien) => {
      if (alien.isSleeping) return;
      if (
        matemanPosition.x === alien.position.x &&
        matemanPosition.y === alien.position.y
      ) {
        setLives(lives - 1);
        setMessage('¡Te han atrapado!');
        if (lives - 1 <= 0) {
          setGameOver(true);
          setMessage('Juego terminado');
        } else {
          setMatemanPosition(initialMatemanPosition);
        }
      }
    });
  };

  const handleAnswer = (answer) => {
    if (answer === question.correct) {
      setScore(score + 100);
      setMessage('¡Correcto!');
    } else {
      setLives(lives - 1);
      setMessage('Respuesta incorrecta.');
      if (lives - 1 <= 0) {
        setGameOver(true);
        setMessage('Juego terminado');
      }
    }
    setQuestion(null);
    const awakeAliens = aliens.map((alien) => ({ ...alien, isSleeping: false }));
    setAliens(awakeAliens);
  };

  return (
    <div className="relative w-full h-full bg-black flex items-center justify-center">
      <div style={{ width: BOARD_SIZE * BLOCK_SIZE, height: BOARD_SIZE * BLOCK_SIZE }}>
        <GameBoard />
        <Mateman position={matemanPosition} direction={matemanDirection} />
        {aliens.map((alien) => (
          <Alien key={alien.id} position={alien.position} type={alien.type} isSleeping={alien.isSleeping} />
        ))}
        <UI score={score} lives={lives} message={message} />
        {question && <Question question={question} onAnswer={handleAnswer} />}
        {gameOver && (
          <div className="absolute inset-0 flex items-center justify-center bg-black bg-opacity-75">
            <div className="text-white text-3xl">Juego Terminado</div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Game;
