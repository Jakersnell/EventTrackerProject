export function clearElementById(id) {
  const contentWrap = document.getElementById(id);
  while (contentWrap.firstElementChild) {
    contentWrap.removeChild(contentWrap.firstElementChild);
  }
}
