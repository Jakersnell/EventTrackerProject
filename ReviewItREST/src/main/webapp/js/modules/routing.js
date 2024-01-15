import { activateHomeView } from "./home.js";
import { activateProductForm } from "./listProduct.js";
import {
  activateLoginForm,
  userIsAdmin,
  userIsLoggedIn,
} from "./login.js";
import { activateProductDetailView, deleteProduct } from "./productDetail.js";
import { clearElementById } from "./util.js";

export function route(viewName = "home", params = null) {
  setTimeout(() => {
    switch (viewName) {
      case "home":
        activateHomeView();
        break;
      case "product":
        activateProductDetailView(params.id);
        break;
      case "login":
        activateLoginForm();
        break;
      case "logout":
        sessionStorage.setItem("auth", null);
        route("home");
        break;
      case "delete-product":
        deleteProduct(params.id, params.routeTo);
        break;
      case "create-product":
        activateProductForm();
        break;
    }
    setTimeout(refresh, 0);
  }, 0);
}

let refresh = () => {
  placeSigninButton();
  placeMakeFormButton();
  distributeRouteListener("home-route", "home");
};

let distributeRouteListener = (className, routeName) => {
  const items = document.getElementsByClassName(className);
  for (let item of items) {
    setTimeout(() => {
      if (item) {
        item.addEventListener("click", () => {
          console.log(`Clicking: ${className}`);
          route(routeName);
        });
      }
    }, 0);
  }
};

let placeSigninButton = () => {
  setTimeout(() => {
    clearElementById("signInDiv");
    const signInDiv = document.getElementById("signInDiv");

    const div = document.createElement("div");
    const h2 = document.createElement("h2");
    div.appendChild(h2);
    let routeName;
    let text;
    let eventListener;
    if (userIsLoggedIn()) {
      routeName = "logout-route";
      text = "Logout";
      eventListener = () => {
        route("logout");
      };
    } else {
      routeName = "login-route";
      text = "Login";
      eventListener = () => {
        route("login");
      };
    }
    h2.textContent = text;
    div.classList.add(routeName);
    div.addEventListener("click", eventListener);

    signInDiv.appendChild(div);
  }, 0);
};

let placeMakeFormButton = () => {
  setTimeout(() => {
    clearElementById("navbtn2");
    if (userIsAdmin()) {
      const div = document.createElement("div");
      div.classList.add("link-btn");
      div.addEventListener("click", () => {
        route("create-product");
      });
      const h2 = document.createElement("h2");
      h2.textContent = "List Product";
      div.appendChild(h2);

      document.getElementById("navbtn2").appendChild(div);
    }
  }, 0);
};
