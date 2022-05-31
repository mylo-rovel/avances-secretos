<script>
export default {
  name: 'SubmitButton',
  props: ["textbutton"]
}
// LA IDEA ES QUE ESTE COMPONENTE RECIBA ESTILOS COMO PROPS
// PARA HACERLO MÁS CUSTOMIZABLE
</script>

<template>
    <button class=" btn submit-button">{{textbutton}}</button>
</template>

<style>
  .submit-button {
      background: #6675df;
      padding: 10px 20px 10px 20px;
      font-size: 20px;
      color: #fff;
      justify-content: center;
      text-transform: uppercase;
      font-weight: bold;

  }
</style>
