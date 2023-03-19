let datiConfermaView;

function setConfermaViewData(){
    let weekDays = getNextWeek();
    $.getJSON("docenti", {from: weekDays[0], to: weekDays[4], materia: vue.materia}, function(data){
        datiConfermaView = data;
        loadConfermaView();
    });
}

function onConfermaViewClick(){
    let selectInsegnante = document.getElementById("selectInsegnante");
    let selectGiorno = document.getElementById("selectGiorno");
    let giorniEl = [];
    let oreEl = [];
    let docentiEl = [];

    removeDocentiList();
    removeCheckboxes();
    removeGiornoList();

    const weekDays = getNextWeek();
    let ore = [15,16,17,18,19];

    for(let i = 0; i < datiConfermaView.length; i++) {
        let obj = datiConfermaView[i];

        if(selectInsegnante.value === (datiConfermaView[i].nome + " " + datiConfermaView[i].cognome) || selectInsegnante.value === "empty"){
            let giorni = []
            weekDays.forEach(function (d){
                addTime(giorni, d, new Array())
            });

            for(let j = 0; j < obj.busyDays.length; j++){
                let busy = obj.busyDays[j];
                addTime(giorni, busy.day, busy.time);
            }

            ore = [15,16,17,18,19];
            for(let k = 0; k < giorni.length; k++) {
                ore = [15,16,17,18,19];
                if((selectGiorno.value === giorni[k].giorno || selectGiorno.value === 'empty')){
                    giorni[k].time.forEach(function (o){
                        if(ore.indexOf(o) !== -1){
                            ore.splice(ore.indexOf(o), 1);
                        }
                    });
                    ore.forEach(function (o){
                        if(oreEl.indexOf(o) === -1){
                            oreEl.push(o);
                        }
                    });
                }

                if (!isTimeIn(getCheckedNumber(), giorni[k].time)) {
                    if(giorniEl.indexOf(giorni[k].giorno) === -1){
                        giorniEl.push(giorni[k].giorno);
                    }
                }
            }
        }

        dict = [];
        if(selectGiorno.value === 'empty'){
            getNextWeek().forEach(function (d){
                dict[d] = 0;
            });
        }

        for(let j = 0; j < obj.busyDays.length; j++) {
            let busy = obj.busyDays[j]

            if((selectGiorno.value === 'empty' || selectGiorno.value === busy.day) && isChecked(busy.time)){
                if(dict[busy.day] === undefined){
                    dict[busy.day] = 1;
                }else{
                    dict[busy.day]++;
                }
            }
        }

        let n = getCheckedNumber().length;

        if((n === 0 && Object.keys(dict).length !== 0) || selectGiorno.value === 'empty'){
            for(let g in dict){
                if(dict[g] < 5){
                    docentiEl.push(datiConfermaView[i].nome + " " + datiConfermaView[i].cognome);
                    break;
                }
            }
        }else{
            if(dict[selectGiorno.value] === undefined){
                docentiEl.push(datiConfermaView[i].nome + " " + datiConfermaView[i].cognome);
            }
        }
    }

    addOreList(oreEl);
    addGiornoList(giorniEl);
    addDocentiList(docentiEl);
}

function isChecked(time){
    var checkBoxes = document.getElementsByClassName("timeCheckBox");
    var ret = true;
    for(var i = 0; i < checkBoxes.length; i++){
        if(checkBoxes[i].checked){
            ret = false;
            if(checkBoxes[i].value == time){
                return true;
            }
        }
    }
    return ret;
}

function getCheckedNumber(){
    var checkBoxes = document.getElementsByClassName("timeCheckBox");
    var n = []
    for(var i = 0; i < checkBoxes.length; i++){
        if(checkBoxes[i].checked){
            n.push(checkBoxes[i].value);
        }
    }
    return n;
}

function isTimeIn(a, time){
    for(let i = 0; i < a.length; i++){
        for(let j = 0; j < time.length; j++){
            if(a[i] == time[j]){
                return true;
            }
        }
    }
    return false;
}

function removeCheckboxes(){
    let ul = document.getElementById("checkOra");
    let bound = 0;
    while(ul.childNodes.length > bound) {
        let chkbox = document.getElementsByClassName(ul.lastChild.value);
        if (chkbox != null && !chkbox[0].checked) {
            ul.lastChild.remove();
        }else{
            bound++;
        }
    }
}

function removeDocentiList(){
    let selectInsegnante = document.getElementById("selectInsegnante");
    let bound = 1;
    while(selectInsegnante.childNodes.length > bound){
        if(selectInsegnante.value !== selectInsegnante.childNodes[bound].value){
            selectInsegnante.childNodes[bound].remove();
        }else{
            bound++;
        }
    }
}

function removeGiornoList(){
    let selectGiorno = document.getElementById("selectGiorno");
    let bound = 1;
    while(selectGiorno.childNodes.length > bound){
        if(selectGiorno.childNodes[bound].value !== selectGiorno.value) {
            selectGiorno.childNodes[bound].remove();
        }else{
            bound++;
        }
    }
}

