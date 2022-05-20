<script lang="ts">

import Vue from 'vue'
import PageHeader from '~/components/PageHeader.vue'



export default Vue.extend({
  name: "MainOperadorPage",
  components: { PageHeader },
  data() {
        return {
          "headerTitle": "Listado de secuencias"
        };
    },
})
</script>