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
        <q-item-label class="text-grey-7" style="font-size: 0.8em">{{
            txt_lang("cls")
          }}
        </q-item-label>
        <treeselect
            :options="optCls"
            maxHeight="800"
            v-model="cls"
            :normalizer="normalizer"
            :placeholder="txt_lang('select')"
            :noChildrenText="txt_lang('noChilds')"
            :noResultsText="txt_lang('noResult')"
            :noOptionsText="txt_lang('noResult')"
            @close="fnCloseCls"
        />

        <q-item-label
            class="text-grey-7"
            style="font-size: 0.8em; margin-top: 10px"
        >{{ txt_lang("obj") }}
        </q-item-label
        >
        <treeselect
            :options="optObj"
            maxHeight="800"
            v-model="obj"
            :placeholder="txt_lang('select')"
            :noChildrenText="txt_lang('noChilds')"
            :noResultsText="txt_lang('noResult')"
            :noOptionsText="txt_lang('noResult')"
            @close="fnCloseObj"
        />
      </q-card-section>

      <q-card-actions align="right">
        <q-btn
            color="primary"
            icon="save"
            :label="txt_lang('save')"
            @click="onOKClick"
            :disable="validOk()"
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
import treeselect from "vue3-treeselect";
import "vue3-treeselect/dist/vue3-treeselect.css";

import {api, baseURL} from "boot/axios";
import {notifyError, notifySuccess, pack, txt_lang} from "src/utils/jsutils";

export default {
  components: {treeselect},

  props: ["mode", "data", "typ"],

  data() {
    return {
      form: this.data,
      optCls: [],
      cls: this.data.clsOrObjCls,

      optObj: [],
      obj: this.data.obj,
    };
  },

  emits: [
    // REQUIRED
    "ok",
    "hide",
  ],

  methods: {
    txt_lang,
    fetchData(cls) {
      api
          .post(baseURL, {
            method: "obj/loadObjTreeSelect",
            params: [cls],
          })
          .then(
              (response) => {
                this.optObj = pack(response.data.result.records, "ord");
              },
              (error) => {
                let msg
                if (error.response) msg = error.response.data.error.message;
                else msg = error.message;
                notifyError(msg);
              }
          );
    },

    fnCloseCls(v) {
      this.form.clsOrObjCls = v;
      this.fetchData(v);
    },

    fnCloseObj(v) {
      this.form.obj = v;
    },

    normalizer(node) {
      return {
        id: node.ent,
        label: node.name,
      };
    },

    validOk() {
      return !this.form.clsOrObjCls;
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

      const method =
          this.mode === "ins" ? "insertNotExtended" : "updateNotExtended";
      let err = false;
      api
          .post(baseURL, {
            method: "typ/" + method,
            params: [this.form],
          })
          .then(
              (response) => {
                err = false;
                this.$emit("ok", response.data.result.records[0]);
                notifySuccess(this.$t("success"));
              },
              (error) => {
                err = true;
                //console.log("error.response.data=>>>", error.response.data.error.message)
                let msg = error.response.data.error.message
                    ? error.response.data.error.message
                    : error.message;
                notifyError(msg);
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
          method: "typ/loadClsTree",
          params: [{typ: this.typ, lang: localStorage.getItem("curLang")}],
        })
        .then((response) => {
          this.optCls = pack(response.data.result.records, "ord");
          //console.log("optCls", this.optCls)
        });

    if (this.mode === "upd") {
      this.fetchData(this.form.clsOrObjCls);
    }

  },
};
</script>
