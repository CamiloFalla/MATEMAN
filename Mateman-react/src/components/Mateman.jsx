// src/components/Mateman.jsx
import React from 'react';

const Mateman = ({ position, direction }) => {
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
      className="absolute"
      style={{
        left: position.x,
        top: position.y,
        width: 24,
        height: 24,
      }}
    >
      <img src={getMatemanImage()} alt="Mateman" className="w-full h-full" />
    </div>
  );
};

export default Mateman;
