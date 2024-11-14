// src/components/Puntaje.jsx
import React from 'react';

const Puntaje = ({ puntaje, vidas }) => {
  return (
    <div className="puntaje">
      <h2>Puntaje: {puntaje}</h2>
      <p>Vidas: {vidas}</p>
    </div>
  );
};

export default Puntaje;
