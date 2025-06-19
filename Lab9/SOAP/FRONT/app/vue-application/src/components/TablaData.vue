<script>
import { ref, onMounted } from 'vue';

export default {
  setup() {
    const columnas = ref([])  // Define columnas as a reactive reference
    const registros = ref([])  // Define registros as a reactive reference

    function deleteRegistro(event){
      event.preventDefault();
      let id = event.target.id.value;
      let url = 'http://localhost:8080/Departamentos'
      fetch(url,{
        method: 'DELETE',
        headers: {
          'Content-Type':'text/plain;charset=UTF-8'
        },
        body: id+'-'
      })
      .then(res => res.text())
      .then(ans => console.info(ans))
      .catch(e => console.error(e));
    }

    onMounted(async () => {
      const url = 'http://localhost:8080/Departamentos'
        const xmlResponse = await fetch(url + '?tipo=data', {
          method: 'GET',
          headers: {
            'Content-type': 'text/xml;charset=UTF-8;'
          }
        })

        const xmlString = await xmlResponse.text()  // Get the response text
        const xml = new DOMParser().parseFromString(xmlString, "text/xml")
        const returns = xml.getElementsByTagName("return")
        columnas.value = Array.from(returns).map(el => el.textContent)

        const xmltabla = await fetch(url + '?tipo=datos', {
          method: 'GET',
          headers: {
            'Content-type': 'text/xml;charset=UTF-8;'
          }
        })

        const xmlDataTable = await xmltabla.text()  // Get the response text
        const xmlTable = new DOMParser().parseFromString(xmlDataTable, "text/xml")
        const returnsTable = xmlTable.querySelectorAll("return")
        // Extract registros
        returnsTable.forEach(returnElement => {
          const campos = returnElement.querySelectorAll("campo")
          const registro = []
          campos.forEach(campo => {
            const nombre = campo.querySelector("nombre").textContent
            const valor = campo.querySelector("valor").textContent
            registro.push(valor)
          })
          registros.value.push(registro)
        })
        console.log(registros.value)
    })

    return {
      columnas,
      registros,
      deleteRegistro  // Return registros to make it accessible in the template
    }
  }
}
</script>
<template>
    <table>
      <tr>
        <th v-for="columna of columnas" :key="columna">{{columna}}</th>
      </tr>
      <tr v-for="datacolumn of registros" :key="datacolumn">
        <td v-for="registroCol of datacolumn" :key="registroCol">{{registroCol}}</td>
        <td class = "buttons">
          <form @submit="deleteRegistro">
            <input type="hidden" name="id" :value="datacolumn[0]"/>
            <button type="submit" class = "delete">Delete</button>
          </form>
        </td>
      </tr>
    </table>
</template>
<style scoped>
.buttons{
  display:flex;
  gap: 2em;
  height: 100%;
}
.delete{
  color: #FFF;
  background-color: #F02;
  &:hover{
    color: #F02;
    outline-color: #F02;
  }
}
button{
  text-transform: uppercase;
  font-family: sans-serif;
  font-weight: bold;
  border-radius: .3em;
  border: none;
  padding: 1em 2em;
}
button:hover{
  background-color: transparent;
  outline: 2px solid #000;
}
table{
  color: #000;
  width: 50%;
  border-collapse:separate;
  background-color: #ffffff;
  border-radius: 1em;
  box-shadow: 0 0 1em rgba(0,0,0,0.1);
  font-size: 1em;
  padding: 2em 5em;
  th{
    text-transform: uppercase;
    background-color: #fdfdfd;
    font-family: sans-serif;
    padding: 1em;
  }
  td{
    text-align: center;
    padding: .5em 1.3em;
    font-family: 'Courier New';
  }
}
</style>
