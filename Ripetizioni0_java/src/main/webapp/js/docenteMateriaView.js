function loadDocenteMateriaView(m){
    vue.dataContainer = [];
    $.getJSON("docenteMateria", {materia: m}, function (data){
        for(let i = 0; i < data.length; i++) {
            vue.dataContainer[i] = (data[i].nome + " " + data[i].cognome);
        }
         addDocMat(vue.dataContainer)
    });
}

function addDocMat(data){
    let select = document.getElementById("selectDocente");
    if(select.childNodes.length > 1){
        return;
    }

    for(let i = 0; i < data.length; i++) {
        let option = document.createElement("option");
        option.setAttribute("value", data[i]);
        option.innerHTML = data[i];
        select.appendChild(option);
    }
}

function rimDocMat(data){
    let select = document.getElementById("selectDocenteRim");
    for(let i = 0; i < data.length; i++) {
        let option = document.createElement("option");
        option.setAttribute("value", data[i]);
        option.innerHTML = data[i];
        select.appendChild(option);
    }
}

function aggiungiDocenteMateria(){
    let select = document.getElementById("selectDocente");
    let docente = select.value.split(" ");
    let m = vue.materia;

    $.post("docenteMateria", {nome: docente[0], cognome: docente[1], materia: m, operazione: "aggiungi"}, function (data){
        console.log("dataAdd: " + data);
    });
}

function rimuoviDocenteMateria(){
    let select = document.getElementById("selectDocenteRim");
    let docente = select.value.split(" ");
    let m = vue.materia;

    $.post("docenteMateria", {nome: docente[0], cognome: docente[1], materia: m, operazione: "rimuovi"}, function (data){
        console.log("dataRim: " + data);
    });
}

function validateFormAggDocMat() {
    let select = document.getElementById("selectDocente");
    let docente = select.value;
    console.log("docente1: " + docente);
    if (docente === "empty") {
        let lblState = document.getElementById("lbl-state-mat-doc");
        lblState.innerHTML = "Devi riempire tutti i campi";
        return false;
    }

    aggiungiDocenteMateria();
    return true;
}

function validateFormRimDocMat() {
    let select = document.getElementById("selectDocenteRim");
    let docente = select.value;
    console.log("docente2: " + docente);
    if (docente === "empty") {
        let lblState = document.getElementById("lbl-state-mat-doc2");
        lblState.innerHTML = "Devi riempire tutti i campi";
        return false;
    }

    rimuoviDocenteMateria();
    return true;
}

