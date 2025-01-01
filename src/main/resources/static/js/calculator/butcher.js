$(document).ready(function () {

    showImages();

    $('#request-price-btn').on('click', function(){
        $(`td:nth-child(2) > span`).remove();
        $(`td:nth-child(4) > span`).remove();

        let server = $('input[name=server]:checked').val(); // server
        let city = $('#start option:selected').val();

        let itemName = $('.selected-img').attr('value');
        if(itemName == undefined){
            showAlert('고기 & 토막낸 생선을 선택을 선택해주세요.');
            return;
        }

        let materialArr = [];
        let afterArr = [];
        switch (itemName) {
            case "_T8_MEAT":
                materialArr = butcherTree['BUTCHER_MATERIAL'];
                afterArr = itemTree[itemName]; 
                break;
            case "_T1_FISHCHOPS":
                materialArr = itemTree[itemName];
                afterArr = ["T1_FISHCHOPS"];
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
               let subtable = getSubtable();      
                switch ($(subtable).attr('id')) {
                    case "fish_table":
                        setTableServerData_fish(response);
                    break;
                    case "meat_table":
                        setTableServerData(response);
                    break;
                }
               
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
        $('.main-table tbody tr').remove();
        $(this).addClass('selected-img');

        let table = $('#cooking_table');
        
        table.hide();
        
        const cateDetail = $(this).attr("value");  

        let subtable = getSubtable();      
        switch ($(subtable).attr('id')) {
            case "fish_table":
                setMainTableDefaultData_fish(itemTree[cateDetail], subtable);
            break;
            case "meat_table":
                setMainTableDefaultData(itemTree[cateDetail], subtable);
            break;
        }
        

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

        let subtable = getSubtable();      
        switch ($(subtable).attr('id')) {
            case "fish_table":
                updateQuantity_fish(quantity); // 개수 설정에 따른 테이블 데이터 수정
                setProfit_fish(quantity, marketTaxBuy, marketTaxSell); // 이익
            break;
            case "meat_table":
                setFocus(quantity); // 포커스(집중)
                updateQuantity(quantity); // 개수 설정에 따른 테이블 데이터 수정
                setTotalAfterPrice(marketTaxSell); // 판매가 설정.
                setTotalMaterialPrice(marketTaxBuy, usageFee,  quantity); // 재료 개수에 따른 재료 비용 설정.
                setProfit(); // 이익
            break;
        }

        
    });

    $('.tooltip').hover(
        function (e) {
            // over
            $(`.${$(this).data('info')}`).fadeIn(200);
        }, function (e) {
            // out
            $(`.${$(this).data('info')}`).fadeOut(200);
        }
    );


    

}); // jquery ready()

// 서버로부터 가격 받아와서 Set (고기)
function setTableServerData(response){
    const main_trs = getSubtable().find('tbody').find('tr');

    // 메인 테이블
    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');

        const v = response[1][i].sell_price_min;
        const beforeName = response[1][i].item_id;
        $(`#${beforeName}`).val(v);
        $(tds[1]).append(`<span class='timeAgo-cooking'>${getPerTime(response[1][i].sell_price_min_date)}</span>`);

        const v2 = response[0][i].sell_price_min;
        const beforeName2 = response[0][i].item_id;     
        $(`#${beforeName2}`).val(v2);
        $(tds[3]).append(`<span class='timeAgo-cooking'>${getPerTime(response[0][i].sell_price_min_date)}</span>`);
    }  
}

// 서버로부터 가격 받아와서 Set (물고기)
function setTableServerData_fish(response){
    const main_trs = getSubtable().find('tbody').find('tr');
    let fishchops = $(`.T1_FISHCHOPS`);

    // 메인 테이블
    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');

        // after
        const v = response[1][0].sell_price_min;
        $(fishchops[i]).val(v);
        $(tds[3]).append(`<span class='timeAgo-cooking'>${getPerTime(response[1][0].sell_price_min_date)}</span>`);

        // before
        const v2 = response[0][i].sell_price_min;
        const beforeName2 = response[0][i].item_id;     
        $(`#${beforeName2}`).val(v2);
        $(tds[1]).append(`<span class='timeAgo-cooking'>${getPerTime(response[0][i].sell_price_min_date)}</span>`);
    }  
}

// 도살은 제련, 제작과는 자원반환률(RRR) 계산식이 다르다.
function getRealQuantity(td){
    const quantity = $(td).find('div').find('span').text();
    const returnRate = ($('#returnRate').val() / 100).toFixed(3);     // 반환율
    // 만들 개수 + (만들 개수 * 반환율)
    return Math.round(Number(quantity) + (quantity * returnRate));
}

