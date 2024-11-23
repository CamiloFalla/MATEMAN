// src/components/GameBoard.jsx
import PropTypes from 'prop-types';
import { BOARD_SIZE, BLOCK_SIZE } from '../data/boardData';

const GameBoard = ({ boardState, foodState }) => {
  const renderBoard = () => {
    return boardState.map((cell, index) => {
      const x = (index % BOARD_SIZE) * BLOCK_SIZE;
      const y = Math.floor(index / BOARD_SIZE) * BLOCK_SIZE;

      let cellContent = null;

      // Si cell === 0, es un muro
      if (cell === 0) {
        cellContent = <div className="bg-blue-900 w-full h-full" />;
      } else {
        // Si hay comida en esta posición
        if (foodState[index]) {
          cellContent = (
            <div
              className="bg-yellow-500 w-2 h-2 rounded-full"
              style={{ margin: 'auto', marginTop: '10px' }}
            />
          );
        }
        // Si no hay comida, dejamos la celda vacía (camino)
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
    <div
      className="relative"
      style={{ width: BOARD_SIZE * BLOCK_SIZE, height: BOARD_SIZE * BLOCK_SIZE }}
    >
      {renderBoard()}
    </div>
  );
};

GameBoard.propTypes = {
  boardState: PropTypes.array.isRequired,
  foodState: PropTypes.array.isRequired,
};

export default GameBoard;


