<template>
  <main>
    <div>
      <ButtonShowProduct />
    </div>
    <TableProduct />
  </main>
</template>

<script>
import TableProduct from './components/TableProduct.vue'
import ButtonShowProduct from './components/ButtonShowProduct.vue'

export default {
  name: 'App',
  components: {
    TableProduct,
    ButtonShowProduct
  }
};
</script>

<style scoped>
/* Aquí puedes poner estilos globales si quieres */
</style>
