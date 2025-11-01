// ================= REGEX E FUNÇÕES DE VALIDAÇÃO =================
const __regexTelefone = /^\(?\d{2}\)?\s?\d{4,5}-?\d{4}$/;
const __regexSenha = /^(?=.*[A-Z])(?=.*[!#@$%&])(?=.*\d)(?=.*[a-z]).{6,15}$/;
const __regexCPF = /^(?:\d{3}\.\d{3}\.\d{3}-\d{2}|\d{11})$/;
const __regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const __regexCNPJ = /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/;

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
    if (!__regexSenha.test(v)) return __result(false, 'Senha deve ter 6–15 caracteres, 1 maiúscula, 1 minúscula, 1 número e 1 símbolo (!#@$%&).');
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
    if (!v) return __result(false, 'CNPJ é obrigatório.');
    const numericCNPJ = v.replace(/[^\d]/g, '');
    if (!__regexCNPJ.test(v) && numericCNPJ.length !== 14) {
        return __result(false, 'CNPJ inválido. Use o formato 00.000.000/0000-00.');
    }
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
    if (!__regexTelefone.test(v)) return __result(false, 'Telefone inválido. Use o formato (11) 99999-9999.');
    return __result(true, '');
}

function validateSimpleRequired(value, fieldName = 'Campo') {
    const v = String(value || '').trim();
    if (!v) return __result(false, `${fieldName} é obrigatório.`);
    if (v.length < 2) return __result(false, `Informe pelo menos 2 caracteres em ${fieldName}.`);
    return __result(true, '');
}

// Exposição global
window.validateEmail = validateEmail;
window.validatePassword = validatePassword;
window.validateName = validateName;
window.validateCNPJ = validateCNPJ;
window.validateCPF = validateCPF;
window.validateTelefone = validateTelefone;
window.validateSimpleRequired = validateSimpleRequired;
window.__validationHelpers = { __result };

// ================= VALIDAÇÃO E SUBMIT DO FORM =================
document.addEventListener('DOMContentLoaded', () => {
    const cadastroForm = document.getElementById('cadastroForm');
    if (!cadastroForm) return;

    function ensureErrorEl(input) {
        let el = input.parentElement.querySelector('.error-message');
        if (!el) {
            el = document.createElement('div');
            el.className = 'error-message';
            input.parentElement.appendChild(el);
        }
        return el;
    }

    function setError(input, message) {
        const msgEl = ensureErrorEl(input);
        if (message) {
            input.classList.add('input-error');
            msgEl.textContent = message;
        } else {
            input.classList.remove('input-error');
            msgEl.textContent = '';
        }
    }

    function validateField(input) {
        const id = input.id;
        const value = input.value.trim();
        switch (id) {
            case 'nome_empresa': return validateName(value);
            case 'cnpj': return validateCNPJ(value);
            case 'senha': return validatePassword(value);
            case 'cat_empresa':
            case 'porteEmpresa':
            case 'estado':
            case 'cidade':
                return validateSimpleRequired(value);
            default:
                return { valid: !!value, message: !!value ? '' : 'Campo obrigatório.' };
        }
    }

    cadastroForm.addEventListener('submit', (e) => {
        e.preventDefault();
        let hasError = false;
        const inputs = Array.from(cadastroForm.querySelectorAll('input[required]'));

        // Validação de cada campo
        inputs.forEach(input => {
            const { valid, message } = validateField(input);
            if (!valid) {
                hasError = true;
                setError(input, message);
            } else {
                setError(input, '');
            }
        });

        const overlay = document.getElementById('loadingOverlay');
        if (overlay) overlay.style.display = 'flex';

        if (!hasError) {
            // Envia o formulário
            cadastroForm.submit();
        } else {
            if (overlay) overlay.style.display = 'none';
        }
    });
});
