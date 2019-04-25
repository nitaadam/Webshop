var user = fetchUser();

function createModificationForm() {
    var modForm = document.getElementById('form-div');
    modForm.innerHTML = `
                        <form id="modification-form">
                            <br>
                            <label for="name"><b>Új név</b></label><br>
                            <input type="text" name="name" id="name" required>
                            <br>
                            <br>
                            <label for="password"><b>Új jelszó</b></label><br>
                            <input type="password" name="password" id="password" required>
                            <br>
                            <meter max="4" id="password-strength-meter"></meter>
                            <p id="password-strength-text"></p>
                            <br>
                            <label for="password"><b>Új jelszó még egyszer</b></label><br>
                            <input type="password" name="password" id="password-repeat" required>
                            <br>
                            <br>
                            <input type="submit" value="Módosítás" id="submit" class="btn btn-warning">
                        </form>
    `
    document.getElementById('modification-form').onsubmit = modify;
    return modForm;
}


function fetchUser() {
  var url = "/api/user";
  return fetch(url)
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      user = jsonData;
      console.log(user);
     createModificationForm();
     passwordStrengthValidator()
    });
}

var strength = {
  0: "Nem megfelelő",
  1: "Nagyon gyenge",
  2: "Gyenge",
  3: "Jó",
  4: "Erős"
}

function passwordStrengthValidator() {
var password = document.getElementById('password');
var meter = document.getElementById('password-strength-meter');
var text = document.getElementById('password-strength-text');

password.addEventListener('input', function() {
  var val = password.value;
  var result = zxcvbn(val);

  // Update the password strength meter
  meter.value = result.score;

  // Update the text indicator
  if (val !== "") {
    text.innerHTML = "Strength: " + strength[result.score];
  } else {
    text.innerHTML = "";
  }
});
}

function modify() {
    var nameInput = document.getElementById("name");
    var passwordInput = document.getElementById("password");
    var passwordRepeatInput = document.getElementById("password-repeat");

    var name = nameInput.value;
    var password = passwordInput.value;
    var passwordRepeat = passwordRepeatInput.value;
        if(password !== passwordRepeat){
//            passwordRepeatInput.value = "";
//            passwordInput.value = "";
//            alert("A két jelszó nem egyezik meg.");
//            return;
                document.getElementById('message-div').setAttribute("class", "alert alert-danger");
                document.getElementById('message-div').innerHTML = "A két jelszó nem egyezik meg.";
            } else {
//     console.log(userName);
                    var userNew = {
                                "userName": user.username,
                                "name" : name,
                                "password": password
                                };
                    fetch("api/userprofile", {
                        method: "PUT",
                        headers: {
                            "Content-type": "application/json"
                        },
                        body: JSON.stringify(userNew)
                    }).then(function(response) {
                        return response.json()
                    }).then(function(jsonData) {
                        if (jsonData.ok){
                            document.getElementById("message-div").setAttribute("class", "alert alert-success");
                        }
                        else {
                            document.getElementById("message-div").setAttribute("class", "alert alert-danger");
                        }
                            document.getElementById("message-div").innerHTML = jsonData.message;
                    });
                    }
                    return false;
                }









