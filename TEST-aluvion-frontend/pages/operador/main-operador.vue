<script lang="ts">
// ESTE ES LA PAGINA LOGIN
import Vue from 'vue'
import PageHeader from '~/components/PageHeader.vue'



export default Vue.extend({
  name: "MainOperadorPage",
  components: { PageHeader },
  data() {
        return {
          "headerTitle": "Simulación de eventos"
        };
    },
})
</script>


<template>
  <section class="main-operador-container">
    <article class="container-header">
        <PageHeader :headerTitle="headerTitle"/>
    </article>
    <article class="container-body-button-menu">
      <NuxtLink to="/operador/secuencia"> <button class="common-menu-button">Secuencias de lluvia</button> </NuxtLink>
      <NuxtLink to="/operador/simulacion"> <button class="common-menu-button">Simulación</button></NuxtLink>
      
    </article>
  </section>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap');

* {
  margin:0;
  font-family: 'Poppins', sans-serif;
}
.main-operador-container {
    width: 100vw;
    height: 100vh;
    display:grid;
    grid-template-rows: 35% auto;
}

.container-body-button-menu {
  display: flex;
  flex-direction: column;
  width: fit-content;
  margin: 0 auto;
}

.common-menu-button {
  background-color: white;
  border: 1px solid black;
  border-radius: 50px;
  padding: 1rem 3rem;
  margin: 20px 0;
  font-size: 2rem;
  font-weight: 300;
}

.common-menu-button:hover {
  background-color: black;
  color: white;
  transform: scale(1.1);
}

</style>
