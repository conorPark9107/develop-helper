$(document).ready(function () {

    showImages();

    $('#request-price-btn').on('click', function(){
         $(`td:nth-child(2) > span`).remove();

        let server = $('input[name=server]:checked').val(); // server
        let city = $('#start option:selected').val();

        let itemName = $('.selected-img').attr('value');
        if(itemName == undefined){
            showAlert('음식을 선택을 선택해주세요.');
            return;
        }

        let materialArr = [];
        let afterArr = [];
        materialArr = cookTree.FISHSAUCE.concat(cookTree[itemName].names); // 재료
        // materialArr = cookTree[itemName].names.concat(cookTree.FISHSAUCE); // 재료
        afterArr[0] = itemName; // 요리(.0 ~ .3 음식들)
        for(let i = 1; i < 4; i++) afterArr[i] = `${itemName}@${i}`;


       $.ajax({
           type: "GET",
           url: "/calculator/getResourcePrice",
           data: {
               server : server,
               city : city,
               before : materialArr,
               after : afterArr,
           },
           dataType: "json",
           beforeSend: function() {
               turnLoading();
           },
           success: function (response) {
               turnLoading();
               setTableServerData(response);
               $('input').trigger('keyup');
           },
           error : function(request, status, error) {
               console.log(error);
               turnLoading();
               showAlert('알수없는 에러가 발생하였습니다. 잠시후에 다시 시도해 주세요.');
           }
       });
    });
   

    $('.image-div').on('click', '.cooking-img' ,function(e){
        $('.cooking-img').removeClass('selected-img');
        $('#fish_table').hide();
        $('#meat_table').hide();

        let table = $('#cooking_table');
        let subtable;
        table.fadeOut(300);

        const cateDetail = $(this).attr("value");
        switch (cateDetail) {
            case '_T1_FISHCHOPS':
                subtable = $('#fish_table');
            break;
            case '_T8_MEAT':
                subtable = $('#meat_table');
            break;
        }
        $(this).addClass('selected-img');

        subtable.show();
        table.fadeIn(300);
    });

    

    $('#marketTaxBuy').change(function(){
        $('input').trigger('keyup');
    });

    $('#marketTaxSell').change(function(){
        $('input').trigger('keyup');
    });

    $('body').on('keyup', 'input', function(){
        let usageFee = $('#usageFee').val();                            // 소비영양 100당 사용료
        let returnRate = ($('#returnRate').val() / 100).toFixed(3);     // 반환율
        let quantity = $('#quantity').val();                            // 단위 개수
        let marketTaxBuy = $('#marketTaxBuy option:selected').val();    // 마켓 구매 세금
        let marketTaxSell = $('#marketTaxSell option:selected').val();  // 마켓 판매 세금

        // 자원 반환율 0이 될때까지 총 반환개수 += (만들 개수 * (반환율 * 100)) / 100
        let realQuantity = Number(quantity);
        let now = Number(quantity);
        while(Math.round(now) > 0){
            realQuantity += Math.round(now * (returnRate * 100)) / 100;
            now = Math.round(now * (returnRate * 100)) / 100;
        }

        // 포커스(집중)
        setFocus(quantity, realQuantity);

        // 개수 설정에 따른 테이블 데이터 수정
        updateQuantity(quantity);

        // 판매가 설정.
        setTotalAfterPrice(marketTaxSell, quantity, realQuantity);

        // 재료 개수에 따른 재료 비용 설정.
        setTotalMaterialPrice(marketTaxBuy, usageFee, realQuantity);

        // 이익
        setProfit();
    });

    $('.tooltip').hover(
        function (e) {
            // over
            $(`#${$(this).data('info')}`).fadeIn(300);
        }, function (e) {
            // out
            $(`#${$(this).data('info')}`).fadeOut(300);
        }
    );


    

}); // jquery ready()

function getBaseFocus(){
    let main_trs = $(`.main-table tbody tr`);
    let td = $(main_trs[0]).find('td')[0];
    const afterItemName = $(td).find('div').find('img').attr('value');
    return cookTree[afterItemName].baseFocus;
}

function getReturnName(){
    let main_trs = $(`.main-table tbody tr`);
    let td = $(main_trs[0]).find('td')[0];
    return $(td).find('div').find('img').attr('value');
}

function getReturnNum(){
    let main_trs = $(`.main-table tbody tr`);
    let td = $(main_trs[0]).find('td')[0];
    const afterItemName = $(td).find('div').find('img').attr('value');
    return cookTree[afterItemName].returnNum;
}

function setFocus(quantity, realQuantity){
    let chef = $('#chef').val();                                    // 요리 운명보드별 레벨
    let butcher = $('#butcher').val();
    let material = $('#material').val();
    let sandwich = $('#sandwich').val();
    let stew = $('#stew').val();
    let omelette = $('#omelette').val();
    let roast = $('#roast').val();
    let pie = $('#pie').val();
    let salad = $('#salad').val();
    let soup = $('#soup').val();
    const selectedItamName = $('.selected-img').attr('value');
    const kindof = selectedItamName.split('_')[2];

    let reduxPoint = 0;
    switch (kindof) {
        case 'SOUP':
            reduxPoint += soup * 250;
        break;
        case 'SALAD':
            reduxPoint += salad * 250;
        break;
        case 'PIE':
            reduxPoint += pie * 250;
        break;
        case 'OMELETTE':
            reduxPoint += omelette * 250;
        break;
        case 'ROAST':
            reduxPoint += roast * 250;
        break;
        case 'STEW':
            reduxPoint += stew * 250;
        break;
        case 'STEW':
            reduxPoint += sandwich * 250;
        break;
    }

    reduxPoint += (chef * 30) + (butcher * 30) +  
            (material * 30) + (sandwich * 30) + 
            (stew * 30) + (omelette * 30) + 
            (roast * 30) + (pie * 30) + 
            (salad * 30) + (soup * 30)

    // (basefocus / 2 ^ (reduxPoint / 10000)) 
    let main_trs = $(`.main-table tbody tr`);
    let baseFocuses = getBaseFocus();
    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');
        let focus = 0;
        if(selectedItamName.includes('AVALON')){
            focus = baseFocuses[i] / 2 ** (reduxPoint / 10000) * quantity;
        }else{
            focus = baseFocuses[i] / 2 ** (reduxPoint / 10000) * realQuantity;
        }


        $(tds[4]).text(Math.round(focus));
    }
    
}



// 요리 메뉴 선택 클릭시 호출.
function showImages(){
    const category = butcherCate;    
    if(category != undefined){
        const image_div = $('.image-div');
        image_div.append('<h5>🔻이미지를 선택하세요🔻</h5>');
        for(let i = 0; i < category.length; i++){
            image_div.append(`<img class='cooking-img' src='/static/image/${category[i]}.png' value="_${category[i]}" />`);
        }
    }    
}


