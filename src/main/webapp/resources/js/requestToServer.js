function JsonRequest(sexR, ageR, countryR) {
    $.ajax({
        type: "GET",
        url: '/gift',
        data: ({sex: sexR, age: ageR, country: countryR}),
        dataType: 'json',
        success: function (data) {
            alert(data.age + "  " + data.name);
        }
    });
}