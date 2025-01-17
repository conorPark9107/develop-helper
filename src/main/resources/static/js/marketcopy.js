$(document).ready(function() {

    const jsonData = logJsonData();
    let list;
    jsonData.then((data) => {
        list = data;
    });   
    

    $(document).on('mouseenter', '.list li', function(){
        $(this).addClass('lihover');
    });
    $(document).on('mouseleave', '.list li', function(){
        $(this).removeClass('lihover');
    });

    let liIndex = 0;
    $('#itemName').on('keydown', function(event){
        let key = event.keyCode;
        const lis = $('.list li');
        $($(lis)[liIndex]).trigger('mouseleave');
        
        
        
        switch (key) {
            case 38: // up
                liIndex = (liIndex - 1 == -1) ? lis.length - 1 : liIndex - 1;
                break;
            case 40: // down
                liIndex = (liIndex + 1 == lis.length) ? 0 : liIndex + 1;
                break;
            case 13: // enter
                
                break;
            default:
                liIndex = 0;
                break;
        }

        $($(lis)[liIndex]).trigger('mouseenter');

    });

    // 검색 아이콘을 클릭 했을 때 이벤트
    $('.search').on('click', function(){
        
        alert('아이콘 버튼 클릭');
    });

    // 검색 자동완성의 li를 클릭했을때.
    $(document).on('click', '.list li', function(){
        alert($(this).attr('value'));
    });


    // 티어 선택 버튼을 클릭하였을때.
    $('.tierListTrigger').on('click', function(){
        const ul = $('.tierList');
        if($(ul).css('display') == 'block'){
            $(ul).fadeOut(100);
        }else{
            $(ul).fadeIn(100);
        }
    });

    // 티어를 선택하였을때.
    $('.tierList li').on('click', function(){
        const value = $(this).attr('value');
        $('.tierList li').removeClass('selected');
        $(this).addClass('selected');
        $('.tierText').text(`${value} tier 🔻`);
    });
    
});


async function logJsonData(){
    const response = await fetch("/static/jsonData/items_Original.json");
    const jsonData = await response.json();
    return jsonData;
}
