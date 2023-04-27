document.querySelector(".submit").addEventListener("click",function(){
    var user = document.querySelector(".user").value;
    var pass = document.querySelector(".pass").value;

    if(user==="admin" && pass==="ramen"){
        //window.location.replace("https://www.google.com");
        redirect();

        //alert("password is correct.");
        
        // document.querySelector(".user").focus();
    }

    else
        alert("Wrong password.Try again.");

});

function redirect(){
    
    window.location.replace("app.html");
}
