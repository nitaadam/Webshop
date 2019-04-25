fetchData();

function fetchData() {
fetch("/api/dashboard", {
method:"GET",
 })
 .then(function(response) {
    return response.json();
         })
 .then(function(jsonData) {
    fillDashboard(jsonData);
      })
    return false;
  }

function fillDashboard(jsonData) {
  document.getElementById('user').innerHTML = 'Jelenleg ' + jsonData.totalUsers + ' regisztrált felhasználónk van.';

  var numberOfActiveProducts = jsonData.totalActiveProducts;
  var numberOfAllProducts = jsonData.totalProducts;
  var numberOfActiveOrders = jsonData.totalActiveOrders;
  var numberOfAllOrders = jsonData.totalOrders;
  var options = {responsive: true,
    maintainAspectRatio: true
  };
  if (numberOfAllProducts !== 0) {
    var ct1 = document.getElementById('chartOne');
    var data = {
      labels: ['Összes termék', 'Aktív termékek', 'Törölt termékek'],
      datasets: [{
        data: [numberOfAllProducts, numberOfActiveProducts, numberOfAllProducts - numberOfActiveProducts],
        backgroundColor: [
       'rgba(205, 220, 57, 0.7)','rgba(255, 235, 59, 0.7)','rgba(255, 152, 0, 0.7)'
        ],
        borderColor: [
          '',
          '',''
        ],
        borderWidth: 0.6

      }]
    };

    var myDoughnutChart = new Chart(ct1, {
      type: 'pie',
      data: data,
      options: options
    });
  } else {
    document.getElementById('message-product').innerHTML = 'Jelenleg nincsenek termékek.';
  }

  if (numberOfAllOrders !== 0) {
    var ct2 = document.getElementById('chartTwo');

    var data2 = {
      labels: ['Összes rendelés', 'Kiszállításra váró rendelések', 'Kiszállított rendelések'],
      datasets: [{
        data: [numberOfAllOrders, numberOfActiveOrders, numberOfAllOrders - numberOfActiveOrders],
        backgroundColor: [
        'rgba(205, 220, 57, 0.7)','rgba(255, 235, 59, 0.7)','rgba(255, 152, 0, 0.7)'
        ],
        borderColor: [
          '',
          '',''

        ],
        borderWidth: 0.6
      }]
    };

    var myDoughnutChart = new Chart(ct2, {
      type: 'pie',
      data: data2,
      options: options
    });
  } else {
    document.getElementById('message-order').innerHTML = 'Jelenleg nincsenek megrendelések.';
  }
}


