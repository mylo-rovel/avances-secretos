<script lang="ts">
export default {
  name: 'TituloContenido',
  props: ["tituloContenido"]
}
</script>
<template>
    <div class="contenido-vista">
        <br></br>
        <h3>{{tituloContenido}}</h3>
        </div>
    </div>
</template>
<style>
.contenido-vista{
    width: 500px;
    margin: 0 auto;
    padding: 40px 20px 40px 20px;
    text-align: center;
}

</style>
