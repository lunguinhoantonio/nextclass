const modal = document.getElementById("modal");

const abrir = document.getElementById("abrirModal");

const fechar = document.getElementById("fecharModal");

const proxima = document.getElementById("proxima");

const etapa1 = document.getElementById("etapa1");

const etapa2 = document.getElementById("etapa2");

/* ABRIR */

abrir.addEventListener("click", () => {

    modal.style.display = "flex";

});

/* FECHAR */

fechar.addEventListener("click", () => {

    modal.style.display = "none";

});

/* PRÓXIMA ETAPA */

proxima.addEventListener("click", () => {

    const dataInput = document.getElementById("data").value;

    const data = new Date(dataInput);

    const diaSemana = data.getDay();

    /* BLOQUEIA SÁBADO E DOMINGO */

    if(diaSemana === 0 || diaSemana === 6){

        alert("Escolha apenas dias úteis.");

        return;
    }

    etapa1.classList.remove("active");

    etapa2.classList.add("active");

});

/* FECHAR CLICANDO FORA */

function fecharModal(){

    modal.style.display = "none";

    /* VOLTA PARA PRIMEIRA ETAPA */

    etapa2.classList.remove("active");

    etapa1.classList.add("active");

}

/* FECHAR NO X */

fechar.addEventListener("click", fecharModal);

/* FECHAR CLICANDO FORA */

window.addEventListener("click", (e) => {

    if(e.target === modal){

        fecharModal();

    }

});