<template>
    <dialog ref = "dialog">
        <form method = "dialog">
            <input type="text" name = "productCode" readOnly>
            <input type="text" namespace = "Nombre del producto" name = "productName">
            <input type="number" namespace = "Cantidad" name = "productCantidad">
            <input type="submit" value = "ADD" class = "button">
        </form>
        <button @click = "closeDialog" class = "button">Cancelar</button>
    </dialog>
</template>
<script>
    export default {
        name: 'FomProduct',
        methods: {
            openDialog() {
                this.$refs.dialog.showModal();
            },
            closeDialog() {
                this.$refs.dialog.close();
            }
        }
    }
</script>
<style scoped>
    form{
        background-color: #FFF;
        text-color: #000;
        display: flex;
        flex-direction: column;
    }
</style>