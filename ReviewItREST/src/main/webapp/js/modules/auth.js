import { routeTo } from "./routing.js";

export function urlAndAuth(url) {
  const auth = getAuthToken();
  if (auth) {
    url += `?auth=${auth.token}`;
  }
  return url;
}

export function getAuthToken() {
  const auth = sessionStorage.getItem("auth");
  let json = null;
  if (auth) {
    json = JSON.parse(auth);
  }
  return json;
}

export function clearAuthToken() {
  sessionStorage.removeItem("auth");
}

export function toggleShowPassword(event) {
  const passwordInputs = [
    event.target.form.password,
    event.target.form.passwordConfirmation,
  ];
  for (let password of passwordInputs) {
    if (password) {
      if (password.type === "password") {
        password.type = "text";
      } else {
        password.type = "password";
      }
    } 
  }
}

export function handleLoginFormSubmit(event) {
  event.preventDefault();
  const form = event.target;
  const username = form.username.value;
  const password = form.password.value;
  attemptLogin(username, password);
}

let attemptLogin = (attemptUsername, attemptPassword) => {
  const xhr = new XMLHttpRequest();
  xhr.open("POST", "/api/auth/login");
  xhr.setRequestHeader("Content-type", "application/json");
  xhr.onreadystatechange = () => {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        sessionStorage.setItem("auth", xhr.responseText);
        routeTo("/");
      } else if (xhr.status === 401) {
        displayLoginError("Incorrect username/password.");
      } else {
        displayLoginError("Something went wrong...");
      }
    }
  };
  const body = {
    username: attemptUsername,
    password: attemptPassword,
  };
  const json = JSON.stringify(body);
  xhr.send(json);
};

let displayLoginError = (msg) => {
  document.getElementById("errorDisplay").textContent = msg;
};
