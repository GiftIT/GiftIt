function JsonRequest(sexR, ageR, countryR, callback) {
    $.ajax({
        type: "GET",
        url: '/gift',
        data: ({sex: sexR, age: ageR, country: countryR}),
        dataType: 'json',
        success: function (data) {
            callback(data);
        }
    });
}

function printRecommendedGifts(data) {
    $("#gifts").detach();
    $("#result").css({"display": "block"});
    var strings = data.result;
    var resultContainer = document.getElementById("result");
    var row = document.createElement("div");
    row.setAttribute("id", "gifts");
    row.setAttribute("class", "row");
    var innerDiv;
    var div;
    var text;
    for (var i = 0; i < strings.length; i++) {
        innerDiv = document.createElement("div");
        if (i == 0) {
            innerDiv.setAttribute("class", "col-sm-2 col-sm-offset-1");
        } else {
            innerDiv.setAttribute("class", "col-sm-2");
        }
        div = document.createElement("div");
        div.setAttribute("class", "result-image");
        $(div).css({'background-image': 'url("../img/' + strings[i] + '.png")'});
        text = document.createElement("h3");
        text.innerHTML = ""+(i+1)+". "+strings[i].slice(0,1).toUpperCase()+strings[i].slice(1)
        innerDiv.appendChild(div);
        innerDiv.appendChild(text);
        row.appendChild(innerDiv);
    }
    resultContainer.appendChild(row);
    addNoise();
    scrolling("#result-text");
}