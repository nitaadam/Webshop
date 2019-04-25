var address = new URL(document.location).searchParams.get("address");
var urls = [
  "/api/products/feedbacks/" +address,
 "/api/feedbacks/",
];

Promise.all(urls.map(url => fetch(url)))
       .then(responses => Promise.all(
         responses.map(r => r.json())
       ))
       .then(m => {
         console.log(m);
         listFeedbacks(m);
       });




function fetchUserWithDeliveredOrder(){
 var address = new URL(document.location).searchParams.get("address");

var url = "/api/feedbacks/getfeedbacks/" + address;
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
           if(jsonData.ok){
           sendFeedback();
           }else{
           document.getElementById("feedback-message-div").setAttribute("class", "alert alert-danger");
            document.getElementById("feedback-message-div").innerHTML = jsonData.message;
            document.getElementById("feedbackTextInput").value = "";
           }
        });
}


function sendFeedback() {
  var review = document.getElementById("feedbackTextInput").value;
  var address = new URL(document.location).searchParams.get("address");
  var stars = starRate;

  var request ={
  "stars": stars,
  "review": review,
  };
  fetch("/api/products/feedbacks/" + address ,{
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

      Promise.all(urls.map(url => fetch(url)))
             .then(responses => Promise.all(
               responses.map(r => r.json())
             ))
             .then(m => {
               listFeedbacks(m);
             });

        document.getElementById("feedbackTextInput").value = "";
        document.getElementById("feedback-message-div").setAttribute("class", "alert alert-success");
      }
      else {
        document.getElementById("feedback-message-div").setAttribute("class", "alert alert-danger");
      }
      document.getElementById("feedback-message-div").innerHTML = jsonData.message;
    });
  return false;
}

function listFeedbacks(jsonData){

var feedbacks = jsonData[0];
var mainDiv = document.getElementById("feedbacksDiv");

console.log(feedbacks.length);

if (feedbacks.length == 0){
    var noFeedbacksP = document.createElement('p');
    noFeedbacksP.innerHTML = "A termékhez még nem írtak értékelést. Légy te az első!";
    mainDiv.appendChild(noFeedbacksP);
    return;
}

document.getElementById("feedbacksDiv").innerHTML = "";
var feedbackDiv = document.createElement("div");

for(var i = 0; i < feedbacks.length; i++){

    var nameAndDate = document.createElement("p");
    nameAndDate.setAttribute("class", "name-and-date-review");
    var starP = document.createElement("p");
    var review = document.createElement("p");

    var date = feedbacks[i].date;
    var partsOfDate = date.split("T");
    var hunDatum = partsOfDate[0].replace(/-/g, ".");
    var partsOfTime = partsOfDate[1].split(":");
    var fullDate = hunDatum + " " + partsOfTime[0] + ":" + partsOfTime[1];

    nameAndDate.innerHTML = feedbacks[i].user.userName + " (" + fullDate + ")" + "<br>";

    starP.innerHTML =`<i class="fa fa-star checked" style="font-size:24px"></i>`.repeat(feedbacks[i].stars);
    starP.innerHTML += `<i class="fa fa-star" style="font-size:24px;color:#D3D3D3"></i>`.repeat(5 -feedbacks[i].stars);

    starP.setAttribute('id',`starTd${feedbacks[i].id}`);

    review.innerHTML = feedbacks[i].review;

    review.setAttribute('id', `reviewTd${feedbacks[i].id}`);


    feedbackDiv.appendChild(nameAndDate);
    feedbackDiv.appendChild(starP);
    feedbackDiv.appendChild(review);


    if (jsonData[1].length > 0){
    var currentUserId = jsonData[1][0].user.id;

       if (currentUserId == feedbacks[i].user.id) {
            var deleteSpan = document.createElement("span");
            var deleteButton = document.createElement("button");
            deleteButton.setAttribute("class", "btn btn-warning");
            deleteButton["data"] = feedbacks[i];
            deleteButton.innerHTML = "Töröl";
            deleteButton.onclick = deleteFeedback;
            deleteButton.setAttribute("id", "delete-button");
            deleteSpan.appendChild(deleteButton);
            var modifySpan = document.createElement("span");
            var modifyButton = document.createElement("button");
            modifyButton.setAttribute("class", "btn btn-warning");
            modifyButton["data"] = feedbacks[i];
            modifyButton.innerHTML = "Szerkeszt";
            modifyButton.onclick = modifyFeedback;
            modifySpan.appendChild(modifyButton);
            feedbackDiv.appendChild(deleteSpan);
            feedbackDiv.appendChild(modifySpan);
        }
    }
     var br = document.createElement("br");
        feedbackDiv.appendChild(br);
        feedbackDiv.appendChild(br);
 }
    mainDiv.appendChild(feedbackDiv);
 }


