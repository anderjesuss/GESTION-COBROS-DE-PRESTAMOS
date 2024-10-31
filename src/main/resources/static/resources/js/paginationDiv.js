const customButtons = document.querySelectorAll('.sales.custom-button');
const rowsPerPage = 6; // Número de filas por página
let currentPage = 1;

// Función para mostrar las filas correspondientes a la página actual
function showPage(page) {
	
    customButtons.forEach((button, index) => {
		
        if (index >= (page - 1) * rowsPerPage && index < page * rowsPerPage) {
			
            button.style.display = 'block';
        } else {
			
            button.style.display = 'none';
        }
    });
}

        // Función para cambiar a la página anterior
        function prevPage() {
            if (currentPage > 1) {
                currentPage--;
                showPage(currentPage);
                updatePagination();
                updatePrevNextButtons();
            }
        }

        // Función para cambiar a la siguiente página
        function nextPage() {
            const totalButtons = customButtons.length;
            const totalPages = Math.ceil(totalButtons / rowsPerPage);

            if (currentPage < totalPages) {
                currentPage++;
                showPage(currentPage);
                updatePagination();
                updatePrevNextButtons();
            }
        }

        // Resto del código...

        // Función para actualizar los estilos de los enlaces de paginación
        function updatePagination() {
            const pages = document.querySelectorAll('.page');

            pages.forEach((page) => {
                const pageNumber = parseInt(page.textContent);

                if (pageNumber === currentPage) {
                    page.classList.add('active');
                } else {
                    page.classList.remove('active');
                }
            });
        }

        // Función para habilitar o deshabilitar los botones de "Anterior" y "Siguiente"
        function updatePrevNextButtons() {
            const totalButtons = customButtons.length;
            const totalPages = Math.ceil(totalButtons / rowsPerPage);

            const prevButton = document.querySelector('.prev');
            const nextButton = document.querySelector('.next');

            if (currentPage === 1) {
                prevButton.classList.add('disabled');
            } else {
                prevButton.classList.remove('disabled');
            }

            if (currentPage === totalPages) {
                nextButton.classList.add('disabled');
            } else {
                nextButton.classList.remove('disabled');
            }
        }

        // Asignar eventos a los botones de "Anterior" y "Siguiente"
        document.querySelector('.prev').addEventListener('click', prevPage);
        document.querySelector('.next').addEventListener('click', nextPage);

        // Mostrar la primera página y crear la paginación al cargar los botones personalizados
        showPage(currentPage);
        updatePrevNextButtons(); // Actualizar los botones "Anterior" y "Siguiente" al inicio