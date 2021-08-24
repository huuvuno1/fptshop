if (location.pathname.includes('/admin/login')) {
    document.querySelector('#btnLogin_admin-vu').addEventListener('click', e => login(e));
    document.querySelector("body").addEventListener('keydown', (e) => {
        if (e.key === "Enter")
            login(e);
    });
}

function login(e) {
    e.preventDefault();
    let waiting = document.getElementById('wait_loading-vu');
    waiting.style = "display: flex;";
    let username = document.getElementById('inputEmail-vu').value;
    let password = document.getElementById('inputPassword-vu').value;
    let msg = document.getElementById('msg-vu');
    if (username.trim() == '' || password.trim() == '') {
        msg.innerText = "Please fill in all the information";
        msg.style = "display: block;";
        waiting.style = "display: none;";
        return;
    }
    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    let raw = JSON.stringify({
        "username": username,
        "password": password
    });

    let requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    fetch("/authentication", requestOptions)
        .then(response => response)
        .then(data => {
            if (data.status == 200)
                location.href = "/admin";
            else {
                waiting.style = "display: none;";
                msg.style = "display: block;";
                msg.classList.remove('alert-warning');
                msg.classList.add('alert-danger');
                msg.innerText = "Incorrect username or password!"
            }
        })
        .catch(error => {
            msg.style = "display: block;";
            msg.innerText = "An error occurred, please try again!"
        });

}