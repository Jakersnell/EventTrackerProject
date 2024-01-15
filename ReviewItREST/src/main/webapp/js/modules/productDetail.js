import { makeAjaxRequest } from "./requests.js";
import { route } from "./routing.js";
import { userIsAdmin } from "./login.js";
import { displayPopupMsg } from "./util.js";

const productTemplate = `
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6" id="adminDisplay"></div>
      <div class="col-3"></div>
    </div>
    <div class="row">
        <div class="col-3"></div>
        <div class="col-2">
          <div id="categories">
            <h3>Categories: </h3>
          </div>
        </div>
        <div class="col-1"></div>
        <div id="product" class="col-5"></div>
        <div class="col-2"></div>
    </div>
    <div class="row mb-2 mt-2" id="adminControls">
      <div class="col-3"></div>
      <div class="col-3">
        <button class="btn btn-primary form-control" id="adminDeleteBtn">Delete</button>
      </div>
      <div class="col-3">
        <button class="btn btn-primary form-control" id="adminEditBtn">Edit</button>
      </div>
      <div class="col-3"></div>
      </div>
    </div>
    <div class="row">
        <div class="col-3"></div>
        <div class="col-7" id="reviews"></div>
        <div class="col-2"></div>
    </div>
`;

export function activateProductDetailView(id) {
  const content = document.getElementById("contentWrap");
  content.innerHTML = productTemplate;
  setTimeout(() => {
    loadProductDetail(id);
  }, 0);
}

let loadProductDetail = (id) => {
  makeAjaxRequest({
    method: "GET",
    url: `products/${id}`,
    expectedStatus: 200,
    success: (product) => {
      displayProductDetail(product);
      initAdminFunctions(product);
      setTimeout(() => loadProductCategories(id), 0);
    },
  });
};

let initAdminFunctions = (product) => {
  setTimeout(() => {
    const adminControls = document.getElementById("adminControls");
    if (userIsAdmin()) {
      const adminDeleteBtn = document.getElementById("adminDeleteBtn");

      let deleteBtnEventHandler;
      if (!product.enabled) {
        adminDeleteBtn.textContent = "Enable";
        deleteBtnEventHandler = () => {
          enableProduct(product);
        };
      } else {
        adminDeleteBtn.textContent = "Disable";
        deleteBtnEventHandler = () => {
          route("delete-product", { id: product.id });
        };
      }
      adminDeleteBtn.addEventListener("click", deleteBtnEventHandler);

      document.getElementById("adminEditBtn").addEventListener("click", () => {
        route("edit-product", { id: product.id });
      });

      document.getElementById("adminDisplay").innerHTML = `
    `;
    } else {
      adminControls.remove();
    }
  }, 0);
};

let displayProductDetail = (product) => {
  const productDiv = document.getElementById("product");
  productDiv.innerHTML = `
  <h2>${product.name}</h2>
  <blockquote>${product.description}</blockquote>
  `;
  if (product.averageRating !== null) {
    productDiv.innerHTML += `
      <h3>Average rating: ${product.averageRating}</h3>
    `;
  } else {
    productDiv.innerHTML += `
    <h3>This product has no ratings.</h3>
    `;
  }
};

let loadProductCategories = (id) => {
  makeAjaxRequest({
    method: "GET",
    url: `products/${id}/categories`,
    expectedStatus: 200,
    success: (categories) => {
      displayProductCategories(categories);
      setTimeout(loadProductReviews(id), 0);
    },
  });
};

let displayProductCategories = (categories) => {
  const catDiv = document.getElementById("categories");
  for (let cat of categories) {
    catDiv.innerHTML += `
    <div class="product-category text-center">
      <h3>${cat.name}</h3>
    </div>
    `;
  }
};

let loadProductReviews = (id) => {
  makeAjaxRequest({
    method: "GET",
    url: `products/${id}/reviews`,
    expectedStatus: 200,
    success: displayProductReviews,
  });
};

let displayProductReviews = (reviews) => {
  const div = document.getElementById("reviews");
  for (let review of reviews) {
    setTimeout(() => {
      div.appendChild(formatReview(review));
    }, 0);
  }
};

let formatReview = (review) => {
  const div = document.createElement("div");
  div.innerHTML = `
  <h3>Rating: ${review.rating}</h3>
  <blockquote>
    ${review.content}
  </blockquote>
  `;
  return div;
};

export function deleteProduct(productId, routeTo) {
  makeAjaxRequest({
    method: "DELETE",
    url: `products/${productId}`,
    expectedStatus: 204,
    success: () => {
      displayPopupMsg("Product successfully deleted!");
      route('product', {id: productId});
    },
    error: (status) => {
      switch (status) {
        case 404:
          displayPopupMsg(`The product with id ${productId} does not exist.`);
          break;
        case 401:
          route("login");
          displayPopupMsg("Your session has expired.");
          break;
        default:
          displayPopupMsg("Something went wrong...");
          break;
      }
    },
  });
}

let enableProduct = (product) => {
  product.enabled = true;
  console.log(JSON.stringify(product));
  setTimeout(() => {
    makeAjaxRequest({
      method: "PUT",
      url: `products/${product.id}`,
      expectedStatus: 200,
      body: product,
      success: () => {
        route("product", { id: product.id });
        displayPopupMsg(`Product successfully enabled!`);
      },
      error: (status) => {
        switch (status) {
          case 404:
            displayPopupMsg(
              `The product with id ${product.id} does not exist.`
            );
            break;
          case 401:
            route("login");
            displayPopupMsg("Your session has expired.");
            break;
          default:
            displayPopupMsg("Something went wrong...");
            break;
        }
      },
    });
  }, 0);
};
