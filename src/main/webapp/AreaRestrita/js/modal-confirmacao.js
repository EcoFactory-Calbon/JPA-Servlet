/**
 * FUNCIONALIDADES DO MODAL DE CONFIRMAÇÃO
 * 
 * Funções principais:
 * - mostrarModalConfirmacao(): Exibe modal de confirmação
 * - fecharModalConfirmacao(): Fecha o modal
 * - confirmarExclusao(): Confirma e executa a exclusão
 * - configurarModal(): Configura eventos do modal
 * 
 * Uso: Para confirmação de exclusão de registros
 */

// Variáveis globais
let modalConfirmacao = null;
let callbackConfirmacao = null;
let dadosExclusao = null;

/**
 * Inicialização quando a página carrega
 */
document.addEventListener('DOMContentLoaded', function() {
    configurarModal();
});

/**
 * Configura o modal de confirmação
 */
function configurarModal() {
    // Criar modal se não existir
    if (!document.getElementById('modal-confirmacao')) {
        criarModalConfirmacao();
    }
    
    modalConfirmacao = document.getElementById('modal-confirmacao');
    
    // Configurar eventos
    const botaoFechar = modalConfirmacao.querySelector('.botao-fechar-modal');
    const botaoCancelar = modalConfirmacao.querySelector('.botao-cancelar-modal');
    const botaoConfirmar = modalConfirmacao.querySelector('.botao-confirmar-exclusao');
    
    botaoFechar.addEventListener('click', fecharModalConfirmacao);
    botaoCancelar.addEventListener('click', fecharModalConfirmacao);
    botaoConfirmar.addEventListener('click', confirmarExclusao);
    
    // Fechar ao clicar no backdrop
    modalConfirmacao.addEventListener('click', function(e) {
        if (e.target === modalConfirmacao) {
            fecharModalConfirmacao();
        }
    });
    
    // Fechar com ESC
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape' && modalConfirmacao.classList.contains('ativo')) {
            fecharModalConfirmacao();
        }
    });
}

/**
 * Cria o modal de confirmação no DOM
 */
