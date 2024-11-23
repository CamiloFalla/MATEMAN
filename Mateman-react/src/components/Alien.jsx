// src/components/Alien.jsx
import React from 'react';
import { BLOCK_SIZE } from '../data/boardData';

const Alien = ({ position, type }) => {
  console.log(`Renderizando Alien en (${position.x}, ${position.y})`); // Log para verificar

  const styles = {
    position: 'absolute',
    top: `${position.y}px`,
    left: `${position.x}px`,
    width: `${BLOCK_SIZE}px`,
    height: `${BLOCK_SIZE}px`,
    backgroundColor: type === 'alien1' ? 'red' : 'blue',
    borderRadius: '50%',
    transition: 'top 0.8s linear, left 0.8s linear', // Asegura que las transiciones sean visibles
  };

  return <div style={styles}></div>;
};

export default Alien;
