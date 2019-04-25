window.onload = function() {
  fetchjsonData();
};

var basketItems;

function fetchjsonData() {
  fetch("http://localhost:8080/api/basket")
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      basketItems = jsonData;
      fillTable(jsonData);
      createContinueShoppingButton();
      fetchDeliveryAddresses();
      if(jsonData.length != 0){
        createOrderButton();
        createEmptyBasketButton();
      }
    });
}

function fetchDeliveryAddresses() {
  fetch("/api/myorders")
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonDataMyorders) {
      createTextareaForDeliveryAddress(jsonDataMyorders);
    });
}

function fillTable(jsonData) {
  var basketMainDiv = document.getElementById("basket-main-div");
  basketMainDiv.innerHTML = "";
  var table = document.createElement("table");
  table.setAttribute("id", "basket-table");
  table.setAttribute("class", "table table-hover");
  var tableHeadTr = document.createElement("tr");
  var codeTh = document.createElement("th");
  codeTh.innerText = "Termékkód";
  tableHeadTr.appendChild(codeTh);
  var nameTh = document.createElement("th");
  nameTh.innerText = "Terméknév";
  tableHeadTr.appendChild(nameTh);
  var manufacturerTh = document.createElement("th");
  manufacturerTh.innerText = "Gyártó";
  tableHeadTr.appendChild(manufacturerTh);
  var quantityTh = document.createElement("th");
  quantityTh.innerText = "Mennyiség";
  tableHeadTr.appendChild(quantityTh);
  var priceTh = document.createElement("th");
  priceTh.innerText = "Ár";
  tableHeadTr.appendChild(priceTh);
  table.appendChild(tableHeadTr);
  var emptyTh = document.createElement("th");
  tableHeadTr.appendChild(emptyTh);
  var secondEmptyTh = document.createElement("th");
  tableHeadTr.appendChild(secondEmptyTh);
  var tableBody = document.createElement("tbody");
  for (var i = 0; i < jsonData.length; i++) {
    var id = jsonData[i].id;
    // console.log(jsonData);
    // console.log(id);
    var tr = document.createElement("tr");

    var codeTd = document.createElement("td");
    codeTd.innerHTML = jsonData[i].product.code;
    tr.appendChild(codeTd);

    var nameTd = document.createElement("td");
    nameTd.innerHTML = jsonData[i].product.name;
    tr.appendChild(nameTd);

    var manufacturerTd = document.createElement("td");
    manufacturerTd.innerHTML = jsonData[i].product.manufacturer;
    tr.appendChild(manufacturerTd);

    var quantityTd = document.createElement("td");
    quantityTd.setAttribute("id", `update-quantity-td${id}`);
    quantityTd.innerHTML = jsonData[i].quantity;
    tr.appendChild(quantityTd);

    var priceTd = document.createElement("td");
    priceTd.innerHTML = jsonData[i].product.price * jsonData[i].quantity + " Ft";
    tr.appendChild(priceTd);

    var updateQuantityButtonTd = document.createElement("td");
    var updateQuantityButton = document.createElement("button");
    updateQuantityButtonTd.appendChild(updateQuantityButton);
    updateQuantityButton.innerHTML = "Mennyiség módosítása";
    updateQuantityButton.setAttribute("class", "btn btn-warning");
    updateQuantityButton.setAttribute("id", `update-quantity-button${id}`);
    updateQuantityButton.onclick = editQuantity;
    updateQuantityButton["data"] = jsonData[i];
    tr.appendChild(updateQuantityButtonTd);
    table.appendChild(tr);

    var deleteButtonTd = document.createElement("td");
    var deleteButton = document.createElement("button");
    deleteButtonTd.appendChild(deleteButton);
    deleteButton.innerHTML = "Törlés";
    deleteButton.setAttribute("class", "btn btn-warning");
    deleteButton.onclick = deleteFromBasket;
    deleteButton["data"] = jsonData[i].product;
    tr.appendChild(deleteButtonTd);
    tableBody.appendChild(tr);
  }
  table.appendChild(tableBody);
  calculateSumOfBasket(jsonData, table);
  basketMainDiv.appendChild(table);

}

