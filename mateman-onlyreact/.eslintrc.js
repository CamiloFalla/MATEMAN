export default {
    extends: ['eslint:recommended', 'plugin:react/recommended'],
    settings: {
      react: {
        version: 'detect', // Detecta automáticamente la versión de React
      },
    },
    rules: {
      'react/react-in-jsx-scope': 'off', // Desactiva la regla que obliga a importar React
      'no-unused-vars': ['error', { varsIgnorePattern: '^React$' }], // Ignora React como variable sin uso
    },
  };
  