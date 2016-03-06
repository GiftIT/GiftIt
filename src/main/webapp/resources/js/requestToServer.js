function JsonRequest(sexR, ageR, countryR) {
    $.ajax({
        type: "GET",
        url: '/gift',
        data: ({sex: sexR, age: ageR, country: countryR}),
        dataType: 'json',
        success: function (data) {
            var text = "";
            for (var i = 0; i < data.result.length; i++) {
                text += data.result[i] + " ";
            }
            alert(text);
        }
    });
}