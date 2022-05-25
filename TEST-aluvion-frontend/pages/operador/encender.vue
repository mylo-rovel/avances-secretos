<script>

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'


    export default Vue.extend({
        name: "encender",
        components: { PageHeader },
        data() {
            return {
                "headerTitle": "Encender lucesita",
                "apiURL": "http://localhost:3001/api/"

            };
        },
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
                        <button type="button" class="btn btn-primary">prender</button>
                    </div>
                </div>
            </form>
        </div>
    </section>
</template>
<style>
</style>