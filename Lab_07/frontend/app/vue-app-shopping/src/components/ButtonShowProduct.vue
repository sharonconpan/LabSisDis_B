<template>
  <div>
    <div class="button" @click="showFormProduct">
      <p>Add Product</p>
    </div>
    <FomProduct ref="formProduct" />
  </div>
</template>

<script>
import FomProduct from './FomProduct.vue'

export default {
  name: 'ButtonShowProduct',
  components: {
    FomProduct
  },
  methods: {
    showFormProduct() {
      this.$refs.formProduct.openDialog();
    }
  }
}
</script>

<style scoped>
.button {
  background-color: rgb(26, 218, 106);
  color: #000;
  font-family: sans-serif;
  width: 29%;
  padding: 1em;
  text-align: center;
  border-radius: 2em;
  text-transform: uppercase;
  transition: 0.3s;
  cursor: pointer;
}
.button:hover {
  transform: scale(1.1);
}
</style>