function deleteFromBasket(jsonData) {
  id = this["data"].id;
  if (!confirm("Biztosan törlöd a terméket a kosárból?")) {
    return;
  }
  fetch("http://localhost:8080/api/basket/" + id, {
    method: "DELETE",
    headers: {
      "Content-type": "application/json"
    }
  })
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      if (jsonData.ok) {
        document.getElementById("basket-message-div").innerHTML = "Termék törölve!";
        document.getElementById("basket-message-div").setAttribute("class", "alert alert-success");
        fetch("http://localhost:8080/api/basket")
          .then(function(response) {
            return response.json();
          })
          .then(function(jsonData) {
            refresh();
            fillTable(jsonData);
          });
      } else {
        document.getElementById("message-div").setAttribute("class", "alert alert-danger");
      }
      //      document.getElementById("message-div").innerHTML = jsonData.message;
    });
}

function calculateSumOfBasket(jsonData, table) {
  var sumOfBasket = 0;
  for (var i = 0; i < jsonData.length; i++) {
    sumOfBasket += parseInt(jsonData[i].product.price * jsonData[i].quantity);
  }
  var tr = document.createElement("tr");
  var codeTd = document.createElement("td");
  tr.appendChild(codeTd);
  var nameTd = document.createElement("td");
  tr.appendChild(nameTd);
  var manufacturerTd = document.createElement("td");
  tr.appendChild(manufacturerTd);
  var sumAmountTd = document.createElement("td");
  sumAmountTd.innerHTML = "Összérték:";
  tr.appendChild(sumAmountTd);
  var priceTd = document.createElement("td");
  priceTd.innerHTML = sumOfBasket + " Ft";
  tr.appendChild(priceTd);
  var emptyTd = document.createElement("td");
  tr.appendChild(emptyTd);
  var secondEmptyTd = document.createElement("td");
  tr.appendChild(secondEmptyTd );
  table.appendChild(tr);
}

function editQuantity() {
  var id = this["data"].id;
  var quantity = this["data"].quantity;

  document.getElementById(
    `update-quantity-td${id}`
  ).innerHTML = `<input id="update-quantity-input${id}" min="1" type="number" value="${quantity}" >`;

  var updateQuantityButton = document.getElementById(
    `update-quantity-button${id}`
  );
  updateQuantityButton.innerHTML = "Mennyiség mentése";
  updateQuantityButton.setAttribute("class", "btn btn-warning");
  updateQuantityButton.onclick = updateQuantity;
}

function updateQuantity() {
  var id = this["data"].id;
  for (var i = 0; i < basketItems.length; i++) {
    if ((basketItems[i].id = id)) {
      var request = {
        id: id,
        product: basketItems[i].product,
        quantity: document.getElementById(`update-quantity-input${id}`).value
      };
      console.log(request);
    }

    fetch("api/basket/quantity", {
      method: "POST",
      body: JSON.stringify(request),
      headers: {
        "Content-type": "application/json"
      }
    })
      .then(function(response) {
        return response.json();
      })
      .then(function(jsonData) {
        console.log(jsonData.ok);
        if (jsonData.ok) {
          refresh();
          document
            .getElementById("basket-message-div")
            .setAttribute("class", "alert alert-success");
        } else {
          document
            .getElementById("basket-message-div")
            .setAttribute("class", "alert alert-danger");
        }
        document.getElementById("basket-message-div").innerHTML =
          jsonData.message;
      });
    return false;
  }
}

function createTextareaForDeliveryAddress(jsonDataMyorders) {
  var deliveryAddresses = [];
  for (var j = 0; j < jsonDataMyorders.length; j++) {
    if (!deliveryAddresses.includes(jsonDataMyorders[j].deliveryAddress) && jsonDataMyorders[j].deliveryAddress != "Személyes átvétel") {
      deliveryAddresses.push(jsonDataMyorders[j].deliveryAddress);
    }
  }

  var template = document.getElementById("template").innerHTML;
  var render = Handlebars.compile(template);
  document.getElementById("buttons").innerHTML = render({addresses: deliveryAddresses});
}

