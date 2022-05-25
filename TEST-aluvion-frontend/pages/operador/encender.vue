<script>

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'


    export default Vue.extend({
        name: "encender",
        components: { PageHeader },
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
    <section class="main-operador-container">
        <div class="container-header">
            <PageHeader :headerTitle="headerTitle"/>
        </div>
        <div>
            <form :action = "apiURL" method="get">
                <div class="form-group row form-check form-switch">
                    <label for="encender_luz" class=" form-check-label col-sm-4 col-form-label">Lucesita</label>
                    <div class="col-sm-6">
                        <button @click="() => enviarOrdenEncender({$axios})" type="button" class="btn btn-primary">prender</button>
                    </div>
                </div>
            </form>
        </div>
    </section>
</template>
<style>
</style>