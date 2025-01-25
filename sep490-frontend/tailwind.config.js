/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        red: {
          50: '#fef8f8',
          100: '#fbdfdf',
          200: '#f8c5c6',
          300: '#f5acad',
          400: '#f29293',
          500: '#ef797a',
          600: '#cb6768',
          700: '#a75555',
          800: '#834343',
          900: '#603031',
          950: '#3c1e1f'
        }
      }
    },
  },
  plugins: [require('tailwindcss-primeui')]
}
