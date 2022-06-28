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
      padding: 0.5rem 1.25rem 0.5rem 1.25rem;
      font-size: 1rem;
      color: #fff;
      justify-content: center;
      text-transform: uppercase;
      font-weight: bold;
      border-radius: 10px;
  }
  .cancel-button:hover {
        background: white;
        color: #C70039;
        border: 1px solid #C70039;
        transform: scale(1.1);
    }
</style>