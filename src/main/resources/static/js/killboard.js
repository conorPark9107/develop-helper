$(document).ready(function() {

    showAlert('알비온온라인 서버에서 공식적으로 데이터를 오픈한게 아닙니다. 저희가 그냥 가져다가 쓰는것일뿐이죠.<br/> 그렇기 때문에 정보를 요청하면 응답이 매우 느린 경우가 있습니다. <br/> 이점 양해부탁드립니다.');


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
                    beforeSend: function() {
                        turnLoading();
                    },
                    success : function(result) {
                       turnLoading();
                       if(result != null && result.length > 0){
                           for(var i = 0; i < result.length; i++){
                                $('#userNames').append('<a href="/killboard/getKillBoard?id='+ result[i].Id +'&location='+ radio +'" class="content-body-item-userGroup-userId">'+ result[i].Name +'</a>');
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





