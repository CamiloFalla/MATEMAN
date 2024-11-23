// src/components/Mateman.jsx
import PropTypes from 'prop-types';
import { BLOCK_SIZE } from '../data/boardData';

const Mateman = ({ position, direction }) => {
  const matemanStyles = {
    position: 'absolute',
    left: position.x,
    top: position.y,
    width: BLOCK_SIZE,
    height: BLOCK_SIZE,
    backgroundImage: `url(/images/${direction}.gif)`, // Imagen dinámica según la dirección
    backgroundSize: 'cover',
  };

  return <div style={matemanStyles}></div>;
};

Mateman.propTypes = {
  position: PropTypes.shape({
    x: PropTypes.number.isRequired,
    y: PropTypes.number.isRequired,
  }).isRequired,
  direction: PropTypes.oneOf(['up', 'down', 'left', 'right']).isRequired,
};

export default Mateman;
