<template>
  <q-dialog
      ref="dialog"
      @hide="onDialogHide"
      persistent
      autofocus
      transition-show="slide-up"
      transition-hide="slide-down"
      style="width: 600px"
  >
    <q-card class="q-dialog-plugin" style="width: 600px">
      <q-bar v-if="mode === 'ins'" class="text-white bg-primary">
        <div>{{ txt_lang("newRecord") }}</div>
      </q-bar>
      <q-bar v-if="mode === 'upd'" class="text-white bg-primary">
        <div>{{ txt_lang("editRecord") }}</div>
      </q-bar>

      <q-card-section>
        <!-- typrole -->
        <q-select
            :dense="dense"
            v-model="typerole"
            :model-value="typerole"
            :options="typeroles"
            :label="txt_lang('role2')"
            option-value="id"
            option-label="name"
            map-options
            @update:model-value="fnSelRole"
        />

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
            :disable="validSave()"
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
  props: ["data", "typ", "mode", "dense"],

  data() {
    return {
      form: this.data,
      typeroles: [],
      typerole: this.data.role,
    };
  },

  emits: [
    // REQUIRED
    "ok",
    "hide",
  ],

  methods: {
    txt_lang,
    fnSelRole() {
      this.form.role = this.typerole.id;
      this.form.cmt = this.typerole.cmt;
    },
    validSave() {
      return this.form.role === 0;
    },

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

      let err = false
      const method = this.mode === "ins" ? "insertTypRole" : "updateTypRole";
      this.form.lang = localStorage.getItem("curLang")
      api
          .post(baseURL, {
            method: "typ/" + method,
            params: [this.form],
          })
          .then(
              (response) => {
                err = false
                this.$emit("ok", response.data.result.records[0]);
                notifySuccess(this.$t("success"));
              },
              (error) => {
                //console.log("error.response.data=>>>", error.response.data.error.message)
                err = true
                notifyError(error.response.data.error.message);
              }
          )
          .finally(() => {
            if (!err) this.hide();
          });
    },

    onCancelClick() {
      // we just need to hide the dialog
      this.hide();
    },
  },
  created() {
    api
        .post(baseURL, {
          method: "typ/selectTypRole",
          params: [this.typ, localStorage.getItem("curLang")],
        })
        .then((response) => {
          this.typeroles = response.data.result.records;
          this.typeroles.unshift({id: 0, name: this.$t("notChosen")});
          console.info(this.typeroles)
        })
      .catch((error) => {
        let msg = error.message;
        if (error.response)
          msg = this.$t(error.response.data.error.message);
        notifyError(msg);
      });


  },
};
</script>
