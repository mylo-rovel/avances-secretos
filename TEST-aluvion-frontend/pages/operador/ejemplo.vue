<script>

import Vue from 'vue'

import SubmitButton from '~/components/SubmitButton.vue'
import CancelButton from '~/components/CancelButtom.vue'


export default Vue.extend({
  name: "Ejemplo",
  
  data() {
        return {
          "textbutton": "enviar",
          "cancelbutton": "cancelar"
        };
    },
})
</script>
<template>
    <section>
        <div class="row">
            <div class="col">
                <SubmitButton :textbutton = "textbutton"/>
            </div>
            <div class="col">
                <CancelButtom :cancelbutton = "cancelbutton"/>
            </div>
        </div>
    </section>
</template>