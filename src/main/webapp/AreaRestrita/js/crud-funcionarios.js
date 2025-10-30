/**
 * Sistema CRUD para Funcionários
 * Funcionalidades: Criar, Ler, Atualizar e Deletar funcionários
 */

// Variáveis globais para controle do estado
let funcionarioParaExcluir = null;
let dadosOriginais = {}; // Armazena dados originais durante edição

/**
 * Inicialização quando a página carrega
 */
document.addEventListener('DOMContentLoaded', function() {
    // Configurar evento do botão de cadastrar
    const btnCadastrar = document.getElementById('btnCadastrar');
    if (btnCadastrar) {
        btnCadastrar.addEventListener('click', abrirModalCadastro);
    }
});

/**
 * Abre o modal de cadastro de novo funcionário
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
 * Salva um novo funcionário
 */
function salvarNovoFuncionario() {
    const form = document.getElementById('formularioCadastro');
    const formData = new FormData(form);
    
    // Validar campos obrigatórios
    const campos = ['cracha', 'nome', 'sobrenome', 'email', 'cargo', 'localizacao', 'status'];
    for (const campo of campos) {
        if (!formData.get(campo)) {
            mostrarToast('Erro', 'Todos os campos são obrigatórios!', 'error');
            return;
        }
    }
    
    // Criar novo funcionário
    const novoFuncionario = {
        id: Date.now(), // ID único baseado em timestamp
        cracha: formData.get('cracha'),
        nome: formData.get('nome'),
        sobrenome: formData.get('sobrenome'),
        email: formData.get('email'),
        cargo: formData.get('cargo'),
        localizacao: formData.get('localizacao'),
        status: formData.get('status')
    };
    
    // Adicionar à tabela
    adicionarFuncionarioNaTabela(novoFuncionario);
    
    // Fechar modal e mostrar mensagem de sucesso
    fecharModalCadastro();
    mostrarToast('Sucesso', 'Funcionário cadastrado com sucesso!', 'success');
    
    // Atualizar contadores (se existirem)
    atualizarContadores();
}

/**
 * Adiciona um funcionário na tabela
 */
function adicionarFuncionarioNaTabela(funcionario) {
    const tbody = document.getElementById('tabelaFuncionariosBody');
    if (!tbody) return;
    
    const novaLinha = document.createElement('tr');
    novaLinha.setAttribute('data-id', funcionario.id);
    
    const statusClass = funcionario.status === 'ativo' ? 'ativo' : 'inativo';
    const statusText = funcionario.status === 'ativo' ? 'Ativa' : 'Inativa';
    
    novaLinha.innerHTML = `
        <td>${funcionario.cracha}</td>
        <td>${funcionario.nome}</td>
        <td>${funcionario.sobrenome}</td>
        <td>${funcionario.email}</td>
        <td>${funcionario.cargo}</td>
        <td>${funcionario.localizacao}</td>
        <td><span class="${statusClass}">${statusText}</span></td>
        <td>
            <div class="acoes-linha">
                <button class="botao-editar" onclick="editarRegistro(${funcionario.id})" title="Editar funcionário">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="botao-excluir" onclick="excluirRegistro(${funcionario.id})" title="Excluir funcionário">
                    <i class="fas fa-trash"></i>
                </button>
                <button class="botao-salvar hidden" onclick="salvarEdicao(${funcionario.id})" title="Salvar alterações">
                    <i class="fas fa-save"></i>
                </button>
                <button class="botao-cancelar hidden" onclick="cancelarEdicao(${funcionario.id})" title="Cancelar edição">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        </td>
    `;
    
    tbody.appendChild(novaLinha);
}

/**
 * Inicia a edição de um funcionário
 */
