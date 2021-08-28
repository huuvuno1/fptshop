var stompClient = null;
let x = 0;

let socket = new SockJS('/ws');
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/user/chat/listen', function (data) {
        if (x == 0) {
            let msg = createUserSendTemplate(JSON.parse(data.body));
            let list = document.querySelector(".inbox_people > .inbox_chat");
            list.innerHTML = msg.outerHTML + list.innerHTML;
            x = 1;
        }

        receiveChatAdmin(JSON.parse(data.body))
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

function createESendChatAdmin(data) {
    let div = document.createElement('div');
    div.innerHTML = `<div class="sent_msg">
                        <p class="txt_send-vu"></p>
                        <span class="time_date"></span> 
                    </div>`
    div.classList.add('outgoing_msg');
    div.querySelector('.txt_send-vu').innerText = data.content;
    div.querySelector('.time_date').innerText = data.time;
    return div;
}

function sendChatAdmin() {
    let inpChat = document.querySelector("div.mesgs > div.type_msg > div > input");
    if (inpChat.value.trim() == "")
        return;
    let element = createESendChatAdmin({
        "content": inpChat.value,
        "time": new Date(),
        "receiver": 'user1629795506265'
    })

    stompClient.send("/app/send-chat", {}, JSON.stringify(
        {
            "content": inpChat.value,
            "time": new Date(),
            "receiver": 'user1629795506265'
        }
    ));


    inpChat.value = ''
    document.querySelector('div.mesgs > div.msg_history').appendChild(element);
    scrollChatAdmin()
}
document.querySelector("#send_msg-vu").addEventListener('click', () => sendChatAdmin())

document.querySelector("div.mesgs > div.type_msg > div > input").addEventListener('keydown', (e) => {
    if (e.key == 'Enter')
        sendChatAdmin();
})

function scrollChatAdmin() {
    let list = document.querySelector("div.mesgs > div.msg_history");
    list.scrollTop = list.scrollHeight
}

function createEReceiveChatAdmin(data) {
    let div = document.createElement('div');
    div.innerHTML = `<div class="incoming_msg">
                        <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                        <div class="received_msg">
                        <div class="received_withd_msg">
                            <p class='txt_send-vu'></p>
                            <span class="time_date"></span>
                        </div>
                    </div>`
    div.classList.add('incoming_msg');
    div.style = 'margin-top: 15px;'
    div.querySelector('.txt_send-vu').innerText = data.content;
    div.querySelector('.time_date').innerText = data.time;
    return div;
}

function receiveChatAdmin(data) {
    let element = createEReceiveChatAdmin(data);
    document.querySelector('div.mesgs > div.msg_history').appendChild(element);
    scrollChatAdmin()
}