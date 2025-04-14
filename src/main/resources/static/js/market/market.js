let itemList = null;
$(document).ready(function() {

    $('#itemName').focus();

    if (!itemList) {
        $.getJSON("/jsonData/items.json", function (data) {
            itemList = data;
            itemList.map((data) => {
                if(data.LocalizedNames != null){
                    return data.LocalizedNames['KO-KR'] = data.LocalizedNames['KO-KR'];
                }
            });     
        });
    }

    $(document).on('mouseenter', '.list li', function(){
        $('.list li').removeClass('lihover');
        $(this).addClass('lihover');
        liIndex = $(this).data('index');
    });

    
    // input text가 포커스를 잃었을때 이벤트
    $('#itemName').on('blur', function(){
        $('#itemList').fadeOut(100);
    });

    // Tier li 요소 클릭 이벤트.
    $('.tierList li').on('click', function(){
        $('#itemName').trigger('keyup');
    });

    // 디바운스 함수 정의
    function debounce(func, delay) {
        let timer;
        return function (...args) {
            clearTimeout(timer);
            timer = setTimeout(() => func.apply(this, args), delay);
        };
    }
    
    // 방향키와 Enter 키 처리 함수
    let liIndex = 0;
    function handleImmediateKey(event) {
        const key = event.keyCode;
        const lis = $('.list li');
        switch (key) {
            case 38: // up
                liIndex = (liIndex - 1 == -1) ? lis.length - 1 : liIndex - 1;
                lis[liIndex].scrollIntoView({ behavior: "smooth", block: "end", inline: "center" });
                break;
            case 40: // down
                liIndex = (liIndex + 1 == lis.length) ? 0 : liIndex + 1;
                lis[liIndex].scrollIntoView({ behavior: "smooth", block: "end", inline: "center" });
                break;
            case 13: // enter
                $('#itemName').val('');
                getPriceFromServer();
                break;
            case 27: // esc
                $('#itemName').val('');
                $('#itemList').empty();
                $('#itemList').fadeOut(100);
                break;
        }

        // 리스트 항목 선택 상태 업데이트
        $($(lis)[liIndex]).trigger('mouseenter');
    }

    // 검색 필터링 함수 (디바운스 적용)
    const handleDebouncedKey = debounce(function (event) {
        const key = event.keyCode;

        // 방향키와 Enter 키는 무시 (이미 처리됨)
        if (key === 38 || key === 40 || key === 13) return;

        const inputVal = $('#itemName').val().replaceAll(" ", "");
        const tier = $('.tierList li.selected').attr('value');
        let list = inputVal ? itemList.filter((item) => {
                    if(tier != undefined){
                        return item.LocalizedNames != null &&
                               item.LocalizedNames['KO-KR'] != undefined &&
                               item.LocalizedNames['KO-KR'].replaceAll(" ", "").toLowerCase().includes(inputVal) &&
                               item.UniqueName.includes(tier);
                    }else{
                        return item.LocalizedNames != null &&
                               item.LocalizedNames['KO-KR'] != undefined &&
                               item.LocalizedNames['KO-KR'].replaceAll(" ", "").toLowerCase().includes(inputVal);
                    }
            })
            : [];

        liIndex = 0; // 검색 결과가 바뀌면 선택 인덱스를 초기화
        $('#itemList').empty();
        if (list != null && list.length > 0) {
            appendItemList(list); // 검색 결과 업데이트
            $('#itemList').fadeIn(100);
        }else{
            $('#itemList').fadeOut(100);
        }
    }, 300); // 300ms 딜레이


    $('#itemName').on('keyup', function (event) {
        // 방향키와 Enter 키는 즉시 처리
        handleImmediateKey(event);
        // 나머지 입력은 디바운스 처리
        handleDebouncedKey(event);
    });

    function appendItemList(filteredList){

        let ul = document.createElement('ul');
            ul.className = 'list';
            ul.id = 'searchList';
        filteredList.forEach((item, index) => {
            const itemVal = item.UniqueName;
            const itemName = item.LocalizedNames['KO-KR'];
            let li = document.createElement('li');
            let div = document.createElement('div');
            let img = document.createElement('img');
            let span = document.createElement('span');
            
            span.textContent = itemName;
            img.src = `https://render.albiononline.com/v1/item/${itemVal}`;
            div.className = 'flex-row vertical-center';
            $(li).attr('value', itemVal);
            $(li).data('index', index);
            div.appendChild(img);
            div.appendChild(span);
            li.appendChild(div);
            ul.appendChild(li);
        });

        $('#itemList').append(ul);
        $($('.list li')[0]).trigger('mouseenter');
        liIndex = 0;
    }

    // 서버로부터 데이터 받아오는 함수 정의
    function getPriceFromServer(){
        $('#itemList').fadeOut(100);
        const itemName = $('li.lihover div span').text();
        $('#itemName').val(itemName);
        const server = $('input[name=server]:checked').val();
        const item = $('li.lihover').attr('value');
        if(item == undefined){
            showAlert('조회하고자 하는 아이템을 검색 후 선택해주세요.');
            return;
        }
        $.ajax({
            type : 'get',
            url : '/market/getPrice',
            async : true,
            dataType : 'json',
            data : {
                server : server,
                itemName : item
            },
            beforeSend : function(){
                turnLoading();
                setTableImages(item);
            },
            success : function(response) {
                turnLoading();
                updateTable(response);
            },
            error : function(request, status, error) {
                turnLoading();
                showAlert('잠시후에 다시 시도해주세요.');
            }
        });
    }

    function setTableImages(item){
        const tables = $('table');
        for(let i = 0; i < tables.length; i++){
            const th = $(tables[i]).children('thead').children('tr').children('th')[0];
            $(th).empty();
            const img = document.createElement('img');
            img.className = 'tableImage';
            img.src = `https://render.albiononline.com/v1/item/${item}.png?quality=${i+1}`;
            $(th).append(img);
        }
    }

    // 서버에서 시장 데이터 받아서 뿌려주기.
    function updateTable(response){
        console.log(response);
        for(let i = 0; i < response.length; i++){
            const obj = response[i];
            const id = obj.city + obj.quality;
            const tds = $(`#${id} td`);
            const buyPriceMin = obj.buy_price_max;
            const sellPriceMin = obj.sell_price_min;
            const kst = obj.kst;
            let updateDate = obj.sell_price_min_date;
            let dateAgo = getPerTime(updateDate);

            $(tds[1]).text(buyPriceMin);
            $(tds[2]).text(sellPriceMin);
            if(updateDate.substr(0, 4) == "0001"){
                updateDate = "갱신 전";
            }else{
                $(tds[3]).text(updateDate.substr(10));
            }
            if(kst.substr(0, 4) == "0001"){
                $(tds[4]).text("갱신 전");
            }else{
                $(tds[4]).text(kst.substr(10));
            }

        }
    }

    // 검색 아이콘을 클릭 했을 때 이벤트
    $('.search').on('click', function(){
        getPriceFromServer();
    });

    // 검색 자동완성의 li를 클릭했을때.
    $(document).on('click', '.list li', function(){
        getPriceFromServer();
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


    // 마을 라디오 버튼을 클릭했을때
    const cityRadio = document.querySelectorAll('input[name="city"]');
    const rows = document.querySelectorAll('tbody > tr');

    // 키값을 가져와서 테이블 행을 숨김.
    function showRowsForKey(key) {
        // 먼저 모든 행을 숨김
        rows.forEach(row => row.classList.add('hide'));

        if (key === 'all') {
            rows.forEach(row => row.classList.remove('hide'));
        } else {
            for (let i = 1; i <= 5; i++) {
                const row = document.getElementById(`${key}${i}`);
                if (row) row.classList.remove('hide');
            }
        }
    }

    cityRadio.forEach(button => {
        button.addEventListener('change', e => {
            const selectedKey = e.target.value;
            showRowsForKey(selectedKey);
        });
    });

    // 품질 라디오 버튼을 클릭했을때
    const qualityRadio = document.querySelectorAll('input[name="quality"]');
    const tables = document.querySelectorAll('.background-2'); // table을 감싸고 있는 div요소
    qualityRadio.forEach(button => {
        button.addEventListener('change', e => {
            const selectedKey = e.target.value;
            showTableForKey(selectedKey);
        });
    });

    // 키값을 가져와서 테이블을 숨김.
    function showTableForKey(key) {
        // 먼저 모든 행을 숨김
        tables.forEach(table => table.classList.add('hide'));

        if (key === 'all') {
            tables.forEach(table => table.classList.remove('hide'));
        } else {
            tables.forEach(table => {
                if(table.id === key){
                    table.classList.remove('hide');
                }else{
                    table.classList.add('hide');
                }
            });
        }
    }




    
});