<script>
export default {
  name: 'InputBoxComponent',
  props:["textoTransparente", "idValue"],
  data() {
      return {
          inputValue : "",
      }
  },
}

</script>

<template>
    <input class="input-box-component" :placeholder="textoTransparente" type="text" :id="idValue">
</template>

<style>

.input-box-component {
    text-align: center;
    border: 0;
    border-top: 1px solid black;
    border-bottom: 1px solid black;
    height: 2rem;
    width: 100%;
    font-weight: bold;
    font-size: 1.3rem;
}

</style>
