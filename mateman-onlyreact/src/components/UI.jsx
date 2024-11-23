// src/components/UI.jsx
import React from 'react';

const UI = ({ score, lives, message }) => {
  return (
    <div className="absolute top-0 left-0 p-4 text-white z-10">
      <div className="text-xl">Puntaje: {score}</div>
      <div className="text-xl flex items-center">
        Vidas:
        {Array.from({ length: lives }).map((_, index) => (
          <img key={index} src="/images/mateman.png" alt="Vida" className="w-6 h-6 ml-2" />
        ))}
      </div>
      {message && <div className="mt-2 text-lg">{message}</div>}
    </div>
  );
};

export default UI;
