// src/components/Alien.jsx
import PropTypes from 'prop-types';
import { BLOCK_SIZE } from '../data/boardData';

const Alien = ({ position, type, isSleeping }) => {
  const alienImage = isSleeping ? '/images/sleep.gif' : `/images/${type}.png`;

  return (
    <div
      className="absolute"
      style={{
        left: position.x,
        top: position.y,
        width: BLOCK_SIZE,
        height: BLOCK_SIZE,
        backgroundImage: `url(${alienImage})`,
        backgroundSize: 'cover',
      }}
    ></div>
  );
};

Alien.propTypes = {
  position: PropTypes.shape({
    x: PropTypes.number.isRequired,
    y: PropTypes.number.isRequired,
  }).isRequired,
  type: PropTypes.string.isRequired,
  isSleeping: PropTypes.bool.isRequired,
};

export default Alien;

