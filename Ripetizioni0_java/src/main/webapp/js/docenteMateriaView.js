function loadDocenteMateriaView(m){
    $.getJSON("docenteMateria", {materia: m}, function (data){
        console.log(data);
        for(let i = 0; i < data.length; i++) {
            let select = document.getElementById("selectDocente");
            let option = document.createElement("option");
            option.setAttribute("value", data[i].nome + " " + data[i].cognome);
            option.innerHTML = data[i].nome + " " + data[i].cognome;
            select.appendChild(option);
        }
    });
}

function aggiungiDocenteMateria(){
    let select = document.getElementById("selectDocente");
    let docente = select.value.split(" ");
    let m = vue.materia;

    $.post("docenteMateria", {nome: docente[0], cognome: docente[1], materia: m}, function (){});
}


function validateFormAggDocMat() {
    let select = document.getElementById("selectDocente");
    let docente = select.value.split(" ");

    if (docente.length < 2) {
        let lblState = document.getElementById("lbl-state-mat-doc");
        lblState.innerHTML = "Devi riempire tutti i campi";
        return false;
    }

    aggiungiDocenteMateria();
    return true;
}
