window.onload = function () {
  loadErrorMessage();
};

function loadErrorMessage() {
  var errorDiv = document.getElementById('message-div');

  var params = new URLSearchParams(window.location.search);

  if (params.has('error')) {
    errorDiv.innerHTML = '<p>Hibás felhasználónév vagy jelszó<p>';
    errorDiv.setAttribute('class', 'alert alert-danger');
  } else {
    errorDiv.innerHTML = '';
    errorDiv.removeAttribute('class', 'alert alert-danger');
  }
}

$(window).scroll(function(){
    $(".arrow").css("opacity", 1 - $(window).scrollTop() / 250);
  //250 is fade pixels
  });