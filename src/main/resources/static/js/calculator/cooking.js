$(document).ready(function () {

    $('#request-price-btn').on('click', function(){
        
        let server = $('input[name=server]:checked').val(); // server
        let category = $('li.clicked').attr('value'); // tableClass for veriable array
        let city = $('#start option:selected').val();


        let isClicked = $('.li').hasClass('clicked');
        if(!isClicked){
            showAlert('요리 선택을 선택해주세요.');
            return;
        }

        let itemName = $('.selected-img').attr('value');
        if(category == 'cooking' && itemName == undefined){
            showAlert('음식을 선택을 선택해주세요.');
            return;
        }

        let materialArr = [];
        let afterArr = [];
        switch (category) {
            case 'cooking':
                materialArr = cookTree[itemName].names.concat(cookTree.FISHSAUCE); // 재료
                afterArr[0] = itemName; // 요리(.0 ~ .3 음식들)
                for(let i = 1; i < 4; i++) afterArr[i] = `${itemName}@${i}`;
                break;
            case 'butcher':
                break;
            case 'mill':
                break;
        }
    


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
        $('.main-table tbody').empty();
        $('.sub-table tbody').empty();
        const cateDetail = $(this).attr("value");
        
        let foodArray = itemTree[cateDetail];

        // 부모푸드의 자식이 있다면 자식푸드들 보여주기.
        if(foodArray != undefined){
            const image_div = $('.image-div');
            $('.sub').remove();
            $('br').remove();
            image_div.append('<br/>');
            for(let i = 0; i < foodArray.length; i++){
                // image_div.append(`<img class='cooking-img sub' src='/static/image/${foodArray[i]}.png' value="${foodArray[i]}" />`);
                image_div.append(`<img class='cooking-img sub' src='/image/${foodArray[i]}.png' value="${foodArray[i]}" />`);
            }
            return;
        }

        $(this).addClass('selected-img');
        foods = cookTree[cateDetail];

        // 메인 테이블 내용 set
        setMainTableDefaultData(foods, cateDetail);

        // 중복제거 후, 재료 테이블에 넣기.        
        setSubTableDefaultData(foods, cookTree.FISHSAUCE);

    });

    

    $('#marketTaxBuy').change(function(){
        $('input').trigger('keyup');
    });

    $('#marketTaxSell').change(function(){
        $('input').trigger('keyup');
    });

    $('body').on('keyup', 'input', function(){
        let usageFee = $('#usageFee').val();                            // 소비영양 100당 사용료
        let returnRate = ($('#returnRate').val() / 100).toFixed(3);     // 반환률
        let quantity = $('#quantity').val();                            // 단위 개수
        let marketTaxBuy = $('#marketTaxBuy option:selected').val();    // 마켓 구매 세금
        let marketTaxSell = $('#marketTaxSell option:selected').val();  // 마켓 판매 세금
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

        // 자원 반환율 0이 될때까지 총 반환개수 += (만들 개수 * (반환율 * 100)) / 100
        let realQuantity = Number(quantity);
        let now = Number(quantity);
        while(Math.round(now) > 0){
            realQuantity += Math.round(now * (returnRate * 100)) / 100;
            now = Math.round(now * (returnRate * 100)) / 100;
        }

        // 개수 설정에 따른 테이블 데이터 수정
        updateQuantity(quantity);

        // 판매가 설정.
        setTotalAfterPrice(marketTaxSell, realQuantity);

        // 재료 개수에 따른 재료 비용 설정.
        setTotalMaterialPrice(quantity, marketTaxBuy, usageFee);

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
// TODO: 인게임에서 가격 비교 해줘야함.
// 이익 설정
function setProfit(){
    let main_trs = $(`.main-table tbody tr`);
    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');
        let buyPrice = $(tds[5]).text();
        let sellPrice = $(tds[6]).text();
        let totalPrice = sellPrice - buyPrice;
        let addColor;
        if(totalPrice >= 0){
            addColor = 'green';
        }else{
            addColor = 'red';
        }
        $(tds[7]).text(totalPrice);
        $(tds[7]).addClass(addColor);
    }

}


// 개수 단위가 변경됨에따라 테이블 수정
function updateQuantity(quantity){
    let main_trs = $(`.main-table tbody tr`);
    let td = $(main_trs[0]).find('td')[0];
    const afterItemName = $(td).find('div').find('img').attr('value');
    const afterReturnNum = cookTree[afterItemName].returnNum;
    
    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');
        const divs = $(tds[2]).find('div');

        for(let j = 0; j < divs.length; j++){
            const name = $(divs[j]).find('img').attr('value');
            const needQuantity = $(`#${name}`).parent().parent().find('td:last-child').text();
            $(divs[j]).find('span').text(needQuantity * quantity);
        }
        $(tds[0]).find('div').find('span').text(afterReturnNum * quantity);
    }
}

// 판매가 설정
function setTotalAfterPrice(tax, realQuantity){
    let main_trs = $(`.main-table tbody tr`);
    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');
        const afterPrice = $(tds[1]).find('input').val();
        const returnNum = $(tds[0]).find('div').find('span').text();

        const price = Math.round((afterPrice * returnNum) * realQuantity);
        const fee = Math.round(price * tax);
        $(tds[6]).text(Math.round(price + fee).toLocaleString());
        $(tds[6]).append(`<br/><span class="priceDetail">판매원가 : ${Math.round(price).toLocaleString()}</span>`);
        $(tds[6]).append(`<br/><span class="priceDetail">판매수수료 : ${Math.round(fee).toLocaleString()}</span>`);
        $(tds[6]).append(`<br/><span class="priceDetail">반환률에 따른 예상 제작 수량 : ${Math.floor(returnNum * realQuantity)}</span>`);
    }
}

// 테이블에 재료 값(구매가) 설정.
function setTotalMaterialPrice(quantity, tax, usageFee){
    let main_trs = $(`.main-table tbody tr`);
    const itemValue = $(main_trs[0]).attr('data-value');
    const usage = (((itemValue * 0.1125) * quantity) * usageFee) / 100;

    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');
        const divs = $(tds[2]).find('div');

        let totalPrice = 0;
        for(let j = 0; j < divs.length; j++){
            const name = $(divs[j]).find('img').attr('value');
            const needQuantity = $(divs[j]).find('span').text();
            const priceOne = $(`#${name}`).val();
            totalPrice += (priceOne * needQuantity);
        }
        
        $(tds[3]).find('input').val(totalPrice);
        
        const fee = totalPrice * tax;
        
        $(tds[5]).text(Math.round(totalPrice + fee + usage).toLocaleString());
        $(tds[5]).append(`<br/><span class="priceDetail">구매원가 : ${totalPrice.toLocaleString()}</span>`);
        $(tds[5]).append(`<br/><span class="priceDetail">구매수수료 : ${Math.round(fee).toLocaleString()}</span>`);
        $(tds[5]).append(`<br/><span class="priceDetail">요리수수료 : ${Math.round(usage).toLocaleString()}</span>`);
    }
}


