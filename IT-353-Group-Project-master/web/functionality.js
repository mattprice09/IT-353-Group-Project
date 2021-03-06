var setLoginBar = function() {
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
}

$(document).ready(function() {
    var W = 1155;
    var H = 866;
  
    setLoginBar();
    
    // ~~~ IMAGE Processing begin
    var canvas = document.getElementById("MainCanvas");
    
    // paint image black
    var ctx = canvas.getContext("2d");
    ctx.beginPath();
    ctx.rect(0, 0, W, H);
    ctx.fillStyle = "black";
    ctx.fill();
    
    // get # pixels bought from the HTML element
    var numBought = $("#numSold").val();
    numBought = parseInt(numBought);
    
    // paint all "purchased" pixels
    var img = new Image;
    img.onload = function() {

        // Get image data
        var tmp = document.createElement("canvas");
        tmp.width = W;
        tmp.height = H;
        
        // Create accessible image data (from the completed image)
        var imgCtx = tmp.getContext("2d");
        imgCtx.drawImage(img, 0, 0, W, H);
        var colorfulData = imgCtx.getImageData(0, 0, W, H);
        var imgData = ctx.getImageData(0, 0, W, H);

        // Copy the correct number of pixels to black image
        for (var i = 0; i < numBought*4; i += 4) {
            imgData.data[i] = colorfulData.data[i];
            imgData.data[i+1] = colorfulData.data[i+1];
            imgData.data[i+2] = colorfulData.data[i+2];
            imgData.data[i+3] = 255;
        }
        ctx.putImageData(imgData, 0, 0);
    };
    img.width = W;
    img.height = H;
    img.src = "resources/MainImage.jpeg";
    
    // Popups on scrollover for the image
    $("#MainCanvas").mousemove(function(e){
        var low_bound = Math.floor(numBought / W);
        var right_bound = numBought - (low_bound * W);

        // Show the popover if the mouse is hovering on a COLORED pixel
        if (event.offsetY < low_bound || (event.offsetX < right_bound && event.offsetY == low_bound)) {
            var popupText = "X-coord is " + event.offsetX + ", y-coord is " + event.offsetY;
            $("#popupMsg").text(popupText);

            // display popover at specified location
            var left = e.pageX;
            var top = e.pageY;
            var height = $(".popover").height();
            $(".popover").show();
            $('.popover').css('left', (left + 20)+'px');
            $('.popover').css('top', (top-(height/2)-10) + 'px');
        } else {
            $(".popover").hide();
        }
    });
    
    $("#MainCanvas").mouseout(function(e) {
        $(".popover").hide();
    });
});