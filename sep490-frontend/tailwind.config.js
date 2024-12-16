/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#ff9901'
        },
        quartz: '#4a4a4a',
        balticSea: '#292929',
        veniceBlue: '#005b86',
        whiteSmoke: '#f5f6f9',
        azure: '#009dff'
      }
    },
  },
  plugins: [require('tailwindcss-primeui')]
}
