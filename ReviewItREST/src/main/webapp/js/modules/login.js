import { makeAjaxRequest } from "./requests.js";
import { route } from "./routing.js";

const loginTemplate = `
    <div class="row">
        <div class="col-4"></div>
        <div class="col-4">
            <div id="errorDisplay"></div>
            <form name="loginForm" id="loginForm" class="mt-4">
                <div class="row">
                    <input type="text" autocomplete="username" name="username" 
                        class="form-control" placeholder="username" required/>
                </div>
                <br>
                <div class="row">
                    <input type="password" autocomplete="current-password" name="password" 
                        class="form-control" placeholder="password" required/>
                </div>
                <br>
                <div class="row">
                    <button type="submit" name="submit" 
                        class="btn btn-primary form-control">login</button>
                </div>
                <div class="row">
                    <input class="form-check-input" type="checkbox" name="showPasswordCheckbox">&nbsp;Show Password</input>
                </div>
            </form>
        </div>
        <div class="col-4"></div>
    </div>
`;

export function logoutHandler() {
  sessionStorage.setItem("auth", "null");
}

export function getAuthToken() {
  if (!userIsLoggedIn()) {
    return null;
  }
  const auth = sessionStorage.getItem("auth");
  return JSON.parse(auth);
}

export function userIsLoggedIn() {
  const auth = sessionStorage.getItem("auth");
  return auth != "null" && auth != undefined && auth != null;
}

export function setAuth(token) {
    sessionStorage.setItem('auth', JSON.stringify(token));
}

export function userIsAdmin() {
  const token = getAuthToken();
  return token != null && token.userIsAdmin;
}

export function activateLoginForm() {
  if (userIsLoggedIn()) {
    route("home");
  }
  const content = document.getElementById("contentWrap");
  content.innerHTML = loginTemplate;
  const loginForm = document.loginForm;
  loginForm.addEventListener("submit", loginFormHandler);
  loginForm.showPasswordCheckbox.addEventListener(
    "click",
    showPasswordEventHandler
  );
}

let showPasswordEventHandler = (event) => {
  var x = document.loginForm.password;
  if (x.type === "password") {
    x.type = "text";
  } else {
    x.type = "password";
  }
};

let loginFormHandler = (event) => {
  event.preventDefault();
  const form = event.target;
  const username = form.username.value;
  const password = form.password.value;
  attemptLogin(username, password);
};

let attemptLogin = (attemptUsername, attemptPassword) => {
  makeAjaxRequest({
    method: "POST",
    url: "auth/login",
    expectedStatus: 200,
    body: {
      username: attemptUsername,
      password: attemptPassword,
    },
    success: (authToken) => {
      setAuth(authToken);
      route("home");
    },
    error: (status) => {
      route("login");
      pauseFormInput();
      switch (status) {
        case 401:
          displayErrorMessage("Incorrect username/password.");
          break;
        case 400:
          console.log(
            `username '${attemptUsername}' and password '${attemptPassword}, failed. status '${status}.''`
          );
          break;
      }
    },
  });
};

let displayErrorMessage = (msg) => {
  setTimeout(() => {
    const errorDisplay = document.getElementById("errorDisplay");
    errorDisplay.innerHTML = `
          <h3 style="color: red;">${msg}</h3>
        `;
  }, 0);
};

let pauseFormInput = () => {
  setTimeout(() => {
    document.loginForm.submit.disabled = true;
    setTimeout(() => {
      document.loginForm.submit.disabled = false;
    }, 1000);
  }, 0);
};
