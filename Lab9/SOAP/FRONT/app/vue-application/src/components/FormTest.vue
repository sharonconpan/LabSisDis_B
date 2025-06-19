<template>
    <div id = "containerTest">
        <p>TEST SQL-AUTOMATE</p>
        <form id="testRoll" @submit="sqlRequest">
            <div id = "containerInput" v-for="(input, index) in inputs" :key="index">
            <input type="text" name="sql" placeholder="Ingresa SQL"/>
            </div>
        </form>
        <button type="button" @click="addInput">ADD</button>
        <input type="submit" value="EXECUTE" form="testRoll" />
        <p>ESTADO : {{message}}</p>
    </div>
</template>

<script setup>
import { ref } from 'vue'

const inputs = ref([''])
const dataSQL = ref("Ingresa SQL")
const message = ref('')

function addInput() {
  inputs.value.push('')
}

async function sqlRequest(event){
    event.preventDefault()
    let inputsText = event.target.querySelectorAll("input[type='text']")
    let data = ''
    inputsText.forEach(intp => data += `${intp.value}-`)
    const urlStore = "http://localhost:8080/Departamentos?tipo=test";
    console.log(data);
    let mensaje = await fetch(urlStore, {
    method: "POST",
    headers: {
        "Content-Type": "text/plain;charset=UTF-8",
    },
    body: data
    })
    .then(response => response.text())
    .catch(console.error);
    mensaje = new DOMParser().parseFromString(mensaje, "text/xml")
    message.value = mensaje.querySelector("return").textContent;
}
</script>

<style scoped>
#containerTest {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1em;
  color: #000;
  padding: 2em;
  border-radius: 1.2em;
  outline: 5px solid #fff;
  border: 4px solid #000;
  box-shadow: 0 0 10em #aaa8;
  background-color: #fff;
}

input[type="text"] {
  padding: 0.8em;
  margin: .4em 0;
  width: max-content;
  font-size: 1.1em;
  border-radius: 1.2em / 50%;
}

button,
input[type="submit"] {
  width: min-content;
  padding: 1.2em 2em;
  font-weight: bold;
  font-family: sans-serif;
  border: none;
  border-radius: 0.5em;
  transition: 0.5s;
}

/* Botón normal */
button {
  color: #fff;
  background-color: #0f6;
  outline: 2px solid #0f6;
}

/* Hover para el botón */
button:hover {
  background-color: transparent;
  color: #0F6;
}

/* Botón de submit */
input[type="submit"] {
  color: #eee;
  background-color: #AAA;
  outline: 2px solid #AAA;
}

/* Hover para el botón de submit */
input[type="submit"]:hover {
    background-color: transparent;
  outline-color: #09f;
  color: #09f;
}

</style>