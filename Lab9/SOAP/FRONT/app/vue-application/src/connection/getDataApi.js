import peticionFetch from "../connection/pointfetch.js";

function getXMLFormat(content){
  const formatFetch = `
  <?xml version="1.0" encoding="UTF-8"?>
  <soapenv:Envelope 
  xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
  xmlns:lab="http://store.com/">
  <soapenv:Header/>
  <soapenv:Body>
    ${content}
  </soapenv:Body>
  </soapenv:Envelope>`;
  return formatFetch;
}

function getDataTable(name){
  let formatFetch = `
    <lab:getDataTable>
      <tabla>${name}</tabla>
    </lab:getDataTable>`;
  formatFetch = getXMLFormat(formatFetch);
  return peticionFetch(formatFetch);
}

function insertDataTable(tabla, person){
  let formatFetch = ``;
  formatFetch = getXMLFormat(formatFetch);
  return peticionFetch(formatFetch);
}