// 현재 테이블의 id를 가져옴
function getSubtable(){
    const cateDetail = $('.selected-img').attr('value');
    switch (cateDetail) {
        case '_T1_FISHCHOPS': return $('#fish_table');
        case '_T8_MEAT': return $('#meat_table');
    }
}

// 이익 설정 (물고기)
function setProfit_fish(quantity, marketTaxBuy, marketTaxSell){
    let main_trs = getSubtable().find('tbody').find('tr');
    getSubtable().find('tbody').find('tr').find(`td:nth-child(5)`).removeClass();
    
    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');
        let buyPrice = $(tds[1]).find('input').val() * quantity;
        buyPrice = buyPrice + (buyPrice * marketTaxBuy);
        let sellQuantity = $(tds[2]).find('span').text();
        let sellPrice = $(tds[3]).find('input').val() * sellQuantity;
        sellPrice = sellPrice - (sellPrice * marketTaxSell);

        let totalPrice = sellPrice - buyPrice;
        let addColor;
        if(totalPrice >= 0){
            addColor = 'green';
        }else{
            addColor = 'red';
        }
        $(tds[4]).text(Math.round(totalPrice).toLocaleString());
        $(tds[4]).addClass(addColor);
    }
}

// 이익 설정 (고기)
function setProfit(){
    let main_trs = getSubtable().find('tbody').find('tr');
    getSubtable().find('tbody').find('tr').find(`td:nth-child(8)`).removeClass();
    
    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');
        let buyPrice = $(tds[5]).find('.buyPrice').text().replaceAll(',', '');
        let sellPrice = $(tds[6]).find('.sellPrice').text().replaceAll(',', '');
        
        let totalPrice = sellPrice - buyPrice;
        let addColor;
        if(totalPrice >= 0){
            addColor = 'green';
        }else{
            addColor = 'red';
        }
        $(tds[7]).text(totalPrice.toLocaleString());
        $(tds[7]).addClass(addColor);
    }
}

// 구매가 설정. (총 재료 비용)
function setTotalMaterialPrice(tax, usageFee,  quantity){
    let main_trs = getSubtable().find('tbody').find('tr');
    const afterReturnNum = getReturnNum();

    const itemValue = $(main_trs[0]).attr('data-value');
    const usage = (((itemValue * 0.1125) * (afterReturnNum * quantity)) * usageFee) / 100;

    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');
        const div = $(tds[2]).find('div');

        let totalPrice = 0;
        
        const priceOne = $(tds[3]).find('input').val();
        const needQuantity = $(div).find('span').text();
        totalPrice += (priceOne * needQuantity);
        
        const fee = totalPrice * tax;

        let span = document.createElement('span');
        $(span).addClass('buyPrice');
        $(span).text(Math.round(totalPrice + fee + usage).toLocaleString());
        $(tds[5]).text('');
        $(tds[5]).append(span);
        $(tds[5]).append(`<br/><span class="priceDetail">구매원가 : ${totalPrice.toLocaleString()}</span>`);
        $(tds[5]).append(`<br/><span class="priceDetail">구매수수료 : ${Math.round(fee).toLocaleString()}</span>`);
        $(tds[5]).append(`<br/><span class="priceDetail">예상 도살 수수료 : ${Math.round(usage).toLocaleString()}</span>`);
    }
}

// 판매가 설정
function setTotalAfterPrice(tax){
    let main_trs = getSubtable().find('tbody').find('tr');
    
    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');
        const afterPrice = $(tds[1]).find('input').val();

        const realQuantity = getRealQuantity(tds[0]);
        
        const price = Math.round(afterPrice * realQuantity);
        const fee = Math.round(price * tax);

        let span = document.createElement('span');
        $(span).addClass('sellPrice');
        $(span).text(Math.round(price - fee).toLocaleString());
        $(tds[6]).text('');
        $(tds[6]).append(span);
        $(tds[6]).append(`<br/><span class="priceDetail">판매원가 : ${Math.round(price).toLocaleString()}</span>`);
        $(tds[6]).append(`<br/><span class="priceDetail">판매수수료 : ${Math.round(fee).toLocaleString()}</span>`);

        const first = `<br/><span class="priceDetail">반환율에 따른 예상 제작 수량 : ${Math.floor(realQuantity)}</span>`;
        $(tds[6]).append(first);
    }
}

// 개수 단위가 변경됨에따라 테이블 수정 (물고기)
function updateQuantity_fish(quantity){
    let main_trs = getSubtable().find('tbody').find('tr');
    

    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');
        const div = $(tds[0]).find('div');
        $(div).find('span').text(quantity); 
        const id = $(tds[1]).find('input').attr('id');
        const afterReturnNum = fishTree[id].choppedFish;
        $(tds[2]).find('div').find('span').text(afterReturnNum * quantity);
    }
}

