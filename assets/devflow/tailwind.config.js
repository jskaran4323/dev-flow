/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{vue,js,ts}'],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        primary: '#00b894',
        surface: '#1e1e2f',
        card: '#2a2a40',
        text: '#dcdcdc',
      },
    },
  },
  plugins: [],
}
