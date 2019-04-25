var fileByteArray = [];
var categories;
fetchCategories();
fetchProducts();

  var createForm = document.getElementById("create-form");
  createForm.onsubmit = handleCreateFormSubmit;
  document.getElementById("file").addEventListener('change',handleFileUpload,false);

//    var imageForm = document.getElementById("picture-form");
//        imageForm.onsubmit = uploadImage;

var activeCheckBox = document.getElementById("active-filter");
    activeCheckBox.addEventListener('change', fetchProducts, false);
var deleteCheckBox = document.getElementById("deleted-filter");
    deleteCheckBox.addEventListener('change', fetchProducts, false);

function fetchCategories(){
    fetch("/api/categories")
    .then(function(response) {
    return response.json();
    })
    .then(function(jsonData) {
       loadCategories(jsonData);
       categories = jsonData;
    });
}

function loadCategories(jsonData){
    for (var i = 0; i < jsonData.length; i++){
        var option = document.createElement("option");
        option.value = jsonData[i].id;
        option.innerHTML = jsonData[i].name;
        document.getElementById("category-input").appendChild(option);
    }
}

function handleCreateFormSubmit() {
  var name = document.getElementById("name-input").value;
  var code = document.getElementById("code-input").value;
  var manufacturer = document.getElementById("manuf-input").value;
  var price = document.getElementById("price-input").value;

  var selector = document.getElementById("category-input");
  var value = selector.options[selector.selectedIndex].value;
    var categoryId;

  if (value == ""){
    categoryId = 6;
  }
    else {
    categoryId = value;
    }

   var request =
  {
  "name": name,
  "code": code,
  "manufacturer": manufacturer,
  "price": price,
  "category" : {
    "id" : categoryId
  },
   "productStatus" : "ACTIVE",
   "image": fileByteArray
   };

  fetch("/api/products", {
    method: "POST",
    body: JSON.stringify(request),
    headers: {
      "Content-type": "application/json"
    }
  }).then(function (response) {
    return response.json();
  }).
    then(function (jsonData) {
      if (jsonData.ok) {
        document.getElementById("name-input").value = "";
        document.getElementById("code-input").value = "";
        document.getElementById("manuf-input").value = "";
        document.getElementById("price-input").value = "";
        document.getElementById("category-input").value = "";
        fileByteArray = [];
        fetchProducts();
        document.getElementById("message-div").setAttribute("class", "alert alert-success");
      }
      else {
        document.getElementById("message-div").setAttribute("class", "alert alert-danger");
      }
      document.getElementById("message-div").innerHTML = jsonData.message;
    });
  return false;
}

function fetchProducts() {
  let url = "/api/products";
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
       if (!activeCheckBox.checked  && !deleteCheckBox.checked) {
        showTable(jsonData);
       }
       else if (activeCheckBox.checked) {
        showActiveProducts(jsonData);
       }
       else if (deleteCheckBox.checked) {
        showDeletedProducts(jsonData);
       }
    });
}

