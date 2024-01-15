import { makeAjaxRequest } from "./requests.js";
import { route } from "./routing.js";
import { displayPopupMsg } from "./util.js";

const productFormTemplate = `
    <div align="center">
        <br>
        <br>
        <br>
        <div class="col-4"></div>
        <div class="col-4">
            <form name="productForm">
                <div class="row">
                    <input class="form-control" name="name" type="text" placeholder="Product name" max="100" required />
                </div>
                <br>
                <div class="row">
                    <textarea class="form-control" name="description" rows="4" cols="50" 
                        placeholder="Product description" max="500" required></textarea>
                </div>
                <br>
                <div class="row">
                    <button class="form-control btn btn-primary" name="submit">Submit</button>
                </div>
            </form>
        </div>
        <div class="col-4"></div>
    </div>
`;

export function activateProductForm() {
  const content = document.getElementById("contentWrap");
  content.innerHTML = productFormTemplate;
  setTimeout(() => initForm(), 0);
}

let initForm = () => {
  const form = document.productForm;
  form.addEventListener("submit", handleFormSubmission);
};

let handleFormSubmission = (event) => {
  console.log('in handleFormSubmision');
  event.preventDefault();
  const form = document.productForm;
  const formData = {
    name: form.name.value,
    description: form.description.value,
  };
  sendFormSubmission(formData);
};

let sendFormSubmission = (formData) => {
  makeAjaxRequest({
    method: "POST",
    url: "products",
    expectedStatus: 200,
    body: formData,
    success: (product) => {
      route("product", product);
      displayPopupMsg("Product created!");
    },
    error: (status) => {
      switch (status) {
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
};
