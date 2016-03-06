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
    $("#result").css({"display": "block"});
    var strings = data.result;
    var resultContainer = document.getElementById("result");
    var row;
    var innerDiv;
    for (var i = 0; i < strings.length; i++) {
        row = document.createElement("div");
        row.setAttribute("class", "row");
        innerDiv = document.createElement("div");
        innerDiv.setAttribute("class", "col-sm-12");
        innerDiv.css({"background-image": 'url("../img/' + strings[i] + '.png")'});
        row.appendChild(innerDiv);
        resultContainer.appendChild(row);
    }
}