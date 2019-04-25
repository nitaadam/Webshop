fetchDataForFirstReport();
fetchDataForSecondReport();

//ELSŐ RIPORTHOZ:

function generateTableForFirstReport(jsonData){

var tableBody = document.getElementById("report1-table-body");

for (var i = 0; i < jsonData.length; i++) {
    var row = document.createElement("tr");

    if ((i+2) % 3 == 0){
    var date = document.createElement("td");
    date.innerHTML = jsonData[i].yearAndMonthOfOrder;
    row.appendChild(date);
    }
    else{
    var emptyCell = document.createElement("td");
    row.appendChild(emptyCell);
    }

    var status = document.createElement("td");
    status.setAttribute("class", "to-be-bordered");
    switch(jsonData[i].orderStatus){
        case "ACTIVE":
            status.innerHTML = "Aktív";
            break;
        case "DELIVERED":
            status.innerHTML = "Kiszállított";
            break;
        case "DELETED":
           status.innerHTML = "Törölt";
           break;
    }
    row.appendChild(status);

    var count = document.createElement("td");
    count.setAttribute("class", "to-be-bordered");
    count.innerHTML = jsonData[i].orderCount;
    row.appendChild(count);
    var price = document.createElement("td");
    price.setAttribute("class", "to-be-bordered");
    price.innerHTML = jsonData[i].totalPriceOfOrder;
    row.appendChild(price);

    tableBody.appendChild(row);
}
}

function fetchDataForFirstReport(){
 let url = "/api/reports/orders";
 fetch(url)
   .then(function (response) {
       return response.json();
     })
     .then(function (jsonData) {
       generateTableForFirstReport(jsonData);
     });
 }


 // INNENTŐL INDUL A 2. RIPORT:

function fetchDataForSecondReport(){
let url = "/api/reports/products";
fetch(url)
  .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
     // console.log(jsonData);
      generateTableForSecondReport(jsonData);
    });
}


function generateTableForSecondReport(jsonData){

var tableBody = document.getElementById("report2-table-body");

for (var i = 0; i < jsonData.length; i++) {
    var row = document.createElement("tr");

    var date = document.createElement("td");
    date.setAttribute("class", "to-be-bordered");
    date.innerHTML = jsonData[i].yearAndMonthOfOrder;
    row.appendChild(date);

    var productName = document.createElement("td");
    productName.setAttribute("class", "to-be-bordered");
    productName.innerHTML = jsonData[i].productName;
    //console.log(jsonData);
    row.appendChild(productName);

    var productCount = document.createElement("td");
    productCount.setAttribute("class", "to-be-bordered");
    productCount.innerHTML = jsonData[i].productCount;
    row.appendChild(productCount);

    var productPrice = document.createElement("td");
    productPrice.setAttribute("class", "to-be-bordered");
    productPrice.innerHTML = jsonData[i].productPrice;
    row.appendChild(productPrice);

    var totalPrice = document.createElement("td");
    totalPrice.setAttribute("class", "to-be-bordered");
    totalPrice.innerHTML = jsonData[i].totalPrice;
    row.appendChild(totalPrice);

    tableBody.appendChild(row);
}
}