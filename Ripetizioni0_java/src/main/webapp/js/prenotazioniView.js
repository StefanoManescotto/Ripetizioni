function getPrenotazioni(){
    let ul = document.getElementById("lista-prenotazioni");

    if(ul != null){
        while(ul.childNodes.length > 1){
            ul.lastChild.remove();
        }
    }

    $.getJSON("prenotazioniUtente", {}, function (data){
        let ul = document.getElementById("lista-prenotazioni");

        for(let i = 0; i < data.length; i++) {
            let li = document.createElement("li");
            li.setAttribute("class", "table-row");

            let div1 = document.createElement("div");
            div1.setAttribute("class", "col-margin col col-2");
            div1.innerHTML = data[i].corso;

            let div2 = document.createElement("div");
            div2.setAttribute("class", "col-margin col col-2");
            div2.innerHTML = data[i].nomeDocente + " " + data[i].cognomeDocente;

            let div3 = document.createElement("div");
            div3.setAttribute("class", "col-margin col col-2");
            div3.innerHTML = data[i].data + "<br>" + data[i].ora + "-" + (data[i].ora + 1);

            let div4;
            div4 = document.createElement("div");
            div4.setAttribute("class", "col-margin col col-2");
            if(data[i].stato === "attiva"){
                let btnCancella = document.createElement("button");
                btnCancella.setAttribute("class", "btn prenotazioni-btn-cancella");
                btnCancella.setAttribute("onclick", "cambiaPrenStato(" + data[i].idPrenotazione + ",  'cancellata')");
                btnCancella.innerHTML = "cancella";
                let btnEffettuata = document.createElement("button");
                if(vueHeader.userType === 'cliente') {
                    btnEffettuata.setAttribute("class", "btn prenotazioni-btn-effettuata");
                    btnEffettuata.setAttribute("onclick", "cambiaPrenStato(" + data[i].idPrenotazione + ", 'effettuata')");
                    btnEffettuata.innerHTML = "effettuata";
                    div4.appendChild(btnEffettuata);
                }
                div4.appendChild(btnCancella);
            }else{
                div4.innerHTML = data[i].stato;
            }

            li.appendChild(div1);
            li.appendChild(div2);
            li.appendChild(div3);
            li.appendChild(div4);

            ul.appendChild(li);
        }
    });
}

function cambiaPrenStato(idPren, state){
    if(state === 'effettuata' || state === 'cancellata'){
        let alertText = "Sei sicuro di voler impostare questa prenotazione come " + state + "?";
        if(confirm(alertText)){
            $.get("cambiaStato", {idPrenotazione: idPren, newState: state}, function () {
                location.reload();
            });
        }
    }
}