function setTableServerData(response){
    let main_trs = $(`.main-table tbody tr`);
    let sub_trs = $(`.sub-table tbody tr`);

    // 메인 테이블
    for(let i = 0; i < main_trs.length; i++){
        let v = response[1][i].sell_price_min;
        let tds = $(main_trs[i]).find('td');
        let input = $(tds[1]).find('input');
        $(input).attr('value', v);
        $(tds[1]).append(`<span class='timeAgo-cooking'>${getPerTime(response[0][i].sell_price_min_date)}</span>`);
    }   
    
    // 재료 테이블
    for(let i = 0; i < sub_trs.length; i++){
        let v = response[0][i].sell_price_min;
        let tds = $(sub_trs[i]).find('td');
        let input = $(tds[1]).find('input');
        $(input).attr('value', v);
        $(tds[1]).append(`<span class='timeAgo-cooking'>${getPerTime(response[0][i].sell_price_min_date)}</span>`);
    }

    $('input').trigger('keyup');
}





// 요리 이미지 클릭했을때 메인 테이블에 내용 넣기.
function setMainTableDefaultData(foods, cateDetail){
    let tbody = $('.main-table tbody');
    for(let i = 0; i <= 3; i++){
        let tr = document.createElement('tr');
        
        let fileName;
        if(i == 0){
            fileName = cateDetail;
        }else{
            fileName = `${cateDetail}@${i}`;
        }
        $(tr).attr('data-value', foods.itemValue);
        $(tr).append(`<td><div class="main-table-after"><img src="/image/${fileName}.png" value="${fileName}" /><span>${foods.returnNum}</span></div></td>`);
        $(tr).append('<td><input type="text"></td>');
        
        let appendData = '';
        for(let j = 0; j < foods.names.length; j++){
            appendData += `<div class="material-cooking"><img src="/image/${foods.names[j]}.png" value="${foods.names[j]}"/>`
            appendData += `<span>${foods.nums[j]}</span></div>`;
        }
        if(i > 0){
            appendData += `<div class="material-cooking"><img src="/image/${cookTree.FISHSAUCE[i-1]}.png" value="${cookTree.FISHSAUCE[i-1]}"/>`
            appendData += `<span>${foods.fishNum}</span></div>`;
        }
        

        $(tr).append(`<td>${appendData}</td>`);
        $(tr).append('<td><input type="text"></td>');
        $(tr).append(`<td>${foods.baseFocus[i]}</td>`);
        $(tr).append('<td>0</td>');
        $(tr).append('<td>0</td>');
        $(tr).append('<td>0</td>');

        tbody.append(tr);
    }
}

