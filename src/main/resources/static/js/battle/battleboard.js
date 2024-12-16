$(document).ready(function () {

    // 전투 기록 행(table row)를 클릭하였을때
    $(".maintable tbody").on('click', 'tr' ,function(e){
        const id = $(this).find('td:eq(0)').find('input').val();
        const server = $(this).data('server');
        var form = $('<form></form>');
        form.attr("method","get");
        form.attr("action","/battle/detail");
        form.append($('<input/>', {type: 'hidden', name: 'id', value:id }));
        form.append($('<input/>', {type: 'hidden', name: 'server', value:server }));
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
            }
        });
    }

    function requestGuildId(){
        const id = $('#inputText').val();
        const server = $('input[name="server"]:checked').val();

        $.ajax({
            type: "GET",
            url: "/battle/getGuildId",
            data: {
                id : id,
                server : server
            },
            dataType: "json",
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

    $('#moreviewBtn').on('click', function(){
        const inputValue = $('#guildId').val();
        const server = $('input[name="server"]:checked').val();
        const l = $('#limit').val();
        const offset = Number($('#offset').val()) + Number(l) + 1;

        $.ajax({
            type: "GET",
            url: "/battle/more",
            data: {
                inputValue : inputValue,
                server : server,
                offset : offset,
            },
            dataType: "json",
            success: function (response) {
                appendResponse(response);
                $('#offset').val(offset);
                if(response.length == 0){
                    showAlert('더 이상 보여줄 데이터가 없습니다.');
                }
            },
            error: function(e){
                showAlert('알수없는 오류가 발생하였습니다. 잠시후에 다시 시도해주세요.');
            }
        });
    });


    function appendResponse(response, id){
        const tbody = $('.maintable tbody');
        const server = $('input[name="server"]:checked').val();

        for(var i = 0; i < response.length; i++){
            const r = response[i];
            const tr = document.createElement('tr');
            $(tr).attr('data-server', server);
            $(tr).addClass('hover-background tr');
            $(tr).append(`<td><input type="checkbox" name="multi" value="${r.id}"/></td>`);
            $(tr).append(`<td>${r.endTime}</td>`);
            $(tr).append(`<td>${r.utcTime} UTC</td>`);
            let guilds = r.guilds;
            let str = `<span class="guild-name">${guilds[0].name}</span>`;
            for(var j = 1; j < guilds.length; j++){
                if(id == guilds[j].id){
                    str += ', ' + `<span class="searched">${guilds[j].name}</span>`;
                }else{
                    str += ', ' + `<span class="guild-name">${guilds[j].name}</span>`;
                }
            }
            $(tr).append(`<td>${str}</td>`);
            $(tr).append(`<td>${r.totalFame}</td>`);
            $(tr).append(`<td>${r.players.length}</td>`);
            $(tr).append(`<td>${r.totalKills}</td>`);

            $(tbody).append(tr);
        }
    }


    $(".closeBtn").on('click', function () {
        $('.blackArea').hide();
        $('.popup').fadeOut(500);
    });

});