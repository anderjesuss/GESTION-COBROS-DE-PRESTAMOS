const sideMenu = document.querySelector('aside');
const menuBtn = document.getElementById('menu-btn');
const closeBtn = document.getElementById('close-btn');

const darkMode = document.querySelector('.dark-mode');
const darkModeIcon1 = darkMode.querySelector('span:nth-child(1)');
const darkModeIcon2 = darkMode.querySelector('span:nth-child(2)');

// Función para habilitar el modo oscuro
function enableDarkMode() {
  document.body.classList.add('dark-mode-variables');
  darkModeIcon1.classList.remove('active');
  darkModeIcon2.classList.add('active');
}

// Función para deshabilitar el modo oscuro
function disableDarkMode() {
  document.body.classList.remove('dark-mode-variables');
  darkModeIcon1.classList.add('active');
  darkModeIcon2.classList.remove('active');
}

menuBtn.addEventListener('click', () => {
  sideMenu.style.display = 'block';
});

closeBtn.addEventListener('click', () => {
  sideMenu.style.display = 'none';
});

darkMode.addEventListener('click', () => {
  const body = document.body;
  const isDarkMode = body.classList.contains('dark-mode-variables');

  if (isDarkMode) {
    disableDarkMode();
  } else {
    enableDarkMode();
  }

  // Guardar el estado del modo oscuro en el almacenamiento local
  localStorage.setItem('darkMode', isDarkMode ? 'disabled' : 'enabled');
});

// Comprobar el estado del modo oscuro al cargar la página
window.addEventListener('load', () => {
  const darkModeState = localStorage.getItem('darkMode');

  if (darkModeState === 'enabled') {
    enableDarkMode();
  } else {
    disableDarkMode();
  }
});
