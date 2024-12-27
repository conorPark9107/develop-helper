$(document).ready(function(){
    $('tbody tr td').on('click', function(e){
        if($(e.target).hasClass("not")){
            return;
        }
        const tds = $(this).parent().find('td');
        const killerId = $(tds[2]).attr('value');
        const victimId = $(tds[5]).attr('value');

        const location = $('input[name=location]:checked').val();

        var form = $('<form></form>');
        form.attr("method","get");
        form.attr("action","/killboard/detail");
        form.append($('<input/>', {type: 'hidden', name: 'killerId', value:killerId }));
        form.append($('<input/>', {type: 'hidden', name: 'victimId', value:victimId }));
        form.append($('<input/>', {type: 'hidden', name: 'location', value:location }));
        form.appendTo('body');
        form.submit();
    });
});