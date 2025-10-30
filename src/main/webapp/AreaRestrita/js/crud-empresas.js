
let empresaParaExcluir = null;
let dadosOriginaisEmpresa = {}; // Armazena dados originais durante edição

/**
 * Inicialização quando a página carrega
 */
document.addEventListener('DOMContentLoaded', function() {
    // Configurar evento do botão de cadastrar
    const btnCadastrar = document.getElementById('btnCadastrar');
    if (btnCadastrar) {
        btnCadastrar.addEventListener('click', abrirModalCadastro);
    }
    
    // Configurar máscara para CNPJ
    const cnpjInput = document.getElementById('cnpj');
    if (cnpjInput) {
        cnpjInput.addEventListener('input', formatarCNPJ);
    }
});

/**
 * Formatar CNPJ com máscara
 */
function formatarCNPJ(event) {
    let valor = event.target.value.replace(/\D/g, '');
    valor = valor.replace(/^(\d{2})(\d)/, '$1.$2');
    valor = valor.replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3');
    valor = valor.replace(/\.(\d{3})(\d)/, '.$1/$2');
    valor = valor.replace(/(\d{4})(\d)/, '$1-$2');
    event.target.value = valor;
}

/**
 * Abre o modal de cadastro de nova empresa
 */
function abrirModalCadastro() {
    const modal = document.getElementById('modalCadastro');
    if (modal) {
        modal.classList.remove('hidden');
        // Limpar formulário
        document.getElementById('formularioCadastro').reset();
    }
}

/**
 * Fecha o modal de cadastro
 */
function fecharModalCadastro() {
    const modal = document.getElementById('modalCadastro');
    if (modal) {
        modal.classList.add('hidden');
    }
}

/**
 * Salva uma nova empresa
 */
function salvarNovaEmpresa() {
    const form = document.getElementById('formularioCadastro');
    const formData = new FormData(form);
    
    // Validar campos obrigatórios
    const campos = ['nomeEmpresa', 'cnpj', 'categoria', 'porte', 'localizacao', 'status'];
    for (const campo of campos) {
        if (!formData.get(campo)) {
            mostrarToast('Erro', 'Todos os campos são obrigatórios!', 'error');
            return;
        }
    }
    
    // Validar CNPJ básico
    const cnpj = formData.get('cnpj').replace(/\D/g, '');
    if (cnpj.length !== 14) {
        mostrarToast('Erro', 'CNPJ deve ter 14 dígitos!', 'error');
        return;
    }
    
    // Criar nova empresa
    const novaEmpresa = {
        id: Date.now(), // ID único baseado em timestamp
        nomeEmpresa: formData.get('nomeEmpresa'),
        cnpj: formData.get('cnpj'),
        categoria: formData.get('categoria'),
        porte: formData.get('porte'),
        localizacao: formData.get('localizacao'),
        status: formData.get('status')
    };
    
    // Adicionar à tabela
    adicionarEmpresaNaTabela(novaEmpresa);
    
    // Fechar modal e mostrar mensagem de sucesso
    fecharModalCadastro();
    mostrarToast('Sucesso', 'Empresa cadastrada com sucesso!', 'success');
    
    // Atualizar contadores
    atualizarContadoresEmpresas();
}

/**
 * Adiciona uma empresa na tabela
 */
function adicionarEmpresaNaTabela(empresa) {
    const tbody = document.getElementById('tabelaEmpresasBody');
    if (!tbody) return;
    
    const novaLinha = document.createElement('tr');
    novaLinha.setAttribute('data-id', empresa.id);
    
    const statusClass = empresa.status === 'ativo' ? 'ativo' : 'inativo';
    const statusText = empresa.status === 'ativo' ? 'Ativa' : 'Inativa';
    
    novaLinha.innerHTML = `
        <td>${empresa.nomeEmpresa}</td>
        <td>${empresa.cnpj}</td>
        <td>${empresa.categoria}</td>
        <td>${empresa.porte}</td>
        <td>${empresa.localizacao}</td>
        <td><span class="${statusClass}">${statusText}</span></td>
        <td>
            <div class="acoes-linha">
                <button class="botao-editar" onclick="editarRegistroEmpresa(${empresa.id})" title="Editar empresa">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="botao-excluir" onclick="excluirRegistroEmpresa(${empresa.id})" title="Excluir empresa">
                    <i class="fas fa-trash"></i>
                </button>
                <button class="botao-salvar hidden" onclick="salvarEdicaoEmpresa(${empresa.id})" title="Salvar alterações">
                    <i class="fas fa-save"></i>
                </button>
                <button class="botao-cancelar hidden" onclick="cancelarEdicaoEmpresa(${empresa.id})" title="Cancelar edição">
                    <i class="fas fa-times"></i>
                </button>
                <a href="/Cadastro/visualizar-funcionario.html" class="botao-visualizar" title="Ver funcionários">
                    <i class="fas fa-users"></i>
                </a>
            </div>
        </td>
    `;
    
    tbody.appendChild(novaLinha);
}

