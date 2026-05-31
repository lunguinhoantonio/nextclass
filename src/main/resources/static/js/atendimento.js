const modal = document.getElementById("modal");
const abrir = document.getElementById("abrirModal");
const fechar = document.getElementById("fecharModal");
const proxima = document.getElementById("proxima");
const enviar = document.getElementById("enviarAgendamento");
const etapa1 = document.getElementById("etapa1");
const etapa2 = document.getElementById("etapa2");
const mensagem = document.getElementById("mensagemRetorno");

const inputData = document.getElementById("data");
const hoje = new Date();
const anoHoje  = hoje.getFullYear();
const mesHoje  = String(hoje.getMonth() + 1).padStart(2, "0");
const diaHoje  = String(hoje.getDate()).padStart(2, "0");
inputData.min = `${anoHoje}-${mesHoje}-${diaHoje}`;

// ABRIR
abrir.addEventListener("click", () => {
    modal.style.display = "flex";
});

// FECHAR
fechar.addEventListener("click", () => {
    modal.style.display = "none";
});

// PRÓXIMA ETAPA
proxima.addEventListener("click", () => {
    const tipo = document.getElementById("tipo").value;
    const dataVal = document.getElementById("data").value;
    const hora = document.getElementById("hora").value;
    const assunto = document.getElementById("assunto").value.trim();
    const descricao = document.getElementById("descricao").value.trim();

    if (!tipo || !dataVal || !hora || !assunto || !descricao) {
        alert("Preencha todos os campos antes de continuar.");
        return;
    }

    const data = new Date(dataVal + "T00:00:00");
    const diaSemana = data.getDay();
    if (diaSemana === 0 || diaSemana === 6) {
        alert("Escolha apenas dias úteis (segunda a sexta-feira).");
        return;
    }

    const hoje = new Date(); hoje.setHours(0,0,0,0);
    if (data < hoje) {
        alert("A data de agendamento não pode estar no passado.");
        return;
    }

    const [h, m] = hora.split(":").map(Number);
    if (h < 8 || h >= 19) {
        alert("Escolha um horário entre 08:00 e 19:00.");
        return;
    }

    document.getElementById("tipoHidden").value = tipo;
    document.getElementById("dataHidden").value = dataVal;
    document.getElementById("horaHidden").value = hora;
    document.getElementById("assuntoHidden").value = assunto;
    document.getElementById("descricaoHidden").value = descricao;

    etapa1.classList.remove("active");
    etapa2.classList.add("active");
});

enviar.addEventListener("click", async () => {
    const nomeCompleto = document.getElementById("nomeCompleto").value.trim();
    const cpf = document.getElementById("cpf").value.trim();
    const dataNascimento = document.getElementById("dataNascimento").value;
    const email = document.getElementById("email").value.trim();
    const telefone = document.getElementById("telefone").value.trim();

    if (!nomeCompleto || !cpf || !dataNascimento || !email || !telefone) {
        exibirMensagem("Preencha todos os campos obrigatórios.", "erro");
        return;
    }

    if (!/^\d{11}$/.test(cpf)) {
        exibirMensagem("CPF deve conter exatamente 11 dígitos numéricos.", "erro");
        return;
    }

    if (!/^\d{11}$/.test(telefone)) {
        exibirMensagem("Telefone deve conter exatamente 11 dígitos numéricos.", "erro");
        return;
    }

    const dataVal = document.getElementById("dataHidden").value;
    const hora = document.getElementById("horaHidden").value;
    const dataAgendamento = `${dataVal}T${hora}:00`;

    const [ano, mes, dia] = dataNascimento.split("-");
    const dataNascimentoFormatada = `${dia}/${mes}/${ano}`;

    const payload = {
        tipo: document.getElementById("tipoHidden").value,
        dataAgendamento: dataAgendamento,
        nomeCompleto: nomeCompleto,
        cpf: cpf,
        dataNascimento: dataNascimentoFormatada,
        email: email,
        telefone: telefone,
        assunto: document.getElementById("assuntoHidden").value,
        descricao: document.getElementById("descricaoHidden").value
    };

    enviar.disabled = true;
    enviar.textContent = "Enviando...";

    try {
        const solicitanteId = 1;

        const response = await fetch(`/nextclass/atendimentos/publico`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            exibirMensagem("Agendamento realizado com sucesso! Em breve entraremos em contato.", "sucesso");
            enviar.textContent = "Enviado ✓";
            setTimeout(fecharModal, 2500);
        } else {
            const resposta = await response.json();
            let msg = "Erro ao enviar agendamento.";
            if (resposta.erro) {
                msg = resposta.erro;
            } else if (resposta.erros && resposta.erros.length > 0) {
                msg = resposta.erros.join("<br>");
            }
            exibirMensagem(msg, "erro");
            enviar.disabled = false;
            enviar.textContent = "Enviar Agendamento";
        }
    } catch (e) {
        console.error("Erro na requisição:", e);
        exibirMensagem("Erro de conexão. Verifique sua internet e tente novamente.", "erro");
        enviar.disabled = false;
        enviar.textContent = "Enviar Agendamento";
    }
});

/* FECHAR CLICANDO FORA */
function fecharModal(){
    modal.style.display = "none";
    etapa2.classList.remove("active");
    etapa1.classList.add("active");
    esconderMensagem();
    enviar.disabled = false;
    enviar.textContent = "Enviar Agendamento";
}

// FECHAR NO X
fechar.addEventListener("click", fecharModal);

// FECHAR CLICANDO FORA
window.addEventListener("click", (e) => {
    if(e.target === modal) fecharModal();
});

function exibirMensagem(texto, tipo) {
    mensagem.textContent = texto;
    mensagem.style.display = "block";
    mensagem.style.color = tipo === "sucesso" ? "#2e7d32" : "#c62828";
    mensagem.style.padding = "8px";
    mensagem.style.borderRadius = "4px";
    mensagem.style.background = tipo === "sucesso" ? "#e8f5e9" : "#ffebee";
}

function esconderMensagem() {
    mensagem.style.display = "none";
    mensagem.textContent   = "";
}