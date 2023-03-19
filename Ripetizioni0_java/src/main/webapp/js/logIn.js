function logIn(){
    let emailInput = document.getElementById("loginEmail");
    let passwordInput = document.getElementById("loginPassword");

    executeLogIn(emailInput.value, passwordInput.value);
}

function executeLogIn(m, psw){
    if(m !== "" && psw !== "") {
        $.post('logIn', {email: m, password: psw}, function (data) {
            if (data === '200') {
                location.href = "index.html";
            } else {
                let lbl = document.getElementById("lbl-status");
                lbl.innerHTML = "Autenticazione fallita: riprova"
            }
        });
    }else{
        let lbl = document.getElementById("lbl-status");
        lbl.innerHTML = "Riempi tutti i campi per proseguire"
    }
}
