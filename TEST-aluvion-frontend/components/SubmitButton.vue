<script lang="ts">
export default {
  name: 'SubmitButtonComponent',
  props: ["textbutton"]
}
// LA IDEA ES QUE ESTE COMPONENTE RECIBA ESTILOS COMO PROPS
// PARA HACERLO MÁS CUSTOMIZABLE



</script>

<template>
    <button class="submit-button-component">{{textbutton}}</button>
</template>

<style>

.submit-button-component {
    background-color: rgb(195, 152, 236);
    padding: 0.8rem 5rem;
    border-radius: 5rem;
    border:0;
    font-size: 1.15rem;
}

</style>
