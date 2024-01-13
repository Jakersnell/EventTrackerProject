export function makeAjaxRequest(options) {
  const xhr = new XMLHttpRequest();
  let url = "api/" + options.url + formatUrlParams(options.params);
  xhr.open(options.method, url);
  xhr.onreadystatechange = () => {
    if (xhr.readyState === 4) {
      if (xhr.status === options.expectedStatus) {
        const parsedJson = JSON.parse(xhr.responseText);
        setTimeout(() => {
          options.success(parsedJson);
        }, 0);
      } else {
        if (options.error) {
          options.error(xhr.status);
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
    xhr.send(options.body);
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
  const auth = sessionStorage.getItem("auth");
  if (auth) {
    const prefix = params ? "" : "?";
    parsed += `${prefix}auth=${auth}`;
  }
  return parsed;
};
