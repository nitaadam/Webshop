fetchOrders();

function fetchOrders(isActive = false){
    fetch(`/api/orders?isActive=${isActive}`)
        .then(function(response) {
        return response.json();
        })
        .then(function(jsonData) {
        console.log(jsonData);
        showTable(jsonData);
        });
}

function showTable(jsonData) {
    console.log(jsonData);
    Handlebars.registerHelper("ifValue", function(val1, val2, options) {
        if ( val1 == val2) {
            return options.fn(this);
        }
    });
    Handlebars.registerHelper("notIfValue", function(val1, val2, options) {
        if ( val1 != val2) {
            return options.fn(this);
        }
    });
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
    });
    Handlebars.registerHelper('dateformatter', function(date) {
        var partsOfDate = date.split("T");
        var hunDatum = partsOfDate[0].replace(/-/g, ".");
        var partsOfTime = partsOfDate[1].split(":");
        return hunDatum + " " + partsOfTime[0] + ":" + partsOfTime[1];
    });
    var template = document.getElementById("template").innerHTML;
    var orderList = Handlebars.compile(template);     //render fgv (template-be rakok dinamikus adatokat)
    document.getElementById("main-div").innerHTML = orderList({orders: jsonData});
}

var checkBox = document.getElementById("active-filter");
checkBox.onclick = filterActiveOrders;

function filterActiveOrders(e){
    var isActive = e.target.checked;
    fetchOrders(isActive);
}

function deleteOrder(id){
    if(!confirm("Biztosan törölni szeretnéd a megrendelést?")){
        return;
    }
    fetch("/orders/"+id, {
        method: "DELETE",
        headers: {
          "Content-type": "application/json"
        }
    })
        .then(function() {
            fetchOrders();
        });
}

function deleteOrderItem(orderId, productAddress){
    if(!confirm("Biztos törölni szeretnéd a terméket?")){
        return;
    }
    fetch("/orders/" + orderId + "/" + productAddress, {
        method: "DELETE",
        headers: {
          "Content-type": "application/json"
        }
    })
        .then(function() {
            fetchOrders();
        });
}

function changeStatusToDelivered(orderId){
    fetch("/orders/"+ orderId +"/status", {
        method: "POST",
        headers: {
              "Content-type": "application/json"
        }
    })
        .then(function() {
            fetchOrders();
        })
}