/**
 * Inicia a edição de uma empresa
 */
function editarRegistroEmpresa(id) {
    const linha = document.querySelector(`tr[data-id="${id}"]`);
    if (!linha) return;
    
    // Armazenar dados originais
    const celulas = linha.querySelectorAll('td');
    dadosOriginaisEmpresa[id] = {
        nomeEmpresa: celulas[0].textContent,
        cnpj: celulas[1].textContent,
        categoria: celulas[2].textContent,
        porte: celulas[3].textContent,
        localizacao: celulas[4].textContent,
        status: celulas[5].querySelector('span').classList.contains('ativo') ? 'ativo' : 'inativo'
    };
    
    // Tornar campos editáveis
    tornarCamposEditaveisEmpresa(linha);
    
    // Mostrar botões de salvar/cancelar e ocultar editar/excluir
    const acoes = linha.querySelector('.acoes-linha');
    acoes.querySelector('.botao-editar').classList.add('hidden');
    acoes.querySelector('.botao-excluir').classList.add('hidden');
    acoes.querySelector('.botao-salvar').classList.remove('hidden');
    acoes.querySelector('.botao-cancelar').classList.remove('hidden');
    
    mostrarToast('Info', 'Modo de edição ativado', 'info');
}

/**
 * Torna os campos de uma linha editáveis
 */
function tornarCamposEditaveisEmpresa(linha) {
    const celulas = linha.querySelectorAll('td');
    
    // Nome da empresa
    celulas[0].innerHTML = `<input type="text" value="${celulas[0].textContent}" class="campo-editavel">`;
    
    // CNPJ (não editável - chave única)
    
    // Categoria
    celulas[2].innerHTML = `
        <select class="campo-editavel">
            <option value="Tecnologia da Informação" ${celulas[2].textContent === 'Tecnologia da Informação' ? 'selected' : ''}>Tecnologia da Informação</option>
            <option value="Gestão Ambiental" ${celulas[2].textContent === 'Gestão Ambiental' ? 'selected' : ''}>Gestão Ambiental</option>
            <option value="Indústria" ${celulas[2].textContent === 'Indústria' ? 'selected' : ''}>Indústria</option>
            <option value="Serviços" ${celulas[2].textContent === 'Serviços' ? 'selected' : ''}>Serviços</option>
            <option value="Comércio" ${celulas[2].textContent === 'Comércio' ? 'selected' : ''}>Comércio</option>
            <option value="Construção Civil" ${celulas[2].textContent === 'Construção Civil' ? 'selected' : ''}>Construção Civil</option>
            <option value="Agronegócio" ${celulas[2].textContent === 'Agronegócio' ? 'selected' : ''}>Agronegócio</option>
            <option value="Saúde" ${celulas[2].textContent === 'Saúde' ? 'selected' : ''}>Saúde</option>
            <option value="Educação" ${celulas[2].textContent === 'Educação' ? 'selected' : ''}>Educação</option>
            <option value="Financeiro" ${celulas[2].textContent === 'Financeiro' ? 'selected' : ''}>Financeiro</option>
        </select>
    `;
    
    // Porte
    celulas[3].innerHTML = `
        <select class="campo-editavel">
            <option value="Microempresa" ${celulas[3].textContent === 'Microempresa' ? 'selected' : ''}>Microempresa</option>
            <option value="Pequena Empresa" ${celulas[3].textContent === 'Pequena Empresa' ? 'selected' : ''}>Pequena Empresa</option>
            <option value="Média Empresa" ${celulas[3].textContent === 'Média Empresa' ? 'selected' : ''}>Média Empresa</option>
            <option value="Grande Empresa" ${celulas[3].textContent === 'Grande Empresa' ? 'selected' : ''}>Grande Empresa</option>
        </select>
    `;
    
    // Localização
    celulas[4].innerHTML = `
        <select class="campo-editavel">
            <option value="SP" ${celulas[4].textContent === 'SP' ? 'selected' : ''}>São Paulo</option>
            <option value="RJ" ${celulas[4].textContent === 'RJ' ? 'selected' : ''}>Rio de Janeiro</option>
            <option value="SC" ${celulas[4].textContent === 'SC' ? 'selected' : ''}>Santa Catarina</option>
            <option value="RS" ${celulas[4].textContent === 'RS' ? 'selected' : ''}>Rio Grande do Sul</option>
            <option value="BA" ${celulas[4].textContent === 'BA' ? 'selected' : ''}>Bahia</option>
            <option value="MG" ${celulas[4].textContent === 'MG' ? 'selected' : ''}>Minas Gerais</option>
            <option value="PR" ${celulas[4].textContent === 'PR' ? 'selected' : ''}>Paraná</option>
            <option value="GO" ${celulas[4].textContent === 'GO' ? 'selected' : ''}>Goiás</option>
            <option value="DF" ${celulas[4].textContent === 'DF' ? 'selected' : ''}>Distrito Federal</option>
            <option value="CE" ${celulas[4].textContent === 'CE' ? 'selected' : ''}>Ceará</option>
            <option value="PE" ${celulas[4].textContent === 'PE' ? 'selected' : ''}>Pernambuco</option>
            <option value="PA" ${celulas[4].textContent === 'PA' ? 'selected' : ''}>Pará</option>
        </select>
    `;
    
    // Status
    const statusAtual = celulas[5].querySelector('span').classList.contains('ativo');
    celulas[5].innerHTML = `
        <select class="campo-editavel">
            <option value="ativo" ${statusAtual ? 'selected' : ''}>Ativo</option>
            <option value="inativo" ${!statusAtual ? 'selected' : ''}>Inativo</option>
        </select>
    `;
}

