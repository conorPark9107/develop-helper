$(document).ready(function(){

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

    $('tbody tr td').on('click', function(e){
        if($(e.target).hasClass("not")){
            return;
        }
        const tds = $(this).parent().find('td');
        const killerId = $(tds[2]).attr('value');
        const victimId = $(tds[5]).attr('value');
        const location = $(tds[2]).data('server');
        alert(location);
        var form = $('<form></form>');
        form.attr("method","get");
        form.attr("action","/killboard/detail");
        form.append($('<input/>', {type: 'hidden', name: 'killerId', value:killerId }));
        form.append($('<input/>', {type: 'hidden', name: 'victimId', value:victimId }));
        form.append($('<input/>', {type: 'hidden', name: 'location', value:location }));
        form.appendTo('body');
        form.submit();
    });

    $('.userLink').on('click', function(e){
        const element = e.currentTarget;
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
    });

});