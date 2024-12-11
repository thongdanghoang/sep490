/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["src/main/resources/templates/**/*.{html,js}"],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: "#01493a",
        },
      },
    },
  },
  plugins: [],
};
