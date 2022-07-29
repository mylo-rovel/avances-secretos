<script>
  import {mapState, mapMutations} from "vuex";

  export default {
    name: 'SecuenciaButton',
    props:["nombreSecuencia", "posicionSecuencia"],
    data(){
      return {
        "nombreSecuencia": "nombreSecuencia",
      
      }
    },
    methods: {
      ...mapMutations(["setSecuenciaModificar"]),
      getValvulas(){
        this.setSecuenciaModificar(posicionSecuencia);
      }
    }
  }
</script>

<template>
  <section>
    <button class="btn-secuencias form-control">{{nombreSecuencia+" "+(posicionSecuencia+1)}}</button></NuxtLink>
    
  </section>
</template>

<style>
  .btn-secuencias{
    background-color: #ecf0f1;
    border: 1px solid #d0d3d4;
    color: black;
    padding: 0.25rem 1.5rem;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 1rem;
    margin: 0;
    cursor: pointer;
    border-radius: 10px;
  }

  .btn-secuencias:hover{
    background: #ecf0f1;
    border: 1px solid #d0d3d4;
    color: black;
    transform: scale(1.1);
  }
  .conf-secuencias{
        margin: 0 auto;
        padding-left: 4rem;
  }  


</style>
