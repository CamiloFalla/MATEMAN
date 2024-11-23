// src/components/Mateman.jsx
import React from 'react';
import { BLOCK_SIZE } from '../data/boardData';


const Mateman = ({ position, direction }) => {
  // Obtén la imagen según la dirección actual de Mateman
  const getMatemanImage = () => {
    switch (direction) {
      case 'left':
        return '/images/left.gif';
      case 'right':
        return '/images/right.gif';
      case 'up':
        return '/images/up.gif';
      case 'down':
      default:
        return '/images/down.gif';
    }
  };

  return (
    <div
      style={{
        position: 'absolute', // Relativo al tablero
        left: `${position.x}px`, // Posición horizontal (en píxeles)
        top: `${position.y}px`, // Posición vertical (en píxeles)
        width: `${BLOCK_SIZE}px`, // Tamaño de ancho basado en el tamaño del bloque
        height: `${BLOCK_SIZE}px`, // Tamaño de alto basado en el tamaño del bloque
        backgroundImage: `url(${getMatemanImage()})`, // Imagen dinámica según dirección
        backgroundSize: 'cover', // Ajusta la imagen al tamaño del bloque
        backgroundPosition: 'center', // Centra la imagen en el bloque
      }}
    />
  );
};

export default Mateman;
