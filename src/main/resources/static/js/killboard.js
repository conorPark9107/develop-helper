$(document).ready(function() {
    $('#userId').on('input',function(e){
        var text = $('#userId').val();
        var radio = $('input[name="location"]:checked').val();

        // id는 3 ~ 16자
        if(text.length >= 3){
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
                    console.log(result);
                },
                error : function(request, status, error) { // 결과 에러 콜백함수
                    console.log(error)
                }
            });
        }
    });
});





