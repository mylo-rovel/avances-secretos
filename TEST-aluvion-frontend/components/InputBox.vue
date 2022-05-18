<script lang="ts">
export default {
  name: 'InputBoxComponent',
  props:["textinput"],
  data() {
      return {
          message:"hello"
      }
  }
}

</script>

<template>
    <input class="input-box-component" :placeholder="textinput" type="text">
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
