<template>
  <div>
    <button @click = "getProductosDesdeSOAP">UPDATE</button>
    <h2>Lista de Productos</h2>
    <table border="1">
      <thead>
        <tr>
          <th>Código</th>
          <th>Nombre</th>
          <th>Cantidad</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="producto in productos" :key="producto.code">
          <td>{{ producto.code }}</td>
          <td>{{ producto.name }}</td>
          <td>{{ producto.cantidad }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      productos: []
    };
  },
  methods: {
    async getProductosDesdeSOAP() {
      
      const urlStore = "http://localhost:8080/soap/store";

      console.log("llamando el Servicio");

      const soapBody = `<?xml version="1.0" encoding="UTF-8"?>
        <soapenv:Envelope 
          xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
          xmlns:lab="http://soap.labsdsoap.com/">
          <soapenv:Header/>
          <soapenv:Body>
            <lab:getMeAllProductos/>
          </soapenv:Body>
        </soapenv:Envelope>`;
      
      try {
        const response = await fetch(urlStore, {
          method: "POST",
          headers: {
            "Content-Type": "text/xml;charset=UTF-8",
            "SOAPAction": ""
          },
          body: soapBody
        });
        

        const text = await response.text();
        console.log(text);
        const parser = new DOMParser();
        const xml = parser.parseFromString(text, "text/xml");

        const returns = xml.getElementsByTagName("return");
        const productos = [];

        for (let i = 0; i < returns.length; i++) {
          const item = returns[i];
          productos.push({
            code: item.getElementsByTagName("code")[0].textContent,
            name: item.getElementsByTagName("name")[0].textContent,
            cantidad: item.getElementsByTagName("cantidad")[0].textContent
          });
        }

        this.productos = productos;
      } catch (error) {
        console.error("Error al obtener productos:", error);
      }
    }
  }
}
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
  background-color: #ffffff;
  color: #000000;
}

th, td {
  padding: 10px;
  text-align: left;
  border: 1px solid #cccccc;
}

th {
  background-color: #f2f2f2;
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}

tr:hover {
  background-color: #e6e6e6;
}
</style>
