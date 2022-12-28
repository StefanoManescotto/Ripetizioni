$.get("authenticate", {}, function (data){
    if(data !== 'not authenticated'){
        location.href = "index.html";
    }
});


const container = document.querySelector(".container-logIn"),
      pwShowHide = document.querySelectorAll(".showHidePw"),
      pwFields = document.querySelectorAll(".password");

const type = window.location.hash.slice(1);

if(type === "signup"){
    container.classList.add("active");
}else{
    container.classList.remove("active");
}

// js code to show/hide password and change icon
pwShowHide.forEach(eyeIcon =>{
    eyeIcon.addEventListener("click", ()=>{
        pwFields.forEach(pwField =>{
            if(pwField.type ==="password"){
                pwField.type = "text";

                pwShowHide.forEach(icon =>{
                    icon.classList.replace("uil-eye-slash", "uil-eye");
                })
            }else{
                pwField.type = "password";

                pwShowHide.forEach(icon =>{
                    icon.classList.replace("uil-eye", "uil-eye-slash");
                })
            }
        })
    })
})

function openSignUpWindow(){
    container.classList.add("active");
}

function openLogInWindow(){
    container.classList.remove("active");
}
