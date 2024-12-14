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
        }
      }
    },
  },
  plugins: [require('tailwindcss-primeui')]
}
