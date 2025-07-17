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
            :model-value="myData.name"
            v-model="myData.name"
            autofocus
            @blur="onBlurName"
            :label="txt_lang('fldName')"
            :rules="[(val) => (!!val && !!val.trim()) || txt_lang('req')]"
        >
        </q-input>
        <!-- fullName-->
        <q-input
            :dense="dense"
            :model-value="myData.fullName"
            v-model="myData.fullName"
            :label="txt_lang('fldFullName')"
            :rules="[(val) => (!!val && !!val.trim()) || txt_lang('req')]"
        >
        </q-input>

        <!-- dbeg-->
        <q-input
            :dense="dense"
            type="date"
            stack-label
            v-model="myData.dbeg"
            :model-value="myData.dbeg"
            clearable
            :label="txt_lang('fldDbeg')"
        >
        </q-input>
        <!-- dend-->
        <q-input
            :dense="dense"
            type="date"
            stack-label
            v-model="myData.dend"
            :model-value="myData.dend"
            clearable
            :label="txt_lang('fldDend')"
        >
        </q-input>

        <!-- cmt -->
        <q-input
            :dense="dense"
            :model-value="myData.cmt"
            v-model="myData.cmt"
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
  props: ["form", "mode", "dense"],

  data() {
    return {
      myData: this.form,
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
      if (this.myData.name) {
        this.myData.name = this.myData.name.trim();
        if (
            !this.myData.fullName ||
            (this.myData.fullName && this.myData.fullName.trim() === "")
        ) {
          this.myData.fullName = this.myData.name;
        }
      }
    },

    validName() {
      if (!this.myData.name) return true;
      else if (this.myData.name.trim().length === 0) return true;
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
      let err = false
      const method = this.mode === "ins" ? "insertVer" : "updateVer";
      this.myData.lang = localStorage.getItem("curLang")
      api
          .post(baseURL, {
            method: "typ/" + method,
            params: [{rec: this.myData}],
          })
          .then(
              (response) => {
                console.info("load ver rec", response.data.result.records[0])
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
  },
};
</script>