// 개수 단위가 변경됨에따라 테이블 수정 (고기)
function updateQuantity(quantity){
    let main_trs = getSubtable().find('tbody').find('tr');
    const afterReturnNum = getReturnNum();
    
    for(let i = 0; i < main_trs.length; i++){
        const tds = $(main_trs[i]).find('td');
        const div = $(tds[2]).find('div');
        $(div).find('span').text(quantity); 
        $(tds[0]).find('div').find('span').text(afterReturnNum * quantity);
    }
}


function getBaseFocus(){
    let main_trs = getSubtable().find('tbody').find('tr');
    let td = $(main_trs[0]).find('td')[0];
    const afterItemName = $(td).find('div').find('img').attr('value');
    return butcherTree[afterItemName].baseFocus;
}

function getReturnName(){
    let main_trs = getSubtable().find('tbody').find('tr');
    let td = $(main_trs[0]).find('td')[0];
    return $(td).find('div').find('img').attr('value');
}

function getReturnNum(){
    let main_trs = getSubtable().find('tbody').find('tr');
    let td = $(main_trs[0]).find('td')[0];
    const afterItemName = $(td).find('div').find('img').attr('value');
    return butcherTree[afterItemName].returnNum;
}

function setFocus(quantity){
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
    let reduxPoint = butcher * 250;
    reduxPoint += (chef * 30) + (butcher * 30) +  
            (material * 30) + (sandwich * 30) + 
            (stew * 30) + (omelette * 30) + 
            (roast * 30) + (pie * 30) + 
            (salad * 30) + (soup * 30)

    // (basefocus / 2 ^ (reduxPoint / 10000)) 
    let main_trs = getSubtable().find('tbody').find('tr');
    for(let i = 0; i < main_trs.length; i++){
        let baseFocuses = getBaseFocus();
        const tds = $(main_trs[i]).find('td');
        let focus = baseFocuses / 2 ** (reduxPoint / 10000) * quantity;
        $(tds[4]).text(Math.round(focus));
    }
    
}

// 테이블에 값 Set (고기)
function setMainTableDefaultData(itemArr, table){
    for(let i = 0; i < itemArr.length; i++){
        const butcherArr = butcherTree[itemArr[i]];
        const tr = document.createElement('tr');
        $(tr).attr('data-value', butcherArr.itemValue);
        $(tr).append(`<td><div class="main-table-after"><img src="/image/${itemArr[i]}.png" value="${itemArr[i]}" /><span>${butcherArr.returnNum}</span></div></td>`);
        $(tr).append(`<td><input type="number" value="0" id="${itemArr[i]}" ></td>`);
        $(tr).append(`<td><div class="material-cooking-meat"><img src="/image/${butcherArr.name}.png" value="${butcherArr.name}" /><span>1</span></div></td>`);
        $(tr).append(`<td><input type="number" value="0" id="${butcherArr.name}"></td>`);
        $(tr).append(`<td>${butcherArr.baseFocus}</td>`);
        $(tr).append(`<td>0</td>`);
        $(tr).append(`<td>0</td>`);        
        $(tr).append('<td>0</td>');
        $(table).append(tr);
    }
}

// 테이블에 값 Set (물고기)
function setMainTableDefaultData_fish(itemArr, table){
    for(let i = 0; i < itemArr.length; i++){
        const fishArr = fishTree[itemArr[i]];
        const tr = document.createElement('tr');
        $(tr).append(`<td><div class="main-table-after"><img src="/image/${itemArr[i]}.png" value="${itemArr[i]}" /><span>1</span></div></td>`);
        $(tr).append(`<td><input type="number" value="0" id="${itemArr[i]}" ></td>`);
        $(tr).append(`<td><div class="main-table-after"><img src="/image/T1_FISHCHOPS.png"/><span>${fishArr.choppedFish}</span></div></td>`);
        $(tr).append(`<td><input type="number" value="0" class="T1_FISHCHOPS" ></td>`);
        $(tr).append('<td>0</td>');
        $(table).append(tr);
    }
}


// 요리 메뉴 선택 클릭시 호출.
function showImages(){
    const category = butcherCate;    
    if(category != undefined){
        const image_div = $('.image-div');
        image_div.append('<h5>🔻이미지를 선택하세요🔻</h5>');
        for(let i = 0; i < category.length; i++){
            image_div.append(`<img class='cooking-img' src='/image/${category[i]}.png' value="_${category[i]}" />`);
        }
    }    
}