function editarRegistro(id) {
    const linha = document.querySelector(`tr[data-id="${id}"]`);
    if (!linha) return;
    
    // Armazenar dados originais
    const celulas = linha.querySelectorAll('td');
    dadosOriginais[id] = {
        cracha: celulas[0].textContent,
        nome: celulas[1].textContent,
        sobrenome: celulas[2].textContent,
        email: celulas[3].textContent,
        cargo: celulas[4].textContent,
        localizacao: celulas[5].textContent,
        status: celulas[6].querySelector('span').classList.contains('ativo') ? 'ativo' : 'inativo'
    };
    
    // Tornar campos editáveis
    tornarCamposEditaveis(linha);
    
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
function tornarCamposEditaveis(linha) {
    const celulas = linha.querySelectorAll('td');
    
    // Crachá (não editável - chave única)
    // Nome
    celulas[1].innerHTML = `<input type="text" value="${celulas[1].textContent}" class="campo-editavel">`;
    
    // Sobrenome
    celulas[2].innerHTML = `<input type="text" value="${celulas[2].textContent}" class="campo-editavel">`;
    
    // Email
    celulas[3].innerHTML = `<input type="email" value="${celulas[3].textContent}" class="campo-editavel">`;
    
    // Cargo
    celulas[4].innerHTML = `<input type="text" value="${celulas[4].textContent}" class="campo-editavel">`;
    
    // Localização
    celulas[5].innerHTML = `
        <select class="campo-editavel">
            <option value="SP" ${celulas[5].textContent === 'SP' ? 'selected' : ''}>São Paulo</option>
            <option value="RJ" ${celulas[5].textContent === 'RJ' ? 'selected' : ''}>Rio de Janeiro</option>
            <option value="SC" ${celulas[5].textContent === 'SC' ? 'selected' : ''}>Santa Catarina</option>
            <option value="RS" ${celulas[5].textContent === 'RS' ? 'selected' : ''}>Rio Grande do Sul</option>
            <option value="BA" ${celulas[5].textContent === 'BA' ? 'selected' : ''}>Bahia</option>
            <option value="MG" ${celulas[5].textContent === 'MG' ? 'selected' : ''}>Minas Gerais</option>
            <option value="PR" ${celulas[5].textContent === 'PR' ? 'selected' : ''}>Paraná</option>
            <option value="GO" ${celulas[5].textContent === 'GO' ? 'selected' : ''}>Goiás</option>
        </select>
    `;
    
    // Status
    const statusAtual = celulas[6].querySelector('span').classList.contains('ativo');
    celulas[6].innerHTML = `
        <select class="campo-editavel">
            <option value="ativo" ${statusAtual ? 'selected' : ''}>Ativo</option>
            <option value="inativo" ${!statusAtual ? 'selected' : ''}>Inativo</option>
        </select>
    `;
}

/**
 * Salva as alterações de um funcionário
 */
function salvarEdicao(id) {
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
    celulas[1].textContent = inputs[0].value; // nome
    celulas[2].textContent = inputs[1].value; // sobrenome
    celulas[3].textContent = inputs[2].value; // email
    celulas[4].textContent = inputs[3].value; // cargo
    celulas[5].textContent = inputs[4].value; // localização
    
    // Status
    const statusClass = inputs[5].value === 'ativo' ? 'ativo' : 'inativo';
    const statusText = inputs[5].value === 'ativo' ? 'Ativa' : 'Inativa';
    celulas[6].innerHTML = `<span class="${statusClass}">${statusText}</span>`;
    
    // Restaurar botões normais
    const acoes = linha.querySelector('.acoes-linha');
    acoes.querySelector('.botao-editar').classList.remove('hidden');
    acoes.querySelector('.botao-excluir').classList.remove('hidden');
    acoes.querySelector('.botao-salvar').classList.add('hidden');
    acoes.querySelector('.botao-cancelar').classList.add('hidden');
    
    // Limpar dados originais
    delete dadosOriginais[id];
    
    mostrarToast('Sucesso', 'Funcionário atualizado com sucesso!', 'success');
}

/**
 * Cancela a edição de um funcionário
 */
function cancelarEdicao(id) {
    const linha = document.querySelector(`tr[data-id="${id}"]`);
    if (!linha) return;
    
    // Restaurar dados originais
    const dadosOriginaisFuncionario = dadosOriginais[id];
    if (dadosOriginaisFuncionario) {
        const celulas = linha.querySelectorAll('td');
        celulas[1].textContent = dadosOriginaisFuncionario.nome;
        celulas[2].textContent = dadosOriginaisFuncionario.sobrenome;
        celulas[3].textContent = dadosOriginaisFuncionario.email;
        celulas[4].textContent = dadosOriginaisFuncionario.cargo;
        celulas[5].textContent = dadosOriginaisFuncionario.localizacao;
        
        const statusClass = dadosOriginaisFuncionario.status === 'ativo' ? 'ativo' : 'inativo';
        const statusText = dadosOriginaisFuncionario.status === 'ativo' ? 'Ativa' : 'Inativa';
        celulas[6].innerHTML = `<span class="${statusClass}">${statusText}</span>`;
    }
    
    // Restaurar botões normais
    const acoes = linha.querySelector('.acoes-linha');
    acoes.querySelector('.botao-editar').classList.remove('hidden');
    acoes.querySelector('.botao-excluir').classList.remove('hidden');
    acoes.querySelector('.botao-salvar').classList.add('hidden');
    acoes.querySelector('.botao-cancelar').classList.add('hidden');
    
    // Limpar dados originais
    delete dadosOriginais[id];
    
    mostrarToast('Info', 'Edição cancelada', 'info');
}

/**
 * Inicia o processo de exclusão de um funcionário
 */
function excluirRegistro(id) {
    // Coletar dados da linha para confirmação
    const linha = document.querySelector(`tr[data-id="${id}"]`);
    const nome = linha ? linha.cells[1].textContent : 'Funcionário';
    
    // Dados do funcionário para exclusão
    const dadosFuncionario = {
        id: id,
        nome: nome,
        linha: linha
    };
    
    // Mostrar modal de confirmação moderno
    mostrarModalConfirmacao(
        'Excluir Funcionário',
        `Tem certeza que deseja excluir o funcionário "${nome}"?`,
        excluirFuncionario,
        dadosFuncionario
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
    funcionarioParaExcluir = null;
}

/**
 * Confirma a exclusão do funcionário
 */
function confirmarExclusao() {
    if (!funcionarioParaExcluir) return;
    
    const linha = document.querySelector(`tr[data-id="${funcionarioParaExcluir}"]`);
    if (linha) {
        linha.remove();
        mostrarToast('Sucesso', 'Funcionário excluído com sucesso!', 'success');
        atualizarContadores();
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
 * Atualiza os contadores (se existirem)
 */
function atualizarContadores() {
    // Esta função pode ser expandida para atualizar contadores de funcionários ativos/inativos
    // Por enquanto, apenas um placeholder
    console.log('Contadores atualizados');
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
        max-width: 150px;
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
