import { makeAjaxRequest } from "./requests.js";

const baseHomeTemplate = `
    <div id="homeFeed" class="container row">
    <div class="col-1"></div>
    <div id="leftSideBar" class="col-3"></div>
    <div id="mainFeed" class="col-5"></div>
    <div class="col-3"></div>
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
  for (let product of products) {
    mainFeed.innerHTML += formatProduct(product);
  }
};

let formatProduct = (product) => {
  return `
    <div id="productList">
        <h2>${product.name}</h2>
    </div>
    `;
};

