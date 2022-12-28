function loadDocentiViewData(){
    let ul = document.getElementById("lista-docenti");
    $.getJSON("docenti", {}, function (data){
        for(let i = 0; i < data.length; i++) {
            let obj = data[i];

            let li = document.createElement("li");
            li.setAttribute("class", "table-row")

            let firstCol = document.createElement("div");
            firstCol.setAttribute("class", "col col-1");
            firstCol.setAttribute("data-label", "Docente");
            firstCol.innerHTML = obj.nome + " " + obj.cognome;

            let secondCol = document.createElement("div");
            secondCol.setAttribute("class", "col col-2");
            secondCol.setAttribute("data-label", "Materie");
            secondCol.innerHTML = obj.materie.join(", ");

            let thirdCol = document.createElement("div");
            thirdCol.setAttribute("class", "col col-3");

            let btnCancella = document.createElement("button");
            btnCancella.setAttribute("class", "btn prenotazioni-btn-cancella");
            btnCancella.setAttribute("onclick", "rimuoviDocente('" + obj.idDocente + "')");
            btnCancella.innerHTML = "cancella";
            // let btnAggiungi = document.createElement("button");
            // btnAggiungi.setAttribute("class", "btn prenotazioni-btn-effettuata");
            // btnAggiungi.setAttribute("onclick", "aggiungiDocenteMateriaView('" + obj.titolo + "')");
            // btnAggiungi.innerHTML = "+ docente";
            // thirdCol.setAttribute("class", "col-margin col col-2");
            // thirdCol.appendChild(btnAggiungi);
            thirdCol.appendChild(btnCancella);

            li.appendChild(firstCol);
            li.appendChild(secondCol);
            li.appendChild(thirdCol);

            ul.appendChild(li);
        }
    });
}

function aggiungiDocenteClick(){
    let inputNome = document.getElementById("docenteNome");
    let inputCognome = document.getElementById("docenteCognome");

    $.get("aggiungiDocente", {nome: inputNome.value, cognome: inputCognome.value}, function (data){
        if(data !== "202"){
            alert("Il docente inserito esiste giá");
        }
    });
}

function validateFormAggDocente() {
    let inputNome = document.getElementById("docenteNome");
    let inputCognome = document.getElementById("docenteCognome");

    if (inputNome.value === "" || inputCognome.value === "") {
        let lblState = document.getElementById("lbl-state-docente");
        lblState.innerHTML = "Devi riempire tutti i campi";
        return false;
    }

    aggiungiDocenteClick();
    return true;
}

function rimuoviDocente(docente){
    let alertText = "Sei sicuro di voler cancellare questo docente?";
    if(confirm(alertText)){
        $.get("cancellaDocente", {idDocente: docente}, function(){
            location.reload();
        });
    }
}
