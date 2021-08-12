setInterval(() => {
    let eSecond = document.getElementById("sale_time_second");
    let second = parseInt(eSecond.innerText) - 1;
    if (second == -1) {
        second = 59;
        let eMinute = document.getElementById("sale_time_minute");
        let minute = parseInt(eMinute.innerText) - 1;
        if (minute == -1) {
            minute = 59;
            let eHour = document.getElementById("sale_time_hour");
            let hour = parseInt(eHour.innerText) - 1;
            if (hour == -1) {
                hour = 23;
                let eDay = document.getElementById("sale_time_day");
                let day = parseInt(eDay.innerText) - 1;
                if (day == -1) {
                    day = hour = minute = second = 0;
                }
                eDay.innerText = day;
            }
            eHour.innerText = hour;
        }
        eMinute.innerText = minute;
    }
    eSecond.innerText = second;
}, 1000)


var iM = 0;
const listImage = [
    "/img/banner/637633241066289329_F_H1_800x300.jpg",
    "/img/banner/637633639065209269_F-H1_800x300.jpg",
    "/img/banner/637633657926617644_F_H1_800x300 THIET KE MAIN 5.jpg",
    "/img/banner/637638057406075035_F_H1_800x300.jpg"
]

setInterval(() => {
    document.querySelector('.wrap-img-slide')
        .innerHTML = `<img id="slide_img" class="leave_animate" src="${listImage[iM]}" alt="">`
    iM++;
    if (iM >= listImage.length)
        iM = 0;
}, 5000)
