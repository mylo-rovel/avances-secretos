<script>
export default {
  name: 'CustomButton',
  props: ["text","custombcolor","customhcolor"]
}

</script>
<template>
    <button class="custom-button" :style="{'--custombcolor':custombcolor, '--customhcolor':customhcolor}" role="button">{{text}}</button>
</template>


<style>

  .custom-button {
    appearance: none;
    backface-visibility: hidden;
    background-color: var(--custombcolor);
    border-radius: 8px;
    border-style: none;
    box-shadow: rgba(var(--customhcolor), .15) 0 4px 9px;
    box-sizing: border-box;
    color: #fff;
    cursor: pointer;
    display: inline-block;
    font-family: Inter,-apple-system,system-ui,"Segoe UI",Helvetica,Arial,sans-serif;
    font-size: 16px;
    font-weight: 600;
    letter-spacing: normal;
    line-height: 1.1;
    outline: none;
    overflow: hidden;
    padding: 10px 20px;
    position: relative;
    text-align: center;
    text-decoration: none;
    transform: translate3d(0, 0, 0);
    transition: all .3s;
    user-select: none;
    -webkit-user-select: none;
    touch-action: manipulation;
    vertical-align: top;
    white-space: nowrap;
  }

  .custom-button:hover {
    background-color: var(--customhcolor);;
    opacity: 1;
    transform: translateY(0);
    transition-duration: .35s;
  }

  .custom-button:active {
    transform: translateY(2px);
    transition-duration: .35s;
  }

  .custom-button:hover {
    box-shadow: rgba(var(--customhcolor), .2) 0 6px 12px;
  }
</style>

