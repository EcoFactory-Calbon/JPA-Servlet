/**
 * Sistema de Navegação do Sidebar - Versão corrigida e otimizada
 */

document.addEventListener('DOMContentLoaded', function () {
  inicializarSidebar();
  instalarPersistenciaDeDestaque();
});

/* -------------------- inicialização -------------------- */
function inicializarSidebar() {
  const sidebarItems = document.querySelectorAll('.sidebar-item');

  // adiciona efeitos de clique/hover
  adicionarEfeitoClique(sidebarItems);

  // aplica destaque inicial com base na URL
  destacarItemAtual();
}

/* -------------------- identificar página -------------------- */
function identificarPaginaAtual() {
  const pathname = window.location.pathname;

  // mapeamento usado para identificar páginas dentro de /AreaRestrita
  const mapeamentoPaginas = {
    '../inicio.jsp': 'inicio.jsp',
    '../visualizar-funcionario.jsp': 'visualizar-funcionario.jsp',
    '/AreaRestrita/visualizar-empresa.jsp': 'visualizar-empresa.jsp',
    '/AreaRestrita/inserir.html': 'inserir.html',
    '/AreaRestrita/inserir-funcionario.html': 'inserir-funcionario.html',
    '/AreaRestrita/inserir-empresa.html': 'inserir-empresa.html',
    '/AreaRestrita/editar.html': 'editar.html',
    '/AreaRestrita/editar-funcionario.html': 'editar-funcionario.html',
    '/AreaRestrita/editar-empresa.html': 'editar-empresa.html'
  };

  if (mapeamentoPaginas[pathname]) return mapeamentoPaginas[pathname];

  for (const identificador of Object.values(mapeamentoPaginas)) {
    if (pathname.includes(identificador)) return identificador;
  }

  const nomeArquivo = pathname.split('/').pop();
  return (nomeArquivo && nomeArquivo !== '') ? nomeArquivo : null;
}

/* -------------------- efeitos no sidebar -------------------- */
function adicionarEfeitoClique(sidebarItems) {
  sidebarItems.forEach(item => {
    const href = item.getAttribute('href') || '';

    // pular apenas o link de logout (que leva para LandingPage)
    if (href.includes('LandingPage')) return;

    // clique no item do sidebar
    item.addEventListener('click', function (e) {
      // evita interferir em links externos ou de outra pasta
      // considerar interno todo link que contenha '/AreaRestrita'
      if (!href.includes('/AreaRestrita/')) return;

      // feedback visual de clique
      this.style.transform = 'scale(0.95)';
      this.style.opacity = '0.8';
      setTimeout(() => {
        this.style.transform = '';
        this.style.opacity = '';
      }, 150);

      // marca o item como ativo imediatamente
      sidebarItems.forEach(si => si.classList.remove('active'));
      this.classList.add('active');

      // se for navegação para outra página, mostrar loading
      // (comparar pathname relativo, para evitar false positives)
      const destino = new URL(href, window.location.origin);
      if (destino.pathname !== window.location.pathname) {
        mostrarLoadingNavegacao();
        // navegação seguirá normalmente pelo link <a>
      }
    });

    // hover
    item.addEventListener('mouseenter', function () {
      if (!this.classList.contains('active')) {
        this.style.transform = 'translateX(2px)';
        this.style.transition = 'all 0.18s ease';
      }
    });

    item.addEventListener('mouseleave', function () {
      if (!this.classList.contains('active')) {
        this.style.transform = 'translateX(0)';
      }
    });
  });
}

/* -------------------- loading -------------------- */
function mostrarLoadingNavegacao() {
  // evita criar múltiplos overlays
  if (document.getElementById('loading-navegacao')) return;

  const loadingOverlay = document.createElement('div');
  loadingOverlay.id = 'loading-navegacao';
  loadingOverlay.style.cssText = `
    position: fixed;
    top: 0; left: 0; right: 0; bottom: 0;
    background: rgba(7,16,28,0.8);
    display: flex; align-items: center; justify-content: center;
    z-index: 9999; backdrop-filter: blur(2px);
  `;

  const spinner = document.createElement('div');
  spinner.style.cssText = `
    width: 40px; height: 40px;
    border: 3px solid rgba(28,213,234,0.25);
    border-top: 3px solid #1cd5ea;
    border-radius: 50%;
    animation: spin 1s linear infinite;
  `;

  if (!document.querySelector('#loading-spinner-style')) {
    const style = document.createElement('style');
    style.id = 'loading-spinner-style';
    style.textContent = `
      @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
      }
    `;
    document.head.appendChild(style);
  }

  loadingOverlay.appendChild(spinner);
  document.body.appendChild(loadingOverlay);

  // remove após 1s por segurança (a própria navegação trocara a página);
  setTimeout(() => {
    const el = document.getElementById('loading-navegacao');
    if (el) el.remove();
  }, 1000);
}

/* -------------------- destacar item -------------------- */
function destacarItemAtual() {
  const pathname = window.location.pathname;
  const sidebarItems = document.querySelectorAll('.sidebar-item');

  // garante que todos comecem sem active
  sidebarItems.forEach(item => item.classList.remove('active'));

  // identificar item a partir da URL
  const identificador = identificarPaginaAtual();
  if (!identificador) return;

  const selector = `a[href*="${identificador}"]`;
  const itemAtivo = document.querySelector(selector);

  if (itemAtivo && itemAtivo.classList.contains('sidebar-item')) {
    itemAtivo.classList.add('active');
  } else {
    // fallback: se nenhum item encontrado, tenta aplicar active ao primeiro link que contenha '/AreaRestrita'
    const fallback = document.querySelector('a[href*="/AreaRestrita/"]');
    if (fallback && fallback.classList.contains('sidebar-item')) {
      fallback.classList.add('active');
    }
  }
}

/* -------------------- manutenção do destaque durante interação -------------------- */
function instalarPersistenciaDeDestaque() {
  // evita recolocar destaque quando o clique foi dentro do próprio sidebar
  document.body.addEventListener('click', function (e) {
    const sidebar = document.querySelector('.sidebar');
    if (sidebar && sidebar.contains(e.target)) return; // clique no menu -> ignora

    // reaplica destaque com base na URL atual
    destacarItemAtual();
  });

  // garante persistência ao focar em elementos do formulário (inputs, selects, textareas, botões)
  const formElements = document.querySelectorAll('input, textarea, select, button');
  formElements.forEach(el => {
    el.addEventListener('focus', function () {
      destacarItemAtual();
    });
  });

  // também reaplica quando o usuário usa teclado (tab) - captura tecla Tab global
  document.addEventListener('keydown', function (e) {
    if (e.key === 'Tab') {
      // debounced para permitir mudança real de foco
      setTimeout(destacarItemAtual, 10);
    }
  });
}

/* -------------------- export (opcional) -------------------- */
window.destacarItemAtual = destacarItemAtual;
