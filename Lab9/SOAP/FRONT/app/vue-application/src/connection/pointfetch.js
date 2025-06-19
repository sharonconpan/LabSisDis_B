function peticionFetch(body){
  const url = "http://localhost:8080/hello";
  const data = fetch(url, {
    method: 'POST',
    headers: {
      "Content-Type": 'text/xml; charset = "UTF-8"',
      "SOAPAction": ""
    },
    body: body
  })
  .then(rest => rest.text())
  .catch(error => console.error(error));
  let dataXML = new window.DOMParser().parseFromString(data, "text/xml");
  return dataXML;
}

export peticionFetch;
