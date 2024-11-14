// src/components/Alien.jsx
import React from 'react';

const Alien = ({ position, type, isSleeping }) => {
  const alienImage = isSleeping ? '/images/sleep.gif' : `/images/${type}.png`;

  return (
    <div
      className="absolute"
      style={{
        left: position.x,
        top: position.y,
        width: 24,
        height: 24,
      }}
    >
      <img src={alienImage} alt="Alien" className="w-full h-full" />
    </div>
  );
};

export default Alien;
