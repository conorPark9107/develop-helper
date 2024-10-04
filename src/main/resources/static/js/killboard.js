$(document).ready(function() {
    $('#userId').on('keyup',function(e){
        var text = $('#userId').val();
        var radio = $('input[name="location"]:checked').val();

        checkId(text);

        if(e.keyCode == 13){
            // idLength : 3 ~ 16
            if(text.length >= 3){

                $('#invalidId_msg').empty();
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
                    success : function(result) {
                       if(result != null && result.length > 0){
                           for(var i = 0; i < result.length; i++){
                                $('#userNames').append('<a href="/killboard/getKillBoard?id='+ result[i].Id +'&location='+ radio +'" class="content-body-item-userGroup-userId">'+ result[i].Name +'</a>');
                           }
                           $('#userList').show();
                       }else{
                           showWarningMsg('검색결과가 없습니다. 인게임 ID를 다시 확인해주세요.');
                       }
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

    $("input[name='radio_kill']").change(function(){
    	var value = $("input[name='radio_kill']:checked").val();
    	if(value == 'kill'){
            $("#deathTable").hide();
            $("#killTable").fadeIn(500);
    	}else{
            $("#killTable").hide();
            $("#deathTable").fadeIn(500);
    	}
    });


    function showWarningMsg(msg){
        $('#invalidId_msg').text(msg);
        $('#invalidId_msg').show();
        $('#invalidId_msg').fadeOut(2000);
    }

    function checkId(){
        var regExpId = /^[a-zA-Z0-9]*$/;
        if(!regExpId.test($('#userId').val())){
            showWarningMsg('알비온 인게임 ID는 영어와 숫자로만 구성되요.. 다시 입력해주세요!');
            $('#userId').empty();
        }
    }

});





