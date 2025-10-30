// ================= REGEX DE VALIDAÇÃO =================
const regexTel = /^\(?[0-9]{2}[) ]*[0-9]{5}[- ]*[0-9]{4}$/;
const regexSenha = /^(?=.*[A-Z])(?=.*[!#@$%&])(?=.*[0-9])(?=.*[a-z]).{6,15}$/;
const regexCPF = /^(?:\d{3}\.\d{3}\.\d{3}-\d{2}|\d{11})$/;
const regexEmail =/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/;
const regexCracha = /^\d+$/;
const regexCNPJ = /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/;

// ======================================================
// Funções de validação
const validarTelefone = (t) => regexTel.test(t);
const validarSenha = (s) => regexSenha.test(s);
const validarCPF = (c) => regexCPF.test(c);
const validarEmail = (e) => regexEmail.test(e);
const validarCracha = (c) => regexCracha.test(c);
const validarCNPJ = (c) => regexCNPJ.test(c);

// ======================================================
// Estilo visual para erro / sucesso
// ======================================================
function marcarErro(campo, mensagem) {
  campo.classList.add("erro-input");
  campo.classList.remove("valido-input");
  campo.classList.add("shake");
  setTimeout(() => campo.classList.remove("shake"), 400);

  const erro = campo.nextElementSibling;
  if (erro) {
    erro.textContent = mensagem;
    erro.classList.add("msg-erro");
  }
}

function marcarValido(campo) {
  campo.classList.remove("erro-input");
  campo.classList.add("valido-input");

  const erro = campo.nextElementSibling;
  if (erro) erro.textContent = "";
}

// ======================================================
// Validação em tempo real
// ======================================================
document.addEventListener("DOMContentLoaded", () => {
  const campos = {
    telefone: validarTelefone,
    senha: validarSenha,
    cpf: validarCPF,
    email: validarEmail,
    cracha: validarCracha,
    cnpj: validarCNPJ,
  };

  const camposInput = ["telefone", "senha", "cpf", "email", "cracha"];
  const camposBlur = ["cnpj"];

  camposInput.forEach((id) => {
    const campo = document.getElementById(id);
    if (campo) {
      campo.addEventListener("input", function () {
        if (!campos[id](this.value))
          marcarErro(this, `${this.previousElementSibling.textContent} inválido.`);
        else marcarValido(this);
      });
    }
  });

  camposBlur.forEach((id) => {
    const campo = document.getElementById(id);
    if (campo) {
      campo.addEventListener("blur", function () {
        const valor = this.value.trim();
        const nomeCampo = this.previousElementSibling.textContent;

        if (!valor) {
          this.classList.remove("erro-input", "valido-input");
          const erro = this.nextElementSibling;
          if (erro) erro.textContent = "";
        } else if (!campos[id](valor)) {
          marcarErro(this, `${nomeCampo} incorreto. Use o formato: 00.000.000/0000-00`);
        } else {
          marcarValido(this);
        }
      });
      campo.removeEventListener("input", null);
    }
  });

  // ======================================================
  // FORMULÁRIO DE CADASTRO
  // ======================================================
  const formCadastro = document.getElementById("cadastro");
  if (formCadastro) {
    formCadastro.addEventListener("submit", function (e) {
      e.preventDefault();
      let valido = true;
      const dados = {};

      this.querySelectorAll("input[required]").forEach((input) => {
        const id = input.id;
        const valor = input.value.trim();
        const funcValida = campos[id];
        const nomeCampo = input.previousElementSibling.textContent;

        if (!valor) {
          marcarErro(input, "Campo obrigatório.");
          valido = false;
        } else if (funcValida && !funcValida(valor)) {
          if (id === "cnpj") {
            marcarErro(input, `${nomeCampo} incorreto. Use o formato: 00.000.000/0000-00`);
          } else {
            marcarErro(input, `${nomeCampo} inválido.`);
          }
          valido = false;
        } else {
          marcarValido(input);
          dados[id] = valor;
        }
      });

      if (valido) {
        localStorage.setItem("dadosCadastro", JSON.stringify(dados));
        alert("✅ Cadastro validado com sucesso!");
        window.location.href = "../criar-senha.jsp";
      } else {
        alert("❌ Corrija os campos destacados antes de continuar.");
      }
    });
  }

  // ======================================================
  // FORMULÁRIO DE ENTRAR (LOGIN)
  // ======================================================
  const formEntrar = document.getElementById("formEntrar");
  const cnpjLogin = document.getElementById("cnpjLogin");

  if (formEntrar && cnpjLogin) {
    formEntrar.addEventListener("submit", (e) => {
      e.preventDefault();
      const cnpj = cnpjLogin.value.trim();

      if (!regexCNPJ.test(cnpj)) {
        marcarErro(cnpjLogin, "CNPJ incorreto. Use o formato: 00.000.000/0000-00");
        return; // 🔒 Impede o envio do formulário
      }

      marcarValido(cnpjLogin);
      alert("✅ CNPJ validado com sucesso!");
      window.location.href = "/LandingPage/visualizar.html";
    });

    // Validação em tempo real
    cnpjLogin.addEventListener("input", () => {
      const valor = cnpjLogin.value.trim();
      if (!valor) {
        cnpjLogin.classList.remove("erro-input", "valido-input");
        const erro = cnpjLogin.nextElementSibling;
        if (erro) erro.textContent = "";
      } else if (!regexCNPJ.test(valor)) {
        marcarErro(cnpjLogin, "CNPJ incorreto. Use o formato: 00.000.000/0000-00");
      } else {
        marcarValido(cnpjLogin);
      }
    });
  }


  // ======================================================
  // FORMULÁRIO DE CRIAR SENHA
  // ======================================================
  const formSenha = document.getElementById("formSenha");
  if (formSenha) {
    const senha = document.getElementById("senha");
    const confirmar = document.getElementById("confirmarSenha");

    formSenha.addEventListener("submit", (e) => {
      e.preventDefault();
      let valido = true;

      if (!regexSenha.test(senha.value)) {
        marcarErro(
          senha,
          "A senha deve ter 6–15 caracteres, 1 maiúscula, 1 minúscula, 1 número e 1 símbolo (!#@$%&)."
        );
        valido = false;
      } else marcarValido(senha);

      if (senha.value !== confirmar.value) {
        marcarErro(confirmar, "As senhas não coincidem.");
        valido = false;
      } else marcarValido(confirmar);

      if (valido) {
        const dados = JSON.parse(localStorage.getItem("dadosCadastro")) || {};
        dados.senha = senha.value;
        localStorage.setItem("dadosCadastro", JSON.stringify(dados));
        alert("✅ Senha criada com sucesso!");
        window.location.href = "/LandingPage/visualizar.html";
      }
    });

    senha.addEventListener("input", () => {
      if (regexSenha.test(senha.value)) marcarValido(senha);
      else marcarErro(senha, "Senha fraca.");
    });
  }

  // ======================================================
  // PÁGINA DE VISUALIZAR
  // ======================================================
  const visualizar = document.getElementById("dadosUsuario");
  if (visualizar) {
    const dados = JSON.parse(localStorage.getItem("dadosCadastro"));
    if (dados) {
      visualizar.innerHTML = `
        <h3>Dados Cadastrados:</h3>
        <ul>
          ${Object.entries(dados)
            .map(([chave, valor]) => `<li><strong>${chave}:</strong> ${valor}</li>`)
            .join("")}
        </ul>
      `;
    }
  }
});
