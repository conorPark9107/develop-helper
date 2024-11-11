$(document).ready(function () {

    fillFocusOnTable();

    $('#request-price-btn').on('click', function(){
        let isClicked = $('.li').hasClass('clicked');

        if(!isClicked){
            showAlert('제련 자원을 선택해주세요.');
            return;
        }

        let server = $('input[name=server]:checked').val(); // server
        let tableClass = $('li.clicked').attr('value'); // tableClass for veriable array
        let city = $('#start option:selected').val();

        let beforeRefine;
        let afterRefine;
        switch (tableClass) {
            case 'metalbar':
                beforeRefine = '_BAR';
                afterRefine = '_METALBAR';
                break;
            case 'planks':
                beforeRefine = '_WOOD';
                afterRefine = '_PLANKS';
                break;
            case 'stoneblock':
                break;
            case 'leather':
                beforeRefine = '_HIDE';
                afterRefine = '_LEATHER';
                break;
            case 'cloth':
                beforeRefine = '_FIBER';
                afterRefine = '_CLOTH';
                break;
        }

        let trs = $(`#meterial_table tbody tr`);
        let trLength = trs.length;

        $.ajax({
            type: "GET",
            url: "/market/getResourcePrice",
            data: {
                server : server,
                city : city,
                before : itemTree[beforeRefine],
                after : itemTree[afterRefine],
            },
            dataType: "json",
            beforeSend: function() {
                turnLoading();
            },
            success: function (response) {
                turnLoading();

                for(let i = 0; i < trLength; i++){
                    let v = response[0][i].sell_price_min;
                    let tds = $(trs[i]).find('td');
                    let input = $(tds[3]).find('input');
                    $(input).attr('value', v);
                }

                for(let i = 0; i < trLength; i++){
                    let v = response[1][i].sell_price_min;
                    let tds = $(trs[i]).find('td');
                    let input = $(tds[1]).find('input');
                    $(input).attr('value', v);
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

    $('#marketTaxBuy').change(function(){
        $('input').trigger('keyup');
    });

    $('#marketTaxSell').change(function(){
        $('input').trigger('keyup');
    });

    $('input').on('keyup', function(){
        let usageFee = $('#usageFee').val();                            // 소비영양 100당 사용료
        let returnRate = ($('#returnRate').val() / 100).toFixed(3);     // 반환률
        let t4 = $('#tier04').val();                                    // 티어별 제련 숙련도
        let t5 = $('#tier05').val();
        let t6 = $('#tier06').val();
        let t7 = $('#tier07').val();
        let t8 = $('#tier08').val();
        let quantity = $('#quantity').val();                            // 단위 개수
        let marketTaxBuy = $('#marketTaxBuy option:selected').val();    // 마켓 구매 세금
        let marketTaxSell = $('#marketTaxSell option:selected').val();  // 마켓 판매 세금

        let realQuantity = Number(quantity);
        let now = Number(quantity);
        while(Math.round(now) > 0){
            realQuantity += Math.round(now * (returnRate * 100)) / 100;
            now = Math.round(now * (returnRate * 100)) / 100;
            console.log(realQuantity);
        }

        // 2티 그리기
        let trs = $('#meterial_table tbody tr');
        let buyPrice = $(trs[0]).find('td:eq(3)').find('input').val() * quantity;
        let sellPrice = $(trs[0]).find('td:eq(1)').find('input').val() * quantity;
        $(trs[0]).find('td:eq(6)').text(buyPrice);
        $(trs[0]).find('td:eq(7)').text(sellPrice);
        $(trs[0]).find('td:eq(8)').text(sellPrice - buyPrice);

        for(let i = 1; i < trs.length; i++){
            let tds = $(trs[i]).find('td');

            let spans = $(tds[4]).find('span');
            let afterQuantity = $(spans[0]).text();
            let beforeQuantity = $(spans[1]).text();
            let refinefee = ((($(tds[4]).attr('value') * 0.1125) * quantity) * usageFee) / 100;

            let afterIndex;
            if(i <= 1){ // 3T
                afterIndex = 0;
            }else if(i > 1 && i <= 6){ // 4T
                afterIndex = 1;
            }else{ // else all
                afterIndex = i - 5;
            }

            // 구매가
            let afterPrice = $($(trs[afterIndex]).find('td')[1]).find('input').val() * afterQuantity ;
            let beforePrice = $(tds[3]).find('input').val() * beforeQuantity;
            let afterTax = afterPrice * marketTaxBuy;
            let beforeTax = beforePrice * marketTaxBuy;

            buyPrice = (((afterPrice + afterTax) + (beforePrice + beforeTax)) * quantity) + refinefee;
            $(tds[6]).text(Math.floor(buyPrice).toLocaleString());
            $(tds[6]).append(`<br/><span class='priceDetail'>재료 원가 : ${(afterPrice + beforePrice).toLocaleString()}</span>`);
            $(tds[6]).append(`<br/><span class='priceDetail'>구매 수수료 : ${((afterTax + beforeTax) * quantity).toLocaleString()}</span>`);
            $(tds[6]).append(`<br/><span class='priceDetail'>제련 수수료 : ${refinefee.toLocaleString()}</span>`);

            // 판매가
            sellPrice = ($(tds[1]).find('input').val() * quantity); // 판매가 * 개수
            let sellTax = sellPrice * marketTaxSell; // 판매가 * 판매 세금
            $(tds[7]).text(Math.floor(sellPrice + sellTax).toLocaleString());
            $(tds[7]).append(`<br/><span class='priceDetail'>판매 원가 : ${Math.floor(($(tds[1]).find('input').val() * quantity)).toLocaleString()}</span>`);
            $(tds[7]).append(`<br/><span class='priceDetail'>판매 수수료 : ${Math.floor(sellTax).toLocaleString()}</span>`);
            $(tds[7]).append(`<br/><span class='priceDetail'>반환률에 따른 예상 제작 수량 : ${Math.round(realQuantity)}개</span>`);

            // 이익
            let buy = Math.floor(buyPrice);
            let sell = Math.floor(sellPrice);
            let totalPrice = Math.floor(sell - buy);
            $(tds[8]).text(totalPrice.toLocaleString());
        }

    });


});

// alert 메세지
function showAlert(msg){
    $.confirm({
        theme: 'supervan',
        title: '',
        content: msg,
        buttons: {
            '네': function () {}
        }
    });
}

// 기본 포커스 비용 티어별(2T ~ 8T)
const focusTable = [18,
                    31,
                    54, 94, 164, 287, 503,
                    94,	164, 287, 503, 880,
                    164, 287, 503, 880, 1539,
                    287, 503, 880, 1539 , 2694,
                    503, 880, 1539, 2694, 4714
                ];

const beforeItemValue = [4,
                         2,
                         4, 12, 28, 60, 124,
                         5, 10, 21, 42, 85,
                         8, 16, 32, 64, 128,
                         12, 25, 51, 102, 204,
                         25, 51, 102, 204, 409
                        ];
const afterItemValue = [4,
                        8,
                        16, 32, 64, 128, 256,
                        32, 64, 128, 256, 512,
                        64, 128, 256, 512, 1024,
                        128, 256, 512, 1024, 2048,
                        256, 512, 1024, 2048, 4096
                        ];

// 초기에 집중 비용 테이블에 작성.
function fillFocusOnTable(){
    let trs = $('table tbody').find('tr');

    for(let j = 0; j <trs.length; j++){
        let tds = $(trs[j]).find('td');
        $(tds[5]).text(focusTable[j]);
    }
}

// 제련물 선택 클릭시 호출.
function clicked(li){
    $('.li').removeClass('clicked');
    $(li).addClass('clicked');
    $('.table-div').hide();
    let x = $(li).attr('value');

    const datas = getMeterialNameAndImageName(x);
    setTitleName(datas[0], datas[1]);
    setContetns(datas[2], datas[3]);

    let table = $(`#meterial_table`);
    let tr = table.find('tbody tr');
    for(let i = 0 ; i < tr.length; i++){
        let tds = $(tr[i]).find('td');
        let x = $(tds[1]).find('input').attr('value');
        let y = $(tds[3]).find('input').attr('value');

        if(x != '' || y != ''){
            $(tds[1]).find('input').attr('value', '');
            $(tds[3]).find('input').attr('value', '');
            $(tds[6]).text('');
            $(tds[7]).text('');
            $(tds[8]).text('');
        }
    }

    table.fadeIn(500);
}

// 테이블에 재료value, 이미지 경로 설정.
function setContetns(beforeArr, afterArr){

    setContetnsFirst(beforeArr, afterArr);

    let trs = $('table tbody').find('tr');
    for(let i = 0; i < trs.length; i++){
        let tds = $(trs[i]).find('td');
        $(tds[0]).find('img').attr('src', `/image/${afterArr[i]}.png`);
        $(tds[2]).find('img').attr('src', `/image/${beforeArr[i]}.png`);
        if(i > 6){
            let imges = $(tds[4]).find('img');
            let span = $(tds[4]).find('span');
            $(imges[0]).attr('src', `/image/${afterArr[i-5]}.png`);
            $(imges[1]).attr('src', `/image/${beforeArr[i]}.png`);

            let afterAvg = $(span[0]).text();
            let beforeAvg = $(span[1]).text();
            $(tds[4]).attr('value', (afterAvg * afterItemValue[i-5]) + (beforeAvg * beforeItemValue[i]));
        }
    }

}

// 4T 까지 재료 직접 등록.
function setContetnsFirst(beforeArr, afterArr){
    let td2_img = $('#t2').find('td:eq(4)').find('div').find('img');
    let td2_span = $('#t2').find('td:eq(4)').find('div').find('span');
    td2_span.attr('value', td2_span.text() * beforeItemValue[0]);
    td2_img.attr('src', `/image/${beforeArr[0]}.png`)

    let td3 = $('#t3').find('td:eq(4)')
    let td3_img = td3.find('div').find('img');
    let td3_avg = td3.find('div').find('span');

    td3.attr('value', ($(td3_avg[0]).text() * afterItemValue[0]) * ($(td3_avg[1]).text() * beforeItemValue[1]));
    $(td3_img[0]).attr('src', `/image/${afterArr[0]}.png`)
    $(td3_img[1]).attr('src', `/image/${beforeArr[1]}.png`)

    let trs = $('table tbody').find('tr');
    for(let i = 2; i < 7; i++){
        let td = $(trs[i]).find('td:eq(4)');
        let imgs = td.find('img');
        let span = td.find('span');
        $(imgs[0]).attr('src', `/image/${afterArr[1]}.png`)
        $(imgs[1]).attr('src', `/image/${beforeArr[i]}.png`)
        let afterAvg = $(span[0]).text();
        let beforeAvg = $(span[1]).text();
        $(td).attr('value', (afterItemValue[1] * afterAvg) + (beforeItemValue[i] * beforeAvg));

    }

}

// 테이블 th 이름 변경
function setTitleName(before, after){
    $('#afterName').text(after);
    $('#beforeName').text(before);
}

// li 클릭시 클릭한 자원으로 이미지 보여주기 위한 함수.
function getMeterialNameAndImageName(x){
    let beforeName, beforeArray;
    let afterName, afterArray;

    switch (x) {
        case 'metalbar':
            beforeName = '광석';
            afterName = '주괴';
            beforeArray = itemTree['_BAR'];
            afterArray = itemTree['_METALBAR'];
        break;
        case 'planks':
            beforeName = '나무';
            afterName = '판자';
            beforeArray = itemTree['_WOOD'];
            afterArray = itemTree['_PLANKS'];
        break;
        // case 'stoneblock':break;
        case 'leather':
            beforeName = '가죽';
            afterName = '피혁';
            beforeArray = itemTree['_HIDE'];
            afterArray = itemTree['_LEATHER'];
        break;
        case 'cloth':
            beforeName = '섬유';
            afterName = '천';
            beforeArray = itemTree['_FIBER'];
            afterArray = itemTree['_CLOTH'];
        break;
    }
    return [beforeName, afterName, beforeArray, afterArray];
}


// https://gall.dcinside.com/mgallery/board/view/?id=albion&no=84100
// https://docs.google.com/spreadsheets/d/1KFSB7DsOqkJxpYjCT_VBoXQMmneP6IkjuVp7d38hNFU/edit?gid=0#gid=0


// 소비영양 100당 사용료 계산식.
// https://wiki.albiononline.com/wiki/Building

// 집중.
// https://www.reddit.com/r/albiononline/comments/17f7d8n/deleted_by_user/