function criarModalConfirmacao() {
    const modalHTML = `
        <div id="modal-confirmacao" class="modal">
            <div class="modal-conteudo">
                <div class="modal-header">
                    <h3>Confirmar Exclusão</h3>
                    <button type="button" class="botao-fechar-modal" aria-label="Fechar modal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="icone-aviso">
                        <i class="fas fa-exclamation-triangle"></i>
                    </div>
                    <p class="texto-destaque">Tem certeza que deseja excluir este registro?</p>
                    <p class="texto-secundario">Esta ação não pode ser desfeita.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="botao-cancelar-modal">
                        <i class="fas fa-times"></i>
                        Cancelar
                    </button>
                    <button type="button" class="botao-confirmar-exclusao">
                        <i class="fas fa-trash"></i>
                        Confirmar Exclusão
                    </button>
                </div>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHTML);
}

/**
 * Mostra o modal de confirmação
 */
function mostrarModalConfirmacao(titulo, texto, callback, dados = null) {
    if (!modalConfirmacao) {
        configurarModal();
    }
    
    // Configurar conteúdo
    const tituloModal = modalConfirmacao.querySelector('.modal-header h3');
    const textoDestaque = modalConfirmacao.querySelector('.texto-destaque');
    const textoSecundario = modalConfirmacao.querySelector('.texto-secundario');
    
    if (tituloModal) tituloModal.textContent = titulo;
    if (textoDestaque) textoDestaque.textContent = texto;
    if (textoSecundario) textoSecundario.textContent = 'Esta ação não pode ser desfeita.';
    
    // Armazenar callback e dados
    callbackConfirmacao = callback;
    dadosExclusao = dados;
    
    // Mostrar modal
    modalConfirmacao.classList.add('ativo');
    document.body.style.overflow = 'hidden';
    
    // Focar no botão de cancelar para acessibilidade
    setTimeout(() => {
        const botaoCancelar = modalConfirmacao.querySelector('.botao-cancelar-modal');
        if (botaoCancelar) botaoCancelar.focus();
    }, 100);
}

/**
 * Fecha o modal de confirmação
 */
function fecharModalConfirmacao() {
    if (modalConfirmacao) {
        modalConfirmacao.classList.remove('ativo');
        document.body.style.overflow = '';
        
        // Limpar callback e dados
        callbackConfirmacao = null;
        dadosExclusao = null;
    }
}

/**
 * Confirma a exclusão e executa o callback
 */
function confirmarExclusao() {
    if (callbackConfirmacao) {
        try {
            callbackConfirmacao(dadosExclusao);
            fecharModalConfirmacao();
        } catch (error) {
            console.error('Erro ao executar callback de exclusão:', error);
            mostrarToast('Erro ao excluir registro', 'erro');
        }
    } else {
        fecharModalConfirmacao();
    }
}

/**
 * Função para exclusão de funcionário
 */
function excluirFuncionario(dados) {
    if (!dados || !dados.id) {
        mostrarToast('Dados inválidos para exclusão', 'erro');
        return;
    }
    
    // Simular exclusão (será substituído por chamada ao backend)
    setTimeout(() => {
        // Remover linha da tabela visualmente
        const linha = document.querySelector(`tr[data-id="${dados.id}"]`);
        if (linha) {
            linha.style.animation = 'fadeOut 0.3s ease-out';
            setTimeout(() => {
                linha.remove();
            }, 300);
        }
        
        mostrarToast(`Funcionário "${dados.nome}" excluído com sucesso!`, 'sucesso');
        
        console.log('Funcionário excluído:', dados);
        
        // TODO: Aqui será feita a chamada para o Servlet
        // await excluirFuncionarioBackend(dados.id);
        
    }, 500);
}

/**
 * Função para exclusão de empresa
 */
function excluirEmpresa(dados) {
    if (!dados || !dados.id) {
        mostrarToast('Dados inválidos para exclusão', 'erro');
        return;
    }
    
    // Simular exclusão (será substituído por chamada ao backend)
    setTimeout(() => {
        // Remover linha da tabela visualmente
        const linha = document.querySelector(`tr[data-id="${dados.id}"]`);
        if (linha) {
            linha.style.animation = 'fadeOut 0.3s ease-out';
            setTimeout(() => {
                linha.remove();
            }, 300);
        }
        
        mostrarToast(`Empresa "${dados.nome}" excluída com sucesso!`, 'sucesso');
        
        console.log('Empresa excluída:', dados);
        
        // TODO: Aqui será feita a chamada para o Servlet
        // await excluirEmpresaBackend(dados.id);
        
    }, 500);
}

/**
 * Mostra toast de notificação
 */
function mostrarToast(mensagem, tipo = 'info') {
    // Verificar se já existe um toast
    let toast = document.getElementById('toast-notificacao');
    
    if (!toast) {
        // Criar toast
        toast = document.createElement('div');
        toast.id = 'toast-notificacao';
        toast.className = 'toast-notificacao';
        document.body.appendChild(toast);
    }
    
    // Configurar ícone baseado no tipo
    let icone = 'fas fa-info-circle';
    if (tipo === 'sucesso') icone = 'fas fa-check-circle';
    if (tipo === 'erro') icone = 'fas fa-exclamation-circle';
    if (tipo === 'aviso') icone = 'fas fa-exclamation-triangle';
    
    // Configurar conteúdo
    toast.innerHTML = `
        <i class="${icone}"></i>
        <span>${mensagem}</span>
    `;
    
    // Adicionar classe de tipo
    toast.className = `toast-notificacao ${tipo}`;
    
    // Mostrar toast
    toast.classList.add('ativo');
    
    // Auto-remover após 4 segundos
    setTimeout(() => {
        toast.classList.remove('ativo');
        setTimeout(() => {
            if (toast.parentNode) {
                toast.parentNode.removeChild(toast);
            }
        }, 300);
    }, 4000);
}

/**
 * Função para integração futura com backend
 * TODO: Implementar quando conectar ao Servlet
 */
async function excluirFuncionarioBackend(id) {
    try {
        const response = await fetch(`/api/funcionarios/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            throw new Error('Erro ao excluir funcionário');
        }
        
        return await response.json();
    } catch (error) {
        console.error('Erro:', error);
        mostrarToast('Erro ao excluir funcionário', 'erro');
        throw error;
    }
}

async function excluirEmpresaBackend(id) {
    try {
        const response = await fetch(`/api/empresas/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            throw new Error('Erro ao excluir empresa');
        }
        
        return await response.json();
    } catch (error) {
        console.error('Erro:', error);
        mostrarToast('Erro ao excluir empresa', 'erro');
        throw error;
    }
}

// Exportar funções para uso global
window.mostrarModalConfirmacao = mostrarModalConfirmacao;
window.fecharModalConfirmacao = fecharModalConfirmacao;
window.excluirFuncionario = excluirFuncionario;
window.excluirEmpresa = excluirEmpresa;
window.mostrarToast = mostrarToast;





