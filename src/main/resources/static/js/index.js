window.onload = function(){
    fetchProducts();
    fetchCategories();
    fetchLast3SoldItems();
}

function fetchLast3SoldItems(){
    fetch("/api/products/last3")
    .then(function(response) {
    return response.json();
    })
    .then(function(jsonData) {
        showLast3SoldItems(jsonData);
    });
}

function showLast3SoldItems(jsonData) {

  console.log(jsonData);

  var firstPic = document.getElementById("first-img");
  if (jsonData[0].image == ""){
                  firstPic.setAttribute('src', "/img/comingSoon.png");
                  }
else if (jsonData[0].image != null) {
    firstPic.setAttribute('src', `data:image/jpg;base64,${jsonData[0].image}`);
  }

  else {
    firstPic.setAttribute('src', '/img/' + jsonData[0].code + '.jpg');
  }
  firstPic.setAttribute('alt', jsonData[0].name);

  var secondPic = document.getElementById("second-img");
   if (jsonData[1].image == ""){
                secondPic.setAttribute('src', "/img/comingSoon.png");
                }

    else if (jsonData[1].image != null) {
    secondPic.setAttribute('src', `data:image/jpg;base64,${jsonData[1].image}`);
  }
  else {
    secondPic.setAttribute('src', '/img/' + jsonData[1].code + '.jpg');
  }
  secondPic.setAttribute('alt', jsonData[1].name);

  var thirdPic = document.getElementById("third-img");
 if (jsonData[2].image == ""){
             thirdPic.setAttribute('src', "/img/comingSoon.png");
             }

  else if (jsonData[2].image != null) {
    thirdPic.setAttribute('src', `data:image/jpg;base64,${jsonData[2].image}`);
    }

  else {
    thirdPic.setAttribute('src', '/img/' + jsonData[2].code + '.jpg');
  }
  thirdPic.setAttribute('alt', jsonData[2].name);

  var firstName = document.getElementById("first-name");
  var firstDuckName = firstName.innerHTML = jsonData[0].name;
  firstName.setAttribute('class', 'name');
  var link1 = document.getElementById("link1");
  link1.setAttribute('href', `product.html?address=${jsonData[0].address}`);

  var secondName = document.getElementById("second-name");
  var secondDuckName = secondName.innerHTML = jsonData[1].name;
  secondName.setAttribute('class', 'name');
  var link2 = document.getElementById("link2");
  link2.setAttribute('href', `product.html?address=${jsonData[1].address}`);

  var thirdName = document.getElementById("third-name");
  var thirdDuckName = thirdName.innerHTML = jsonData[2].name;
  thirdName.setAttribute('class', 'name');
  var link3 = document.getElementById("link3");
  link3.setAttribute('href', `product.html?address=${jsonData[2].address}`);
}

function fetchCategories(){
    fetch("/api/categories")
    .then(function(response) {
    return response.json();
    })
    .then(function(jsonData) {
       loadCategories(jsonData);
    });
}

function loadCategories(jsonData){
    var select = document.getElementById("category-input");
    select.onchange = function(){
    console.log(select.value);
    if (select.value == "all"){
        fetchProducts();
    }
    else {
         fetchProductsOfCategory();
    }
    }

    for (var i = 0; i < jsonData.length; i++){
        var option = document.createElement("option");
        option.value = jsonData[i].id;
        option.innerHTML = jsonData[i].name;
        document.getElementById("category-input").appendChild(option);
    }
}

function fetchProductsOfCategory(){
    var id = document.getElementById("category-input").value;
    fetch("api/categories/" + id)
    .then(function(response){
    return response.json();
    })
    .then(function(jsonData) {
    showTable(jsonData);
    })
}

function fetchProducts(){
    let url ="http://localhost:8080/api/products?filter=active";
    fetch(url)
        .then(function(response) {
        return response.json();
        })
        .then(function(jsonData) {
        showTable(jsonData);
        });
}

function showTable(jsonData) {
    var mainDiv = document.getElementById("main-div");
    if (jsonData.length == 0){
        mainDiv.innerHTML = "<br>" + "Ebben a kategóriában jelenleg nem forgalmazunk terméket!";
        return;
    }
    var str = "";
    for(var i=0; i<jsonData.length; i++){
            str += `
                <div id="product-${i+1}" class="product">
                    <a href = 'product.html?address=${jsonData[i].address}'>
                        <div>
                            <img id="${jsonData[i].id}" class="small_pics">
                        </div>
                    </a>
                    <div class="products" align="center">
                        <br>
                        <span class="product_name">${jsonData[i].name} </span><br>
                        <span class="product_manufacturer">${jsonData[i].manufacturer} </span><br>
                        <span class="product_category">${jsonData[i].category.name} </span><br>
                        <span class="product_price">${jsonData[i].price} Ft</span><br>
                    </div>
                </div>
                `;
                }

    mainDiv.innerHTML = str;

    for (var i = 0; i< jsonData.length; i++){
    if (jsonData[i].image == ""){
            document.getElementById(jsonData[i].id).setAttribute('src', "/img/comingSoon.png");
            }
    else if (jsonData[i].image != null){
        document.getElementById(jsonData[i].id).setAttribute('src', `data:image/jpg;base64,${jsonData[i].image}`);
    }

    else {
        document.getElementById(jsonData[i].id).setAttribute('src', `/img/${jsonData[i].code}.jpg`);
    }
    }
}