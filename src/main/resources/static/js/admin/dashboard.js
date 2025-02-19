const DISPLAY = true;
const BORDER = true;
const CHART_AREA = true;
const TICKS = true;



document.getElementById('drawChartBtn').addEventListener("click", function() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    const date = `${year}-${month}-${day}`;

    fetch(`/admin/dashboard/getVisitList?date=${date}`)
    .then(response => response.json())
    .then(response =>  {
        drawChart(response);
    });

});

function drawChart(response){
    let chart = document.getElementById('visitChart');

    const keys = response.map((obj) => obj.date);
    const counts = response.map((obj) => obj.count);

    new Chart(chart, {
        type: 'line',
        data: {
            labels: keys,
            datasets: [{
                data: counts,
                label: "날짜별 방문자 현황",
                borderColor: "#FD9F28",
                backgroundColor : "#fd9f2891",
                fill: false
            }
            ]
        },
        options: {},
    });
}

function getVisitCount(e){
    const value = e.value;

    fetch(`/admin/dashboard/pickDate?date=${value}`)
    .then(response => response.text())
    .then(data => {
        const element = document.getElementById('visitCountByDate');
        element.innerText = `${data} 명이 방문하였습니다.`;
    })
}