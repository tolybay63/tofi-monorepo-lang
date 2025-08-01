<template>
  <q-dialog
      ref="dialog"
      @hide="onDialogHide"
      persistent
      autofocus
      transition-show="slide-up"
      transition-hide="slide-down"
  >
    <q-card class="q-dialog-plugin" style="width: 800px">
      <q-bar class="text-white bg-primary">
        <div>{{ txt_lang("update") }}</div>
      </q-bar>

      <q-card>
        <q-card-section>
          <!-- cls -->
          <q-select
              v-model="cls" :model-value="cls" use-input map-options
              input-debounce="0" :label="txt_lang('cls')" option-value="id" option-label="name"
              dense options-dense :options="optCls" clearable
              @update:model-value="inputValueCls" @filter="filterFnCls"
              @clear="fnClearCls"
          >
            <template v-slot:no-option>
              <q-item>
                <q-item-section class="text-grey">
                  {{ txt_lang("noResults") }}
                </q-item-section>
              </q-item>
            </template>
          </q-select>

          <!-- obj -->
          <q-select
              v-model="obj" :model-value="obj" use-input map-options
              input-debounce="0" :label="txt_lang('obj')" option-value="id" option-label="name"
              dense options-dense :options="optObj" clearable
              @update:model-value="inputValueObj" @filter="filterFnObj"
              @clear="fnClearObj" :disable="!form.cls"
          >
            <template v-slot:no-option>
              <q-item>
                <q-item-section class="text-grey">
                  {{ txt_lang("noResults") }}
                </q-item-section>
              </q-item>
            </template>
          </q-select>

          <!-- isDefault -->
          <q-toggle
              style="margin-left: 5px; margin-top: 15px"
              dense
              v-model="form.isDefault"
              :model-value="form.isDefault"
              :label="txt_lang('isDefault')"
          />


        </q-card-section>
      </q-card>

      <q-card-actions align="right">
        <q-btn
            :loading="loading" dense color="primary" icon="save" :label="txt_lang('save')"
            @click="onOKClick" :disable="validSave()"
        >
          <template #loading>
            <q-spinner-hourglass color="white"/>
          </template>
        </q-btn>

        <q-btn
            dense color="primary" icon="cancel" :label="txt_lang('cancel')" @click="onCancelClick"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import {api, baseURL} from "boot/axios";
import {notifyError, txt_lang} from "src/utils/jsutils";


export default {
  props: ["data", "act", "prop", "typ", "mode"],
  //components: {QTreeTable},

  data() {

    return {
      form: this.data,
      lang: this.lg,
      cls: this.data.cls,
      optCls: [],
      optClsOrg: [],

      obj: this.data.obj,
      optObj: [],
      optObjOrg: [],
      loading: false,
    };
  },

  emits: [
    // REQUIRED
    "ok",
    "hide",
  ],

  methods: {
    txt_lang,

    //todo
    validSave() {
      return false;
    },

    loadObj(cls) {
      this.loading = true;
      api
        .post(baseURL, {
          method: this.act + "/loadProviderObjForSelect",
          params: [cls],
        })
        .then((response) => {
          this.optObj = response.data.result.records;
          this.optObjOrg = response.data.result.records;
        })
        .finally(() => {
          this.loading = false;
        })
    },

    fnClearCls() {
      this.form.cls = null;
    },

    inputValueCls(val) {
      if (val) {
        this.form.cls = val.id;
        this.loadObj(val.id)
      }
    },

    filterFnCls(val, update) {
      if (val === null || val === "") {
        update(() => {
          this.optCls = this.optClsOrg;
        });
        return;
      }
      update(() => {
        if (this.optClsOrg.length < 2) return;
        const needle = val.toLowerCase();
        let name = "name";
        this.optCls = this.optClsOrg.filter((v) => {
          return v[name].toLowerCase().indexOf(needle) > -1;
        });
      });
    },

    fnClearObj() {
      this.form.obj = null;
    },

    inputValueObj(val) {
      if (val) {
        this.form.obj = val.id;
      }
    },

    filterFnObj(val, update) {
      if (val === null || val === "") {
        update(() => {
          this.optObj = this.optObjOrg;
        });
        return;
      }
      update(() => {
        if (this.optObjOrg.length < 2) return;
        const needle = val.toLowerCase();
        let name = "name";
        this.optObj = this.optObjOrg.filter((v) => {
          return v[name].toLowerCase().indexOf(needle) > -1;
        });
      });
    },


    // following method is REQUIRED
    // (don't change its name --> "show")
    show() {
      this.$refs.dialog.show();
    },

    onOKClick() {
      this.loading = true;
      api
          .post(baseURL, {
            method: this.act + "/saveProvider",
            params: [this.form, this.mode],
          })
          .then(
              (response) => {
                this.$emit("ok", response.data.result.records[0]);
              },
              (error) => {
                let msg = error.message;
                if (error.response.data.error.message)
                  msg = error.response.data.error.message;
                notifyError(msg);
              }
          )
          .finally(() => {
            this.loading = false;
            this.hide();
          });
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

    onCancelClick() {
      // we just need to hide the dialog
      this.hide();
    },

  },

  created() {

    this.loading = true;
    api
        .post(baseURL, {
          method: this.act + "/loadProviderClsForSelect",
          params: [this.prop, this.typ, this.mode],
        })
        .then((response) => {
          this.optCls = response.data.result.records;
          this.optClsOrg = response.data.result.records;
        })
        .finally(() => {
          this.loading = false;
        })

    if (this.mode==="upd") {
      this.loadObj(this.cls)
    }
  },


};
</script>
