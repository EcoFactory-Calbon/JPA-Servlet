document.addEventListener('DOMContentLoaded', () => {
    const container = document.querySelector('.valores');
    if (!container) return;

    // 1. Duplica o conteúdo para criar o loop contínuo
    const clone = container.innerHTML;
    container.innerHTML += clone;

    let scrollVelocidade = 0.6; // Valor baixo para mais suavidade (menor = mais suave)

    function loop() {
        container.scrollLeft += scrollVelocidade;

        // 2. Condição de Reinício: 
        // Quando rola a metade (o tamanho do conteúdo original), reinicia em 0.
        if (container.scrollLeft >= container.scrollWidth / 2) {
            container.scrollLeft = 0;
        }

        // 3. Usa requestAnimationFrame para rolagem suave e otimizada
        requestAnimationFrame(loop);
    }
    loop();
});