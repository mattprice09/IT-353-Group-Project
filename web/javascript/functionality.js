$(document).ready(function() {
  
    // Handle nav bar display based off of login status
    if ($("#loggedIn").val() == "Y") {
        // logged in
        $("#loginContainer").css('display', 'none');
        $("#welcomeContainer").css('display', 'block');
    } else {
        // not logged in
        $("#welcomeContainer").css('display', 'none');
        $("#loginContainer").css('display', 'block');
    }
});