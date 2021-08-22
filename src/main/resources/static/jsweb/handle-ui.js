// handle slide
let isDrag = false;
let isClick = true;
let xCurrent = 0;
let xDown = 0;
const productsSlide = document.querySelector('#slide_proc');

productsSlide.addEventListener('mousedown', e => {
    xDown = e.clientX - productsSlide.offsetLeft;
    xCurrent = productsSlide.scrollLeft;
    isDrag = true;
});
  
productsSlide.addEventListener('mousemove', e => {
    if (isDrag) {
        isClick = false;
        let xNew = e.clientX - productsSlide.offsetLeft;
        xCurrent += -Math.round((xNew - xDown));
        if (xCurrent > productsSlide.scrollWidth) {
            xCurrent = productsSlide.scrollWidth;
        }
        if (xCurrent < 0)
        {
            xCurrent = 0;
        }
        productsSlide.style = "scroll-behavior: unset;";
        productsSlide.scrollLeft = xCurrent;
        xDown = xNew;
        hideShowNextSide();
        e.preventDefault();
    }
    
});
  
window.addEventListener('mouseup', e => {
    isDrag = false;
    console.log("up")
});

productsSlide.addEventListener('mouseup', e => {
    isDrag = false;
});
productsSlide.addEventListener('click', e => {
    if (!isClick) {
        e.preventDefault();
        isClick = true;
    }
})


// next list product in list promotion
const promotionNext = document.querySelector("body > div.app > div.container.flex.frame.promotion_frame > div.slide_arrow.slide_arrow-right");
const promotionPrev = document.querySelector("body > div.app > div.container.flex.frame.promotion_frame > div.slide_arrow.slide_arrow-left");

promotionNext.addEventListener('click', async () => {
    productsSlide.style = "scroll-behavior: smooth;";
    productsSlide.scrollLeft += productsSlide.offsetWidth;
    promotionPrev.style.display = "block";
})
    
promotionPrev.addEventListener('click', () => {
    productsSlide.style = "scroll-behavior: smooth;";
    productsSlide.scrollLeft -= productsSlide.offsetWidth;
    hideShowNextSide();
    promotionNext.style.display = "block";
})

function hideShowNextSide() {
    if (productsSlide.scrollLeft <= 0)
        promotionPrev.style.display = "none";
    else
        promotionPrev.style.display = "block";
    if (productsSlide.scrollLeft >= (productsSlide.scrollWidth - productsSlide.offsetWidth))
        promotionNext.style.display = "none";
    else
        promotionNext.style.display = "block";
}