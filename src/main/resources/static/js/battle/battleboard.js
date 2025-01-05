$(document).ready(function () {

    function NotReload(){
        if( (event.ctrlKey == true && (event.keyCode == 78 || event.keyCode == 82)) || (event.keyCode == 116) ) {
            event.keyCode = 0;
            event.cancelBubble = true;
            event.returnValue = false;
        }
    }
    document.onkeydown = NotReload;

    setInterval(timer, 1000);

    function timer(){
        var text = Number($('#timer').text());
        text--;
        if(text < 1){
            text = 60;
            refresh();
        } 
        $('#timer').text(text);
    }

    function refresh(){
        const server = $('input[name="server"]:checked').val();
        const id = $('#guildId').val();
        const tr = $('.maintable tbody tr')[0];
        const recentId = $(tr).data('id');
        const date = $(tr).children('td').eq(1).text();
        console.log(recentId + " | " + date);
        $.ajax({
            type: "GET",
            url: "/battle/refresh",
            data: {
                inputValue : id,
                server : server,
                recentId : recentId
            },
            dataType: "json",
            success: function (response) {
                prependResponse(response, id);
            }
        });
    }

    // 전투 기록 행(table row)를 클릭하였을때
    $(".maintable tbody").on('click', 'tr' ,function(e){
        turnLoading();
        const id = $(this).find('td:eq(0)').find('input').val();
        const kill = $(this).find('td:eq(6)').text();
        const server = $(this).data('server');
        var form = $('<form></form>');
        form.attr("method","get");
        form.attr("action","/battle/detail");
        form.append($('<input/>', {type: 'hidden', name: 'id', value:id }));
        form.append($('<input/>', {type: 'hidden', name: 'server', value:server }));
        form.append($('<input/>', {type: 'hidden', name: 'kill', value:kill }));
        form.appendTo('body');
        form.submit();
    });

    // 길드리스트의 길드를 클릭했을때
    $('#guildList').on('click', "li" ,function () {
        const id = $(this).attr('value');
        submitToServer(id);
        $('.maintable tbody tr').remove();
        $('.blackArea').hide();
        $('.popup').fadeOut(500);
    });


    // 사용자가 보고자하는 서버(라디오 버튼)가 바꼈을때
    $('input[name="server"]').on('change', function(){
        turnLoading();
        const server = $('input[name="server"]:checked').val();
        var form = $('<form></form>');
        form.attr("method","get");
        form.attr("action","/battle");
        form.append($('<input/>', {type: 'hidden', name: 'server', value:server }));
        form.appendTo('body');
        form.submit();
    });

    function submitToServer(id){
        const server = $('input[name="server"]:checked').val();

        $.ajax({
            type: "GET",
            url: "/battle/more",
            data: {
                inputValue : id,
                server : server
            },
            dataType: "json",
            beforeSend: function(){
                turnLoading();
            },
            success: function (response) {
                if(response.length == 0){
                    $('.maintable tbody').append('<tr><td colspan="7">검색 결과가 없습니다.</td></tr>');
                }else{
                    appendResponse(response, id);
                }
                $('#guildId').val(id);
            },
            error : function(request,status,error){
                showAlert('알수없는 오류가 발생하였습니다. 잠시후에 다시 시도해주세요.');
            },
            complete : function(){
                turnLoading();
            }
        });
    }

    function requestGuildId(){
        const id = $('#inputText').val();
        const server = $('input[name="server"]:checked').val();

        if(id == '' || id == null){
            window.location.href = '/battle';
            return;
        }

        $.ajax({
            type: "GET",
            url: "/battle/getGuildId",
            data: {
                id : id,
                server : server
            },
            dataType: "json",
            beforeSend: function(){
                turnLoading();
            },
            success: function (response) {
                const ul = $('#guildList');
                $(ul).empty();

                if(response.length == 0){
                    showAlert('검색 결과가 없습니다. 다시 입력해주세요.');
                    return;
                }

                for(var i = 0; i < response.length; i++){
                    $(ul).append(`<li class="list-item" value="${response[i].Id}">${response[i].Name}</li>`);
                }
                $('.blackArea').show();
                $('.popup').fadeIn(500);
            },
            error : function(request,status,error){
                showAlert('알수없는 오류가 발생하였습니다. 잠시후에 다시 시도해주세요.');
            },
            complete : function(){
                turnLoading();
            }
        });
    }
    
    $("#inputText").on("keyup", function (e) {
        if(e.keyCode == 13){
            requestGuildId();
        }

    });
    $("#searchBtn").on('click', function(e){
        requestGuildId();
    });

    $("multiBtn").click(function (e) { 
        // TODO : multi mode로 여러 체크박스에 선택된 id들을 모두 종합해서 보여줘야함.
        
    });

    // 더보기 버튼.
    $('#moreviewBtn').on('click', function(){
        const inputValue =  $('#guildId').val();
        const server = $('input[name="server"]:checked').val();
        const l = $('#limit').val();
        const offset = $('.maintable tbody tr').length;

        $.ajax({
            type: "GET",
            url: "/battle/more",
            data: {
                inputValue : inputValue,
                server : server,
                offset : offset,
            },
            dataType: "json",
            beforeSend: function(){
                turnLoading();
            },
            success: function (response) {
                appendResponse(response, inputValue);
                $('#offset').val(offset);
                if(response.length == 0){
                    showAlert('더 이상 보여줄 데이터가 없습니다.');
                }
            },
            error: function(e){
                showAlert('알수없는 오류가 발생하였습니다. 잠시후에 다시 시도해주세요.');
            },
            complete : function(){
                turnLoading();
            }
        });
    });

    // 테이블 앞에 붙이기
    function prependResponse(response, id){
        const tbody = $('.maintable tbody');
        const server = $('input[name="server"]:checked').val();

        for(var i = response.length-1; i >= 0; i--){
            const r = response[i];
            const tr = document.createElement('tr');
            $(tr).attr('data-server', server);
            $(tr).attr('data-id', r.id);
            $(tr).addClass('hover-background tr');
            $(tr).append(`<td><input type="checkbox" name="multi" value="${r.id}"/></td>`);
            $(tr).append(`<td>${r.endTime}</td>`);
            $(tr).append(`<td>${r.utcTime} UTC</td>`);
            let guilds = r.guilds;
            let str = ``;
            for(var j = 0; j < guilds.length; j++){
                if(id == guilds[j].id){
                    str += ', ' + `<span class="searched">${guilds[j].name}</span>`;
                }else{
                    str += ', ' + `<span class="guild-name">${guilds[j].name}</span>`;
                }
            }
            $(tr).append(`<td>${str.slice(1)}</td>`);
            $(tr).append(`<td>${r.totalFameStr}</td>`);
            $(tr).append(`<td>${r.players.length}</td>`);
            $(tr).append(`<td>${r.totalKills}</td>`);

            $(tbody).prepend(tr);
        }
    }


    // 테이블 뒤에 붙이기
    function appendResponse(response, id){
        const tbody = $('.maintable tbody');
        const server = $('input[name="server"]:checked').val();

        for(var i = 0; i < response.length; i++){
            const r = response[i];
            const tr = document.createElement('tr');
            $(tr).attr('data-server', server);
            $(tr).attr('data-id', r.id);
            $(tr).addClass('hover-background tr');
            $(tr).append(`<td><input type="checkbox" name="multi" value="${r.id}"/></td>`);
            $(tr).append(`<td>${r.endTime}</td>`);
            $(tr).append(`<td>${r.utcTime} UTC</td>`);
            let guilds = r.guilds;
            let str = ``;
            for(var j = 0; j < guilds.length; j++){
                if(id == guilds[j].id){
                    str += ', ' + `<span class="searched">${guilds[j].name}</span>`;
                }else{
                    str += ', ' + `<span class="guild-name">${guilds[j].name}</span>`;
                }
            }
            $(tr).append(`<td>${str.slice(1)}</td>`);
            $(tr).append(`<td>${r.totalFameStr}</td>`);
            $(tr).append(`<td>${r.players.length}</td>`);
            $(tr).append(`<td>${r.totalKills}</td>`);

            $(tbody).append(tr);
        }
    }

    $('.guildLink').on('click', function(){
        const server = $('input[name="server"]:checked').val();
        const id = $(this).data('id');

        $.ajax({
            type: "GET",
            url: "/battle/more",
            data: {
                inputValue : id,
                server : server,
                limit : 20
            },
            dataType: "json",
            beforeSend: function(){
                turnLoading();
            },
            success: function (response) {
                $('.maintable tbody').empty();
                if(response.length == 0){
                    $('.maintable tbody').append('<tr><td colspan="7">검색 결과가 없습니다.</td></tr>');
                }else{
                    appendResponse(response, id);
                }
                $('#guildId').val(id);
            },
            error : function(request,status,error){
                console.log(error);
                showAlert('알수없는 오류가 발생하였습니다. 잠시후에 다시 시도해주세요.');
            },
            complete : function(){
                turnLoading();
            }
        });

    });

    $(".closeBtn").on('click', function () {
        $('.blackArea').hide();
        $('.popup').fadeOut(500);
    });

});