/**
 * Salva as alterações de uma empresa
 */
function salvarEdicaoEmpresa(id) {
    const linha = document.querySelector(`tr[data-id="${id}"]`);
    if (!linha) return;
    
    // Coletar dados dos campos editáveis
    const inputs = linha.querySelectorAll('.campo-editavel');
    
    // Validar campos
    for (const input of inputs) {
        if (!input.value.trim()) {
            mostrarToast('Erro', 'Todos os campos são obrigatórios!', 'error');
            return;
        }
    }
    
    // Atualizar a linha com os novos dados
    const celulas = linha.querySelectorAll('td');
    celulas[0].textContent = inputs[0].value; // nome empresa
    celulas[2].textContent = inputs[1].value; // categoria
    celulas[3].textContent = inputs[2].value; // porte
    celulas[4].textContent = inputs[3].value; // localização
    
    // Status
    const statusClass = inputs[4].value === 'ativo' ? 'ativo' : 'inativo';
    const statusText = inputs[4].value === 'ativo' ? 'Ativa' : 'Inativa';
    celulas[5].innerHTML = `<span class="${statusClass}">${statusText}</span>`;
    
    // Restaurar botões normais
    const acoes = linha.querySelector('.acoes-linha');
    acoes.querySelector('.botao-editar').classList.remove('hidden');
    acoes.querySelector('.botao-excluir').classList.remove('hidden');
    acoes.querySelector('.botao-salvar').classList.add('hidden');
    acoes.querySelector('.botao-cancelar').classList.add('hidden');
    
    // Limpar dados originais
    delete dadosOriginaisEmpresa[id];
    
    mostrarToast('Sucesso', 'Empresa atualizada com sucesso!', 'success');
}

/**
 * Cancela a edição de uma empresa
 */
function cancelarEdicaoEmpresa(id) {
    const linha = document.querySelector(`tr[data-id="${id}"]`);
    if (!linha) return;
    
    // Restaurar dados originais
    const dadosOriginais = dadosOriginaisEmpresa[id];
    if (dadosOriginais) {
        const celulas = linha.querySelectorAll('td');
        celulas[0].textContent = dadosOriginais.nomeEmpresa;
        celulas[2].textContent = dadosOriginais.categoria;
        celulas[3].textContent = dadosOriginais.porte;
        celulas[4].textContent = dadosOriginais.localizacao;
        
        const statusClass = dadosOriginais.status === 'ativo' ? 'ativo' : 'inativo';
        const statusText = dadosOriginais.status === 'ativo' ? 'Ativa' : 'Inativa';
        celulas[5].innerHTML = `<span class="${statusClass}">${statusText}</span>`;
    }
    
    // Restaurar botões normais
    const acoes = linha.querySelector('.acoes-linha');
    acoes.querySelector('.botao-editar').classList.remove('hidden');
    acoes.querySelector('.botao-excluir').classList.remove('hidden');
    acoes.querySelector('.botao-salvar').classList.add('hidden');
    acoes.querySelector('.botao-cancelar').classList.add('hidden');
    
    // Limpar dados originais
    delete dadosOriginaisEmpresa[id];
    
    mostrarToast('Info', 'Edição cancelada', 'info');
}

/**
 * Inicia o processo de exclusão de uma empresa
 */
