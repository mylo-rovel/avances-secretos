<script>
export default {
  name: 'CancelButton',
  props: ["cancelbutton"]
}

</script>

<template>
    <button class=" btn cancel-button">{{cancelbutton}}</button>
</template>

<style>
  .cancel-button {
      background: #C70039;
      padding: 10px 20px 10px 20px;
      font-size: 20px;
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