$(document).ready(function () {
    
    function submitToServer(){
        const inputValue = $(this).val();
        const server = $('input[name="server"]:checked').val();
        $.ajax({
            type: "GET",
            url: "/battle/getBattles",
            data: {
                inputValue : inputValue,
                server : server
            },
            dataType: "dataType",
            success: function (response) {
                
            }
        });
    }
    
    $("#inputText").on("keyup", function (e) {
        if(e.keyCode == 13){
            submitToServer();
        }
    });
    $("#searchBtn").on('click', function(e){
        submitToServer();
    });

    $("multiBtn").click(function (e) { 
        // TODO : multi mode로 여러 체크박스에 선택된 id들을 모두 종합해서 보여줘야함.
        
    });

    





});