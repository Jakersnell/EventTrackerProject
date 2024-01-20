import { toggleShowPassword, handleLoginFormSubmit } from "./modules/auth.js";
import { init, route } from "./modules/routing.js";

window.addEventListener("load", () => {
  window.onpopstate = route;
  window.route = route;
  window.toggleShowPassword = toggleShowPassword;
  window.handleLoginFormSubmit = handleLoginFormSubmit;
  init();
});
