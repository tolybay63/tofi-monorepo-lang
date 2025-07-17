<template>
  <q-dialog
      ref="dialog"
      @hide="onDialogHide"
      persistent
      autofocus
      transition-show="slide-up"
      transition-hide="slide-down"
  >
    <q-card class="q-dialog-plugin" style="width: 600px">
      <q-bar v-if="mode === 'ins'" class="text-white bg-primary">
        <div>{{ txt_lang("newRecord") }}</div>
      </q-bar>
      <q-bar v-if="mode === 'upd'" class="text-white bg-primary">
        <div>{{ txt_lang("editRecord") }}</div>
      </q-bar>

      <q-card-section>
        <!-- dbeg-->
        <q-input
            :dense="dense"
            type="date"
            stack-label
            :model-value="form.dbeg"
            v-model="form.dbeg"
            :label="txt_lang('fldDbeg')"
        >
        </q-input>
        <!-- dend-->
        <q-input
            :dense="dense"
            type="date"
            stack-label
            :model-value="form.dend"
            v-model="form.dend"
            :label="txt_lang('fldDend')"
        >
        </q-input>

        <!-- cmt -->
        <q-input
            :dense="dense"
            :model-value="form.cmt"
            v-model="form.cmt"
            type="textarea"
            :label="txt_lang('fldCmt')"
        >
        </q-input>
        <!---->
      </q-card-section>

      <q-card-actions align="right">
        <q-btn
            :dense="dense"
            color="primary"
            icon="save"
            :label="txt_lang('save')"
            @click="onOKClick"
        />
        <q-btn
            :dense="dense"
            color="primary"
            icon="cancel"
            :label="txt_lang('cancel')"
            @click="onCancelClick"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import {api, baseURL} from "boot/axios";
import {notifyError, notifySuccess, txt_lang} from "src/utils/jsutils";

export default {
  props: ["data", "mode", "dense"],

  data() {
    return {
      form: this.data,
    };
  },

  emits: [
    // REQUIRED
    "ok",
    "hide",
  ],

  methods: {
    txt_lang,
    // following method is REQUIRED
    // (don't change its name --> "show")
    show() {
      this.$refs.dialog.show();
    },

    // following method is REQUIRED
    // (don't change its name --> "hide")
    hide() {
      this.$refs.dialog.hide();
    },

    onDialogHide() {
      // required to be emitted
      // when QDialog emits "hide" event
      this.$emit("hide");
    },

    onOKClick() {
      // on OK, it is REQUIRED to
      // emit "ok" event (with optional payload)
      // before hiding the QDialog

      //delete this.form.accessLevel_text
      //console.log("al:", this.al)
      //console.log("this.form:", this.form)

      const method =
          this.mode === "ins" ? "insertRelTypRoleLife" : "updateRelTypRoleLife";
      this.form.lang = localStorage.getItem("curLang");
      api
          .post(baseURL, {
            id: this.form.id,
            method: "reltyp/" + method,
            params: [{rec: this.form}],
          })
          .then(
              (response) => {
                this.$emit("ok", response.data.result.records[0]);
                notifySuccess(this.$t("success"));
              },
              (error) => {
                //console.log("error.response.data=>>>", error.response.data.error.message)
                notifyError(error.response.data.error.message);
              }
          )
          .finally(() => {
            this.hide();
          });
    },

    onCancelClick() {
      // we just need to hide the dialog
      this.hide();
    },
  },
  created() {
  },
};
</script>
