$(document).ready(function (){
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

