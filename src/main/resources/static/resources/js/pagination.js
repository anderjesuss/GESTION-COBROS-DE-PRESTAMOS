	const table = document.querySelector('table');
	const rowsPerPage = 7; // Número de filas por página
	let currentPage = 1;
	
	// Función para mostrar las filas correspondientes a la página actual
	function showPage(page) {
	    const rows = table.querySelectorAll('tbody tr');
	    const start = (page - 1) * rowsPerPage;
	    const end = start + rowsPerPage;
	
	    rows.forEach((row, index) => {
	        if (index >= start && index < end) {
	            row.style.display = 'table-row';
	        } else {
	            row.style.display = 'none';
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
	    const rows = table.querySelectorAll('tbody tr');
	    const totalRows = rows.length;
	    const totalPages = Math.ceil(totalRows / rowsPerPage);
	
	    if (currentPage < totalPages) {
	        currentPage++;
	        showPage(currentPage);
	        updatePagination();
	        updatePrevNextButtons();
	    }
	}
	
	// Función para crear los enlaces de paginación dinámicamente
	function createPagination() {
	    const rows = table.querySelectorAll('tbody tr');
	    const totalRows = rows.length;
	    const totalPages = Math.ceil(totalRows / rowsPerPage);
	    const paginationContainer = document.querySelector('.pagination');
	
	    paginationContainer.innerHTML = '';
	
	    for (let i = 1; i <= totalPages; i++) {
	        const pageLink = document.createElement('a');
	        pageLink.textContent = i;
	        pageLink.href = '#';
	        pageLink.classList.add('page');
	        if (i === currentPage) {
	            pageLink.classList.add('active');
	        }
	        paginationContainer.appendChild(pageLink);
	    }
	
	    // Asignar eventos a los enlaces de paginación
	    document.querySelectorAll('.page').forEach((page) => {
	        page.addEventListener('click', () => {
	            currentPage = parseInt(page.textContent);
	            showPage(currentPage);
	            updatePagination();
	            updatePrevNextButtons();
	        });
	    });
	}
	
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
	    const rows = table.querySelectorAll('tbody tr');
	    const totalRows = rows.length;
	    const totalPages = Math.ceil(totalRows / rowsPerPage);
	
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
	
	// Mostrar la primera página y crear la paginación al cargar la tabla
	showPage(currentPage);
	createPagination();
	updatePrevNextButtons(); // Actualizar los botones "Anterior" y "Siguiente" al inicio
