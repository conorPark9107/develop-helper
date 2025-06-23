$(document).ready(function() {
    /*
    var tempPageName = window.location.href;
    var strpageName = tempPageName.split("/");
    var nowPage = strpageName[strpageName.length-1].split("?")[0];
    if(nowPage === 'killboard'){
        showAlert('알비온온라인 서버에서 공식적으로 데이터를 오픈한게 아닙니다. <br/> 그렇기 때문에 정보를 요청하면 응답이 매우 느린 경우가 있습니다.');
    }
    */
    $('input[name="location"]').on('change', function(){
        turnLoading();
        const server = $(this).val();
        var form = $('<form></form>');
        form.attr("method","get");
        form.attr("action","/killboard");
        form.append($('<input/>', {type: 'hidden', name: 'server', value: server}));
        form.appendTo('body');
        form.submit();
    });

    $('.biggest-table tbody tr').on('click', function(){
        turnLoading();
        var server = $('input[name="location"]:checked').val();
        const killerId = $(this).data('killer');
        const victimId = $(this).data('victim');

        var form = $('<form></form>');
        form.attr("method","get");
        form.attr("action","/killboard/detail");
        form.append($('<input/>', {type: 'hidden', name: 'killerId', value:killerId }));
        form.append($('<input/>', {type: 'hidden', name: 'victimId', value:victimId }));
        form.append($('<input/>', {type: 'hidden', name: 'location', value:server }));
        form.appendTo('body');
        form.submit();
    });

    $('#userId').on('keyup',function(e){
        var text = $('#userId').val();
        var radio = $('input[name="location"]:checked').val();

        checkId(text);

        if(e.keyCode == 13){
            // idLength : 3 ~ 16
            if(text.length >= 3){

                $('#invalidId_msg').empty();
                $('#userNames').empty();

                $.ajax({
                    type : 'get',
                    url : '/killboard/getUserInfo',
                    async : true,
                    dataType : 'json',
                    data : {
                        "inputId" : text,
                        "serverLocation" : radio
                    },
                    beforeSend: function() {
                        turnLoading();
                    },
                    success : function(result) {
                       turnLoading();
                       if(result != null && result.length > 0){
                           for(var i = 0; i < result.length; i++){
                                $('#userNames').append('<span data-userid="'+ result[i].Id +'" data-server="'+ radio + '" data-username="' + result[i].Name +'" class="content-body-item-userGroup-userId userLink">'+ result[i].Name +'</span>');
                           }
                           $('#userList').show();
                       }else{
                            showAlert('검색결과가 없습니다. 인게임 ID를 다시 확인해주세요.');
                       }
                    },
                    error : function(request, status, error) {
                        console.log(error);
                        turnLoading();
                        showAlert('서버로부터 응답이 없습니다. 나중에 시도해주세요.');
                    }
                });
            }else{
                showWarningMsg('인게임 ID를 다시 확인해주세요. Albion Online의 계정생성 규칙은 3 ~ 16자입니다.', 1000);
            }
        }

    });

    $('#userList').on('click', function(){
        $('#userList').hide();
    });

    $('#userNames').on('click', function(){
        $('#blackArea').show();
        $('#loading').css('display', 'block');
    });

    $('.link').on('click', function(){
        turnLoading();
    });

    $('.userLink').on('click', function(e){
        submitForCount(e);
    });

    $('#userNames').on('click', '.userLink', function(e) {
        submitForCount(e);
    });

    function submitForCount(e){
        const element = e.target;
        const userId = element.dataset.userid;
        const server = element.dataset.server;
        const userName = element.dataset.username;

        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '/killboard/getKillBoard';

        const inputs = {
            userId: userId,
            server: server,
            userName: userName
        };

        for(const key in inputs){
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = key;
            input.value = inputs[key];
            form.appendChild(input)
        }

        document.body.appendChild(form);
        form.submit();
    }


    function showWarningMsg(msg, time){
        $('#invalidId_msg').text(msg);
        $('#invalidId_msg').show();
        $('#invalidId_msg').fadeOut(time);
    }

    function checkId(){
        var regExpId = /^[a-zA-Z0-9]*$/;
        if(!regExpId.test($('#userId').val())){
            showWarningMsg('알비온 인게임 ID는 영어와 숫자로만 구성되요.. 다시 입력해주세요!', 700);
            $('#userId').empty();
        }
    }
});





