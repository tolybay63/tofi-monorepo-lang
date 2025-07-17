<template>
  <div class="no-padding no-margin">
    <q-table
        style="height: calc(100vh - 220px); width: 100%"
        color="primary"
        card-class="bg-amber-1"
        table-class="text-grey-8"
        row-key="id"
        :columns="cols"
        :rows="rows"
        :wrap-cells="true"
        :table-colspan="2"
        table-header-class="text-bold text-white bg-blue-grey-13"
        separator="cell"
        :filter="filter"
        :loading="loading"
        :dense="dense"
        :rows-per-page-options="[0]"
        selection="single"
        v-model:selected="selected"
    >
      <template #bottom-row>
        <q-td colspan="100%" v-if="selected.length > 0" class="text-bold">
          {{ this.infoSelected(selected[0]) }}
        </q-td>
        <q-td colspan="100%" v-else-if="this.rows.length > 0" class="text-bold">
          {{ txt_lang("infoRow") }}
        </q-td>
      </template>

      <template v-slot:top>
        <div style="font-size: 1.2em; font-weight: bold;">{{ txt_lang("notextended") }}</div>

        <q-space/>
        <q-btn
            v-if="hasTarget('mdl:mn_ds:typ:sel:notext:ins')"
            :dense="dense"
            icon="post_add"
            color="secondary"
            :disable="loading"
            @click="editRow(null, 'ins')"
        >
          <q-tooltip transition-show="rotate" transition-hide="rotate">
            {{ txt_lang("newRecord") }}
          </q-tooltip>
        </q-btn>
        <q-btn
            v-if="hasTarget('mdl:mn_ds:typ:sel:notext:upd')"
            :dense="dense"
            icon="edit"
            color="secondary"
            class="q-ml-sm"
            :disable="loading || selected.length === 0"
            @click="editRow(selected[0], 'upd')"
        >
          <q-tooltip transition-show="rotate" transition-hide="rotate">
            {{ txt_lang("editRecord") }}
          </q-tooltip>
        </q-btn>
        <q-btn
            v-if="hasTarget('mdl:mn_ds:typ:sel:notext:del')"
            :dense="dense"
            icon="delete"
            color="secondary"
            class="q-ml-sm"
            :disable="loading || selected.length === 0"
            @click="removeRow(selected[0])"
        >
          <q-tooltip transition-show="rotate" transition-hide="rotate">
            {{ txt_lang("deletingRecord") }}
          </q-tooltip>
        </q-btn>

        <q-space/>
        <q-input
            :dense="dense"
            debounce="300"
            color="primary"
            :model-value="filter"
            v-model="filter"
            :label="txt_lang('txt_filter')"
        >
          <template v-slot:append>
            <q-icon name="search"/>
          </template>
        </q-input>
      </template>

      <template #loading>
        <q-inner-loading :showing="loading" color="secondary"></q-inner-loading>
      </template>
    </q-table>
  </div>
</template>

<script>

import {api, baseURL} from "boot/axios";
import {hasTarget, notifyError, notifyInfo, notifySuccess, txt_lang} from "src/utils/jsutils";
import UpdateNotExtended from "pages/typ/notextended/UpdateNotExtended.vue";

export default {
  name: "NotExtended",
  props: ["typParent"],

  data: function () {
    return {
      cols: [],
      rows: [],
      filter: "",
      loading: false,
      selected: [],
      dense: true,

      typ: this.typParent,
    };
  },
  methods: {
    txt_lang,
    hasTarget,

    infoSelected(row) {
      let o =
          row["nameObj"] !== undefined
              ? " - " + this.$t("obj") + ": " + row["nameObj"]
              : "";
      return this.$t("cls") + ": " + row["nameObj"] + o;
    },

    editRow(rec, mode) {
      let data = {
        typ: this.typParent,
/*
        id: 0,
        clsOrObjCls: null,
        obj: null,
*/
      };

      if (mode === "upd") {
        data = {
          id: rec.id,
          typ: rec.typ,
          clsOrObjCls: rec.clsOrObjCls,
          obj: rec.obj,
        };
      }
      this.$q
          .dialog({
            component: UpdateNotExtended,
            componentProps: {
              mode: mode,
              data: data,
              typ: this.typParent,
              // ...
            },
          })
          .onOk((r) => {
            if (mode === "ins") {
              this.rows.push(r);
              this.selected = [];
              this.selected.push(r);
            } else {
              for (let key in r) {
                if (r.hasOwnProperty(key)) {
                  rec[key] = r[key];
                }
              }
            }
          });
    },

    removeRow(rec) {
      this.$q
          .dialog({
            title: this.$t("confirmation"),
            message:
                this.$t("deleteRecord") +
                '<div style="color: plum">(' +
                rec["nameClass"] +
                rec["nameObj"]
                    ? "; " + rec["nameObj"]
                    : "" + ")</div>",
            html: true,
            cancel: true,
            persistent: true,
            focus: "cancel",
          })
          .onOk(() => {
            let index = this.rows.findIndex((row) => row.id === rec.id);
            api
                .post(baseURL, {
                  method: "typ/deleteNotExtended",
                  params: [rec],
                })
                .then(
                    () => {
                      this.rows.splice(index, 1);
                      this.selected = [];
                      notifySuccess(this.$t("success"));
                    },
                    (error) => {
                      let msg
                      if (error.response) msg = error.response.data.error.message;
                      else msg = error.message;
                      notifyError(msg);
                    }
                );
          })
          .onCancel(() => {
            notifyInfo(this.$t("canceled"));
          });
    },

    fetchData() {
      this.loading = true;
      api
          .post(baseURL, {
            method: "typ/loadNotExtended",
            params: [this.typParent, localStorage.getItem("curLang")],
          })
          .then((response) => {
            this.rows = response.data.result.records;
          })
          .catch((error) => {

            let msg = error.message;
            if (error.response) msg = error.response.data.error.message;

            notifyError(msg);
            //
          })
          .finally(() => {
            //setTimeout(() => {
            this.loading = false;
            //}, 1000)
          });
    },

    getColumns() {
      return [
        {
          name: "nameClass",
          label: this.$t("cls"),
          field: "nameClass",
          align: "left",
          classes: "bg-blue-grey-1",
          headerStyle: "font-size: 1.2em",
          style: "text-align: left; width:50%",
        },
        {
          name: "nameObj",
          label: this.$t("obj"),
          field: "nameObj",
          align: "left",
          classes: "bg-blue-grey-1",
          headerStyle: "font-size: 1.2em",
          style: "text-align: left; width:50%",
        },
      ];
    },
  },

  mounted() {
    this.typ = parseInt(this.$route["params"].typ, 10);
    //console.log("typ", this.typ)
    //console.log("typParent", this.typParent)
    this.fetchData();
  },

  created() {
    this.cols = this.getColumns();
    //
  },

  setup() {
  }

};
</script>

<style scoped></style>
