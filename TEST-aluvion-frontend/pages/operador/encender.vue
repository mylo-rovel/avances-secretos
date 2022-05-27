<script>

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'
    import Contenido from '~/components/Contenido.vue'
    import NavbarPag from '~/components/NavbarPag.vue'


    export default Vue.extend({
        name: "encender",
        components: { PageHeader, Contenido, NavbarPag },
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
                    console.log(serverResponse);
                    alert("ERROR. rayos :(")
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

</style>
