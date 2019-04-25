fetchUser();
//fetchBasket();
addBootstrap();
addLogoFavIvon();
//createFooter();

function fetchUser(url) {
  var url = "/api/user";
  fetch(url)
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      validateUser(jsonData);
    });
}

function validateUser(jsonData) {
  var header = document.getElementById("header");
  var footer = document.getElementById("footer");
  if (jsonData.role == "ROLE_USER") {
    header.appendChild(createMenuForUser());
    document.getElementById("greeting").innerHTML =
      "Szia " + jsonData.username + "!";
//    footer.appendChild(createFooter());
  }
  if (jsonData.role == "ROLE_ADMIN") {
    header.appendChild(createMenuForAdmin());
    document.getElementById("greeting").innerHTML =
      "Szia " + jsonData.username + "!";
//    footer.appendChild(createFooter());
  } else if (jsonData.role == "ROLE_NOT_AUTHORIZED") {
    header.appendChild(createMenuForNotLoggedIn());
//    footer.appendChild(createFooter());
  }
}

function createMenuForNotLoggedIn() {
  var header = document.getElementById("header");
  var navbarDiv = document.createElement("div");
  navbarDiv.setAttribute("class", "navigationbar");
  navbarDiv.innerHTML = `
            <!--Logó, mint termékek/főoldal menüpont-->
                  <a href="index.html"><img src="img/logoFINAL.jpg" alt="" style="width: 240px; padding-top: 13px;" /></a>

                  <!--Rólunk-->
                  <div class="dropdown" style="font-size: 30px; padding-top: 10px; padding-left: 60px;">
                    <button class="dropbtn" onclick="window.location.href='http://localhost:8080/about.html'" >Rólunk</button>
                  </div>

                  <!--Főoldal-->
                  <div class="dropdown" style="font-size: 30px; padding-top: 10px;">
                    <button class="dropbtn" onclick="window.location.href='http://localhost:8080/index.html'" >Termékek</button>
                  </div>

                  <!--Jobb oldali menüpontok-->
                  <div class="dropdown_logout">
                    <div class="dropdown" style="font-size: 30px; padding-top: 10px;">
                      <button class="dropbtn" onclick="window.location.href='http://localhost:8080/login.html'" ></a>Bejelentkezés</button>
                    </div>
                    <div class="dropdown" style="font-size: 30px; padding-top: 10px;">
                      <button class="dropbtn" onclick="window.location.href='http://localhost:8080/register.html'" ></a>Regisztráció</button>
                    </div>
                  </div>
    `;
  header.appendChild(navbarDiv);
  return navbarDiv;
}

function createMenuForUser() {
  var header = document.getElementById("header");
  var navbarDiv = document.createElement("div");
  navbarDiv.setAttribute("class", "navigationbar");
  navbarDiv.innerHTML = `
            <!--Logó, mint termékek/főoldal menüpont-->
                  <a href="index.html"><img src="img/logoFINAL.jpg" alt="" style="width: 240px; padding-top: 13px;" /></a>

                  <!--Rólunk-->
                  <div class="dropdown" style="font-size: 30px; padding-top: 10px; padding-left: 60px;">
                    <button class="dropbtn" onclick="window.location.href='http://localhost:8080/about.html'" >Rólunk</button>
                  </div>

                  <!--Főoldal-->
                  <div class="dropdown">
                    <button class="dropbtn" onclick="window.location.href='http://localhost:8080/index.html'" style="font-size: 30px; padding-top: 22px;">Termékek</button>
                  </div>

                  <!--User kosár menüje-->
                  <!--
                  <div class="dropdown_basket">
                    <button class="dropbtn" onclick="window.location.href='http://localhost:8080/basket.html'">
                    <i class="material-icons" style="color: #4b6892; font-size: 50px;">shopping_cart</i></button>
                  </div>
                  -->

                  <!--User kosár menüje számlálóval-->
                  <div class="dropdown_basket">
                      <button class="dropbtn" onclick="window.location.href='http://localhost:8080/basket.html'">
                          <div class="dropdown_basket">
                              <span class="fa-stack fa-2x has-badge" style="font-size: 20px;" data-count=fetchBasket()>
                                <i class="fa fa-circle fa-stack-2x fa-inverse"></i>
                                <i style="" class="fa fa-shopping-cart fa-stack-2x red-cart"></i>
                              </span>
                          </div>
                      </button>
                  </div>

                  <!--User dropdown menüje-->
                  <div class="dropdown_logout">
                    <button class="dropbtn"><a href="#"><i class="material-icons" style="color: #4b6892; font-size: 50px;">perm_identity</i></a></button>
                        <div class="dropdown-content">
                          <a href="myorders.html">Megrendeléseim</a>
                          <a href="userprofile.html">Profilom </a>
                          <a href="/logout">Kijelentkezés </a>
                        </div>
                  </div>
                  <p id="greeting" class="dropbtn" style="padding-top: 22px; font-size: 30px;"></p>

    `;
  header.appendChild(navbarDiv);
  return navbarDiv;
}

