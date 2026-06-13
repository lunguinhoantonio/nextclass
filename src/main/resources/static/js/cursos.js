/* ============================================================
   MATRÍCULA — modal de confirmação
   ============================================================ */

const modalMatricula = document.getElementById("modalMatricula");
const fecharMatriculaEl = document.getElementById("fecharMatricula");
const btnCancelar = document.getElementById("btnCancelarMatricula");
const btnConfirmar = document.getElementById("btnConfirmarMatricula");
const btnFecharResultado = document.getElementById("btnFecharResultado");
const nomeConfirmacao = document.getElementById("nomeConfirmacao");
const matriculaConfirmacao = document.getElementById("matriculaConfirmacao");
const matriculaResultado = document.getElementById("matriculaResultado");
const matriculaIcone = document.getElementById("matriculaIcone");
const matriculaTitulo = document.getElementById("matriculaTitulo");
const matriculaTexto = document.getElementById("matriculaTexto");

let cursoIdPendente = null;

function confirmarMatricula(btn) {
    cursoIdPendente = btn.dataset.cursoId;
    nomeConfirmacao.textContent = btn.dataset.cursoNome;
    matriculaConfirmacao.style.display = "block";
    matriculaResultado.style.display = "none";
    modalMatricula.style.display = "flex";
}

function fecharModalMatricula() {
    modalMatricula.style.display = "none";
    cursoIdPendente = null;
}

fecharMatriculaEl.addEventListener("click", fecharModalMatricula);
btnCancelar.addEventListener("click", fecharModalMatricula);
window.addEventListener("click", e => { if (e.target === modalMatricula) fecharModalMatricula(); });

btnConfirmar.addEventListener("click", async () => {
    if (!cursoIdPendente) return;

    btnConfirmar.disabled = true;
    btnConfirmar.textContent = "Matriculando...";

    try {
        const turmasRes = await fetch(`/nextclass/turmas?cursoId=${cursoIdPendente}&ativa=true`, {
            credentials: "include"
        });

        if (turmasRes.status === 401 || turmasRes.status === 403) {
            window.location.href = "/login";
            return;
        }

        if (!turmasRes.ok) throw new Error("Não foi possível buscar as turmas do curso.");

        const turmas = await turmasRes.json();
        if (!turmas.length) throw new Error("Nenhuma turma ativa disponível para este curso no momento.");

        const turmaId = turmas[0].id;

        const matRes = await fetch("/nextclass/matriculas", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify({ turmaId })
        });

        if (matRes.status === 401 || matRes.status === 403) {
            window.location.href = "/login";
            return;
        }

        if (matRes.ok) {
            const matricula = await matRes.json();
            mostrarResultado(
                "✓",
                "Matrícula realizada!",
                `Você foi matriculado com sucesso. Status: ${matricula.status}.`,
                "sucesso"
            );
        } else {
            const err = await matRes.json().catch(() => ({}));
            throw new Error(err.erro || err.message || "Erro ao realizar matrícula.");
        }

    } catch (e) {
        mostrarResultado("✗", "Não foi possível matricular", e.message, "erro");
    } finally {
        btnConfirmar.disabled = false;
        btnConfirmar.textContent = "Confirmar";
    }
});

function mostrarResultado(icone, titulo, texto, tipo) {
    matriculaConfirmacao.style.display = "none";
    matriculaResultado.style.display = "block";
    matriculaIcone.textContent = icone;
    matriculaIcone.className = `modal-resultado-icone ${tipo}`;
    matriculaTitulo.textContent = titulo;
    matriculaTexto.textContent = texto;
}

btnFecharResultado.addEventListener("click", fecharModalMatricula);