// 요리 이미지 클릭 했을 때, 재료 테이블 채워 넣기
function setSubTableDefaultData(foods, fishArray){
    let tbody = $('.sub-table tbody');
    for(let i = 0; i < fishArray.length; i++){
        const tr = document.createElement('tr');
        $(tr).append(`<td><img src="/image/${fishArray[i]}.png" /></td>`);
        $(tr).append(`<td><input id="${fishArray[i]}" type="text"></td>`);
        $(tr).append(`<td>${foods.fishNum}</td>`);
        tbody.append(tr);
    }

    for(let i = 0; i < foods.names.length; i++){
        const tr = document.createElement('tr');
        $(tr).append(`<td><img src="/image/${foods.names[i]}.png" /></td>`);
        $(tr).append(`<td ><input id="${foods.names[i]}" type="text"></td>`);
        $(tr).append(`<td>${foods.nums[i]}</td>`);
        tbody.append(tr);
    }
}

// function getReduxPoint(tier){
//     const t4 = $('#tier04').val();  // 티어별 제련 숙련도
//     const t5 = $('#tier05').val();
//     const t6 = $('#tier06').val();
//     const t7 = $('#tier07').val();
//     const t8 = $('#tier08').val();

//     let returnVal = 0;
//     switch (tier) {
//         case 4:
//             returnVal += t4 * 250;
//         break;
//         case 5:
//             returnVal += t5 * 250;
//         break;
//         case 6:
//             returnVal += t6 * 250;
//         break;
//         case 7:
//             returnVal += t7 * 250;
//         break;
//         case 8:
//             returnVal += t8 * 250;
//         break;
//     }
//     returnVal += (t4 * 30) + (t5 * 30) + (t6 * 30) + (t7 * 30) + (t8 * 30);
//     return returnVal;
// }

// 요리 메뉴 선택 클릭시 호출.
function clicked(li){
    $('.main-table tbody').empty();
    $('.sub-table tbody').empty();

    $('.table-div-cooking').hide();
    $('.li').removeClass('clicked');
    $('.image-div').empty();
    $(li).addClass('clicked');

    const category = getCategory($(li).attr('value'));    
    if(category != undefined){
        const image_div = $('.image-div');
        image_div.append('<h5>🔻음식을 선택하세요🔻</h5>');
        for(let i = 0; i < category.length; i++){
            image_div.append(`<img class='cooking-img' src='/image/${category[i]}.png' value="_${category[i]}" />`);
        }
    }

    let x = $(li).attr('value');
    let table = $(`.table-div-cooking`);
    table.fadeIn(500);
}

function getCategory(val){

    switch (val) {
        case 'cooking': return foodCate;
        case 'butcher': break;
        case 'mill': break;
    }

}