function excluirRegistroEmpresa(id) {
    // Coletar dados da linha para confirmação
    const linha = document.querySelector(`tr[data-id="${id}"]`);
    const nome = linha ? linha.cells[1].textContent : 'Empresa';
    
    // Dados da empresa para exclusão
    const dadosEmpresa = {
        id: id,
        nome: nome,
        linha: linha
    };
    
    // Mostrar modal de confirmação moderno
    mostrarModalConfirmacao(
        'Excluir Empresa',
        `Tem certeza que deseja excluir a empresa "${nome}"?`,
        excluirEmpresa,
        dadosEmpresa
    );
}

/**
 * Fecha o modal de confirmação
 */
function fecharModal() {
    const modal = document.getElementById('modalConfirmacao');
    if (modal) {
        modal.classList.add('hidden');
    }
    empresaParaExcluir = null;
}

/**
 * Confirma a exclusão da empresa
 */
function confirmarExclusaoEmpresa() {
    if (!empresaParaExcluir) return;
    
    const linha = document.querySelector(`tr[data-id="${empresaParaExcluir}"]`);
    if (linha) {
        linha.remove();
        mostrarToast('Sucesso', 'Empresa excluída com sucesso!', 'success');
        atualizarContadoresEmpresas();
    }
    
    fecharModal();
}

/**
 * Mostra uma mensagem toast
 */
function mostrarToast(titulo, mensagem, tipo) {
    const toast = document.getElementById('toast');
    const toastIcon = document.getElementById('toastIcon');
    const toastMsg = document.getElementById('toastMsg');
    
    if (!toast || !toastIcon || !toastMsg) return;
    
    // Definir ícone baseado no tipo
    let icone = '';
    switch (tipo) {
        case 'success':
            icone = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjQiIGhlaWdodD0iMjQiIHZpZXdCb3g9IjAgMCAyNCAyNCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZD0iTTkgMTJMMTEgMTRMMTUgMTAiIHN0cm9rZT0iIzJFRDU3MyIgc3Ryb2tlLXdpZHRoPSIyIiBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiLz4KPC9zdmc+';
            break;
        case 'error':
            icone = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjQiIGhlaWdodD0iMjQiIHZpZXdCb3g9IjAgMCAyNCAyNCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZD0iTTE4IDZMMDYgMThNMDYgNkwxOCAxOCIgc3Ryb2tlPSIjRkY0NzU3IiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIvPgo8L3N2Zz4=';
            break;
        case 'info':
            icone = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjQiIGhlaWdodD0iMjQiIHZpZXdCb3g9IjAgMCAyNCAyNCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZD0iTTEyIDE2VjEyIiBzdHJva2U9IiMxQ0Q1RUEiIHN0cm9rZS13aWR0aD0iMiIgc3Ryb2tlLWxpbmVjYXA9InJvdW5kIiBzdHJva2UtbGluZWpvaW49InJvdW5kIi8+CjxjaXJjbGUgY3g9IjEyIiBjeT0iOCIgcj0iMSIgZmlsbD0iIzFDRDVFQSIvPgo8L3N2Zz4=';
            break;
    }
    
    toastIcon.src = icone;
    toastMsg.textContent = `${titulo}: ${mensagem}`;
    
    toast.classList.remove('hidden');
    
    // Auto-fechar após 4 segundos
    setTimeout(() => {
        toast.classList.add('hidden');
    }, 4000);
}

/**
 * Atualiza os contadores de empresas
 */
function atualizarContadoresEmpresas() {
    // Contar total de empresas
    const totalEmpresas = document.querySelectorAll('#tabelaEmpresasBody tr').length;
    
    // Contar empresas ativas
    const empresasAtivas = document.querySelectorAll('#tabelaEmpresasBody .ativo').length;
    
    // Contar empresas inativas
    const empresasInativas = document.querySelectorAll('#tabelaEmpresasBody .inativo').length;
    
    // Atualizar elementos na tela (se existirem)
    const totalElement = document.querySelector('.total p');
    const ativosElement = document.querySelector('.ativos p');
    const inativosElement = document.querySelector('.inativos p');
    
    if (totalElement) totalElement.textContent = totalEmpresas;
    if (ativosElement) ativosElement.textContent = empresasAtivas;
    if (inativosElement) inativosElement.textContent = empresasInativas;
}

// Adicionar estilos CSS para campos editáveis
const style = document.createElement('style');
style.textContent = `
    .campo-editavel {
        padding: 6px 8px;
        border: 1px solid #1cd5ea;
        border-radius: 4px;
        background: #1a2a3a;
        color: #fff;
        font-size: 14px;
        width: 100%;
        max-width: 180px;
    }
    
    .campo-editavel:focus {
        outline: none;
        border-color: #1cd5ea;
        box-shadow: 0 0 0 2px rgba(28, 213, 234, 0.2);
    }
    
    .campo-editavel option {
        background: #1a2a3a;
        color: #fff;
    }
`;
document.head.appendChild(style);
