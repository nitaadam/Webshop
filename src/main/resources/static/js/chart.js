fetchData();

function fetchData() {
fetch("/api/dashboard", {
method:"GET",
 })
 .then(function(response) {
return response.json();
         })
 .then(function(jsonData) {
    createChart(jsonData);
      })
        return false;
  }


function createChart(jsonData) {
   var myConfig = {
      "globals": {
        "font-family": "Lucida Sans Unicode"
      },
      "layout": "2x2",
      "graphset": [{
          "type": "scorecard",
          "border-top": "1px solid #ccc",
          "border-right": "1px solid #ccc",
          "border-bottom": "1px solid #ccc",
          "border-left": "1px solid #ccc",
          "options": {
            "title": {
              "text": "Termékek",
              "color": "#a00"
            },
            "value": {
              "text": jsonData.totalProducts,
              "color": "#a00"
            },
            "icon": {
              "background-scale": 0.75,
    //          "background-image": "/studio/demo/LM9PTBOF/clock.png"
            }
          },
          "plotarea": {
            "margin": 10
          }
        },
        {
          "type": "scorecard",
          "border-top": "1px solid #ccc",
          "border-right": "1px solid #ccc",
          "border-bottom": "1px solid #ccc",
          "options": {
            "title": {
              "text": "Megrendelések",
              "color": "#0a0"
            },
            "value": {
              "text": jsonData.totalOrders,
              "color": "#0a0"
            },
            "icon": {
              "background-color": "#8eba31 #8dcd41",
              "border-color": "#6fb023",
              "background-scale": 0.75,
    //          "background-image": "/studio/demo/LM9PTBOF/layes.png"
             }
          },
          "plotarea": {
            "margin": 10
          }
        },
        {
          "type": "scorecard",
          "border-right": "1px solid #ccc",
          "border-bottom": "1px solid #ccc",
          "border-left": "1px solid #ccc",
          "options": {
            "title": {
              "text": "Felhasználók - lekérdezni!"
    //          "size": 100,
            },
            "value": {
              "text": jsonData.totalUsers,
              "color": "#a0a",
            },
            "icon": {
              "background-color": "#ffc90e #ffa100",
              "border-color": "#ff7200",
              "background-scale": 0.75,
    //          "background-image": "/studio/demo/LM9PTBOF/calendar.png"
            },
            "bars": [{
                "text": "Normál userek",
                "value": "lekérdezni!"
              },
              {
                "text": "Adminisztrátorok",
                "value": "lekérdezni!"
              }
            ]
          },
          "plotarea": {
            "margin": 10
          }
        },
        {
          "type": "scorecard",
          "border-right": "1px solid #ccc",
          "border-bottom": "1px solid #ccc",
          "options": {
            "title": {
              "text": "Kategóriák - lekérdezni!"
            },
            "value": {
              "text": "12"
            },
            "icon": {
              "background-color": "#c90eff #a100ff",
              "border-color": "#7200ff",
              "background-scale": 0.75,
    //          "background-image": "/studio/demo/LM9PTBOF/email.png"
            },
            "bars": [{
                "text": "Luxury",
                "value": 3
              },
              {
                "text": "Foglalkozások",
                "value": 4
              },
              {
                "text": "Sport",
                "value": 5
              }
            ]
          },
          "plotarea": {
            "margin": 10
          }
        }
      ]
        };

          zingchart.render({
            id: 'myChart',
            data: myConfig,
            height: 320,
            width: 1280,
            modules : 'scorecard'
          });
}
