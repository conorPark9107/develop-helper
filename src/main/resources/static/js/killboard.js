$(document).ready(function() {
    $('#userId').on('keyup',function(e){
        var text = $('#userId').val();
        var radio = $('input[name="location"]:checked').val();

        if(e.keyCode == 13){
            // idLength : 3 ~ 16
            if(text.length >= 3){

                $('#invalidId_msg').remove();

                $.ajax({
                    type : 'get',
                    url : '/killboard/getKillBoard',
                    async : true,
                    dataType : 'text',
                    data : {
                        "inputId" : text,
                        "serverLocation" : radio
                    },
                    success : function(result) { // 결과 성공 콜백함수
                        console.log(result[0].id);
                    },
                    error : function(request, status, error) { // 결과 에러 콜백함수
                        console.log(error)
                    }
                });
            }else{
                $('#userId').after('<p id="invalidId_msg" style="color : red; font-size:0.3em;">인게임 ID를 다시 확인해주세요.</p>');
                $('#invalidId_msg').after('<p id="invalidId_msg" style="color : red; font-size:0.3em;">Albion Online의 계정생성 규칙은 3 ~ 16자입니다.</p>');
            }
        }

    });
});