function showTable(jsonData) {
  table = document.getElementById("product-table");
  table.innerHTML = "";
  for (var i = 0; i < jsonData.length; i++) {
        var tr = document.createElement("tr");
        tr["tr-data"] = jsonData[i];
        var id = jsonData[i].id;

        var nameTd = document.createElement("td");
        nameTd.innerHTML = "<a href='/product.html?address=" + jsonData[i].address + "'>" + jsonData[i].name + "</a>";
        var nameTdId = 'nameTd' + id;
        nameTd.setAttribute('id', nameTdId);
        tr.appendChild(nameTd);

        var codeTd = document.createElement("td");
        codeTd.innerHTML = jsonData[i].code;
        var codeTdId = 'codeTd' + id;
        codeTd.setAttribute('id', codeTdId);
        tr.appendChild(codeTd);

        var addressTd = document.createElement("td");
        addressTd.innerHTML = jsonData[i].address;
        var addressTdId = 'addressTd' + id;
        addressTd.setAttribute('id', addressTdId);
        tr.appendChild(addressTd);

        var manufacturerTd = document.createElement("td");
        manufacturerTd.innerHTML = jsonData[i].manufacturer;
        var manuTdId = 'manuTd' + id;
        manufacturerTd.setAttribute('id', manuTdId);
        tr.appendChild(manufacturerTd);

        var categoryTd = document.createElement("td");
        categoryTd.innerHTML = jsonData[i].category.name;
        var categoryTdId = 'categoryTd' + id;
        categoryTd.setAttribute('id', categoryTdId);
        tr.appendChild(categoryTd);

        var priceTd = document.createElement("td");
        priceTd.innerHTML = jsonData[i].price + " Ft";
        var priceTdId = 'priceTd' + id;
        priceTd.setAttribute('id', priceTdId);
        tr.appendChild(priceTd);

        var statusTd = document.createElement("td");
        if (jsonData[i].productStatus == "ACTIVE"){
        statusTd.innerHTML = "Aktív";
        }
        else if (jsonData[i].productStatus == "DELETED"){
        statusTd.innerHTML = "Törölt";
        }
        var statusTdId = 'statusTd' + id;
        statusTd["itself"] = jsonData[i].productStatus;
        statusTd.setAttribute('id', statusTdId);
        tr.appendChild(statusTd);

//        var pictureButtonTd = document.createElement("td");
//            var pictureButton = document.createElement("input");
//            pictureButton.setAttribute("type", "file");
//            pictureButton.setAttribute('id', `pictureButton${id}`);
//            pictureButton.addEventListener("change", handleFileUpload, false);
//            pictureButtonTd.appendChild(pictureButton);
//            tr.appendChild(pictureButtonTd);

        var deleteButtonTd = document.createElement("td");
        if (jsonData[i].productStatus == "ACTIVE"){
        var deleteButton = document.createElement("button");
        deleteButton.setAttribute("class", "btn btn-warning");
        deleteButton["data"] = jsonData[i];
        deleteButtonTd.appendChild(deleteButton);
        deleteButton.onclick = deleteProduct;
        deleteButton.setAttribute("id", "delete-button");
        var trashIcon = document.createElement("i");
        trashIcon.setAttribute("class", "fa fa-trash");
        trashIcon.setAttribute("area-hidden", "true");
        deleteButton.appendChild(trashIcon);
        }
        tr.appendChild(deleteButtonTd);


        var editButtonTd = document.createElement("td");
        var editButton = document.createElement("button");
        editButtonTd.appendChild(editButton);
        editButton.setAttribute('id', `editButton${id}`);

        editButton.setAttribute("class", "btn btn-warning");


        var editIcon = document.createElement("i");
        editIcon.setAttribute("class", "fa fa-pencil");
        editIcon.setAttribute("area-hidden", "true");
        editButton.appendChild(editIcon);

        editButton.onclick = editProduct;

        editButton["data"] = jsonData[i];

        tr.appendChild(editButtonTd);

        table.appendChild(tr);
        }
      }


