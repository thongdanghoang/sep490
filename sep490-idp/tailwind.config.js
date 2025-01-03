/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["src/main/resources/templates/**/*.{html,js}"],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: "#1DCBD7",
          dark: '#16A9B2',
        },
      },
      fontFamily: {
        inter: ['Inter', 'sans-serif'], // Add 'Inter' font family
      }
    },

  },
  plugins: [],
};
