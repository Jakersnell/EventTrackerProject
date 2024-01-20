let handleResize = () => {
    const scWidth = window.innerWidth;
    const filterControlsButton = document.getElementById('filterControlsButton');

    let disabled = true;
    if (scWidth < 768) {
       disabled = false;
    }

    filterControlsButton.disabled = disabled;
};
window.addEventListener("resize",  handleResize);
handleResize();