function showActiveProducts(jsonData) {
    table = document.getElementById("product-table");
    table.innerHTML = "";
        for (var i = 0; i < jsonData.length; i++) {
          if (jsonData[i].productStatus == 'ACTIVE') {
            var tr = document.createElement("tr");
            tr["tr-data"] = jsonData[i];
            var id = jsonData[i].id;

            var nameTd = document.createElement("td");
            nameTd.innerHTML = "<a href='/product.html?address=" + jsonData[i].address + "'>" + jsonData[i].name + "</a>";
            var nameTdId = 'nameTd' + id;
            nameTd.setAttribute('id', nameTdId);
            tr.appendChild(nameTd);

            var codeTd = document.createElement("td");
            codeTd.innerHTML = jsonData[i].code;
            var codeTdId = 'codeTd' + id;
            codeTd.setAttribute('id', codeTdId);
            tr.appendChild(codeTd);

            var addressTd = document.createElement("td");
            addressTd.innerHTML = jsonData[i].address;
            var addressTdId = 'addressTd' + id;
            addressTd.setAttribute('id', addressTdId);
            tr.appendChild(addressTd);

            var manufacturerTd = document.createElement("td");
            manufacturerTd.innerHTML = jsonData[i].manufacturer;
            var manuTdId = 'manuTd' + id;
            manufacturerTd.setAttribute('id', manuTdId);
            tr.appendChild(manufacturerTd);

            var categoryTd = document.createElement("td");
            categoryTd.innerHTML = jsonData[i].category.name;
            var categoryTdId = 'categoryTd' + id;
            categoryTd.setAttribute('id', categoryTdId);
            tr.appendChild(categoryTd);

            var priceTd = document.createElement("td");
            priceTd.innerHTML = jsonData[i].price + " Ft";
            var priceTdId = 'priceTd' + id;
            priceTd.setAttribute('id', priceTdId);
            tr.appendChild(priceTd);

            var statusTd = document.createElement("td");
            statusTd.innerHTML = "Aktív";
            var statusTdId = 'statusTd' + id;
            statusTd["itself"] = jsonData[i].productStatus;
            statusTd.setAttribute('id', statusTdId);
            tr.appendChild(statusTd);

            var deleteButtonTd = document.createElement("td");
            var deleteButton = document.createElement("button");
            deleteButton.setAttribute("class", "btn btn-warning");
            deleteButton["data"] = jsonData[i];
            deleteButtonTd.appendChild(deleteButton);
            deleteButton.onclick = deleteProduct;
            deleteButton.setAttribute("id", "delete-button");
            var trashIcon = document.createElement("i");
            trashIcon.setAttribute("class", "fa fa-trash");
            trashIcon.setAttribute("area-hidden", "true");
            deleteButton.appendChild(trashIcon);

            tr.appendChild(deleteButtonTd);

            var editButtonTd = document.createElement("td");
            var editButton = document.createElement("button");
            editButtonTd.appendChild(editButton);
            editButton.setAttribute('id', `editButton${id}`);

            editButton.setAttribute("class", "btn btn-warning");

            var editIcon = document.createElement("i");
            editIcon.setAttribute("class", "fa fa-pencil");
            editIcon.setAttribute("area-hidden", "true");
            editButton.appendChild(editIcon);

            editButton.onclick = editProduct;

            editButton["data"] = jsonData[i];

            tr.appendChild(editButtonTd);

            table.appendChild(tr);
          }
    }
    return table;
}

function showDeletedProducts(jsonData) {
    table = document.getElementById("product-table");
    table.innerHTML = "";
        for (var i = 0; i < jsonData.length; i++) {
          if (jsonData[i].productStatus == 'DELETED') {
            var tr = document.createElement("tr");
            tr["tr-data"] = jsonData[i];
            var id = jsonData[i].id;

            var nameTd = document.createElement("td");
            nameTd.innerHTML = "<a href='/product.html?address=" + jsonData[i].address + "'>" + jsonData[i].name + "</a>";
            var nameTdId = 'nameTd' + id;
            nameTd.setAttribute('id', nameTdId);
            tr.appendChild(nameTd);

            var codeTd = document.createElement("td");
            codeTd.innerHTML = jsonData[i].code;
            var codeTdId = 'codeTd' + id;
            codeTd.setAttribute('id', codeTdId);
            tr.appendChild(codeTd);

            var addressTd = document.createElement("td");
            addressTd.innerHTML = jsonData[i].address;
            var addressTdId = 'addressTd' + id;
            addressTd.setAttribute('id', addressTdId);
            tr.appendChild(addressTd);

            var manufacturerTd = document.createElement("td");
            manufacturerTd.innerHTML = jsonData[i].manufacturer;
            var manuTdId = 'manuTd' + id;
            manufacturerTd.setAttribute('id', manuTdId);
            tr.appendChild(manufacturerTd);

            var categoryTd = document.createElement("td");
            categoryTd.innerHTML = jsonData[i].category.name;
            var categoryTdId = 'categoryTd' + id;
            categoryTd.setAttribute('id', categoryTdId);
            tr.appendChild(categoryTd);

            var priceTd = document.createElement("td");
            priceTd.innerHTML = jsonData[i].price + " Ft";
            var priceTdId = 'priceTd' + id;
            priceTd.setAttribute('id', priceTdId);
            tr.appendChild(priceTd);

            var statusTd = document.createElement("td");
            statusTd.innerHTML = "Törölt";
            var statusTdId = 'statusTd' + id;
            statusTd["itself"] = jsonData[i].productStatus;
            statusTd.setAttribute('id', statusTdId);
            tr.appendChild(statusTd);

            var editButtonTd = document.createElement("td");
            var editButton = document.createElement("button");
            editButtonTd.appendChild(editButton);
            editButton.setAttribute('id', `editButton${id}`);

            editButton.setAttribute("class", "btn btn-warning");

            var editIcon = document.createElement("i");
            editIcon.setAttribute("class", "fa fa-pencil");
            editIcon.setAttribute("area-hidden", "true");
            editButton.appendChild(editIcon);

            editButton.onclick = editProduct;

            editButton["data"] = jsonData[i];

            tr.appendChild(editButtonTd);

            table.appendChild(tr);
          }
    }
    return table;
}

