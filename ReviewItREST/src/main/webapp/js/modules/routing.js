import { activateHomeView } from "./home.js";

export function routeApplication(viewName = "home", params = null) {
  setTimeout(() => {
    switch (viewName) {
      case "home":
        activateHomeView();
        break;
    }
  }, 0);
}

