var id;
var user = fetchUser();

function fetchUser() {
  var url = "/api/user";
  return fetch(url)
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      user = jsonData;
      fetchProduct();
    });
}

function fetchProduct() {
  var address = new URL(document.location).searchParams.get("address");
  var url = "/api/products/" + address;
  fetch(url)
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
    console.log(jsonData);
      if ((!jsonData.hasOwnProperty('productStatus')) || jsonData.productStatus == "DELETED") {
         window.location='/404.html';
      } else {
        var imgDiv = document.getElementById("product-img");
        var img = document.createElement("img");
            img.setAttribute("class", "small_pics");
            if (jsonData.image == ""){
                                      img.setAttribute('src', "/img/comingSoon.png");
                                      }

            else if (jsonData.image != null){
            img.setAttribute("src", `data:image/jpg;base64,${jsonData.image}`);
            }

            else {
            img.setAttribute("src", `/img/${jsonData.code}.jpg`);
            }
            img.setAttribute('alt', jsonData.name);
            imgDiv.appendChild(img);
        document.getElementById("name-td").innerHTML = jsonData.name;
        document.getElementById("code-td").innerHTML = "Termékkód: " + jsonData.code;
        document.getElementById("manufacturer-td").innerHTML = jsonData.manufacturer;
        document.getElementById("price-td").innerHTML = jsonData.price + " Ft";
        document.getElementById("category-td").innerHTML = jsonData.category.name + " kategória";
        id = jsonData.id;
        document.getElementById("counter-input").classList.add("mystyle");
        if (user.role == "ROLE_USER") {
          // putAddToBasketQuntityTogether(jsonData.id);
          createFeedbackBox(jsonData.id);
          console.log(jsonData.id);
          createAddToBasketButton(jsonData.id);
          createQuantityInput();
            createGoToBasketButton();
            document.getElementById("counter-input").classList.remove("mystyle");
        } else if (user.role == "ROLE_NOT_AUTHORIZED") {
          var messageTr = document.createElement("div");
          var messageTd = document.createElement("p");
          messageTd.innerHTML = "A vásárláshoz" + "<a href=" + "http://localhost:8080/login.html> jelentkezz be!</a>";
          document.getElementById("login-message-div").appendChild(messageTr);
          messageTr.appendChild(messageTd);
        }
        createBackToProductsButton();
      }
    });
}

//KÉSZ:
function createGoToBasketButton(){
    var gotobasketDiv = document.getElementById("buttons");
    var gotobasketButton = document.createElement("input");
    gotobasketButton.setAttribute("type", "button");
    gotobasketButton.setAttribute("class", "btn btn-warning");
    gotobasketButton.value = "Mutasd a kosaram";
    gotobasketButton.setAttribute("onclick", "window.location.href='http://localhost:8080/basket.html'");
    gotobasketDiv.appendChild(gotobasketButton);
//    document.getElementById("product-data").appendChild(gotobasketTr);

}

function createBackToProductsButton(){
        var backtoproductsDiv = document.getElementById("back-to-main-page");
        var backtoproductsButton = document.createElement("input");
        backtoproductsButton.setAttribute("type", "button");
        backtoproductsButton.setAttribute("class", "btn btn-warning");
        backtoproductsButton.value = "Vissza a termékekhez";
        backtoproductsButton.setAttribute("onclick", "window.location.href='http://localhost:8080/index.html'");
        backtoproductsDiv.appendChild(backtoproductsButton);
}

function createAddToBasketButton(id) {
  var table = document.getElementById("product-data");
  var addButtonTd = document.getElementById("buttons");
  var addToBasketButton = document.createElement("input");
  addToBasketButton.setAttribute("type", "button");
  addToBasketButton.setAttribute("class", "btn btn-warning");
  addToBasketButton.setAttribute("style", "margin-right:30px");
  addToBasketButton.value = "Kosárba";

  addToBasketButton.onclick = addToBasket;

  addButtonTd.appendChild(addToBasketButton);
}

function createQuantityInput() {
    // This button will increment the value
        $('[data-quantity="plus"]').click(function(e){
            // Stop acting like a button
            e.preventDefault();
            // Get the field name
            fieldName = $(this).attr('data-field');
            // Get its current value
            var currentVal = parseInt($('input[name='+fieldName+']').val());
            // If is not undefined
            if (!isNaN(currentVal)) {
                // Increment
                $('input[name='+fieldName+']').val(currentVal + 1);
            } else {
                // Otherwise put a 1 there
                $('input[name='+fieldName+']').val(1);
            }
        });
    // This button will decrement the value till 1
        $('[data-quantity="minus"]').click(function(e) {
            // Stop acting like a button
            e.preventDefault();
            // Get the field name
            fieldName = $(this).attr('data-field');
            // Get its current value
            var currentVal = parseInt($('input[name='+fieldName+']').val());
            // If it isn't undefined or its greater than 1
            if (!isNaN(currentVal) && currentVal > 1) {
                // Decrement one
                $('input[name='+fieldName+']').val(currentVal - 1);
            } else {
                // Otherwise put a 1 there
                $('input[name='+fieldName+']').val(1);
            }
        });
        $(".input-number").keydown(function (e) {
            if(e.keyCode == 13){
                addToBasket();
                $('input[name='+fieldName+']').val(1);
            }
            // Allow: backspace, delete, tab, escape
            if ($.inArray(e.keyCode, [46, 8, 9, 27]) !== -1 ||
                 // Allow: Ctrl+A, Crtl+C, Crtl+V, Crtl+X
                (e.ctrlKey === true && e.keyCode == 65 && e.keyCode == 67 && e.keyCode == 86 && e.keyCode == 88) ||
                 // Allow: home, end, left, right
                (e.keyCode >= 35 && e.keyCode <= 39)) {
                     // let it happen, don't do anything
                     return;
            }
            // Ensure that it is a number and stop the keypress
            if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
                e.preventDefault();
            }
        });

        $('.input-number').change(function() {
            minValue =  parseInt($(this).attr('min'));
            maxValue =  parseInt($(this).attr('max'));
            valueCurrent = parseInt($(this).val());
            name = $(this).attr('name');
            if(!$(this).val()) {
               // alert('Input can not be left blank');
                return false;
            }
            if(valueCurrent >= minValue) {
                $(".btn-number[data-type='minus'][data-field='"+name+"']").removeAttr('disabled')
            } else {
                alert('Sorry, the minimum value was reached');
                $(this).val($(this).data('oldValue'));
            }
            if(valueCurrent <= maxValue) {
                $(".btn-number[data-type='plus'][data-field='"+name+"']").removeAttr('disabled')
            } else {
                alert('Sorry, the maximum value was reached');
                $(this).val($(this).data('oldValue'));
            }
        });
}

function addToBasket() {
  var request = {
      "product":{
          "id" : id,
          "name" : document.getElementById("name-td").value,
          "code" : document.getElementById("code-td").value ,
          "manufacturer" : document.getElementById("manufacturer-td").value,
          "price" : document.getElementById("price-td").value,
//          "address" : document.getElementById("address-td").value,
      },
      "quantity": document.getElementById(`quantity-input`).value
    }
  fetch("/api/products/" + id + "/tobasket", {
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
      console.log(jsonData);
      if (jsonData.ok) {
        document
          .getElementById("basket-message-div")
          .setAttribute("class", "alert alert-success");
      } else {
        document
          .getElementById("basket-message-div")
          .setAttribute("class", "alert alert-danger");
      }
      document.getElementById("basket-message-div").innerHTML = jsonData.message;
    });
}