function deleteProduct() {
  var id = this["data"].id;
  if (!confirm("Biztosan törölni szeretnéd?")) {
    return;
  }
  fetch("/api/products/" + id, {
    method: "DELETE",
  })
    .then(function (response) {
      document.getElementById("message-div").innerHTML = "Termék törölve!";
      document.getElementById("message-div").setAttribute("class", "alert alert-success");
      fetchProducts();
    });
}

function editProduct(){
    var id = this["data"].id;
    var name = this["data"].name;
    var code = this["data"].code;
    var address = this["data"].address;
    var manu = this["data"].manufacturer;
    var category = this["data"].category;
    var price = this["data"].price;
    var status = this["data"].status;




   document.getElementById(`nameTd${id}`).innerHTML = `<input id="nameInput${id}" type="text" value="${name}" required>`
   document.getElementById(`codeTd${id}`).innerHTML = `<input id="codeInput${id}" type="text" value="${code}" required>`
   document.getElementById(`addressTd${id}`).innerHTML = `<input id="addressInput${id}" type="text" value="${address}" required>`
   document.getElementById(`manuTd${id}`).innerHTML = `<input id="manuInput${id}" type="text" value="${manu}" required>`
   document.getElementById(`priceTd${id}`).innerHTML = `<input id="priceInput${id}" type="number" value="${price}" min=1 max=2000000 required>`
   document.getElementById(`categoryTd${id}`).innerHTML = `<select id="categoryInput${id}" >  <option disabled value="">---Kategória---</option> </select>`

   for (var i = 0; i < categories.length; i++){
           var option = document.createElement("option");
           option.value = categories[i].id;
           option.innerHTML = categories[i].name;
           if (categories[i].id == category.id){
           option.selected = true;
           }
           document.getElementById(`categoryInput${id}`).appendChild(option);
       }


    var editButton = document.getElementById(`editButton${id}`);
    editButton.setAttribute("class", "btn btn-warning");

    var saveIcon = document.createElement("i");
    editButton.removeChild(editButton.childNodes[0]);
    saveIcon.setAttribute("class", "fa fa-save");
    saveIcon.setAttribute("area-hidden", "true");
    editButton.appendChild(saveIcon);


    editButton.onclick = saveProduct;

    }

    function saveProduct(){
        var id = this["data"].id;
        var request = {
        "id" : id,
        "name" : document.getElementById(`nameInput${id}`).value,
        "code" : document.getElementById(`codeInput${id}`).value,
        "address" :  document.getElementById(`addressInput${id}`).value,
        "manufacturer" : document.getElementById(`manuInput${id}`).value,
        "price" :  document.getElementById(`priceInput${id}`).value,
        "productStatus" : document.getElementById(`statusTd${id}`).itself,
        "category" : {
           "id" : document.getElementById(`categoryInput${id}`).value,
        }
         }

         fetch("/api/products/" + id, {
                         method: "POST",
                         body: JSON.stringify(request),
                         headers: {
                             "Content-type": "application/json"
                         }
                     })
                     .then(function (response) {
                         return response.json();
                     }).
                      then(function (jsonData) {

                                 if (jsonData.ok) {
                                   fetchProducts();
                                     document.getElementById("message-div").setAttribute("class", "alert alert-success");
                                 }
                                 else {
                                 document.getElementById("message-div").setAttribute("class", "alert alert-danger");
                                 }
                                 document.getElementById("message-div").innerHTML = jsonData.message;
                               });
                           return false;

    }

    function handleFileUpload(evt){
        var fileList = evt.target.files;
        var file = fileList[0];
        var reader = new FileReader();
        reader.readAsArrayBuffer(file);

          reader.onloadend = function (evt) {

               var arrayBuffer = evt.target.result,
                         array = new Uint8Array(arrayBuffer);

             for (let i = 0; i < array.length; i++) {
                        fileByteArray.push(array[i]);
                      }
          }
    }