function addGiornoList(gList){
    let selectGiorno = document.getElementById("selectGiorno");
    gList.forEach(function (g){
        if(g !== selectGiorno.value) {
            let option = document.createElement("option");
            option.setAttribute("value", g);
            option.innerHTML = g;
            selectGiorno.appendChild(option);
        }
    });
}

function addDocentiList(dList){
    let selectInsegnante = document.getElementById("selectInsegnante");
    dList.forEach(function (d){
        if(d !== selectInsegnante.value){
            let option = document.createElement("option");
            option.setAttribute("value", d);
            option.innerHTML = d;
            selectInsegnante.appendChild(option);
        }
    });
}

function addOreList(oreList){
    let ul = document.getElementById("checkOra");
    oreList.forEach(function (o){
        let chb = document.getElementsByClassName(o);
        if(chb[0] === undefined){
            let li = document.createElement("li");
            let input = document.createElement("input");
            input.setAttribute("class", o + " timeCheckBox");
            input.setAttribute("type", "checkbox");
            input.setAttribute("onclick", "onConfermaViewClick()");
            input.setAttribute("value", o);
            li.appendChild(input);
            li.setAttribute("value", o);
            li.innerHTML = li.innerHTML + (" " + o + "-" + (o + 1));

            ul.appendChild(li);
        }
    });
}

function loadConfermaView(){
    let selectInsegnante = document.getElementById("selectInsegnante");
    let selectGiorno = document.getElementById("selectGiorno");
    let ul = document.getElementById("checkOra");

    removeCheckboxes();
    removeGiornoList();
    removeDocentiList();

    getNextWeek().forEach(function (d){
        let option = document.createElement("option");
        option.setAttribute("value", d);
        option.innerHTML = d;
        selectGiorno.appendChild(option);
    });

    for(let i = 15; i <= 19; i++){
        let li = document.createElement("li");
        let input = document.createElement("input");
        input.setAttribute("class", i + " timeCheckBox");
        input.setAttribute("type", "checkbox");
        input.setAttribute("onclick", "onConfermaViewClick()");
        input.setAttribute("value", i);
        li.appendChild(input);
        li.setAttribute("value", i);
        li.innerHTML = li.innerHTML + (" " + i + "-" + (i + 1));

        ul.appendChild(li);
    }

    datiConfermaView.forEach(function (d){
        let option = document.createElement("option");
        option.setAttribute("value", d.nome + " " + d.cognome);
        option.innerHTML = d.nome + " " + d.cognome;
        selectInsegnante.appendChild(option);
    });
}

function getNextWeek(date = new Date()) {
    const dateCopy = new Date(date.getTime());
    var weekDays = []

    const nextMonday = new Date(
        dateCopy.setDate(
            dateCopy.getDate() + ((7 - dateCopy.getDay() + 1) % 7 || 7),
        ),
    );

    let current = nextMonday;
    let month, day;

    for (var i = 0; i < 5; i++) {
        month = current.getMonth() + 1;
        day = current.getDate();
        month = month > 9 ? month : "0" + month;
        day = day > 9 ? day : "0" + day;
        weekDays.push(current.getFullYear() + "-" + month + "-" + day);
        current.setDate(current.getDate() + 1);
    }

    return weekDays;
}

function addTime(giorni, g, t){
    exist = false;
    for(let k = 0; k < giorni.length; k++){
        if(giorni[k].giorno === g){
            exist = true;
            giorni[k].time.push(t);
        }
    }
    if(!exist){
        giorni.push({
            giorno: g,
            time: [t]
        });
    }
}

function prenota(){
    let docente = document.getElementById("selectInsegnante").value.split(" ");
    let dataPren = document.getElementById("selectGiorno").value;
    let checkBoxes = document.getElementsByClassName("timeCheckBox");
    let nomeCorso = document.getElementById("confermaViewMateria").innerText;
    let ore = "";

    for(var i = 0; i < checkBoxes.length; i++){
        if(checkBoxes[i].checked){
            ore += checkBoxes[i].value + "-";
        }
    }
    ore = ore.slice(0, -1);

    $.get("prenota", {corso: nomeCorso.toString(), nome: docente[0], cognome: docente[1], data: dataPren, ora: ore}, function (data){
        if(data !== "200"){
            alert("Orario non disponibile, operazione non completata")
        }else{
            alert("operazione completata");
            location.reload();
        }
    });
}

function validateFormConferma() {
    let docente = document.getElementById("selectInsegnante").value;
    let dataPren = document.getElementById("selectGiorno").value;
    let checkBoxes = document.getElementsByClassName("timeCheckBox");
    let nomeCorso = document.getElementById("confermaViewMateria").innerText;
    let ore = "";

    for (var i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked) {
            ore += checkBoxes[i].value + "-";
        }
    }

    if (docente === "empty" || dataPren === "empty" || ore === "" || nomeCorso === "") {
        let lblState = document.getElementById("lbl-state-prenota");
        lblState.innerHTML = "Devi riempire tutti i campi";
        return false;
    }

    prenota();
    return false;
}
