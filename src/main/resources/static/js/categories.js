window.onload = function () {
  fetchCategories();

  var createForm = document.getElementById("create-form");
  createForm.onsubmit = handleCreateFormSubmit;
}

//  ÚJ KATEGÓRIA FELVÉTELE

function handleCreateFormSubmit() {
  //var id = document.getElementById("id-input").value;
  var name = document.getElementById("name-input").value;
  var viewOrder = document.getElementById("viewOrder-input").value;

  var request =
  {
  "name": name,
  "viewOrder": viewOrder,
  };

  fetch("/api/categories", {
    method: "POST",
    body: JSON.stringify(request),
    headers: {
      "Content-type": "application/json"
    }
  }).then(function (response) {
    return response.json();
  }).
    then(function (jsonData) {
        console.log('POST fetch jsonData-ja: ' + jsonData);
      if (jsonData.ok) {
       // document.getElementById("id-input").value = "";
        document.getElementById("name-input").value = "";
        document.getElementById("viewOrder-input").value = "";
        fetchCategories();
        document.getElementById("message-div").setAttribute("class", "alert alert-success");
      } else {
        document.getElementById("message-div").setAttribute("class", "alert alert-danger");
      }
      document.getElementById("message-div").innerHTML = jsonData.message;
    });
  return false;
}

// TÁBLÁZAT:

function fetchCategories() {
  var url = "/api/categories";
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showTable(jsonData);
    });
}

function showTable(jsonData) {
  table = document.getElementById("product-table");
  table.innerHTML = "";

  for (var i = 0; i < jsonData.length; i++) {
    var tr = document.createElement("tr");
    tr["tr-data"] = jsonData[i];
    var id = jsonData[i].id;

    var orderTd = document.createElement("td");
    orderTd.innerHTML = jsonData[i].viewOrder;
    var orderTdId = 'orderTd' + id;
    orderTd.setAttribute('id', orderTdId);
    tr.appendChild(orderTd);

    var nameTd = document.createElement("td");
    nameTd.innerHTML = jsonData[i].name;
    var nameTdId = 'nameTd' + id;
    nameTd.setAttribute('id', nameTdId);
    tr.appendChild(nameTd);



    var deleteButtonTd = document.createElement("td");

    if (jsonData[i].id != 6){

    var deleteButton = document.createElement("button");
        deleteButton.setAttribute("class", "btn btn-warning");
        deleteButton["data"] = jsonData[i];
        deleteButtonTd.appendChild(deleteButton);

//        deleteButton.innerHTML = "Töröl";
        deleteButton.onclick = deleteCategory;
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
//    editButton.innerHTML = "Szerkeszt";
    editButton.setAttribute('id', `editButton${id}`);

    editButton.setAttribute("class", "btn btn-warning");

    var editIcon = document.createElement("i");
    editIcon.setAttribute("class", "fa fa-pencil");
    editIcon.setAttribute("area-hidden", "true");

    editButton.appendChild(editIcon);

    editButton.onclick = updateCategory;

    editButton["data"] = jsonData[i];

    tr.appendChild(editButtonTd);

    table.appendChild(tr);
  }
}

// TÖRLÉSGOMB:

function deleteCategory() {
  if (!confirm("Biztosan törölni szeretnéd?")) {
    return;
  }
  console.log(this["data"]);
  fetch("/api/categories", {
    method: "DELETE",
    body: JSON.stringify(this["data"]),
    headers: {
     "Content-type": "application/json"
    }
  })
    .then(function (response){
    return response.json();
    }).
    then(function (jsonData){
         if (jsonData.ok){
    fetchCategories();
    document.getElementById("message-div").setAttribute("class", "alert alert-success");
    }
    else{
    document.getElementById("message-div").setAttribute("class", "alert alert-danger");
    }
    document.getElementById("message-div").innerHTML = jsonData.message;
  });
}

// SZERKESZTÉSGOMB:

function updateCategory(){
   var id = this["data"].id;
   var name = this["data"].name;
   var viewOrder = this["data"].viewOrder;

   document.getElementById(`nameTd${id}`).innerHTML = `<input id="nameInput${id}" type="text" value="${name}" required>`
   document.getElementById(`orderTd${id}`).innerHTML = `<input id="viewOrderInput${id}" type="number" min=1 value="${viewOrder}" required>`

   var editButton = document.getElementById(`editButton${id}`);
//   editButton.innerHTML = "Mentés";
   editButton.setAttribute("class", "btn btn-warning");

    var saveIcon = document.createElement("i");
    editButton.removeChild(editButton.childNodes[0]);
//     editButton.classList.remove("fa fa-pen");
    saveIcon.setAttribute("class", "fa fa-save");
    saveIcon.setAttribute("area-hidden", "true");
    editButton.appendChild(saveIcon);

   editButton.onclick = saveCategory;
}

// MENTÉSGOMB:

function saveCategory(){
    var id = this["data"].id;
    var request = {
    "id" : id,
    "name" : document.getElementById(`nameInput${id}`).value,
    "viewOrder" : document.getElementById(`viewOrderInput${id}`).value,
    }

    fetch("/api/categories", {
        method: "PUT",
        body: JSON.stringify(request),
        headers: {
            "Content-type": "application/json"
            }
        })
    .then(function (response) {
        return response.json();
    }).then(function (jsonData) {
        console.log(jsonData);
        if (jsonData.ok) {
            document.getElementById(`nameTd${id}`).innerHTML = jsonData.name;
            document.getElementById(`orderTd${id}`).innerHTML = jsonData.viewOrder;

            fetchCategories();

            document.getElementById("message-div").setAttribute("class", "alert alert-success");
        } else {
            document.getElementById("message-div").setAttribute("class", "alert alert-danger");
        }
        document.getElementById("message-div").innerHTML = jsonData.message;
    });
    return false;
}