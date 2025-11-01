// ================= REGEX DE VALIDAÇÃO (FUNÇÕES PURAS) =================
const __regexTelefone = /^\(?\d{2}\)?\s?\d{4,5}-?\d{4}$/;
const __regexSenha = /^(?=.*[A-Z])(?=.*[!#@$%&])(?=.*\d)(?=.*[a-z]).{6,15}$/;
const __regexCPF = /^(?:\d{3}\.\d{3}\.\d{3}-\d{2}|\d{11})$/;
const __regexEmail = /^(?:[a-z0-9!#$%&'+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}(?:25[0-5]|2[0-4]\d|[01]?\d\d?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+))\])$/i;
const __regexCNPJ = /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/; // Regex de CNPJ mais completa

function __result(valid, message) {
	return { valid, message };
}

function validateEmail(value) {
	const v = String(value || '').trim();
	if (!v) return __result(false, 'E-mail é obrigatório.');
	if (!__regexEmail.test(v)) return __result(false, 'Informe um e-mail válido.');
	return __result(true, '');
}

function validatePassword(value) {
	const v = String(value || '').trim();
	if (!v) return __result(false, 'Senha é obrigatória.');
	if (!__regexSenha.test(v)) return __result(false, 'A senha deve ter 6–15 caracteres, 1 maiúscula, 1 minúscula, 1 número e 1 símbolo (!#@$%&).');
	return __result(true, '');
}

function validateName(value) {
	const v = String(value || '').trim();
	if (!v) return __result(false, 'Nome é obrigatório.');
	if (v.length < 3) return __result(false, 'Informe pelo menos 3 caracteres.');
	return __result(true, '');
}

function validateCNPJ(value) {
	const v = String(value || '').trim();
	if (!v) return __result(false, 'CNPJ é **obrigatório**.');
	// Remove non-numeric characters for a better validation test
	const numericCNPJ = v.replace(/[^\d]/g, '');
	// Basic check for format: XX.XXX.XXX/XXXX-XX
	if (!__regexCNPJ.test(v) && numericCNPJ.length !== 14) {
		return __result(false, 'CNPJ inválido. Use o formato **00.000.000/0000-00**.');
	}
	// You might want to add a more complex CNPJ checksum validator here
	return __result(true, '');
}

function validateCPF(value) {
	const v = String(value || '').trim();
	if (!v) return __result(false, 'CPF é obrigatório.');
	if (!__regexCPF.test(v)) return __result(false, 'CPF inválido.');
	return __result(true, '');
}

function validateTelefone(value) {
	const v = String(value || '').trim();
	if (!v) return __result(false, 'Telefone é obrigatório.');
	if (!__regexTelefone.test(v)) return __result(false, 'Telefone inválido. Use o formato **(11) 99999-9999**.');
	return __result(true, '');
}

function validateSimpleRequired(value, fieldName = 'Campo') {
	const v = String(value || '').trim();
	if (!v) return __result(false, `${fieldName} é **obrigatório**.`);
	if (v.length < 2) return __result(false, `Informe pelo menos 2 caracteres em ${fieldName}.`);
	return __result(true, '');
}

// Exposição global (sem listeners automáticos)
window.validateEmail = validateEmail;
window.validatePassword = validatePassword;
window.validateName = validateName;
window.validateCNPJ = validateCNPJ;
window.validateCPF = validateCPF;
window.validateTelefone = validateTelefone;
window.validateSimpleRequired = validateSimpleRequired;
window.__validationHelpers = { __result };


document.addEventListener('DOMContentLoaded', () => {
	function ensureErrorEl(input) {
		// Procura por um elemento de mensagem de erro já existente ou cria um
		let el = input.closest('.campo').querySelector('.mensagem-erro') || input.parentElement.parentElement.querySelector('.mensagem-erro');

		// Se o elemento não existir, cria-o e o insere abaixo do input-container
		if (!el) {
			el = document.createElement('div');
			el.className = 'mensagem-erro';
			const campoDiv = input.closest('.campo');
			if (campoDiv) {
				campoDiv.appendChild(el);
			}
		}
		return el;
	}

	function setError(input, message) {
		const msgEl = ensureErrorEl(input);
		if (message) {
			// Adiciona a classe de erro ao input e define a mensagem
			input.classList.add('input-error');
			input.style.borderColor = '#ff4757'; // Borda vermelha
			msgEl.style.color = '#ff4757'; // Mensagem vermelha
			msgEl.textContent = message;
		} else {
			// Remove a classe de erro, restaura a borda e limpa a mensagem
			input.classList.remove('input-error');
			input.style.borderColor = ''; // Volta ao padrão do CSS
			msgEl.textContent = '';
		}
	}

	function validateField(input) {
		const id = input.id;
		const value = input.value;
		const required = input.hasAttribute('required') || input.closest('.campo').querySelector('.obrigatorio');

		// Se o campo não é obrigatório e está vazio, retorna sucesso.
		if (!required && !value.trim()) { return __result(true, ''); }

		// Validação específica para cada campo
		switch (id) {
			case 'nome': // Form Funcionário
			case 'nome_empresa': // Form Empresa
				return validateName(value);
			case 'email': // Form Funcionário
				return validateEmail(value);
			case 'telefone': // Form Funcionário
				return validateTelefone(value);
			case 'cnpj': // Form Empresa
				return validateCNPJ(value);
			case 'cargo': // Form Funcionário (usando required simples)
			case 'empresa': // Form Funcionário (usando required simples)
			case 'categoriaEmpresa': // Form Empresa (usando required simples)
			case 'porteEmpresa': // Form Empresa (usando required simples)
			case 'estado': // Form Empresa (usando required simples)
			case 'cidade': // Form Empresa (usando required simples)
				// Obtém o nome do campo para a mensagem de erro
				const label = input.closest('.campo')?.querySelector('.label')?.textContent.replace(/\s\*/g, '').trim() || id;
				return validateSimpleRequired(value, label);
			default:
				// Se for obrigatório mas não tiver validação específica
				if (required) {
					return validateSimpleRequired(value, 'Campo');
				}
				return __result(true, '');
		}
	}

	function handleSubmit(form) {
		form.addEventListener('submit', (e) => {
			e.preventDefault();
			const overlay = document.getElementById('loadingOverlay');
			if (overlay) overlay.style.display = 'flex';
			let hasError = false;
			// Seleciona todos os inputs que são obrigatórios
			const inputs = Array.from(form.querySelectorAll('input[required], input, select, textarea')).filter(input => {
				// Filtra para manter apenas os campos que precisam ser validados
				return input.hasAttribute('required') || input.closest('.campo')?.querySelector('.obrigatorio');
			});

			inputs.forEach((input) => {
				const { valid, message } = validateField(input);
				if (!valid) {
					hasError = true;
					setError(input, message);
				} else {
					setError(input, '');
				}
			});

			// Foca no primeiro campo com erro
			if (hasError) {
				const firstErrorInput = inputs.find(input => input.classList.contains('input-error'));
				if (firstErrorInput) {
					firstErrorInput.focus();
				}
			}

			if (!hasError) {
				// Simulação de envio, você deve adicionar a lógica AJAX/Fetch aqui
				console.log('Formulário Válido, enviando dados...');
				// form.submit(); // Descomente para submeter o formulário de forma tradicional

				// Exemplo de Toast de Sucesso (se o toast.js estiver implementado)
				// showToast('Sucesso!', 'Dados salvos com sucesso!', 'success');
			}

			if (overlay) overlay.style.display = 'none';
		});
	}

	// Anexa o manipulador de submit aos formulários necessários
	const formFuncionario = document.querySelector('.formulario-funcionario form');
	const formEmpresa = document.getElementById('formInserirEmpresa');

	if (formFuncionario) handleSubmit(formFuncionario);
	if (formEmpresa) handleSubmit(formEmpresa);

	// Código para o Loading Overlay (mantido do original)
	if (!document.getElementById('loadingOverlay')) {
		const overlay = document.createElement('div');
		overlay.id = 'loadingOverlay';
		overlay.className = 'loading-overlay';
		overlay.style.cssText = 'position:fixed;inset:0;background:rgba(0,0,0,0.4);display:none;align-items:center;justify-content:center;z-index:9999;';
		const box = document.createElement('div');
		box.className = 'loading-box';
		box.textContent = 'Enviando, aguarde…';
		box.style.cssText = 'background:#fff;padding:16px 20px;border-radius:8px;box-shadow:0 4px 12px rgba(0,0,0,0.15);font-weight:600;';
		overlay.appendChild(box);
		document.body.appendChild(overlay);
	}
});