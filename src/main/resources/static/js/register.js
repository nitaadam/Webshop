createRegisterForm();
document.getElementById('register-form').onsubmit = register;

window.scroll(function(){
    $(".arrow").css("opacity", 1 - $(window).scrollTop() / 250);
  });

function createRegisterForm() {
    var regForm = document.getElementById("register-form");
    var form = `
            <div id="form-div">
            <div style="width:44%" id ="message-div"></div>

            <br>
            <label for="name"><b>Név</b></label><br>
            <input type="text" name="name" id="name" required>
            <br>
            <br>
            <label for="username"><b>Felhasználónév</b></label><br>
            <input type="text" name="username" id="username" required>
            <br>
            <br>
            <label for="password"><b>Jelszó</b></label><br>
            <input type="password" name="password" id="password" required>
            <br>
            <br>
            <meter max="4" id="password-strength-meter" style="height: 25px;"></meter>
            <br>
            <p id="password-strength-text"></p>
            <br>
            <label for="password"><b>Jelszó újra</b></label><br>
            <input type="password" name="password" id="password-repeat" required>
            <br>
            <br>
            <input type="submit" value="Regisztráció" id="submit" class="btn btn-warning">
    `
    regForm.innerHTML = form;
}

function register() {
    var nameInput = document.getElementById("name");
    var usernameInput = document.getElementById("username");
    var passwordInput = document.getElementById("password");
    var passwordRepeatInput = document.getElementById("password-repeat");

    var name = nameInput.value;
    var username = usernameInput.value;
    var password = passwordInput.value;
    var passwordRepeat = passwordRepeatInput.value;
            if(password !== passwordRepeat){
                document.getElementById('message-div').setAttribute("class", "alert alert-danger");
                document.getElementById('message-div').innerHTML = "A két jelszó nem egyezik meg.";
                } else {
                        var user = {
                                    "name": name,
                                    "userName": username,
                                    "password": password
                                    }

                                    console.log(user);

                    fetch('/api/users', {
                          method: 'POST',
                             body: JSON.stringify(user),
                             headers: {
                            'Content-type': 'application/json'
                          }
                        })
                    .then(function (response) {
                    //console.log(response);
                        return response.json();
                    })
                    .then(function (jsonData) {
                    //console.log(jsonData);
                            if (jsonData.ok){
                            document.getElementById("message-div").setAttribute("class", "alert alert-success");
                            document.getElementById("name").innerHTML = "";
                            document.getElementById("username").innerHTML = "";
                            document.getElementById("password").innerHTML = "";
                            }
                            else {
                            document.getElementById("message-div").setAttribute("class", "alert alert-danger");
                            }

                             document.getElementById('message-div').innerHTML = jsonData.message;
                    })
                    }
                    return false;
                    }

var strength = {
  0: "Nem megfelelő",
  1: "Nagyon gyenge",
  2: "Gyenge",
  3: "Jó",
  4: "Erős"
}

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
    text.innerHTML = strength[result.score];
  } else {
    text.innerHTML = "";
  }
});
