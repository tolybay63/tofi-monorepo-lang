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
        <!-- name -->
        <q-input
            :dense="dense"
            :model-value="form.name"
            v-model="form.name"
            autofocus
            @blur="onBlurName"
            :label="txt_lang('fldName')"
            :rules="[(val) => (!!val && !!val.trim()) || txt_lang('req')]"
        >
        </q-input>
        <!-- fullName-->
        <q-input
            :dense="dense"
            :model-value="form.fullName"
            v-model="form.fullName"
            :label="txt_lang('fldFullName')"
            :rules="[(val) => (!!val && !!val.trim()) || txt_lang('req')]"
        >
        </q-input>

        <!-- dbeg-->
        <q-input
            :dense="dense"
            type="date"
            stack-label
            :model-value="form.dbeg"
            v-model="form.dbeg"
            :label="txt_lang('fldDbeg')"
            clearable
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
            clearable
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
            :disable="validName()"
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

    onBlurName() {
      if (this.form.name) {
        this.form.name = this.form.name.trim();
        if (
            !this.form.fullName ||
            (this.form.fullName && this.form.fullName.trim() === "")
        ) {
          this.form.fullName = this.form.name;
        }
      }
    },

    validName() {
      if (!this.form.name) return true;
      else if (this.form.name.trim().length === 0) return true;
      return false;
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

      let err = false;
      const method = this.mode === "ins" ? "insertVer" : "updateVer";
      this.form.lang = localStorage.getItem("curLang")
      api
          .post(baseURL, {
                method: "reltyp/" + method,
            params: [{rec: this.form}],
          })
          .then(
              (response) => {
                err = false;
                this.$emit("ok", response.data.result.records[0]);
                notifySuccess(this.$t("success"));
              },
              (error) => {
                //console.log("error.response.data=>>>", error.response.data.error.message)
                err = true;
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
  },
};
</script>
