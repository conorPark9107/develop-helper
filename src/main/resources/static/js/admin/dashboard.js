function getVisitCount(e){
    const value = e.value;
    fetch(`/admin/dashboard/pickDate?date=${value}`)
    .then(response => response.text())
    .then(data => {
        const element = document.getElementById('visitCountByDate');
        element.innerText = `${data} 명이 방문하였습니다.`;
    })
}