<script>

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'
    import Contenido from '~/components/Contenido.vue'
    import NavbarPag from '~/components/NavbarPag.vue'

    export default Vue.extend({
        name: "PaginaEncender",
        // components: { PageHeader },
        data() {
            return {
                "headerTitle": "Encender lucesita",
                "apiURL": "http://localhost:8080/api",
                "tituloContenido": "Iniciar Sistema",
                "tituloPag": "Encender sistema"
            };
        },
        methods: {
        async enviarOrdenEncender({$axios}) {
            const serverPath = `${this.apiURL}/leddeldestino`;
            const serverResponse = await $axios.$get(serverPath).catch(err => err);
            if (serverResponse instanceof Error) {
                alert("ERROR. rayos =(", serverResponse)
                return false;
            }
            alert(serverResponse);
            return true;
        },
        }
    })
</script>

<template>
    <section>
        <div>
            <NavbarPag :tituloPag="tituloPag"/>
        </div>
        <div class="container-header">
            <PageHeader :headerTitle="headerTitle"/>
        </div>
        <div class="main-operador-container">
            <div class="container">
                <div class="contenido1">
                    <div class="col">
                        <Contenido :tituloContenido="tituloContenido"/>
                    </div>
                    <div class="col">
                        <div class="contenido-vista">
                            <form :action = "apiURL" method="get">
                                <div class="boton-encender form-group">
                                    <button @click="() => enviarOrdenEncender({$axios})" type="button" class="btn btn-primary btn-lg">ENCENDER</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--
        <article class="header-section">
            <div class="header-logo-section">
                <img class="common-anid-logo" src="~/assets/img/anid-logo.png" alt="conicyt-logo">
                <article class="header-logo-section-text">
                    <p>FONDEF</p>
                    <p>Fondo de Fomento al Desarrollo</p>
                    <p>Cientifico y Tecnológico</p>
                </article>
            </div>
            <div class="header-title-section">
                <p class="header-title-section-title">{{headerTitle}}</p>
            </div>
        </article>

        <article class="body-section">
            <div class="main-button-section">
                <p>Encender LED</p>
                <button @click="() => enviarOrdenEncender({$axios})" >PRENDER</button>
            </div>
        </article>-->
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

    .boton-encender{
        padding: 100px, 100px;
        text-align: center;
    }
    .contenido1{
        width: 500px;
        margin: 0 auto;
        padding: 20px 20px 20px 20px;
    }
    /* IMPORTANTE PARA QUE EL CONTENEDOR PRINCIPAL OCUPE TODO EL ESPACIO */
    .main-operador-container{
        height: 100%;
        width: 100%;
    }

    .header-section{
        height: fit-content;
    }

    /* TOP RIGHT PART OF THE HEADER */
    .header-logo-section {
        /* background-color: red; */
        display: flex;
        height: fit-content;
    }
    .header-logo-section-text {
        width: fit-content;
        font-size: 0.85rem;
        margin-top: auto;
        padding-left: 5px;
    }
    .header-logo-section-text p:first-child {
        font-size: 1rem;
    }
    .header-logo-section-text p {
        margin-bottom: 2px;
        margin-left: 2px;
    }

    .common-anid-logo { 
        min-height: 10rem;
        min-width: 10rem;
        width: 30vw;
        height: 20vh;
    }
    /* TOP RIGHT PART OF THE HEADER FINAL */

    .header-title-section {
        margin: 0 auto;
        margin-top: 1.2rem;
        border-bottom: 1px solid black;
        width: 90%;
    }

    .header-title-section-title {
        width: fit-content;
        margin: 0 auto;
        font-size: 1.5rem;
    }

    .main-button-section p,
    .main-button-section button{
        font-size: 1.2rem;
        font-weight: 800;
    }

    .main-button-section button {
        border: 0;
        border-radius: 50px;
        padding: 0.5rem 1.5rem;
        border: 1px solid black;
    }

    .main-button-section button:hover {
        background-color: black;
        color: white;
        transform: scale(1.5);
    }

</style>

