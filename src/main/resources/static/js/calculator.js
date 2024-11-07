$(document).ready(function () {
    
    fillFocusOnTable();

});

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
        console.log(trs.length);

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