function isValidEmail(email) {
	const emailRegex = /^[A-Za-z0-9._]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
	return emailRegex.test(email);
}


const signupForm = document.getElementById('FormLogin');
const correo = document.getElementById('idCorreo');
const contrasena = document.getElementById('idContraseña');
    
// Función para ocultar el mensaje de error de un campo
function hideError(elementId) {
	document.getElementById(elementId).style.display = 'none';
}
    
// Agregar eventos de escucha para ocultar los mensajes de error al escribir
correo.addEventListener('input', () => hideError('correoError'));
    
signupForm.addEventListener('submit', function (e) {
	e.preventDefault();
    
	// Lógica de validación aquí
	let valid = true;
    
	if (correo.value.trim() === '') {
		document.getElementById('correoError').style.display = 'block';
		valid = false;
	} else if (!isValidEmail(correo.value.trim())) {
		document.getElementById('correoError').style.display = 'block';
		document.getElementById('correoError').textContent = 'Por favor, ingrese un correo electrónico válido';
		valid = false;
	}
    
	if (contrasena.value.trim() === '') {
		document.getElementById('contrasenaError').style.display = 'block';
		valid = false;
	}
    
	if (valid) {
		// Aquí puedes enviar el formulario si pasa la validación
		signupForm.submit();
	}
});