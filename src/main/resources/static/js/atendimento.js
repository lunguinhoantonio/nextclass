const LS_KEY = "nextclass_atendimentos";

function salvarAtendimento(atendimento) {
    const lista = carregarAtendimentos();
    if (!lista.find(a => a.id === atendimento.id)) {
        lista.push({
            id: atendimento.id,
            assunto: atendimento.assunto || "Sem assunto",
            criadoEm: new Date().toISOString(),
            nome: atendimento.nomeCompleto || ""
        });
        localStorage.setItem(LS_KEY, JSON.stringify(lista));
    }
}

function carregarAtendimentos() {
    try {
        return JSON.parse(localStorage.getItem(LS_KEY)) || [];
    } catch {
        return [];
    }
}

const secaoMeus = document.getElementById("meusAtendimentos");
const listaEl = document.getElementById("listaAtendimentos");

function renderizarMeusAtendimentos() {
    const lista = carregarAtendimentos();
    if (!lista.length) {
        secaoMeus.style.display = "none";
        return;
    }
    secaoMeus.style.display = "block";
    listaEl.innerHTML = "";

    lista.forEach(a => {
        const card = document.createElement("div");
        card.className = "atendimento-card";

        const info = document.createElement("div");
        info.className = "atendimento-card-info";
        const dataFormatada = new Date(a.criadoEm).toLocaleDateString("pt-BR", { day:"2-digit", month:"2-digit", year:"numeric" });
        info.innerHTML = `
            <strong>Atendimento #${a.id}</strong>
            <span>${a.assunto} &mdash; ${dataFormatada}</span>
        `;

        const btnChat = document.createElement("button");
        btnChat.textContent = "💬 Acessar Chat";
        btnChat.addEventListener("click", () => abrirChat(a.id, a.assunto, a.nome));

        card.appendChild(info);
        card.appendChild(btnChat);
        listaEl.appendChild(card);
    });
}
const modal = document.getElementById("modal");
const abrir = document.getElementById("abrirModal");
const fecharEl = document.getElementById("fecharModal");
const proxima = document.getElementById("proxima");
const enviar = document.getElementById("enviarAgendamento");
const etapa1 = document.getElementById("etapa1");
const etapa2 = document.getElementById("etapa2");
const mensagem = document.getElementById("mensagemRetorno");

const inputData = document.getElementById("data");
const hoje = new Date();
inputData.min = `${hoje.getFullYear()}-${String(hoje.getMonth()+1).padStart(2,"0")}-${String(hoje.getDate()).padStart(2,"0")}`;

abrir.addEventListener("click", () => { modal.style.display = "flex"; });

function fecharModal() {
    modal.style.display = "none";
    etapa2.classList.remove("active");
    etapa1.classList.add("active");
    esconderMensagem();
    enviar.disabled = false;
    enviar.textContent = "Enviar Agendamento";
}

fecharEl.addEventListener("click", fecharModal);
window.addEventListener("click", e => { if (e.target === modal) fecharModal(); });

document.getElementById("voltar").addEventListener("click", () => {
    etapa2.classList.remove("active");
    etapa1.classList.add("active");
});

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
    const dataObj = new Date(dataVal + "T00:00:00");
    if (dataObj.getDay() === 0 || dataObj.getDay() === 6) {
        alert("Escolha apenas dias úteis (segunda a sexta-feira).");
        return;
    }
    const hojeZ = new Date(); hojeZ.setHours(0,0,0,0);
    if (dataObj < hojeZ) {
        alert("A data de agendamento não pode estar no passado.");
        return;
    }
    const [h] = hora.split(":").map(Number);
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
    const [ano, mes, dia] = dataNascimento.split("-");

    const payload = {
        tipo: document.getElementById("tipoHidden").value,
        dataAgendamento: `${dataVal}T${hora}:00`,
        nomeCompleto,
        cpf,
        dataNascimento: `${dia}/${mes}/${ano}`,
        email,
        telefone,
        assunto: document.getElementById("assuntoHidden").value,
        descricao: document.getElementById("descricaoHidden").value
    };

    enviar.disabled = true;
    enviar.textContent = "Enviando...";

    try {
        const res = await fetch("/nextclass/atendimentos/publico", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            const criado = await res.json();
            salvarAtendimento({ ...criado, nomeCompleto });

            exibirMensagem(
                `✓ Agendamento #${criado.id} realizado! <a href="#" id="linkChat" style="color:#5B21B6;font-weight:600;">Acessar o chat</a>`,
                "sucesso"
            );
            document.getElementById("linkChat")?.addEventListener("click", e => {
                e.preventDefault();
                fecharModal();
                abrirChat(criado.id, criado.assunto, nomeCompleto);
            });

            enviar.textContent = "Enviado ✓";
            renderizarMeusAtendimentos();
        } else {
            const resposta = await res.json().catch(() => ({}));
            const msg = resposta.erro || resposta.erros?.join("<br>") || "Erro ao enviar agendamento.";
            exibirMensagem(msg, "erro");
            enviar.disabled = false;
            enviar.textContent = "Enviar Agendamento";
        }
    } catch (e) {
        exibirMensagem("Erro de conexão. Verifique sua internet e tente novamente.", "erro");
        enviar.disabled = false;
        enviar.textContent = "Enviar Agendamento";
    }
});

function exibirMensagem(html, tipo) {
    mensagem.innerHTML = html;
    mensagem.style.display = "block";
    mensagem.style.color = tipo === "sucesso" ? "#2e7d32" : "#c62828";
    mensagem.style.padding = "8px";
    mensagem.style.borderRadius = "4px";
    mensagem.style.background = tipo === "sucesso" ? "#e8f5e9" : "#ffebee";
}
function esconderMensagem() {
    mensagem.style.display = "none";
    mensagem.innerHTML = "";
}

