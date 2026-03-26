const changeRadio = () => {
    requestDataByCategory();
};

// 특정 table row를 클릭했을 때. 상세 페이지 요청.
function requestDetailPage(id){
    window.location.href = `/tierList/detail?id=${id}`;
}

function requestDataByCategory(){
    const optionsValue = document.querySelector('input[name="options"]:checked').value;
    const categoryValue = document.querySelector('input[name="category"]:checked').value;
    window.location.href = `/tierList?options=${optionsValue}&category=${categoryValue}`;
}

document.addEventListener("DOMContentLoaded", function () {
    const options = document.querySelectorAll('input[name="options"]');
    const category = document.querySelectorAll('input[name="category"]');
    const tr = document.querySelectorAll('table tbody tr');

    options.forEach(e => e.addEventListener('change', changeRadio));
    category.forEach(e => e.addEventListener('change', changeRadio));
    tr.forEach(e => e.addEventListener('click', function(){
        requestDetailPage(this.id);
    }));
});




