import React from 'react';
import ReactDOM from 'react-dom/client';
import './styles/global.css' // Asegúrate de importar tus estilos
import Game from './components/Game';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Game />
  </React.StrictMode>
);