function onNewAddressChanged() {
  var textareaDiv = document.getElementById("textarea-div");
  textareaDiv.innerHTML = "";
  var radios = document.getElementsByName("radiobutton");
  for (var i = 0; i < radios.length; i++) {
    if (radios[i].checked && radios[i].id == "radio-new-address") {
      var title = document.createElement("h4");
      title.setAttribute("id", "new-delivery-address");
      title.textContent = "Új szállítási cím megadása";
      textareaDiv.appendChild(title);

      var textarea = document.createElement("textarea");
      textarea.setAttribute("id", "textarea");
      textarea.setAttribute("class", "form-control");
      textarea.setAttribute("rows", "2");
      textarea.setAttribute("cols", "80");
      textarea.setAttribute("style", "width:50%");
      textarea.setAttribute("placeholder","  Kérlek ide add meg az új szállítási címet!");
      textareaDiv.appendChild(textarea);
    }
  }
}

function getAddress() {
  var radios = document.getElementsByName("radiobutton");
  var value;
  for (var i = 0; i < radios.length; i++) {
    if (radios[i].checked) {
      value = radios[i].value;
    }
    if (radios[i].checked && radios[i].id == "radio-new-address") {
      value = document.getElementById("textarea").value;
    }
  }
  return value;
}

function createEmptyBasketButton(){
    var emptyBasketButtonAndContinueShoppingButtonDiv = document.getElementById("empty-basket-and-continue-shopping-button-div");
    var emptyBasketButton = document.createElement("button");
    emptyBasketButton.setAttribute("class", "btn btn-warning");
    emptyBasketButton.setAttribute("id", "empty-basket-button");
    emptyBasketButton.innerHTML = "Kosár ürítése";
    emptyBasketButtonAndContinueShoppingButtonDiv.appendChild(emptyBasketButton);
    emptyBasketButton.onclick = emptyBasket;
}

function createOrderButton(){
    var orderButtonDiv = document.getElementById("order-button-div");
    var orderButton = document.createElement("button");
    orderButton.setAttribute("class", "btn btn-warning");
    orderButton.setAttribute("id", "order-button");
    orderButton.innerHTML = "Megrendelés";
    orderButtonDiv.appendChild(orderButton);
    orderButton.onclick = createOrder;
}

function createContinueShoppingButton(){
    var emptyBasketButtonAndContinueShoppingButtonDiv = document.getElementById("empty-basket-and-continue-shopping-button-div");
    var continueShoppingButton = document.createElement("button");
    continueShoppingButton.setAttribute("class", "btn btn-warning");
    continueShoppingButton.setAttribute("id", "continue-shopping-button");
    continueShoppingButton.innerHTML = "Vásárlás folytatása";
    var continueShoppingButtonLink = document.createElement("a");
    continueShoppingButtonLink.setAttribute("href", "/index.html");
    continueShoppingButtonLink.appendChild(continueShoppingButton);
    emptyBasketButtonAndContinueShoppingButtonDiv.appendChild(continueShoppingButtonLink);
}

function createOrder() {
  var address = getAddress();
  var request = { deliveryAddress: address };
  fetch("/api/myorders", {
    method: "POST",
    body: JSON.stringify(request),
    headers: {
      "Content-type": "application/json"
    }
  })
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
        if (jsonData.ok) {
            document.getElementById("basket-message-div").setAttribute("class", "alert alert-success");
            refresh();
        } else {
            document.getElementById("basket-message-div").setAttribute("class", "alert alert-danger");
        }
        document.getElementById("basket-message-div").innerHTML = jsonData.message;
    });
}

function emptyBasket() {
  if (!confirm("Biztosan kiüríted a kosarad?")) {
    return;
  }

  fetch("http://localhost:8080/api/basket/", {
    method: "DELETE",
    headers: {
      "Content-type": "application/json"
    }
  })
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      if (jsonData.ok) {
        document.getElementById("basket-message-div").innerHTML = "Kosár kiürítve!";
        document.getElementById("basket-message-div").setAttribute("class", "alert alert-success");
        refresh();
      } else {
        document.getElementById("message-div").setAttribute("class", "alert alert-danger");
      }
    });
}

function refresh(){
    document.getElementById("empty-basket-button").remove();
    document.getElementById("order-button").remove();
    document.getElementById("continue-shopping-button").remove();
    document.getElementById("basket-table").remove();
    var radios = document.getElementsByName("radiobutton");
        for (var i = 0; i < radios.length; i++) {
            if (radios[i].checked && radios[i].id == "radio-new-address") {
                document.getElementById("textarea").value ="";
            }
        }
    fetchjsonData();
}