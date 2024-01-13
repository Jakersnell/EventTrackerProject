// function getProducts() {
//     const xhr = new XMLHttpRequest();
//     let url = "api/products" + formatUrlParams(options.params);
  
//     xhr.onreadystatechange = () => {
//       if (xhr.readyState === 4) {
//         if (xhr.status === options.expectedStatus) {
//           const parsedJson = JSON.parse(xhr.responseText);
//           options.success(parsedJson);
//         } else {
//           options.error(xhr.status);
//         }
//       }
//     };
  
//     if (options.body) {
//       xhr.send(options.body);
//     } else {
//       xhr.send();
//     }
//   }
// }
