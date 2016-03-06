$(window).scroll(function () {
    var st = $(this).scrollTop();
    $("#snow3").css({"transform": "translate(0%," + st / 45 + "%)"});
    $("#gift").css({"transform": "translate(0%," + st / 15 + "%)"});
    $("#snow2").css({"transform": "translate(0%," + st / 60 + "%)"});
});

$(document).ready(function () {
    window.onbeforeunload = function () {
        window.scrollTo(0, 0);
    }
    addNoise();
});

function scrolling(to) {
    $('html, body').animate({
        scrollTop: $(to).parent().offset().top
    }, 1500);
    addNoise();
}

function printAges(sex) {
    const ages = ["<6", "6-10", "10-15", "15-18", "18-30", "30-40", "40-50", ">50"];
    $("#age").css({"display": "block"});
    var image = document.getElementById("age-image");
    image.innerHTML = "";
    var text = document.getElementById("age-text");
    text.innerHTML = "";
    text.appendChild(document.createTextNode('Age?'));
    var str;
    if (sex) {
        str = "boy";
    } else {
        str = "girl";
    }
    var div;
    var innerDiv;
    for (var i = 1; i <= 8; i++) {
        div = document.createElement("div" );
        $(div).css({"margin": "3% auto 0"});
        div.setAttribute("class", "col-sm-3");
        innerDiv = document.createElement("div");
        innerDiv.setAttribute("class", "age-images");
        $(innerDiv).css({"background-image": 'url("../img/' + (str + i) + '.png")'});
        div.appendChild(innerDiv);
        div.innerHTML += '<h3>' + ages[i - 1] + '</h3>';
        image.appendChild(div);
    }
    if (sex) {
        $(".age-images").css({"border": "2px solid #96d6eb"});
        $(".age-images").hover(function () {
            $(this).css("background-color", "#51bcc3");
        }, function () {
            $(this).css("background-color", "");
        });
    } else {
        $(".age-images").css({"border": "2px solid #3f826d"});
        $(".age-images").hover(function () {
            $(this).css("background-color", "#d4c5e2");
        }, function () {
            $(this).css("background-color", "");
        });
    }
    $(".age-images").css({"cursor": "pointer"});
    scrolling("#age-text");
    $(".age-images").click(function () {
        $("#country").css({"display": "block"});
        var text = document.getElementById("country-text");
        text.innerHTML = "";
        text.appendChild(document.createTextNode('From Where?'));
        scrolling("#country");
    });
}

//stretch the noise on the whole page
function addNoise() {
    $("#noise").css({"height": "" + $(document).height()});
}

