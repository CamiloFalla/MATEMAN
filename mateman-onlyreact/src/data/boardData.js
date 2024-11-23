// src/data/boardData.js

export const BOARD_SIZE = 15;
export const BLOCK_SIZE = 24;

// Nomenclatura de tablero:
// 0 = bloque no transitable
// 1 = borde izquierdo
// 2 = borde superior
// 4 = borde derecho
// 8 = borde inferior
// 16 = punto (comida)

export const levelData = [
  19,26,26,26,26,26,18,26,18,26,26,26,26,26,22,
  21,0,0,0,0,0,21,0,21,0,0,0,0,0,21,
  17,18,26,18,26,18,20,0,17,18,26,18,26,18,20,
  17,20,0,21,0,17,20,0,17,20,0,21,0,17,20,
  17,20,0,21,0,25,20,0,17,28,0,21,0,17,20,
  17,20,0,21,0,0,17,18,20,0,0,21,0,17,20,
  17,20,0,17,18,26,16,32,32,26,18,20,0,17,20,
  17,32,18,32,20,0,17,32,20,0,17,32,18,32,20,
  17,24,32,32,20,0,17,32,20,0,17,32,32,24,20,
  21,0,17,32,20,0,17,24,20,0,17,32,20,0,21,
  21,0,17,24,20,0,21,0,21,0,17,24,20,0,21,
  21,0,21,0,17,18,20,0,17,18,20,0,21,0,21,
  21,0,21,0,25,24,32,18,32,24,28,0,21,0,21,
  17,18,20,0,0,0,17,32,20,0,0,0,17,18,20,
  25,24,24,26,26,26,24,24,24,26,26,26,24,24,28
];



