<template>
  <div
      class="fullscreen bg-blue text-white text-center q-pa-md flex flex-center"
  >
    <div>
      <div style="font-size: 30vh">404</div>

      <div class="text-h2" style="opacity: 0.4">{{ txt_lang("nothing_here") }}</div>

      <q-btn
          class="q-mt-xl"
          color="white"
          text-color="blue"
          unelevated
          to="/"
          :label="txt_lang('home')"
          no-caps
      />
    </div>
  </div>
</template>

<script>
import {defineComponent} from "vue";
import {txt_lang} from "src/utils/jsutils.js";

export default defineComponent({
  name: "ErrorNotFound",
  methods: {txt_lang},
});
</script>
