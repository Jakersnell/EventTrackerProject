import { getAuthToken, userIsAdmin } from "./login.js";
import { makeAjaxRequest } from "./requests.js";
import { route } from "./routing.js";

const baseHomeTemplate = `
    <div id="homeFeed" class="container row">
      <div class="col-3"></div>
      <div id="leftSideBar" class="col-2"></div>
      <div id="mainFeed" class="col-5"></div>
      <div class="col-2"></div>
    </div>
`;

export function activateHomeView() {
  const content = document.getElementById("contentWrap");
  content.innerHTML = baseHomeTemplate;
  setTimeout(loadProducts, 0);
}

let loadProducts = () => {
  makeAjaxRequest({
    method: "GET",
    url: "products",
    expectedStatus: 200,
    success: displayProducts,
  });
};

let displayProducts = (products) => {
  const mainFeed = document.getElementById("mainFeed");
  while (mainFeed.firstElementChild) {
    mainFeed.removeChild(mainFeed.firstElementChild);
  }

  for (let product of products) {
    setTimeout(() => {
      mainFeed.appendChild(createProductListView(product));
      if (product !== products[products.length - 1]) {
        const lineBreak = document.createElement("br");
        mainFeed.appendChild(lineBreak);
      }
    }, 0);
  }
};

let createProductListView = (product) => {
  const clickableProduct = document.createElement("div");
  clickableProduct.classList.add("product-listing");

  clickableProduct.innerHTML = `
  <h2>${product.name}</h2>
  <hr>
  <h4>${product.description}</h4>
  `;

  clickableProduct.addEventListener("click", (e) => {
    route("product", { id: product.id });
  });

  return clickableProduct;
};
