$(document).ready(function() {
    $('#userId').on('keyup',function(e){
        var text = $('#userId').val();
        var radio = $('input[name="location"]:checked').val();

        checkId(text);

        if(e.keyCode == 13){
            // idLength : 3 ~ 16
            if(text.length >= 3){

                $('#invalidId_msg').remove();
                $('#userNames a').remove();

                $.ajax({
                    type : 'get',
                    url : '/killboard/getUserInfo',
                    async : true,
                    dataType : 'json',
                    data : {
                        "inputId" : text,
                        "serverLocation" : radio
                    },
                    success : function(result) { // 결과 성공 콜백함수
                        /*
                            {
                                "totalKills":null,
                                "gvgKills":null,
                                "gvgWon":null,
                                "Id":"CzVrkgSrQlSd8Cdr-SYf8Q",
                                "Name":"met",
                                "GuildId":"",
                                "GuildName":"",
                                "AllianceId":"",
                                "AllianceName":"",
                                "Avatar":"",
                                "AvatarRing":"",
                                "KillFame":123317100,
                                "DeathFame":188522070,
                                "FameRatio":"0.65"
                            }...
                           */
                           for(var i = 0; i < result.length; i++){
                                $('#userNames').append('<a href="/killboard/getKillBoard?id='+ result[i].Id +'&location='+ radio +'" class="content-body-item-userGroup-userId">'+ result[i].Name +'</a>');
                           }
                           $('#userList').css('display', 'block');
                    },
                    error : function(request, status, error) {
                        console.log(error);
                        showWarningMsg('인게임 ID를 다시 확인해주세요.');
                    }
                });
            }else{
                showWarningMsg('인게임 ID를 다시 확인해주세요. Albion Online의 계정생성 규칙은 3 ~ 16자입니다.');
            }
        }

    });

    function showWarningMsg(msg){
        $('#invalidId_msg').text(msg);
        $('#invalidId_msg').fadeIn(500);
        $('#invalidId_msg').fadeOut(3000);
    }

    function checkId(){
        var regExpId = /^[a-zA-Z0-9]*$/;
        if(!regExpId.test($('#userId').val())){
            showWarningMsg('알비온 인게임 ID는 영어와 숫자로만 구성되요.. 다시 입력해주세요!');
            $('#userId').val('');
        }
    }

});





