import { getAuthToken as moduleGetAuth } from "./modules/login.js";
import { route } from "./modules/routing.js";

window.addEventListener("load", (_) => {
  route("home");
  const homeBtns = document.getElementsByClassName("home-btn");
});

