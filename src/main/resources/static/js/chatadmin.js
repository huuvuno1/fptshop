var stompClient = null;


let socket = new SockJS('/ws');
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/user/chat/listen', function (data) {
        let msg = createUserSendTemplate(JSON.parse(data.body));
        console.log(msg)
        let list = document.querySelector(".inbox_people > .inbox_chat");
        list.innerHTML = msg.outerHTML + list.innerHTML;
    });
}, function (error) {
    console.log(error)
});


function newMessage() {

}

function createUserSendTemplate(data) {
    //active_chat
    let div = document.createElement('div');
    div.classList.add('chat_list');
    div.innerHTML = `<div class="chat_people">
                        <div class="chat_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                        <div class="chat_ib">
                            <h5 style="display: flex; justify-content: space-between;">
                                <span class="chat_name"></span>
                                <span class="chat_date"></span>
                            </h5>
                            <p class="chat_text"></p>
                        </div>
                    </div>`
    div.querySelector('.chat_name').innerText = data.sender;
    div.querySelector('.chat_date').innerText = data.time;
    div.querySelector('.chat_text').innerText = data.content;
    return div;
}