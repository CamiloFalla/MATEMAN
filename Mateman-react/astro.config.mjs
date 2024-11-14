// @ts-check
import { defineConfig } from 'astro/config';
import tailwind from '@astrojs/tailwind';
import react from '@astrojs/react';

// https://astro.build/config
export default defineConfig({
  integrations: [tailwind(), react()], // Configura la integración de Tailwind CSS
  site: 'https://tusitio.com', // URL de tu sitio si estás desplegándolo
  base: '/', // Ruta base para tu proyecto (útil si estás alojando el proyecto en una subcarpeta)
  markdown: {
    remarkPlugins: [],
    rehypePlugins: [],
  },
  output: 'static' // Configura si es una página estática
});