function createFeedbackBox (id){

var mainDiv = document.getElementById("sendFeedback");
var divForSendFeedback = document.createElement("div");
divForSendFeedback.setAttribute("style", "margin-top:20px");
var str = "";
str = `
 <span onmouseover="starmark(this)" onclick="starmark(this)" id="1one" style="font-size:30px;cursor:pointer;"  class="fa fa-star checked"></span>
  <span onmouseover="starmark(this)" onclick="starmark(this)" id="2one"  style="font-size:30px;cursor:pointer;" class="fa fa-star "></span>
  <span onmouseover="starmark(this)" onclick="starmark(this)" id="3one"  style="font-size:30px;cursor:pointer;" class="fa fa-star "></span>
  <span onmouseover="starmark(this)" onclick="starmark(this)" id="4one"  style="font-size:30px;cursor:pointer;" class="fa fa-star"></span>
  <span onmouseover="starmark(this)" onclick="starmark(this)" id="5one"  style="font-size:30px;cursor:pointer;" class="fa fa-star"></span>
  <br/>
  <textarea class="form-control" rows="5" id="feedbackTextInput" placeholder="Írj értékelést"></textarea>
  <br>
  <button  onclick="fetchUserWithDeliveredOrder()" type="button"  class="btn btn-warning">Küldés</button>
`
divForSendFeedback.innerHTML = str;
mainDiv.appendChild(divForSendFeedback);
}

var count;
var starRate = 1;
function starmark(item){

count=item.id[0];
sessionStorage.starRating = count;
var subid= item.id.substring(1);
for(var i=0;i<5;i++)
{
if(i<count){
document.getElementById((i+1)+subid).style.color="#ffd700";
}
else{
document.getElementById((i+1)+subid).style.color="black";
}}
starRate = count;
}


function modifyFeedback(){

    var id = this["data"].id;
    var review = this["data"].review;
    var stars = this["data"].stars;

document.getElementById(`starTd${id}`).innerHTML = `<input id="starsInput${id}" type="number"  min=1 max =5 value="${stars}" required>`;
document.getElementById(`reviewTd${id}`).innerHTML = `<input id="reviewInput${id}" type="text" value="${review}" required>`;

    this.innerHTML = "Mentés";
    this.onclick = saveFeedback;
}

function saveFeedback(){
    var id = this["data"].id;
    var address = new URL(document.location).searchParams.get("address");
    var request ={
        "stars": document.getElementById(`starsInput${id}`).value,
        "review": document.getElementById(`reviewInput${id}`).value,
  };
  console.log(request);
  fetch("/api/products/modifyFeedback/" +address ,{
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
         Promise.all(urls.map(url => fetch(url)))
          .then(responses => Promise.all(
            responses.map(r => r.json())
          ))
          .then(m => {
          document.getElementById("feedbacksDiv").innerHTML = "";
            listFeedbacks(m);
          });
        document.getElementById("feedback-message-div").setAttribute("class", "alert alert-success");
      }
      else {
        document.getElementById("feedback-message-div").setAttribute("class", "alert alert-danger");
      }
      document.getElementById("feedback-message-div").innerHTML = jsonData.message;
    });
  return false;
}

function deleteFeedback(){
console.log(this["data"]);
    var id = this["data"].id;
if (!confirm("Biztosan törölni szeretnéd?")) {
    return;
  }
  fetch("/api/feedbacks/" + id,{
    method: "DELETE",
    headers: {
      "Content-type": "application/json"
    }
  }).then(function (response) {
    return response.json();
  }).
    then(function (jsonData) {
      if (jsonData.ok) {
      document.getElementById("feedbacksDiv").innerHTML = "";
        Promise.all(urls.map(url => fetch(url)))
               .then(responses => Promise.all(
                 responses.map(r => r.json())
               ))
               .then(m => {
                 listFeedbacks(m);
               });
        document.getElementById("feedback-message-div").setAttribute("class", "alert alert-success");
      }
      else {
        document.getElementById("feedback-message-div").setAttribute("class", "alert alert-danger");
      }
      document.getElementById("feedback-message-div").innerHTML = jsonData.message;
    });
  return false;
}
