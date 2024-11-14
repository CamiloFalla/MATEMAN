// src/components/Juego.jsx
import React, { useState } from 'react';
import Tablero from './Tablero';
import Puntaje from './Puntaje';

const Juego = () => {
  const [inGame, setInGame] = useState(false);

  return (
    <div>
      <h1>MateMan - Aprende Jugando</h1>
      {!inGame && (
        <button onClick={() => setInGame(true)}>
          Presiona aquí para Iniciar
        </button>
      )}
      {inGame && (
        <>
          <Puntaje />
          <Tablero />
        </>
      )}
    </div>
  );
};

export default Juego;
