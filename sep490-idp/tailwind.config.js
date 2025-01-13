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
      },
      keyframes: {
        fadeIn: {
          '0%': { opacity: '0', transform: 'translateY(-10px)' },
          '100%': { opacity: '1', transform: 'translateY(0)' },
        },
      },
      animation: {
         fadeIn: 'fadeIn 0.6s ease-out',
      },
    },

  },
  plugins: [],
};
