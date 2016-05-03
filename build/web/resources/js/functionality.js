var setLoginBar = function() {
    // Handle nav bar display based off of login status
    if ($("#loggedIn").val() == "Y") {
        // logged in
        $("#loginContainer").css('display', 'none');
        $("#registerNav").css('display', 'none');
        $("#updateNav").css('display', 'block');
        $("#welcomeContainer").css('display', 'block');
    } else {
        // not logged in
        $("#updateNav").css('display', 'none');
        $("#welcomeContainer").css('display', 'none');
        $("#loginContainer").css('display', 'block');
        $("#registerNav").css('display', 'block');
    }
}
var W = 1155;
var H = 866;
var initializeHomePage = function() {
    
    setLoginBar();
    
    // get # pixels bought from the HTML element
    var numBought = $("#numSold").val();
    numBought = parseInt(numBought);
    
    // ~~~ IMAGE Processing begin
    var canvas = document.getElementById("MainCanvas");
    
    // get main image context
    var ctx = canvas.getContext("2d");
    ctx.beginPath();
    ctx.rect(0, 0, W, H);
    
    // load "cover" image then paint the good image upon callback
    var coverImg = new Image;
    coverImg.onload = function() {
        var tmp = document.createElement("canvas");
        tmp.width = W;
        tmp.height = H;
        
        // Paint cover image
        var imgCtx = tmp.getContext("2d");
        imgCtx.drawImage(coverImg, 0, 0, W, H);
        ctx.putImageData(imgCtx.getImageData(0, 0, W, H), 0, 0);
        
        paintMainImage(numBought, ctx);
    };
    coverImg.width = W;
    coverImg.height = H;
    coverImg.src = "resources/images/MainSealed3.jpg";
    
    // Popups on scrollover for the image
    $("#MainCanvas").mousemove(function(e){
        var low_bound = Math.floor(numBought / W);
        var right_bound = numBought - (low_bound * W);

        // Get popover text
        var popupText = "";
        if (event.offsetY < low_bound || (event.offsetX < right_bound && event.offsetY == low_bound)) {
            popupText = "X-coord is " + event.offsetX + ", y-coord is " + event.offsetY;
            $("#popoverID").addClass("popover");
            $("#popoverID").removeClass("popover-donate");
        } else {
            popupText = "Donate to reveal more pixels!";
            $("#popoverID").removeClass("popover");
            $("#popoverID").addClass("popover-donate");
//            $(".popover").hide();
        }
        
        $("#popupMsg").text(popupText);

        // display popover at specified location
        var left = e.pageX;
        var top = e.pageY;
        var height = $("#popoverID").height();
        $("#popoverID").show();
        $('#popoverID').css('left', (left + 20)+'px');
        $('#popoverID').css('top', (top-(height/2)-10) + 'px');
    });
    
    $("#MainCanvas").mouseout(function(e) {
        $(".popover").hide();
    });
}

// Paints the main image over the "cover" image
var paintMainImage = function(numBought, ctx) {
    
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
    img.src = "resources/images/MainImage.jpeg";
}