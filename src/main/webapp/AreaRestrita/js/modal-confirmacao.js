document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("modalConfirmacao");
    const botaoCancelar = modal.querySelector(".cancelar");
    const botaoConfirmar = modal.querySelector(".confirmar");
    let idEmpresaParaExcluir = null;

    // Abre modal e guarda ID
    function abrirModal(id) {
        idEmpresaParaExcluir = id;
        modal.style.display = "flex";
    }

    // Fecha modal
    function fecharModal() {
        modal.style.display = "none";
        idEmpresaParaExcluir = null;
    }

    // Confirma exclusão e envia para o servlet
    function confirmarExclusao() {
        if (!idEmpresaParaExcluir) return;

        const form = document.createElement("form");
        form.method = "post";
        form.action = "removerEmpresaServlet";

        const input = document.createElement("input");
        input.type = "hidden";
        input.name = "id";
        input.value = idEmpresaParaExcluir;

        form.appendChild(input);
        document.body.appendChild(form);
        form.submit();

        fecharModal();
    }

    // Eventos dos botões do modal
    botaoCancelar.addEventListener("click", fecharModal);
    botaoConfirmar.addEventListener("click", confirmarExclusao);

    // Fecha clicando fora do conteúdo
    modal.addEventListener("click", (e) => {
        if (e.target === modal) fecharModal();
    });

    // Fecha com ESC
    document.addEventListener("keydown", (e) => {
        if (e.key === "Escape" && modal.style.display === "flex") fecharModal();
    });

    // Botões de exclusão da tabela
    const botoesExcluir = document.querySelectorAll(".botao-excluir");
    botoesExcluir.forEach((botao) => {
        botao.addEventListener("click", () => {
            const id = botao.getAttribute("data-id");
            abrirModal(id);
        });
    });
});
