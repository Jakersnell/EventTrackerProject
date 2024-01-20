import { clearAuthToken, getAuthToken } from "./auth.js";

const loadingBarHTML = `
<div class="middle-spacer"></div>
<div class="d-flex justify-content-center">
  <div class="spinner-border" role="status">
    <span class="sr-only"></span>
  </div>
</div>
`;

const urlPatterns = {
  404: { html: "/html/404.html", action: null },
  "/": { html: "/html/home.html", action: null },
  "/login": { html: "/html/login.html", action: null },
  "/signup": { html: "html/signup.html", action: null },
};

let filterRouting = (href) => {
  let routeTo = href;
  switch (href) {
    case "/login":
      if (getAuthToken()) {
        routeTo = "/home";
      }
      break;
    case "/logout":
      routeTo = "/home";
      if (!getAuthToken()) {
      } else {
        clearAuthToken();
      }
      break;
    case "/signup":
      if (getAuthToken()) {
        routeTo = "/home";
        break;
      }
  }
  return routeTo;
};

export function init() {
  const path = new URLSearchParams(window.location.search).get("path") || "";
  window.history.replaceState({}, "", window.location.origin + path);
  routeTo(path);
}

export function route(event) {
  event.preventDefault();
  routeTo(event.target.href);
}

export function routeTo(href) {
  window.history.pushState({}, "", filterRouting(href));
  const page = urlPatterns[window.location.pathname] || urlPatterns[404];
  routeApplication(page.html);
  refresh();
  if (page.action) {
    setTimeout(page.action, 0);
  }
}

const routeApplication = async (path) => {
  console.log(path);
  document.getElementById("router-output").innerHTML = loadingBarHTML;
  const xhr = new XMLHttpRequest();
  xhr.open("GET", path);
  xhr.onreadystatechange = () => {
    if (xhr.readyState === 4) {
      if (xhr.status == 200) {
        document.getElementById("router-output").innerHTML = xhr.responseText;
      } else {
        console.log(
          `Encountered error while attempting html GET request on ${path}, status ${xhr.status}.`
        );
      }
    }
  };
  xhr.send();
};

let refresh = () => {
  loginRefresh();
};

let loginRefresh = () => {
  setTimeout(() => {
    const isLoggedIn = getAuthToken() !== null;

    const loggedInConstrained = document.getElementsByClassName("if-logged-in");
    Array.from(loggedInConstrained).forEach((element) => {
      element.style.display = isLoggedIn ? "block" : "none";
    });

    const loggedOutConstrained =
      document.getElementsByClassName("if-not-logged-in");
    Array.from(loggedOutConstrained).forEach((element) => {
      element.style.display = isLoggedIn ? "none" : "block";
    });
  }, 0);
};