function createMenuForAdmin() {
  var header = document.getElementById("header");
  var navbarDiv = document.createElement("div");
  navbarDiv.setAttribute("class", "navigationbar");
  navbarDiv.innerHTML = `
                  <!--Logó, mint termékek/főoldal menüpont-->
                  <a href="index.html"><img src="img/logoFINAL.jpg" alt="" style="width: 240px; padding-top: 13px;" /></a>

                  <!--Rólunk-->
                  <div class="dropdown" style="font-size: 30px; padding-top: 10px; padding-left: 60px;">
                    <button class="dropbtn" onclick="window.location.href='http://localhost:8080/about.html'" >Rólunk</button>
                  </div>

                  <!--Admin dropdown menü-->
                  <div class="dropdown">
                    <button class="dropbtn" style="font-size: 30px; padding-top: 22px; margin-left: 25px;">Adminisztráció<i class="fa fa-angle-down" style="padding-left: 10px;"></i></button>
                    <div class="dropdown-content">
                      <a href="adminproducts.html">Termékek</a>
                      <a href="orders.html">Rendelések</a>
                      <a href="users.html">Felhasználók</a>
                      <a href="categories.html">Kategóriák</a>
                      <a href="reports.html">Riportok</a>
                      <a href="dashboard.html">Dashboard</a>
                    </div>
                  </div>
                  <!--Admin dropdown logout menü-->
                  <div class="dropdown_logout" style="margin-right: 55px;">
                    <button class="dropbtn" ><i class="material-icons" style="color: #4b6892; font-size: 50px;">perm_identity</i></a></button>
                        <div class="dropdown-content"><a href="/logout" style="text-align: left;">Kijelentkezés </a></div>
                  </div>
                  <p id="greeting" class="dropbtn" style="padding-top: 22px; font-size: 30px; margin-right: 10px;"></p>
    `;
  header.appendChild(navbarDiv);
  return navbarDiv;
}

function createFooter() {
  var footer = document.getElementById("footer");
  var footerDiv = document.createElement("div");
  footerDiv.setAttribute("class", "container text-center text-md-left");
  footerDiv.innerHTML = `
<!-- Footer links -->
      <div class="row text-center text-md-left mt-3 pb-3">

      </div>
      <!-- Footer links -->

      <hr>

      <!-- Grid row -->
      <div class="row d-flex align-items-center">

        <!-- Grid column -->
        <div class="col-md-7 col-lg-8">

          <!--Copyright-->
          <p class="text-center text-md-left">© 2018 Copyright:
              <strong> bits n bytes </strong>
          </p>

        </div>
        <!-- Grid column -->

        <!-- Grid column -->
        <div class="col-md-5 col-lg-4 ml-lg-0">

          <!-- Social buttons -->
          <div class="text-center text-md-right">
            <ul class="list-unstyled list-inline">
              <li class="list-inline-item">
                <a class="btn-floating btn-sm rgba-white-slight mx-1">
                  <i class="fab fa-facebook-f"></i>
                </a>
              </li>
              <li class="list-inline-item">
                <a class="btn-floating btn-sm rgba-white-slight mx-1">
                  <i class="fab fa-twitter"></i>
                </a>
              </li>
              <li class="list-inline-item">
                <a class="btn-floating btn-sm rgba-white-slight mx-1">
                  <i class="fab fa-google-plus-g"></i>
                </a>
              </li>
              <li class="list-inline-item">
                <a class="btn-floating btn-sm rgba-white-slight mx-1">
                  <i class="fab fa-linkedin-in"></i>
                </a>
              </li>
            </ul>
          </div>

        </div>
        <!-- Grid column -->

      </div>
      <!-- Grid row -->
        `;
  footer.appendChild(footerDiv);
  return footerDiv;
}

function addBootstrap() {
  var head = document.getElementsByTagName("head")[0];

  var bootstraplink = document.createElement("link");
  bootstraplink.setAttribute("rel", "stylesheet");
  bootstraplink.setAttribute("type", "text/css");
  bootstraplink.setAttribute("href", "css/bootstrap.min.css");

  head.appendChild(bootstraplink);

  var fontLink = document.createElement("link");
  fontLink.setAttribute("rel", "stylesheet");
  fontLink.setAttribute("href", "https://fonts.googleapis.com/css?family=Dosis");

  head.appendChild(fontLink);

  var iconLink = document.createElement("link");
  iconLink.setAttribute("rel", "stylesheet");
  iconLink.setAttribute("href", "https://fonts.googleapis.com/icon?family=Material+Icons");

  head.appendChild(iconLink);

  var bootstraplink2 = document.createElement("link");
  bootstraplink2.setAttribute("rel", "stylesheet");
  bootstraplink2.setAttribute("href", "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css");

  head.appendChild(bootstraplink2);
}

function addLogoFavIvon() {
  var head = document.getElementsByTagName("head")[0];

  var link = document.createElement("link");
  link.setAttribute("rel", "icon");
  link.setAttribute("href", "img/browsericon.jpg");

  head.appendChild(link);
}

function fetchBasket() {
  fetch("http://localhost:8080/api/basket")
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
        getBasketQuantity(jsonData);
    });
}

function getBasketQuantity(jsonData) {
  var basketQuantity = 0;
  for (var i = 0; i < jsonData.length; i++) {
    basketQuantity += parseInt(jsonData[i].quantity);
  }
  console.log("kosár quantity: " + basketQuantity);
  return basketQuantity;

  }