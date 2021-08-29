var stompClient = null;

function connect() {
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {stompClient.subscribe('/user/chat/listen', function (data) {
        receiveMessage(JSON.parse(data.body));
    });

    }, function (error) {
        console.log(error)
    });
}

function createMessageElement(data) {
    let msg = document.createElement('div');
    msg.classList.add("bl_message", "flex");
    let html = `<div class="bl_message-avt">
                        <img src="./static/img/icon/default-profile.svg" alt="">
                    </div>
                    <div class="bl_message-list">
                        <div class="bl_message-detail flex">
                            <div class="bl_msg">
                            </div>
                            <div class="bl_message-time">
                            </div>
                        </div>
                    </div>`;
    msg.innerHTML = html;
    msg.querySelector('.bl_msg').innerText = data.content;
    msg.querySelector('.bl_message-time').innerText = data.time;
    return msg;
}

function createNextMsg(data) {
    let msg = document.createElement('div');
    msg.classList.add("bl_message-detail", "flex");
    let html = `<div class="bl_msg">
                </div>
                <div class="bl_message-time">
                </div>`;
    msg.innerHTML = html;
    msg.querySelector('.bl_msg').innerText = data.content;
    msg.querySelector('.bl_message-time').innerText = data.time;
    return msg;
}

let inputMessage = document.querySelector("#txtChat");
if (inputMessage) {
    inputMessage.addEventListener('keydown', e => {
        if (e.key == 'Enter')
            sendMessage(e);
    })
}

let btnSend = document.querySelector("#btn_send-chat");
if (btnSend) {
    btnSend.addEventListener('click', e => sendMessage(e))
}
function sendMessage(e) {
    let inp = document.querySelector("#txtChat");
    let text = inp.value;
    inp.value = '';

    stompClient.send("/app/send-chat", {}, JSON.stringify(
        {
            'content': text,
            'receiver': 'admin'
        }
    ));

    let msg = createMessageElement({content: text, time: "11:00"})
    msg.classList.add('type_send')
    document.querySelector("div.fr_chat-history.flex").appendChild(msg);
    scrollBottom();
}

function scrollBottom() {
    let list = document.querySelector("div.fr_chat-history.flex");
    list.scrollTop = list.scrollHeight
}

function receiveMessage(data) {
    let list = document.querySelectorAll(".bl_message");
    let eLast = list[list.length-1]
    if (eLast.classList.contains('type_receive')) {
        let msgs = eLast.querySelector('.bl_message-list');
        msgs.appendChild(createNextMsg(data));
    }
    else {
        let msg = createMessageElement(data)
        msg.classList.add('type_receive')
        document.querySelector("div.fr_chat-history.flex").appendChild(msg);
    }
    scrollBottom();
}

function showChat() {
    let fr = document.querySelector('.fr_chat');
    let icon = document.querySelector("div.round.f_chat");
    if (fr.style.display == "none") {
        icon.innerHTML = `<i class="fal fa-times icon_chat"></i>`;
        document.querySelector('.f_top').style.display = "none"
        fr.style.display = "block";
        // check xem da tung chat chua
        let msgChat = getMessageChatSession();
        if (msgChat) {
            connect();
            document.querySelector('.begin').style.display = "none";
            document.querySelector('.fr_message').style.display = "block";
        }
        else {
            document.querySelector(".begin_input.btn_join").addEventListener('click', async () => {
                let data = validateChatField();
                if (data) {
                    await registerSessionUser(data);
                    let messge = {
                        "content": "Tên: " + data.fullName + "\nSố điện thoại: " + data.phoneNumber + "\nEmail: " + data.email,
                        "time": new Date(),
                        "receiver": "admin"
                    }
                    let msg = createMessageElement(messge);
                    msg.classList.add('type_send')
                    document.querySelector("div.fr_chat-history.flex").appendChild(msg);
                    stompClient.send("/app/send-chat", {}, JSON.stringify(messge))
                }
                else {
                    // bat nhap lai
                    return;
                }
                fr.style.height = "500px";
                document.querySelector('.begin').style.display = "none";
                document.querySelector('.fr_message').style.display = "block";
            })
        }

    }
    else {
        icon.innerHTML = `<i class="fas fa-comment-dots icon_chat"></i>`;
        fr.style.display = "none";
        document.querySelector('.f_top').style.display = "block"
    }
}

document.querySelector("div.round.f_chat").addEventListener('click', () => showChat())
document.querySelector("#txt_inl").addEventListener('click', () => showChat())


function validateChatField() {
    // validate sau
    // neu k hop le thi return null
    let fullName = document.getElementById("full_name");
    let phoneNumber = document.getElementById("phone_number");
    let email = document.getElementById('email');
    let data = {
        fullName: fullName.value,
        phoneNumber: phoneNumber.value,
        email: email.value
    }
    return data;
}

async function registerSessionUser(data) {
    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    let raw = JSON.stringify({
        "fullName": data.fullName,
        "phoneNumber": data.phoneNumber,
        "email": data.email
    });

    let requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    await fetch("/api/v1/chats/users/create", requestOptions)
        .then(response => response.json())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
}

async function getMessageChatSession() {
    let requestOptions = {
        method: 'GET',
    };

    await fetch("/api/v1/chats", requestOptions)
        .then(response => response.json())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
    return true;
}