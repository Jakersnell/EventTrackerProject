export function displayPopupMsg(text) {
  setTimeout(() => {
    const msgDisplay = document.getElementById("msgDisplay");
    msgDisplay.innerHTML = `
  <div class="row">
    <div class="col-10">
        <h3>${text}</h3>
    </div>
    <div class="col-2">
        <button id="closePopup">
            X
        </button>
    </div>
  </div>
  `;
  }, 0);

  setTimeout(() => {
    const popupBtn = document.getElementById("closePopup");
    popupBtn.addEventListener("click", closePopup);
  }, 0);

  setTimeout(closePopup, 15000);
}

let closePopup = () => {
  const msgDisplay = document.getElementById("msgDisplay");
  msgDisplay.innerHTML = "";
};

export function clearElementById(id) {
  const element = document.getElementById(id);
  while (element.firstElementChild) {
    element.removeChild(element.firstElementChild);
  }
}