const modalChat = document.getElementById("modalChat");
const fecharChat = document.getElementById("fecharChat");
const chatMsgs = document.getElementById("chatMensagens");
const chatVazio = document.getElementById("chatVazio");
const chatInput = document.getElementById("chatInput");
const chatEnviar = document.getElementById("chatEnviar");
const chatErro = document.getElementById("chatErro");
const chatNomeBar = document.getElementById("chatAnonBar");
const chatNomeAnon = document.getElementById("chatNomeAnon");

let chatAtendimentoId = null;
let chatPoll = null;

function abrirChat(id, assunto, nomePadrao) {
    chatAtendimentoId = id;
    document.getElementById("chatTitulo").textContent = `Atendimento #${id}`;
    document.getElementById("chatAssunto").textContent = assunto || "";
    const token = localStorage.getItem("token");
    if (!token) {
        chatNomeBar.style.display = "block";
        if (nomePadrao) chatNomeAnon.value = nomePadrao;
    } else {
        chatNomeBar.style.display = "none";
    }

    modalChat.style.display = "flex";
    carregarMensagens();
    chatPoll = setInterval(carregarMensagens, 5000);
}

fecharChat.addEventListener("click", () => {
    modalChat.style.display = "none";
    clearInterval(chatPoll);
});
window.addEventListener("click", e => {
    if (e.target === modalChat) { modalChat.style.display = "none"; clearInterval(chatPoll); }
});

async function carregarMensagens() {
    if (!chatAtendimentoId) return;
    try {
        const res = await fetch(`/nextclass/atendimentos/${chatAtendimentoId}/mensagens`);
        if (!res.ok) return;
        const msgs = await res.json();
        chatMsgs.querySelectorAll(".bubble-wrap").forEach(el => el.remove());
        if (!msgs.length) {
            chatVazio.style.display = "block";
        } else {
            chatVazio.style.display = "none";
            msgs.forEach(m => chatMsgs.appendChild(criarBolha(m)));
            chatMsgs.scrollTop = chatMsgs.scrollHeight;
        }
    } catch (_) {}
}

function criarBolha(msg) {
    const isAtendente = msg.tipoRemetente === "ATENDENTE";
    const wrap = document.createElement("div");
    wrap.className = "bubble-wrap";
    wrap.style.cssText = `display:flex; flex-direction:column; max-width:75%; align-self:${isAtendente ? "flex-end" : "flex-start"};`;

    const bubble = document.createElement("div");
    bubble.style.cssText = `padding:10px 14px; border-radius:18px; font-size:14px; line-height:1.45; word-break:break-word;
        background:${isAtendente ? "#5B21B6" : "#fff"};
        color:${isAtendente ? "#fff" : "#111"};
        border-bottom-${isAtendente ? "right" : "left"}-radius:4px;
        box-shadow:0 1px 2px rgba(0,0,0,.1);`;
    bubble.textContent = msg.conteudo;

    const meta = document.createElement("div");
    meta.style.cssText = `font-size:11px; color:#9ca3af; margin-top:3px; ${isAtendente ? "text-align:right;" : ""}`;
    const hora = new Date(msg.enviadoEm).toLocaleTimeString("pt-BR", { hour:"2-digit", minute:"2-digit" });
    meta.textContent = `${msg.nomeRemetente || "Anônimo"} · ${hora}`;

    wrap.appendChild(bubble);
    wrap.appendChild(meta);
    return wrap;
}

async function enviarMensagem() {
    const conteudo = chatInput.value.trim();
    if (!conteudo) return;

    const token = localStorage.getItem("token");

    if (!token) {
        const nome = chatNomeAnon.value.trim();
        if (!nome) {
            chatErro.textContent = "Informe seu nome antes de enviar.";
            chatNomeAnon.focus();
            return;
        }
    }

    const body = { conteudo };
    if (!token) body.nomeRemetente = chatNomeAnon.value.trim();

    const headers = { "Content-Type": "application/json" };
    if (token) headers["Authorization"] = `Bearer ${token}`;

    chatEnviar.disabled = true;
    chatErro.textContent = "";

    try {
        const res = await fetch(`/nextclass/atendimentos/${chatAtendimentoId}/mensagens/solicitante`, {
            method: "POST", headers, body: JSON.stringify(body)
        });
        if (!res.ok) {
            const err = await res.json().catch(() => ({}));
            throw new Error(err.message || `Erro ${res.status}`);
        }
        const nova = await res.json();
        chatVazio.style.display = "none";
        chatMsgs.appendChild(criarBolha(nova));
        chatMsgs.scrollTop = chatMsgs.scrollHeight;
        chatInput.value = "";
        chatInput.style.height = "auto";
    } catch (e) {
        chatErro.textContent = e.message;
    } finally {
        chatEnviar.disabled = false;
    }
}

chatEnviar.addEventListener("click", enviarMensagem);
chatInput.addEventListener("keydown", e => {
    if (e.key === "Enter" && !e.shiftKey) { e.preventDefault(); enviarMensagem(); }
});
chatInput.addEventListener("input", () => {
    chatInput.style.height = "auto";
    chatInput.style.height = Math.min(chatInput.scrollHeight, 100) + "px";
});

/* ============================================================
   INIT — renderiza atendimentos salvos ao carregar a página
   ============================================================ */
renderizarMeusAtendimentos();