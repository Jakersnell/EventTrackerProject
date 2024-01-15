import { getAuthToken, userIsLoggedIn } from "./login.js";

export function makeAjaxRequest(options) {
  const xhr = new XMLHttpRequest();
  let url = "api/" + options.url + formatUrlParams(options.params);
  xhr.open(options.method, url);
  xhr.onreadystatechange = () => {
    if (xhr.readyState === 4) {
      if (xhr.status === options.expectedStatus) {
        const parsedJson = xhr.responseText ? JSON.parse(xhr.responseText) : "";
        setTimeout(() => {
          options.success(parsedJson);
        }, 0);
      } else {
        if (options.error) {
          setTimeout(() => {
            options.error(xhr.status);
          }, 0);
        } else {
          console.log(
            `
            Error while attempting ${options.method} for url '${url}'.
            Status code '${xhr.status}'.
            `
          );
        }
      }
    }
  };

  if (options.body) {
    xhr.setRequestHeader("Content-type", "application/json");
    const jsonData = JSON.stringify(options.body);
    xhr.send(jsonData);
  } else {
    xhr.send();
  }
}

let formatUrlParams = (params) => {
  let parsed = "";
  if (params) {
    parsed +=
      "?" +
      Object.keys(params)
        .map((param) => `${param}=${params[param]}`)
        .join("&");
  }
  if (userIsLoggedIn()) {
    const auth = getAuthToken().token;
    const prefix = params ? "" : "?";
    parsed += `${prefix}auth=${auth}`;
  }
  return parsed;
};
