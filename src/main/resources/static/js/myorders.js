fetchMyOrders();

function fetchMyOrders(){
    fetch("/api/myorders")
        .then(function(response) {
        return response.json();
        })
        .then(function(jsonData) {
        console.log(jsonData);
        showTable(jsonData);
        });
}

function showTable(jsonData) {
    var messageDiv = document.getElementById("message-div");
    if(jsonData.length == 0){
        var h2 = document.createElement("h2");
        h2.innerHTML = "<br><br>Ahhoz, hogy ki tudjuk listázni a rendeléseid, kérlek, előbb adj le rendelést!"
        messageDiv.appendChild(h2);
    }
    Handlebars.registerHelper("modifStatus", function(val1, options) {
        if ( val1 == "ACTIVE") {
            return 'Aktív';
        }
        if( val1 == "DELETED"){
            return 'Törölt';
        }
        if( val1 == "DELIVERED"){
            return 'Kiszállítva';
        }
    });    Handlebars.registerHelper('dateformatter', function(date) {
        var partsOfDate = date.split("T");
        var hunDatum = partsOfDate[0].replace(/-/g, ".");
        var partsOfTime = partsOfDate[1].split(":");
        return hunDatum + " " + partsOfTime[0] + ":" + partsOfTime[1];
    });
    var template = document.getElementById("template").innerHTML;
    var orderList = Handlebars.compile(template);
    document.getElementById("main-div").innerHTML = orderList({orders: jsonData});
}