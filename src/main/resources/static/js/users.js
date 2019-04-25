fetchUsers();

function fetchUsers() {
  var url = "/api/users/";

  console.log(url);
  fetch(url)
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      showTable(jsonData);
    });
}

function showTable(jsonData) {
  table = document.getElementById("users-table");
  table.innerHTML = "";
  var deleted = "null";
  for (var i = 0; i < jsonData.length; i++) {
    console.log(jsonData[i]);
    if (jsonData[i].name !== "null") {
      var tr = document.createElement("tr");

      //név:

      //  var nameInput = document.createElement("input");
      //   nameInput.setAttribute("id", jsonData[i].userName);
      //   nameInput.value = jsonData[i].name;
      //  nameTd.appendChild(nameInput);
      var nameTd = document.createElement("td");
      nameTd.innerHTML = jsonData[i].name;
      nameTd.setAttribute("id", jsonData[i].id);
      tr.appendChild(nameTd);

      //felhasználónév:
      var userNameTd = document.createElement("td");
      userNameTd.innerHTML = jsonData[i].userName;
      userNameTd.setAttribute("id", jsonData[i].userName);
      tr.appendChild(userNameTd);

      //jelszó:

      var passwordTd = document.createElement("td");
      // var passwordInput = document.createElement("input");
      // passwordInput.setAttribute("type", "password")
      //passwordInput.setAttribute("id", jsonData[i].id);
      //passwordInput.value = jsonData[i].password;
      // passwordTd.appendChild(passwordInput);
      passwordTd.innerHTML = jsonData[i].password;
      passwordTd.setAttribute("id", jsonData[i].userName + "pw");
      tr.appendChild(passwordTd);

      //role
      var roleTd = document.createElement("td");
      roleTd.setAttribute("id", jsonData[i].userRole);
      if (jsonData[i].userRole == "ROLE_ADMIN") {
        roleTd.innerHTML = "adminisztrátor";
      } else {
        roleTd.innerHTML = "felhasználó";
      }

      tr.appendChild(roleTd);

      var modifyButtonTd = document.createElement("td");
      var modifyButton = document.createElement("button");
      modifyButtonTd.appendChild(modifyButton);
      modifyButton.setAttribute("class", "btn btn-warning");
//      modifyButton.innerHTML = "Felhasználó adatainak módosítása";
      var editIcon = document.createElement("i");
      editIcon.setAttribute("class", "fa fa-pencil");
      editIcon.setAttribute("area-hidden", "true");
      modifyButton.appendChild(editIcon);

      modifyButton.setAttribute("id", `modifyButton${jsonData[i].userName}`);
      modifyButton["data"] = jsonData[i];
      modifyButton.type = "button";
      modifyButton.onclick = modifyUser;
      tr.appendChild(modifyButtonTd);

      var buttonTd = document.createElement("td");
      var deleteButton = document.createElement("button");
      deleteButton.setAttribute("class", "btn btn-warning");
      buttonTd.appendChild(deleteButton);
//      deleteButton.innerHTML = "Felhasználó törlése";
      var trashIcon = document.createElement("i");
      trashIcon.setAttribute("class", "fa fa-trash");
      trashIcon.setAttribute("area-hidden", "true");
      deleteButton.appendChild(trashIcon);

      deleteButton.onclick = deleteUser;
      deleteButton["data"] = jsonData[i].userName;
      deleteButton.type = "button";
      tr.appendChild(buttonTd);

      table.appendChild(tr);
    }
  }
}

function deleteUser() {
  var userName = this["data"];
  if (!confirm("Biztosan törölni szeretnéd?")) {
    return;
  }
  var request = { username: userName };
  fetch("/api/users/" + userName, {
    method: "DELETE"
  })
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      console.log(jsonData);
      if (jsonData.ok) {
        fetchUsers();
        document
          .getElementById("message-div")
          .setAttribute("class", "alert alert-success");
      }
      //      if (jsonData.message == "Ha törölni szeretnéd ezt a felhasználót, adj hozzá egy másik adminisztrátort a fiókhoz!") {
      //        document
      //          .getElementById("message-div")
      //          .setAttribute("class", "alert alert-danger");
      //      }
      else {
        document
          .getElementById("message-div")
          .setAttribute("class", "alert alert-danger");
      }
      document.getElementById("message-div").innerHTML = jsonData.message;
    });
}

function modifyUser() {
  console.log(this["data"]);
  var id = this["data"].id;
  var name = this["data"].name;
  var userName = this["data"].userName;
  var userPw = this["data"].password;
  // var enabled =this["data"].id;
  //var name = document.getElementById(userName).value;
  //var password = document.getElementById(enabled).value;

  document.getElementById(
    `${id}`
  ).innerHTML = `<input id="nameInput${id}" type="text" value="${name}" required>`;
  document.getElementById(
    `${userName}pw`
  ).innerHTML = `<input id="userPwInput${id}" type="password" value="${userPw}" required>`;

  //var editButton = document.getElementById(`modifyButton${userName}`);
  this.innerHTML = "Mentés";
  this.onclick = saveUser;
}

function saveUser() {
  var id = this["data"].id;
  var request = {
    id: id,
    name: document.getElementById(`nameInput${id}`).value,
    userName: document.getElementById(`${this["data"].userName}`).innerText,
    password: document.getElementById(`userPwInput${id}`).value
  };
  console.log(request);
  fetch("/api/users/" + this["data"].userName, {
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
        //var name = this["data"].name;
        //document.getElementById(name).innerHTML = jsonData.name;
        //document.getElementById(`nameInput${id}`).innerHTML = jsonData.name;
        // document.getElementById(`userPwInput${id}`).innerHTML = jsonData.password;
        fetchUsers();
        document
          .getElementById("message-div")
          .setAttribute("class", "alert alert-success");
      } else {
        document
          .getElementById("message-div")
          .setAttribute("class", "alert alert-danger");
      }
      document.getElementById("message-div").innerHTML = jsonData.message;
    });
  return false;
}
