<script>
    import Vue from 'vue'
    export default {
    name: 'CarouselPag',
    props:["x" ],
    data() {
      return { 
        rolUsuario: "" ,
        linkInicio: "",
        linkPerfil: "",
        linkOpciones: "",
        //#0a132d color del gob
        };
    }
}
</script>
<template>
    <section>
        <div id="carouselImages" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img :src="require('~/assets/img/cerro.png?url')" class="d-block w-100" alt="cerro">
                </div>
                <div class="carousel-item">
                    <img :src="require('~/assets/img/cerro.png?url')" class="d-block w-100" alt="cerro">
                </div>
                <div class="carousel-item">
                    <img :src="require('~/assets/img/cerro.png?url')" class="d-block w-100" alt="cerro">
                </div>
            </div>
        </div>
    </section>
</template>
<style>
</style>