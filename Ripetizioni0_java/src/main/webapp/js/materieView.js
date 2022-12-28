function setMaterieData(){
    var ul = document.getElementById("lista-materie");

    if(ul != null){
        while(ul.childNodes.length > 1){
            ul.lastChild.remove();
        }
    }

    $.getJSON("ripetizioni", {}, function(data){
        let ul = document.getElementById("lista-materie");

        for(let i = 0; i < data.length; i++) {
            let obj = data[i];

            let li = document.createElement("li");
            li.setAttribute("class", "table-row")

            let firstCol = document.createElement("div");
            firstCol.setAttribute("class", "col col-1");
            firstCol.setAttribute("data-label", "Materia");
            firstCol.innerHTML = obj.titolo;

            let secondCol = document.createElement("div");
            secondCol.setAttribute("class", "col col-2");
            secondCol.setAttribute("data-label", "Insegnante");
            secondCol.innerHTML = obj.insegnanti.join(", ");

            let thirdCol = document.createElement("div");
            thirdCol.setAttribute("class", "col col-3");

            if(vueHeader.userType === 'amministratore'){
                let btnCancella = document.createElement("button");
                btnCancella.setAttribute("class", "btn prenotazioni-btn-cancella");
                btnCancella.setAttribute("onclick", "cancellaMateria('" + obj.titolo + "')");
                btnCancella.innerHTML = "cancella";
                let btnAggiungi = document.createElement("button");
                btnAggiungi.setAttribute("class", "btn prenotazioni-btn-effettuata");
                btnAggiungi.setAttribute("onclick", "changeDocenteMateriaView('" + obj.titolo + "')");
                btnAggiungi.innerHTML = "+ docente";
                thirdCol.setAttribute("class", "col-margin col col-2");
                thirdCol.appendChild(btnAggiungi);
                thirdCol.appendChild(btnCancella);
            }else {
                let btnPrenota = document.createElement("button");
                btnPrenota.setAttribute("class", "btn btn-prenota");
                btnPrenota.setAttribute("titolo", obj.titolo);
                btnPrenota.setAttribute("onclick", "onPrenotaClick(this)");
                btnPrenota.innerHTML = "Prenota";
                thirdCol.appendChild(btnPrenota);
            }

            li.appendChild(firstCol);
            li.appendChild(secondCol);
            li.appendChild(thirdCol);

            ul.appendChild(li);
        }
    });
}

function aggiungiMateriaClick(){
    let inputMateria = document.getElementById("nuovaMateria");
    let inputDescrizione = document.getElementById("descrizione");

    $.get("aggiungiMateria", {materia: inputMateria.value, descrizione: inputDescrizione.value}, function (data){
        if(data !== "202"){
            alert("La materia inserita esiste giá");
        }
    });
}

function validateFormAggMateria(){
    let inputMateria = document.getElementById("nuovaMateria");
    let inputDescrizione = document.getElementById("descrizione");

    if (inputMateria.value === "" || inputDescrizione.value === "" ) {
        let lblState = document.getElementById("lbl-state-materia");
        lblState.innerHTML = "Devi riempire tutti i campi";
        return false;
    }

    aggiungiMateriaClick();
    return true;
}

function cancellaMateria(m){
    let alertText = "Sei sicuro di voler rimuovere questa materia?";
    if(confirm(alertText)) {
        $.get("cancellaMateria", {materia: m}, function () {
            location.reload();
        });
    }
}

function onPrenotaClick(a){
    if(vueHeader.userType === 'cliente') {
        vue.pageTitle = "";
        vue.materia = a.getAttribute("titolo");
        vue.currentView = 'confermaPren';
        setConfermaViewData();
    }else if(vueHeader.userType === 'amministratore'){
        alert("Gli amministratori non possono prenotare ripetizioni!");
    }else{
        alert("Effettua il LogIn/Registrati per poter effettuare una prenotazione!");
    }
}
