<script>

    import Vue from 'vue'
    // import PageHeader from '~/components/PageHeader.vue'


    export default Vue.extend({
        name: "PaginaEncender",
        // components: { PageHeader },
        data() {
            return {
                "headerTitle": "Encender lucesita",
                "apiURL": "http://localhost:8080/api"

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
    <section class="main-operador-container">
        <!--
        <div class="container-header">
            <PageHeader :headerTitle="headerTitle"/>
        </div>
        <div>
            <form :action="apiURL" method="get">
                <div class="form-group row form-check form-switch">
                    <label for="encender_luz" class=" form-check-label col-sm-4 col-form-label">Lucesita</label>
                    <div class="col-sm-6">
                        <button type="button" class="btn btn-primary" @click="() => enviarOrdenEncender({$axios})">prender</button>
                    </div>
                </div>
            </form>
        </div>
        -->
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
        </article>
    </section>
</template>


<style>
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


.body-section {
    margin: 0 auto;
    margin-top: 2rem;
    text-align: center;
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