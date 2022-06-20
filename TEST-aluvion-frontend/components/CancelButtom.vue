<script>
export default {
  name: 'CancelButton',
  props: ["cancelbutton"]
}

</script>

<template>
    <button class="cancel-button">{{cancelbutton}}</button>
</template>

<style>
  .cancel-button {
      background: #C70039;
      padding: 1rem 1.25rem 1rem 1.25rem;
      font-size: 1rem;
      color: #fff;
      justify-content: center;
      text-transform: uppercase;
      font-weight: bold;
      border-radius: 3rem;
  }
  .cancel-button:hover {
        background: white;
        color: #C70039;
        border: 1px solid #C70039;
        transform: scale(1.1);
    }
</style>