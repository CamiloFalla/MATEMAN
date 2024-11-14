// src/components/GameBoard.jsx
import React from 'react';
import { BOARD_SIZE, BLOCK_SIZE, levelData } from '../data/boardData';

const GameBoard = () => {
  const renderBoard = () => {
    return levelData.map((cell, index) => {
      const x = (index % BOARD_SIZE) * BLOCK_SIZE;
      const y = Math.floor(index / BOARD_SIZE) * BLOCK_SIZE;

      let cellContent = null;
      if (cell === 0) {
        cellContent = <div className="bg-blue-900 w-full h-full" />;
      } else if (cell & 16) {
        cellContent = <div className="bg-yellow-500 w-2 h-2 rounded-full" style={{ margin: 'auto', marginTop: '10px' }} />;
      }

      return (
        <div
          key={index}
          className="absolute"
          style={{ left: x, top: y, width: BLOCK_SIZE, height: BLOCK_SIZE }}
        >
          {cellContent}
        </div>
      );
    });
  };

  return (
    <div className="relative" style={{ width: BOARD_SIZE * BLOCK_SIZE, height: BOARD_SIZE * BLOCK_SIZE }}>
      {renderBoard()}
    </div>
  );
};

export default GameBoard;
