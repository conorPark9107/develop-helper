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
            case 'metalbar_table':
                beforeRefine = '_BAR';
                afterRefine = '_METALBAR';
                break;
            case 'planks_table':
                beforeRefine = '_WOOD';
                afterRefine = '_PLANKS';
                break;
            case 'stoneblock_table':
                break;
            case 'leather_table':
                beforeRefine = '_HIDE';
                afterRefine = '_LEATHER';
                break;
            case 'cloth_table':
                beforeRefine = '_FIBER';
                afterRefine = '_CLOTH';
                break;
        }

        let trs = $(`#${tableClass} tbody tr`);
        let trLength = $(`#${tableClass} tbody tr`).length;
        
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

            },
            error : function(request, status, error) {
                console.log(error);
                turnLoading();
                showAlert('알수없는 에러가 발생하였습니다. 잠시후에 다시 시도해 주세요.');
            }
        });


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

// 초기에 집중 비용 테이블에 작성.
function fillFocusOnTable(){
    let tables = $('table tbody');
    for(let i = 0; i < tables.length; i++){
        let trs = $(tables[i]).find('tr');

        for(let j = 0; j <trs.length; j++){
            let tds = $(trs[j]).find('td');
            $(tds[5]).text(focusTable[j]);
        }
        
    }
}

// 제련물 선택 클릭시 호출.
function clicked(li){
    $('.li').removeClass('clicked');
    $(li).addClass('clicked');
    $('.table-div').hide();
    let x = $(li).attr('value');
    $(`#${x}`).fadeIn(500);
}


// https://gall.dcinside.com/mgallery/board/view/?id=albion&no=84100
// https://docs.google.com/spreadsheets/d/1KFSB7DsOqkJxpYjCT_VBoXQMmneP6IkjuVp7d38hNFU/edit?gid=0#gid=0


// 소비영양 100당 사용료 계산식.
// https://wiki.albiononline.com/wiki/Building

// 집중.
// https://www.reddit.com/r/albiononline/comments/17f7d8n/deleted_by_user/