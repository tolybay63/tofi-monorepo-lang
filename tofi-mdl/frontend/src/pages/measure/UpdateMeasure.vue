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
        <q-item-section v-if="isChild">
          {{ txt_lang("baseMeasure") }}: {{ parentName }}
        </q-item-section>

        <!-- name -->
        <q-input
            v-model="form.name"
            :model-value="form.name"
            autofocus
            @blur="onBlurName"
            :label="txt_lang('fldName')"
            :rules="[(val) => (!!val && !!val.trim()) || txt_lang('req')]"
        >
        </q-input>
        <!-- fullName-->
        <q-input
            v-model="form.fullName"
            :model-value="form.fullName"
            :label="txt_lang('fldFullName')"
            :rules="[(val) => (!!val && !!val.trim()) || txt_lang('req')]"
        >
        </q-input>

        <!-- cod -->
        <q-input
            v-model="form.cod"
            :model-value="form.cod"
            :label="txt_lang('code')"
            :placeholder="txt_lang('msgCodeGen')"
        />
        <!-- accessLevel -->
        <q-select
            v-model="al"
            :options="options"
            :label="txt_lang('accessLevel')"
            option-value="id"
            :option-label="dictText()"
            map-options
            :model-value="al"
            @update:model-value="fnSelect()"
        />

        <!-- kFromBase -->
        <q-input
            v-if="isChild"
            :model-value="form.kFromBase"
            v-model="form.kFromBase"
            :label="txt_lang('kfcMeasure')"
            type="number"
            input-class="text-right"
        >
        </q-input>

        <!-- cmt -->
        <q-input
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
            color="primary"
            icon="save"
            :label="txt_lang('save')"
            @click="onOKClick"
            :disable="validName()"
        />
        <q-btn
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
  props: ["mode", "isChild", "parentName", "data"],

  data() {
    return {
      form: this.data,
      options: [],
      al: this.data.accessLevel === 0 ? 1 : this.data.accessLevel,
    };
  },
  //al: this.form.accessLevel===0 ? 1 : this.form.accessLevel
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

    fnSelect() {
      //console.log("select", this.al)
      this.form.accessLevel = this.al.id;
    },

    dictText() {
      return "text";
    },

    validName() {
      return (
          !this.form.name ||
          !this.form.fullName ||
          parseFloat(this.form.kFromBase) <= 0
      );
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

      delete this.form["accessLevel_text"];
      const method = this.mode === "ins" ? "insert" : "update";
      this.form.accessLevel =
          typeof this.al === "object" ? this.al.id : this.al;
      this.form.lang = localStorage.getItem("curLang")
      api
          .post(baseURL, {
            method: "measure/" + method,
            params: [{rec: this.form}],
          })
          .then(
              (response) => {
                //this.$emit("ok", {res: true});
                this.$emit("ok", response.data.result.records[0]);
                notifySuccess(this.$t("success"));
              },
              (error) => {
                //console.log("error.response.data=>>>", error.response.data.error.message)
                let msg = error.response.data.error.message
                    ? error.response.data.error.message
                    : error.message;
                notifyError(msg);
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
    const lang = localStorage.getItem("curLang")
    api
        .post(baseURL, {
          method: "dict/load",
          params: [{dict: "FD_AccessLevel", lang: lang}],
        })
        .then((response) => {
          this.options = response.data.result.records;
        });

    return {};
  },
};
</script>
